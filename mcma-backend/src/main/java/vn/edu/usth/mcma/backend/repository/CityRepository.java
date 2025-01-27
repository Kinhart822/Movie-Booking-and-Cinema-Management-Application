package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.City;

import java.util.Arrays;
import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findAllByNameContaining(String name, Pageable pageable);

    List<City> findAllByStatus(Integer status);
}
