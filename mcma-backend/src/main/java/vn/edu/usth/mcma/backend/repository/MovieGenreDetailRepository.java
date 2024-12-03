package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.MovieGenreDetail;

@Repository
public interface MovieGenreDetailRepository extends JpaRepository<MovieGenreDetail, Long> {
}
