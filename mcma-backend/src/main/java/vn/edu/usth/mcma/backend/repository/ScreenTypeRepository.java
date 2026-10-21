package vn.edu.usth.mcma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.usth.mcma.backend.entity.ScreenType;

public interface ScreenTypeRepository extends JpaRepository<ScreenType, String> {
    List<ScreenType> findAllByStatus(Integer status);
}
