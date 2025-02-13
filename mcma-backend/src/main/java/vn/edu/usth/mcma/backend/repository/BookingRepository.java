package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.domain.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
