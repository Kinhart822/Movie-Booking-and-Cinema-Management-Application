import React from "react";
import { Film, Plus, Clock, DollarSign, Ticket } from "lucide-react";
import "./CinemaDashboard.css";
import CinemaHeader from "./CinemaHeader/CinemaHeader";
import CinemaSideBar from "./CinemaSideBar/CinemaSideBar";

const CinemaDashboard = () => {
  const recentBookings = [
    {
      id: 1,
      movie: "Inception",
      seats: "A1, A2",
      time: "20:00",
      amount: "$30",
    },
    {
      id: 2,
      movie: "The Dark Knight",
      seats: "B5, B6",
      time: "18:30",
      amount: "$30",
    },
    {
      id: 3,
      movie: "Interstellar",
      seats: "C3, C4",
      time: "19:15",
      amount: "$30",
    },
  ];

  const upcomingMovies = [
    { id: 1, title: "Dune: Part Two", showtime: "20:00", occupancy: "75%" },
    { id: 2, title: "Deadpool 3", showtime: "18:30", occupancy: "60%" },
    { id: 3, title: "Godzilla x Kong", showtime: "19:15", occupancy: "45%" },
  ];

  return (
    <div className="dashboard-container">
      {/* Top Bar */}

      <CinemaHeader />

      {/* Main Content */}
      <div className="dashboard-content">
        {/* Sidebar */}
        <CinemaSideBar />

        {/* Main Content Area */}
        <div className="main-content">
          {/* Stats Cards */}
          <div className="stats-grid">
            <div className="stat-card">
              <div className="stat-content">
                <div>
                  <p className="stat-label">Total Revenue</p>
                  <h3 className="stat-value">$12,890</h3>
                </div>
                <div className="stat-icon">
                  <DollarSign />
                </div>
              </div>
            </div>
            <div className="stat-card">
              <div className="stat-content">
                <div>
                  <p className="stat-label">Total Bookings</p>
                  <h3 className="stat-value">284</h3>
                </div>
                <div className="stat-icon">
                  <Ticket />
                </div>
              </div>
            </div>
            <div className="stat-card">
              <div className="stat-content">
                <div>
                  <p className="stat-label">Active Movies</p>
                  <h3 className="stat-value">12</h3>
                </div>
                <div className="stat-icon">
                  <Film />
                </div>
              </div>
            </div>
            <div className="stat-card">
              <div className="stat-content">
                <div>
                  <p className="stat-label">Showtime Today</p>
                  <h3 className="stat-value">8</h3>
                </div>
                <div className="stat-icon">
                  <Clock />
                </div>
              </div>
            </div>
          </div>

          {/* Recent Bookings & Upcoming Movies */}
          <div className="info-grid">
            <div className="info-card">
              <div className="info-header">
                <h2>Recent Bookings</h2>
                <button className="view-all">View All</button>
              </div>
              <div className="bookings-list">
                {recentBookings.map((booking) => (
                  <div key={booking.id} className="booking-item">
                    <div>
                      <h3 className="booking-title">{booking.movie}</h3>
                      <p className="booking-details">Seats: {booking.seats}</p>
                    </div>
                    <div className="booking-meta">
                      <p className="booking-amount">{booking.amount}</p>
                      <p className="booking-time">{booking.time}</p>
                    </div>
                  </div>
                ))}
              </div>
            </div>

            <div className="info-card">
              <div className="info-header">
                <h2>Upcoming Movies</h2>
                <button className="add-movie">
                  <Plus className="add-icon" />
                  <span>Add Movie</span>
                </button>
              </div>
              <div className="movies-list">
                {upcomingMovies.map((movie) => (
                  <div key={movie.id} className="movie-item">
                    <div>
                      <h3 className="movie-title">{movie.title}</h3>
                      <p className="movie-time">{movie.showtime}</p>
                    </div>
                    <div className="movie-meta">
                      <p className="occupancy-rate">{movie.occupancy}</p>
                      <p className="occupancy-label">Occupancy</p>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CinemaDashboard;
