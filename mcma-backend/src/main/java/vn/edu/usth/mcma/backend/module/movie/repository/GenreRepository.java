package vn.edu.usth.mcma.backend.module.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.usth.mcma.backend.module.movie.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findAllByStatus(Integer status);
}
