package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.domain.Booking;
import vn.edu.usth.mcma.backend.domain.BookingConcession;
import vn.edu.usth.mcma.backend.domain.BookingConcessionPK;

import java.util.List;

@Repository
public interface BookingConcessionRepository extends JpaRepository<BookingConcession, BookingConcessionPK> {
    @Query("select bc from BookingConcession bc where bc.id.booking = :booking")
    List<BookingConcession> findAllByBooking(@Param("booking") Booking booking);
}
