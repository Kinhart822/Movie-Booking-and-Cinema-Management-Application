package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.dto.ScreenProjection;
import vn.edu.usth.mcma.backend.entity.Cinema;
import vn.edu.usth.mcma.backend.entity.Screen;

import java.util.List;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {
    @Query(nativeQuery = true, value = """
            select s.id                                         as id,
                   s.name                                       as name,
                   st.id                                        as type,
                   count(distinct se.screen_id, se.row, se.col) as numberOfSeats,
                   concat(u.first_name, ' ', u.last_name)       as lastModifiedBy,
                   s.last_modified_date                         as lastModifiedDate,
                   s.status                                     as status
            from screen s
                     left join screen_type st on s.type_id = st.id
                     left join seat se on se.screen_id = s.id
                     left join user u on u.id = s.last_modified_by
            where (:query is null or :query like concat('%', :query, '%'))
              and s.cinema_id = :cinemaId
            group by s.id""")
    List<ScreenProjection> findAllProjectionByQuery(@Param(value = "cinemaId") Long cinemaId, @Param(value = "query") String query);
    List<Screen> findAllByCinema(Cinema cinema);
}
