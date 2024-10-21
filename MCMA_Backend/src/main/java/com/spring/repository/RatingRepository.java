package com.spring.repository;

import com.spring.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    @Query("SELECT r FROM Rating r JOIN r.movieRespond mp WHERE mp.user.id = :userId")
    List<Rating> findByUserId(Integer userId);

    @Query("SELECT r FROM Rating r JOIN r.movieRespond mp WHERE mp.user.id = :userId AND mp.movie.id = :movieId")
    Rating findByUserIdAndMovieId(@Param("userId") Integer userId,
                                  @Param("movieId") Integer movieId);

    @Query("SELECT r FROM Rating r JOIN r.movieRespond mp WHERE mp.movie.id = :movieId")
    List<Rating> findByMovieId(@Param("movieId") Integer movieId);
}
