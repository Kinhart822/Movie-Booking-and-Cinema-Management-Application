package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.dao.MoviePerformerType;

@Repository
public interface MoviePerformerTypeRepository extends JpaRepository<MoviePerformerType, Long> {
}
