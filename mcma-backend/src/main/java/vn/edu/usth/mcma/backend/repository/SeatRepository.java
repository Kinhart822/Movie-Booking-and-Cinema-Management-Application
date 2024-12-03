package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.Seat;
import vn.edu.usth.mcma.backend.entity.SeatPK;

@Repository
public interface SeatRepository extends CrudRepository<Seat, SeatPK> {
}
