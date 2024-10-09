package com.spring.repository;

import com.spring.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
    @Query("SELECT f.id FROM Food f WHERE f.name = :name")
    Integer findFoodIdByFood(@Param("name") String name);
}
