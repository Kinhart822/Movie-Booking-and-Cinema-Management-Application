package vn.edu.usth.mcma.service.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.service.common.dao.Seat;
import vn.edu.usth.mcma.service.common.dao.SeatPK;

@Repository
public interface CommonSeatRepository extends CrudRepository<Seat, SeatPK> {
}
