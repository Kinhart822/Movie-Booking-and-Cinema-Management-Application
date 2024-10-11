package com.spring.repository;

import com.spring.entities.MoviePerformer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoviePerformerRepository extends JpaRepository<MoviePerformer, Integer> {
    @Query("SELECT mp FROM MoviePerformer mp JOIN mp.movieSet m WHERE m.id = :movieId")
    List<MoviePerformer> findMoviePerformersByMovieId(@Param("movieId") Integer movieId);
}
