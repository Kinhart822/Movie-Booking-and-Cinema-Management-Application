package vn.edu.usth.mcma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.edu.usth.mcma.backend.entity.ConcessionDrink;
import vn.edu.usth.mcma.backend.entity.ConcessionDrinkPK;

public interface ConcessionDrinkRepository
        extends JpaRepository<ConcessionDrink, ConcessionDrinkPK> {
    @Query("select cd from ConcessionDrink cd where cd.id.concession.id = :concessionId")
    List<ConcessionDrink> findAllByConcessionId(Long concessionId);
}
