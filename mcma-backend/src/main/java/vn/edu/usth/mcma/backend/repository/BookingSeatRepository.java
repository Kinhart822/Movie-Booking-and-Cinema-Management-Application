package vn.edu.usth.mcma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.edu.usth.mcma.backend.entity.Booking;
import vn.edu.usth.mcma.backend.entity.BookingSeat;
import vn.edu.usth.mcma.backend.entity.BookingSeatPK;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, BookingSeatPK> {
    @Query("select bs from BookingSeat bs where bs.id.booking = :booking")
    List<BookingSeat> findAllByBooking(@Param("booking") Booking booking);
}
