package com.spring.repository;

import com.spring.entities.Seat;
import com.spring.enums.SeatStatus;
import com.spring.enums.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> findBySeatTypeAndSeatStatus(SeatType type, SeatStatus status);
}


