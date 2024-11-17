package com.spring.repository;

import com.spring.entities.MovieSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieScheduleRepository extends JpaRepository<MovieSchedule, Integer> {
    @Query("SELECT ms " +
            "FROM MovieSchedule ms " +
            "JOIN ms.movie m " +
            "JOIN ms.cinema c " +
            "WHERE m.id = :movieId " +
            "AND c.id = :cinemaId ")
    List<MovieSchedule> findMovieSchedulesByMovieIdAndCinemaId(
            @Param("movieId") Integer movieId,
            @Param("cinemaId") Integer cinemaId);

    @Query("SELECT ms FROM MovieSchedule ms JOIN ms.cinema c WHERE c.id = :cinemaId")
    List<MovieSchedule> findMovieSchedulesByCinemaId(@Param("cinemaId") Integer cinemaId);

    @Query("SELECT ms " +
            "FROM MovieSchedule ms " +
            "JOIN ms.movie m " +
            "JOIN ms.cinema c " +
            "JOIN ms.screen s " +
            "WHERE m.id = :movieId " +
            "AND c.id = :cinemaId " +
            "AND s.id = :screenId")
    List<MovieSchedule> findMovieSchedulesByMovieAndCinemaAndScreen(
            @Param("movieId") Integer movieId,
            @Param("cinemaId") Integer cinemaId,
            @Param("screenId") Integer screenId
    );
}


