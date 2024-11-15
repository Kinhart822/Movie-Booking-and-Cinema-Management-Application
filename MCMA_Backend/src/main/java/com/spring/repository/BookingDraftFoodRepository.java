package com.spring.repository;

import com.spring.entities.BookingDraftFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDraftFoodRepository extends JpaRepository<BookingDraftFood, Integer> {
}
