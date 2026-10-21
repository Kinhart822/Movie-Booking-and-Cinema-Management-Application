package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.usth.mcma.backend.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, String> {}
