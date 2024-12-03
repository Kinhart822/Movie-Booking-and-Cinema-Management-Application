package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.MoviePerformer;
import vn.edu.usth.mcma.backend.entity.MoviePerformerPK;

@Repository
public interface MoviePerformerRepository extends JpaRepository<MoviePerformer, MoviePerformerPK> {
}
