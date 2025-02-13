package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.domain.BookingConcession;
import vn.edu.usth.mcma.backend.domain.BookingConcessionPK;

@Repository
public interface BookingConcessionRepository extends JpaRepository<BookingConcession, BookingConcessionPK> {
}
