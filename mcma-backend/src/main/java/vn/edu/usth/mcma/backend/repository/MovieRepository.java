package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.dao.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
