package com.spring.repository;

import com.spring.entities.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Integer> {
    @Query("SELECT s FROM Screen s WHERE s.cinema.id = :cinemaId")
    List<Screen> findByCinemaId(@Param("cinemaId") Integer cinemaId);
}
