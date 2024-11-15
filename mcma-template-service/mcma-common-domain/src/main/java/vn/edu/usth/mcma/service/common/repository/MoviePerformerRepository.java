package vn.edu.usth.mcma.service.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.service.common.dao.MoviePerformer;
import vn.edu.usth.mcma.service.common.dao.MoviePerformerPK;

@Repository
public interface MoviePerformerRepository extends JpaRepository<MoviePerformer, MoviePerformerPK> {
}
