package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.domain.Booking;
import vn.edu.usth.mcma.backend.domain.BookingAudience;
import vn.edu.usth.mcma.backend.domain.BookingAudiencePK;

import java.util.List;

@Repository
public interface BookingAudienceRepository extends JpaRepository<BookingAudience, BookingAudiencePK> {
    @Query("select ba from BookingAudience ba where ba.id.booking = :booking")
    List<BookingAudience> findAllByBooking(@Param("booking") Booking booking);
}
