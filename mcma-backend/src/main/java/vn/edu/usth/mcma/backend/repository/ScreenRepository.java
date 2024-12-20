package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.Cinema;
import vn.edu.usth.mcma.backend.entity.Screen;

import java.util.List;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {
    List<Screen> findAllByNameContaining(String name, Pageable pageable);
}
