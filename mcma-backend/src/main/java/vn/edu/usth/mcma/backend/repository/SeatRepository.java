package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.Screen;
import vn.edu.usth.mcma.backend.entity.Seat;
import vn.edu.usth.mcma.backend.entity.SeatPK;
import vn.edu.usth.mcma.backend.entity.SeatType;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, SeatPK> {
    @Query("select s from Seat s where s.id.screen = :screen order by s.id.row, s.id.col")
    List<Seat> findAllByScreen(@Param("screen") Screen screen);
    @Query("select s from Seat s where s.id.screen = :screen and s.id.row = s.rootRow and s.id.col = s.rootCol order by s.id.row, s.id.col")
    List<Seat> findAllRootByScreen(@Param("screen") Screen screen);
}
