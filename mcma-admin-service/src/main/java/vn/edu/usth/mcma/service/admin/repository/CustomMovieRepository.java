package vn.edu.usth.mcma.service.admin.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.service.admin.service.dto.response.MovieProjection;
import vn.edu.usth.mcma.service.common.domain.Movie;
import vn.edu.usth.mcma.service.common.repository.MovieRepository;

import java.util.List;

@Repository
public interface CustomMovieRepository extends MovieRepository {

    @Query(nativeQuery = true, value = """
            select m.ID,
                   m.TITLE,
                   m.LENGTH,
                   m.DATE_PUBLISH,
                   mrd.NAME,
                   mrd.DESCRIPTION,
                   m.TRAILER_LINK,
                   m.CREATED_BY,
                   m.LAST_MODIFIED_BY,
                   m.CREATED_DATE,
                   m.LAST_MODIFIED_DATE
            from movie m
                     join movie_rating_detail mrd on m.RATING_ID = mrd.ID
            where (:title is null or :title = '' or m.TITLE like CONCAT('%', :title, '%'))
            order by m.LAST_MODIFIED_DATE desc
            limit :limit offset :offset""")
    List<MovieProjection> getAllMovies(
            @Param("title") String title,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset);
}
