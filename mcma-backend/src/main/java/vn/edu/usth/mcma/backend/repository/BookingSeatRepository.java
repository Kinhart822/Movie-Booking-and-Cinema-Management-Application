package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.domain.Booking;
import vn.edu.usth.mcma.backend.domain.BookingSeat;
import vn.edu.usth.mcma.backend.domain.BookingSeatPK;

import java.util.List;

@Repository
public interface BookingSeatRepository extends JpaRepository<BookingSeat, BookingSeatPK> {
    @Query("select bs from BookingSeat bs where bs.id.booking = :booking")
    List<BookingSeat> findAllByBooking(@Param("booking") Booking booking);
}
