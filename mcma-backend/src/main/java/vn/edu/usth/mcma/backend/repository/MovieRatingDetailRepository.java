package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.MovieRatingDetail;

@Repository
public interface MovieRatingDetailRepository extends JpaRepository<MovieRatingDetail, Long> {
}
