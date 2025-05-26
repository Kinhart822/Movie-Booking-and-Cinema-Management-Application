import React, { useState, useEffect } from "react";
import Timeline from "react-calendar-timeline";
import "react-calendar-timeline/style.css";
import CinemaHeader from "../CinemaHeader/CinemaHeader";
import CinemaSideBar from "../CinemaSideBar/CinemaSideBar";
import LoadingSpinner from "../../CinemaDashboard/CinemaManagement/LoadingSpinner/LoadingSpinner";
import { api } from "../../../api/apiClient";
import "./CinemaSchedules.css";
import moment from "moment";

const CinemaSchedules = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [timelineItems, setTimelineItems] = useState([]);
  const [groups, setGroups] = useState([]);
  const [error, setError] = useState(null);
  const [cinemaFilter, setCinemaFilter] = useState(null);
  const [cinemas, setCinemas] = useState([]);
  const [screens, setScreens] = useState([]);
  const [movies, setMovies] = useState([]);
  const [defaultTimeRange, setDefaultTimeRange] = useState({
    defaultTimeStart: new Date().setHours(new Date().getHours() - 12),
    defaultTimeEnd: new Date().setHours(new Date().getHours() + 12),
  });
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [newSchedule, setNewSchedule] = useState({
    screenId: "",
    movieId: "",
    startTime: "",
  });

  // Fetch schedules for the cinema
  const fetchSchedules = async (cinemaId) => {
    try {
      const { groups: fetchedGroups, items: fetchedItems } =
        await api.schedules.getByCinema(cinemaId);
      setGroups(fetchedGroups);
      setTimelineItems(fetchedItems);

      if (fetchedItems.length > 0) {
        const now = moment();
        const nearestFutureItem = fetchedItems
          .filter((item) => moment(item.start_time).isAfter(now)) // Only future items
          .sort((a, b) => moment(a.start_time) - moment(b.start_time))[0]; // Find the nearest

        if (nearestFutureItem) {
          const nearestTime = moment(nearestFutureItem.start_time);
          setDefaultTimeRange({
            defaultTimeStart: nearestTime.clone().subtract(12, "hours").toDate(),
            defaultTimeEnd: nearestTime.clone().add(12, "hours").toDate(),
          });
        }
      }
    } catch (err) {
      setError(err.message);
      console.error("Failed to fetch schedules:", err);
    } finally {
      setIsLoading(false);
    }
  };

  const fetchCinemas = async () => {
    try {
      const cinemasData = await api.cinemas.filterCinema();
      if (Array.isArray(cinemasData) && cinemasData.length > 0) {
        setCinemas(cinemasData);
        setCinemaFilter(cinemasData[0].cinemaId);
      } else {
        setCinemas([]);
      }
    } catch (error) {
      console.error("Error fetching cinemas:", error.message);
    }
  };

  const fetchScreensAndMovies = async () => {
    try {
      const screensData = await api.screens.filterScreen(cinemaFilter);
      const moviesData = await api.movies.filterMovie();
      setScreens(screensData);
      setMovies(moviesData);
    } catch (error) {
      console.error("Error fetching screens or movies:", error.message);
    }
  };

  const handleAddSchedule = async () => {
    try {
      const payload = [{
        screenId: newSchedule.screenId,
        movieId: newSchedule.movieId,
        startDateTime: moment(newSchedule.startTime).toISOString(),
      },];
      await api.schedules.addSchedules(payload);
      setIsModalOpen(false);
      fetchSchedules(cinemaFilter);
    } catch (error) {
      console.error("Error adding schedule:", error.message);
    }
  };

  useEffect(() => {
    fetchCinemas();
  }, []);

  useEffect(() => {
    if (cinemaFilter) {
      fetchSchedules(cinemaFilter);
      fetchScreensAndMovies();
    }
  }, [cinemaFilter]);

  const handleItemMove = (itemId, dragTime, newGroupOrder) => {
    const roundToNearest15Min = (time) => {
      const minutes = time.minutes();
      const roundedMinutes = Math.round(minutes / 15) * 15;
      return time.clone().minutes(roundedMinutes).seconds(0);
    };

    setTimelineItems((items) =>
      items.map((item) => {
        if (item.id === itemId) {
          const snappedStartTime = roundToNearest15Min(moment(dragTime));
          const movieLength = moment
            .duration(item.end_time.diff(item.start_time))
            .asMinutes();

          return {
            ...item,
            start_time: snappedStartTime,
            end_time: moment(snappedStartTime).add(movieLength, "minutes"),
            group: groups[newGroupOrder].id,
          };
        }
        return item;
      })
    );
  };

  return (
    <div className="cinema-management-container">
      {isLoading && <LoadingSpinner />}
      <CinemaHeader />
      <div className="dashboard-content">
        <CinemaSideBar />
        <div className="main-content">
          <div className="cinema-management">
            <div className="cinema-header">
              <div className="header-title">
                <h1>Movie Schedules</h1>
                <p>Manage your movie schedules</p>
              </div>
              <div className="filters">
                {Array.isArray(cinemas) && cinemas.length > 0 ? (
                  <select
                    value={cinemaFilter || ""}
                    onChange={(e) => setCinemaFilter(e.target.value)}
                    className="status-filter"
                  >
                    {cinemas.map((cinema) => (
                      <option key={cinema.cinemaId} value={cinema.cinemaId}>
                        {cinema.cinemaName}
                      </option>
                    ))}
                  </select>
                ) : (
                  <p>Loading cinemas...</p>
                )}
              </div>
            </div>
            <button
              className="add-schedule-button"
              onClick={() => setIsModalOpen(true)}
            >
              Add Schedule
            </button>
            {isModalOpen && (
              <div className="modal">
                <div className="modal-content">
                  <h2>Add New Schedule</h2>
                  <label>
                    Screen:
                    <select
                      value={newSchedule.screenId}
                      onChange={(e) =>
                        setNewSchedule({ ...newSchedule, screenId: e.target.value })
                      }
                    >
                      <option value="">Select Screen</option>
                      {screens.map((screen) => (
                        <option key={screen.screenId} value={screen.screenId}>
                          {screen.screenName}
                        </option>
                      ))}
                    </select>
                  </label>
                  <label>
                    Movie:
                    <select
                      value={newSchedule.movieId}
                      onChange={(e) =>
                        setNewSchedule({ ...newSchedule, movieId: e.target.value })
                      }
                    >
                      <option value="">Select Movie</option>
                      {movies.map((movie) => (
                        <option key={movie.movieId} value={movie.movieId}>
                          {movie.movieName}
                        </option>
                      ))}
                    </select>
                  </label>
                  <label>
                    Start Time:
                    <input
                      type="datetime-local"
                      value={newSchedule.startTime}
                      onChange={(e) =>
                        setNewSchedule({ ...newSchedule, startTime: e.target.value })
                      }
                    />
                  </label>
                  <div className="modal-actions">
                    <button onClick={handleAddSchedule}>OK</button>
                    <button onClick={() => setIsModalOpen(false)}>Cancel</button>
                  </div>
                </div>
              </div>
            )}
            {error ? (
              <div className="error-message">{error}</div>
            ) : (
              <div className="schedule-timeline">
                <Timeline
                  groups={groups}
                  items={timelineItems}
                  visibleTimeStart={defaultTimeRange.defaultTimeStart}
                  visibleTimeEnd={defaultTimeRange.defaultTimeEnd}
                  // onTimeChange={(
                  //   visibleTimeStart,
                  //   visibleTimeEnd,
                  //   updateScrollCanvas
                  // ) => {
                  //   updateScrollCanvas(
                  //     defaultTimeRange.defaultTimeStart,
                  //     defaultTimeRange.defaultTimeEnd
                  //   );
                  // }}
                  lineHeight={50}
                  canResize={false}
                  onItemMove={handleItemMove}
                  timeSteps={{
                    second: 0,
                    minute: 15,
                    hour: 1,
                    day: 1,
                    month: 1,
                    year: 1,
                  }}
                  dragSnap={15 * 60 * 1000} // Snap to 15-minute intervals
                />
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default CinemaSchedules;
