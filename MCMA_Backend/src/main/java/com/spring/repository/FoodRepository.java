package com.spring.repository;

import com.spring.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

    @Query("SELECT f FROM Food f WHERE f.movieSchedule.id = :movieScheduleId")
    List<Food> findByMovieScheduleId(@Param("movieScheduleId") Integer movieScheduleId);

    @Query("SELECT f.id FROM Food f WHERE f.name = :name")
    Integer findFoodIdByFood(@Param("name") String name);
}
