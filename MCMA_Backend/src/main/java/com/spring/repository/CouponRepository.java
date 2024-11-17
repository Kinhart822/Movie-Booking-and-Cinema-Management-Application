package com.spring.repository;

import com.spring.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    @Query("SELECT c FROM Coupon c JOIN c.movieSet m WHERE m.id = :movieId AND c.id = :couponId")
    Coupon findCouponByMovieIdAndCouponId(@Param("movieId") Integer movieId, @Param("couponId")Integer couponId);

    @Query("SELECT c FROM Coupon c JOIN c.userSet uc WHERE uc.id = :userId AND c.id = :couponId")
    Coupon findCouponByUserIdAndCouponId(@Param("userId") Integer userId, @Param("couponId")Integer couponId);

    @Query("SELECT c FROM Coupon c JOIN c.userSet uc WHERE uc.id = :userId")
    List<Coupon> findAvailableCouponsForUser(@Param("userId") Integer userId);

    @Query("SELECT c FROM Coupon c JOIN c.movieSet m WHERE m.id IN :movieId")
    List<Coupon> findAvailableCouponsByMovieId(@Param("movieId") Integer movieId);

}
