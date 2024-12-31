package com.spring.repository;

import com.spring.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Integer> {
    @Query("SELECT c FROM Cinema c WHERE  c.city.id = :cityId")
    List<Cinema> findByCityId(@Param("cityId") Integer cityId);

    @Query("SELECT c FROM Cinema c JOIN c.city ct JOIN ct.movie m WHERE ct.id = :cityId AND m.id = :movieId")
    List<Cinema> findByMovieIdAndCityId(@Param("movieId") Integer movieId, @Param("cityId") Integer cityId);

}
