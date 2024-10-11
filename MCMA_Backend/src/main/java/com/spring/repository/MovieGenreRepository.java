package com.spring.repository;

import com.spring.entities.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenre, Integer> {
    @Query("SELECT mg FROM MovieGenre mg JOIN mg.movieSet m WHERE m.id = :movieId")
    List<MovieGenre> findMovieGenresByMovie(@Param("movieId") Integer movieId);
}

