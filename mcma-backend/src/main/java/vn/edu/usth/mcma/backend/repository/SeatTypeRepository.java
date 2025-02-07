package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.SeatType;

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, String> {
}
