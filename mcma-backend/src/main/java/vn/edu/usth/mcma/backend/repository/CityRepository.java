package vn.edu.usth.mcma.backend.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.usth.mcma.backend.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findAllByNameContaining(String name, Pageable pageable);

    List<City> findAllByStatus(Integer status);
}
