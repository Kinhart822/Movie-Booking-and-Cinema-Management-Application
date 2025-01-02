package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.Performer;

@Repository
public interface PerformerRepository extends JpaRepository<Performer, Long> {
}
