package vn.edu.usth.mcma.service.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.service.common.domain.MovieGenreDetail;

@Repository
public interface MovieGenreDetailRepository extends JpaRepository<MovieGenreDetail, Long> {
}
