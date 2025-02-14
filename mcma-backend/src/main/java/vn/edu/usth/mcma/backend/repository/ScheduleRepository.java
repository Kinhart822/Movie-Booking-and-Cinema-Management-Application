package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.Movie;
import vn.edu.usth.mcma.backend.entity.Schedule;
import vn.edu.usth.mcma.backend.entity.Screen;
import vn.edu.usth.mcma.backend.entity.SeatType;

import java.time.Instant;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(nativeQuery = true, value = """
            select *
            from schedule s
            where s.screen_id = :screenId
              and s.start_date_time < date_add(:start, interval :length minute)
              and date_add(s.start_date_time, interval :length minute) > :start
            """)
    List<Schedule> eventsInRange(@Param(value = "screenId") Long screenId, @Param(value = "start") Instant time, @Param(value = "length") Integer length);
    List<Schedule> findAllByMovieAndStartDateTimeIsAfterAndStatusIs(Movie movie, Instant time, Integer status);
    List<Schedule> findAllByMovieAndScreenAndStartDateTimeIsAfterAndStatusIs(Movie movie, Screen screen, Instant time, Integer status);
    List<Schedule> findAllByStartDateTimeIsAfterAndStatusIs(Instant time, Integer status);

    @Query("select s from Schedule s left join Cinema c on s.screen.cinema = c where c.id = :cinemaId")
    List<Schedule> findAllScheduleByCinema(Long cinemaId);

    List<Schedule> findAllByScreen(Screen screen);
}