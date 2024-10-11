package com.spring.repository;

import com.spring.entities.Cinema;
import com.spring.entities.MovieSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Integer> {
    @Query("SELECT c FROM Cinema c WHERE c.id = :cinemaId AND c.city.id = :cityId")
    Optional<Cinema> findByIdAndCityId(@Param("cinemaId") Integer cinemaId, @Param("cityId") Integer cityId);

    List<Cinema> findByCityId(Integer cityId);
}
