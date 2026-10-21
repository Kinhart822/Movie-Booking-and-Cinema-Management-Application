package vn.edu.usth.mcma.backend.module.cinema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.edu.usth.mcma.backend.module.cinema.entity.Schedule;
import vn.edu.usth.mcma.backend.module.cinema.entity.SeatType;

public interface SeatTypeRepository extends JpaRepository<SeatType, String> {
    @Query(
            value =
                    "select distinct s.seatType from Seat s left join Schedule sche on s.id.screen = sche.screen where sche = :schedule order by s.seatType.unitPrice")
    List<SeatType> findAllBySchedule(@Param(value = "schedule") Schedule schedule);
}
