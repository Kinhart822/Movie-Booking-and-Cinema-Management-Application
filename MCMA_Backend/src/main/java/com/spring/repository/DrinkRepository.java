package com.spring.repository;

import com.spring.entities.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Integer> {

    @Query("SELECT d FROM Drink d WHERE d.movieSchedule.id = :movieScheduleId")
    List<Drink> findByMovieScheduleId(@Param("movieScheduleId") Integer movieScheduleId);

    @Query("SELECT d.id FROM Drink d WHERE d.name = :name")
    Integer findDrinkIdByFood(@Param("name") String name);
}
