package vn.edu.usth.mcma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.edu.usth.mcma.backend.entity.Concession;

public interface ConcessionRepository extends JpaRepository<Concession, Long> {
    @Query(
            "select c from Concession c where :cinemaId is null or c in (select cons from Cinema ci join ci.concessionSet cons where ci.id = :cinemaId)")
    List<Concession> findAllByCinemaId(@Param(value = "cinemaId") Long cinemaId);
}
