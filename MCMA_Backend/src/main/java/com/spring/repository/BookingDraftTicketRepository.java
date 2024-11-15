package com.spring.repository;

import com.spring.entities.BookingDraftTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDraftTicketRepository extends JpaRepository<BookingDraftTicket, Integer> {
}
