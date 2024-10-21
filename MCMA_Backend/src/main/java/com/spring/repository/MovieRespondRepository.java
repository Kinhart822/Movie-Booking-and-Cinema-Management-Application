package com.spring.repository;

import com.spring.entities.MovieRespond;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRespondRepository extends JpaRepository<MovieRespond, Integer> {
    List<MovieRespond> findByUserId(Integer userId);
    List<MovieRespond> findByMovieId(Integer movieId);
    MovieRespond findByUserIdAndMovieId(Integer userId, Integer movieId);
}


