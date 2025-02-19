package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.ScheduleSeat;
import vn.edu.usth.mcma.backend.entity.ScheduleSeatPK;

import java.util.List;

@Repository
public interface ScheduleSeatRepository extends JpaRepository<ScheduleSeat, ScheduleSeatPK> {
    @Query("select ss from ScheduleSeat ss where ss.id.schedule.id = :scheduleId order by ss.id.seat.rootRow, ss.id.seat.rootCol")
    List<ScheduleSeat> findAllByScheduleId(@Param("scheduleId") Long scheduleId);
}
