package com.spring.repository;

import com.spring.entities.BookingDraftSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDraftSeatRepository extends JpaRepository<BookingDraftSeat, Integer> {
}
