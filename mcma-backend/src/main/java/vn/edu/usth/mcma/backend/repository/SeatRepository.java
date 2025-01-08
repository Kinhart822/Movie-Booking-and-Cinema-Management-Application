package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.Seat;
import vn.edu.usth.mcma.backend.entity.SeatPK;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, SeatPK> {
    @Query("select s from Seat s where s.pk.screenId = :screenId order by s.pk.screenId, s.pk.row, s.pk.col")
    List<Seat> findAllByScreenId(@Param("screenId") Long screenId);
}
