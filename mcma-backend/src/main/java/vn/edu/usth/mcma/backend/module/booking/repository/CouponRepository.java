package vn.edu.usth.mcma.backend.module.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.usth.mcma.backend.module.booking.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {}
