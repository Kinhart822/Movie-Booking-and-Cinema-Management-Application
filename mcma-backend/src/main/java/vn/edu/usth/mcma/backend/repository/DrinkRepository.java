package vn.edu.usth.mcma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.usth.mcma.backend.entity.Drink;

public interface DrinkRepository extends JpaRepository<Drink, Long> {
    List<Drink> findAllByNameContaining(String query);
}
