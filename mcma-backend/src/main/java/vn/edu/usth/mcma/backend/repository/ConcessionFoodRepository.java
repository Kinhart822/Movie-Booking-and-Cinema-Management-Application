package vn.edu.usth.mcma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.edu.usth.mcma.backend.entity.ConcessionFood;
import vn.edu.usth.mcma.backend.entity.ConcessionFoodPK;

public interface ConcessionFoodRepository extends JpaRepository<ConcessionFood, ConcessionFoodPK> {
    @Query("select cf from ConcessionFood cf where cf.id.concession.id = :concessionId")
    List<ConcessionFood> findAllByConcessionId(Long concessionId);
}
