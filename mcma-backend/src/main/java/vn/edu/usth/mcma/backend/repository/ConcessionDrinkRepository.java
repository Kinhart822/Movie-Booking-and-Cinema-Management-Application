package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.ConcessionDrink;
import vn.edu.usth.mcma.backend.entity.ConcessionDrinkPK;
import vn.edu.usth.mcma.backend.entity.ConcessionFood;

import java.util.List;

@Repository
public interface ConcessionDrinkRepository extends JpaRepository<ConcessionDrink, ConcessionDrinkPK> {
    @Query("select cd from ConcessionDrink cd where cd.id.concession.id = :concessionId")
    List<ConcessionDrink> findAllByConcessionId(Long concessionId);
}
