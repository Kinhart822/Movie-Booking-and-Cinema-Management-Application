package vn.edu.usth.mcma.backend.module.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.edu.usth.mcma.backend.module.booking.entity.Booking;
import vn.edu.usth.mcma.backend.module.booking.entity.BookingConcession;
import vn.edu.usth.mcma.backend.module.booking.entity.BookingConcessionPK;

public interface BookingConcessionRepository
        extends JpaRepository<BookingConcession, BookingConcessionPK> {
    @Query("select bc from BookingConcession bc where bc.id.booking = :booking")
    List<BookingConcession> findAllByBooking(@Param("booking") Booking booking);
}
