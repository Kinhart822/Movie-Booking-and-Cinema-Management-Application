package com.spring.repository;

import com.spring.entities.MovieSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MovieScheduleRepository extends JpaRepository<MovieSchedule, Integer> {

    @Query("SELECT ms " +
            "FROM MovieSchedule ms " +
            "WHERE ms.movie.id = :movieId " +
            "AND ms.movie.cinema.id = :cinemaId " +
            "AND DATE(ms.startTime) = :selectedDate")
    List<MovieSchedule> findSchedulesByMovieCinemaAndDate(
            @Param("movieId") Integer movieId,
            @Param("cinemaId") Integer cinemaId,
            @Param("selectedDate") LocalDate selectedDate
    );
}


