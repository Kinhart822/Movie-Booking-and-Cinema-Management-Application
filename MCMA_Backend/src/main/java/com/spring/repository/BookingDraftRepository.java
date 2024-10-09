package com.spring.repository;

import com.spring.entities.BookingDraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingDraftRepository extends JpaRepository<BookingDraft, Integer> {
    Optional<BookingDraft> findByUserId(Integer userId);

}
