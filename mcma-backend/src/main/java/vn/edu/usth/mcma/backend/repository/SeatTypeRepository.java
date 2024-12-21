package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.SeatType;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, Long> {
    List<SeatType> findAllByStatusIn(List<Long> statuses);
}
