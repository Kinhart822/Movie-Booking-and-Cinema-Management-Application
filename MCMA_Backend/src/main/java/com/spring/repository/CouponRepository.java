package com.spring.repository;

import com.spring.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    @Query("SELECT c.id FROM Coupon c JOIN c.movieSet m WHERE m.id = :movieId")
    List<Integer> findCouponIdsByMovieId(@Param("movieId") Integer movieId);

    @Query("SELECT c.id FROM Coupon c JOIN c.userCouponSet uc WHERE uc.id = :userId")
    List<Integer> findCouponIdsByUserId(@Param("userId") Integer userId);

    @Query("SELECT c FROM Coupon c JOIN c.userCouponSet uc WHERE uc.user.id = :userId")
    List<Coupon> findAvailableCouponsForUser(@Param("userId") Integer userId);

    @Query("SELECT c FROM Coupon c JOIN c.movieSet m WHERE m.id IN :cinemaIds")
    List<Coupon> findAvailableCouponsByMovieIds(@Param("cinemaIds") List<Integer> movieIds);

}
