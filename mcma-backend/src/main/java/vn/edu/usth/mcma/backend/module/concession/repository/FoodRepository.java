package vn.edu.usth.mcma.backend.module.concession.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.usth.mcma.backend.module.concession.entity.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByNameContaining(String query);
}
