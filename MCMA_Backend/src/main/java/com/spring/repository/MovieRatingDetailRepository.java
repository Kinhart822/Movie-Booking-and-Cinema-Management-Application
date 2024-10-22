package com.spring.repository;

import com.spring.entities.MoviePerformer;
import com.spring.entities.MovieRatingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRatingDetailRepository extends JpaRepository<MovieRatingDetail, Integer> {
    @Query("SELECT mrd FROM MovieRatingDetail mrd JOIN mrd.movieSet m WHERE m.id = :movieId")
    List<MovieRatingDetail> findMovieRatingDetailsByMovieId(@Param("movieId") Integer movieId);
}
