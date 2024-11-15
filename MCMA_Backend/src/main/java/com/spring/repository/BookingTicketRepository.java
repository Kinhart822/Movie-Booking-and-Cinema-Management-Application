package com.spring.repository;

import com.spring.entities.BookingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingTicketRepository extends JpaRepository<BookingTicket, Integer> {
}
