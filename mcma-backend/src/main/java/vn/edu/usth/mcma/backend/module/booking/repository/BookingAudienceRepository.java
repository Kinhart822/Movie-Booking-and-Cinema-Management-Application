package vn.edu.usth.mcma.backend.module.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.edu.usth.mcma.backend.module.booking.entity.Booking;
import vn.edu.usth.mcma.backend.module.booking.entity.BookingAudience;
import vn.edu.usth.mcma.backend.module.booking.entity.BookingAudiencePK;

public interface BookingAudienceRepository
        extends JpaRepository<BookingAudience, BookingAudiencePK> {
    @Query("select ba from BookingAudience ba where ba.id.booking = :booking")
    List<BookingAudience> findAllByBooking(@Param("booking") Booking booking);
}
