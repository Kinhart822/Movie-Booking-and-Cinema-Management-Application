package vn.edu.usth.mcma.backend.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.usth.mcma.backend.entity.Advertisement;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    List<Advertisement> findAllByStartDateBeforeAndEndDateAfter(
            Instant startDateBefore, Instant endDateAfter);
}
