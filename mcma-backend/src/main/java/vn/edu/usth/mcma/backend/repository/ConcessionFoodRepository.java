package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.ConcessionFood;
import vn.edu.usth.mcma.backend.entity.ConcessionFoodPK;

import java.util.List;

@Repository
public interface ConcessionFoodRepository extends JpaRepository<ConcessionFood, ConcessionFoodPK> {
    @Query("select cf from ConcessionFood cf where cf.id.concession.id = :concessionId")
    List<ConcessionFood> findAllByConcessionId(Long concessionId);
}
