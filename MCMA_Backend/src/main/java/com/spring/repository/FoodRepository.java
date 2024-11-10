package com.spring.repository;

import com.spring.entities.Cinema;
import com.spring.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
    List<Food> findByCinema(Cinema cinema);
}
