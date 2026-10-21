package vn.edu.usth.mcma.backend.module.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.usth.mcma.backend.module.movie.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, String> {}
