package vn.edu.usth.mcma.service.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.service.common.dao.MovieResponse;

@Repository
public interface MovieResponseRepository extends JpaRepository<MovieResponse, Long> {
}
