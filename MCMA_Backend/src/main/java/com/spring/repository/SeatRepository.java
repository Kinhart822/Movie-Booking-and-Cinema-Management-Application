package com.spring.repository;

import com.spring.entities.Seat;
import com.spring.enums.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    @Query("SELECT s FROM Seat s WHERE s.seatStatus = :status")
    List<Seat> findBySeatStatus(@Param("status") SeatStatus status);
}


