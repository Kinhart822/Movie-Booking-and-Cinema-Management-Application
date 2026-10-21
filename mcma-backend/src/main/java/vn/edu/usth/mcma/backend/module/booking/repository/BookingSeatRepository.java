package vn.edu.usth.mcma.backend.module.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.edu.usth.mcma.backend.module.booking.entity.Booking;
import vn.edu.usth.mcma.backend.module.booking.entity.BookingSeat;
import vn.edu.usth.mcma.backend.module.booking.entity.BookingSeatPK;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, BookingSeatPK> {
    @Query("select bs from BookingSeat bs where bs.id.booking = :booking")
    List<BookingSeat> findAllByBooking(@Param("booking") Booking booking);
}
