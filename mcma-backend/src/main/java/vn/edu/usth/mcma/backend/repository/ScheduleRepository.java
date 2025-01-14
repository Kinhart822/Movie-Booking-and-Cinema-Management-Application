package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.dto.SchedulePresentation;
import vn.edu.usth.mcma.backend.entity.Movie;
import vn.edu.usth.mcma.backend.entity.Schedule;
import vn.edu.usth.mcma.backend.entity.Screen;

import java.time.Instant;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(nativeQuery = true, value = """
            select *
            from schedule s
            where (:start between s.start_time and s.end_time
               or :end between s.start_time and s.end_time
               or :start < s.start_time and :end > s.end_time)
               and s.screen_id = :screenId
            """)
    List<Schedule> eventsInRange(@Param(value = "screenId") Long screenId, @Param(value = "start") Instant time, @Param(value = "end") Instant endTime);
    List<Schedule> findAllByMovieAndStartTimeIsAfterAndStatusIs(Movie movie, Instant time, Integer status);
    List<Schedule> findAllByMovieAndScreenAndStartTimeIsAfterAndStatusIs(Movie movie, Screen screen, Instant time, Integer status);
    List<Schedule> findAllByStartTimeIsAfterAndStatusIs(Instant time, Integer status);
}