package com.spring.repository;

import com.spring.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    @Query("SELECT c FROM City c WHERE c.id = :cityId AND c.movie.id = :movieId")
    Optional<City> findByIdAndMovieId(@Param("cityId") Integer cityId, @Param("movieId") Integer movieId);
}
