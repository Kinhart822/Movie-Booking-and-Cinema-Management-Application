package com.spring.repository;

import com.spring.entities.Booking;
import com.spring.enums.BookingStatus;
import com.spring.enums.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.movie.id = :movieId")
    List<Booking> findByUserIdAndMovieId(@Param("userId") Integer userId, @Param("movieId") Integer movieId);

    @Query("SELECT b FROM Booking b WHERE b.paymentMethod =:paymentMethod ORDER BY b.dateUpdated DESC")
    List<Booking> findByPaymentMethod(@Param("paymentMethod") PaymentMethod paymentMethod);

    @Query("SELECT b FROM Booking b WHERE b.status =:bookingStatus AND b.user.id = :userId ORDER BY b.dateUpdated DESC")
    List<Booking> findByStatusAndByUser(@Param("bookingStatus") BookingStatus bookingStatus, @Param("userId") Integer userId);

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId ORDER BY b.dateUpdated DESC")
    List<Booking> findByUserId(@Param("userId") Integer userId);

    List<Booking> findByEndDateTimeBefore(LocalDateTime endDateTime);

    @Query("SELECT b FROM Booking b ORDER BY b.dateUpdated DESC")
    List<Booking> findAllOrderByDateUpdatedDesc();
}
