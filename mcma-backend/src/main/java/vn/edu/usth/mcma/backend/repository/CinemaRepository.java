package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.dto.cinema.CinemaProjection;
import vn.edu.usth.mcma.backend.entity.Cinema;
import vn.edu.usth.mcma.backend.entity.City;

import java.util.List;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    @Deprecated
    List<Cinema> findAllByNameContaining(String name);
    @Query(nativeQuery = true, value = """
            select cine.id                                as id,
                   cine.name                              as name,
                   c.name                                 as city,
                   count(s.id)                            as numberOfScreens,
                   concat(u.first_name, ' ', u.last_name) as lastModifiedBy,
                   cine.last_modified_date                as lastModifiedDate,
                   cine.status                            as status
            from cinema cine
                     left join city c on cine.city_id = c.id
                     left join screen s on cine.id = s.cinema_id
                     left join user u on cine.last_modified_by = u.id
            where :query is null or cine.name like concat('%', :query, '%')
            group by cine.id""")
    List<CinemaProjection> findAllProjectionByQuery(@Param(value = "query") String query);

    List<Cinema> findAllByCity(City city);
}
