package com.spring.repository;

import com.spring.entities.BookingDraftCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDraftCouponRepository extends JpaRepository<BookingDraftCoupon, Integer> {
}
