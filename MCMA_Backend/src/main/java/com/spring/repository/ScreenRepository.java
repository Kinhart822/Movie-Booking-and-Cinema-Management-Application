package com.spring.repository;

import com.spring.entities.Cinema;
import com.spring.entities.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Integer> {
    @Query("SELECT s FROM Screen s WHERE s.id = :screenId AND s.cinema.id = :cinemaId")
    Optional<Screen> findByIdAndCinemaId(@Param("screenId") Integer screenId, @Param("cinemaId") Integer cinemaId);
}
