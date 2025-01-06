package vn.edu.usth.mcma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.usth.mcma.backend.entity.Movie;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    /*
     * ====
     * User
     * ====
     */
    List<Movie> findAllByNameContainingAndStatusIs(String title, Integer status);

    List<Movie> findAllByStatusIs(Integer status);

    List<Movie> findAllByPublishDateAfterAndStatusIs(Instant time, Integer status);

//    @Query(nativeQuery = true, value = """
//                 SELECT
//                     m.id,
//                     m.name,
//                     m.length,
//                     m.description,
//                     m.image_url,
//                     m.background_image_url,
//                     m.trailer_link,
//                     m.date_publish,
//                     GROUP_CONCAT(DISTINCT mrd.name SEPARATOR ',') AS rating_name,
//                     GROUP_CONCAT(DISTINCT mrd.description SEPARATOR ',') AS rating_description,
//                     GROUP_CONCAT(DISTINCT mgd.name SEPARATOR ',') AS genre_name,
//                     GROUP_CONCAT(DISTINCT mpd.name SEPARATOR ',') AS performer_name,
//                     GROUP_CONCAT(DISTINCT mpd.performer_type SEPARATOR ',') AS performer_type,
//                     GROUP_CONCAT(DISTINCT mpd.performer_sex SEPARATOR ',') AS performer_sex
//                 FROM movie m
//                 LEFT JOIN set_movie_rating_detail srd ON m.id = srd.movie_id
//                 LEFT JOIN movie_rating_detail mrd ON srd.movie_rating_detail_id = mrd.id
//                 LEFT JOIN set_movie_genre g ON m.id = g.movie_id
//                 LEFT JOIN movie_genre mg ON g.movie_genre_id = mg.id
//                 LEFT JOIN movie_genre_detail mgd ON mg.movie_genre_detail_id = mgd.id
//                 LEFT JOIN set_movie_performer p ON m.id = p.movie_id
//                 LEFT JOIN movie_performer mp ON p.movie_performer_id = mp.id
//                 LEFT JOIN movie_performer_detail mpd ON mp.movie_performer_detail_id = mpd.id
//                 WHERE mg.id = :movieGenreId
//                 GROUP BY m.id, m.name, m.length, m.image_url, m.trailer_link, m.date_publish
//                 ORDER BY m.date_publish DESC
//            """)
//    List<Object[]> getAllMoviesByMovieGenreSet(@Param("movieGenreId") Integer movieGenreId);
//
//    @Query(nativeQuery = true, value = """
//                SELECT
//                    m.id,
//                    m.name,
//                    m.length,
//                    m.description,
//                    m.image_url,
//                    m.background_image_url,
//                    m.trailer_link,
//                    m.date_publish,
//                    GROUP_CONCAT(DISTINCT mrd.name SEPARATOR ',') AS rating_name,
//                    GROUP_CONCAT(DISTINCT mrd.description SEPARATOR ',') AS rating_description,
//                    GROUP_CONCAT(DISTINCT mgd.name SEPARATOR ',') AS genre_name,
//                    GROUP_CONCAT(DISTINCT mpd.name SEPARATOR ',') AS performer_name,
//                    GROUP_CONCAT(DISTINCT mpd.performer_type SEPARATOR ',') AS performer_type,
//                    GROUP_CONCAT(DISTINCT mpd.performer_sex SEPARATOR ',') AS performer_sex
//                FROM movie m
//                LEFT JOIN set_movie_rating_detail srd ON m.id = srd.movie_id
//                LEFT JOIN movie_rating_detail mrd ON srd.movie_rating_detail_id = mrd.id
//                LEFT JOIN set_movie_genre g ON m.id = g.movie_id
//                LEFT JOIN movie_genre mg ON g.movie_genre_id = mg.id
//                LEFT JOIN movie_genre_detail mgd ON mg.movie_genre_detail_id = mgd.id
//                LEFT JOIN set_movie_performer p ON m.id = p.movie_id
//                LEFT JOIN movie_performer mp ON p.movie_performer_id = mp.id
//                LEFT JOIN movie_performer_detail mpd ON mp.movie_performer_detail_id = mpd.id
//                WHERE (:name IS NULL OR :name = '' OR mgd.name LIKE CONCAT('%', :name, '%'))
//                GROUP BY m.id, m.name, m.length, m.image_url, m.trailer_link, m.date_publish
//                ORDER BY m.date_publish DESC
//                LIMIT :limit OFFSET :offset
//            """)
//    List<Object[]> getAllMoviesByMovieGenreName(
//            @Param("name") String name,
//            @Param("limit") Integer limit,
//            @Param("offset") Integer offset
//    );
//
//    List<Movie> findByDatePublishBefore(Date date);
//
//    @Query("SELECT m FROM Movie m WHERE m.datePublish > :futureStartDate")
//    List<Movie> findComingSoonMovies(@Param("futureStartDate") Date futureStartDate);
//
//    @Query("SELECT m FROM Movie m " +
//            "JOIN m.movieResponds mr " +
//            "JOIN mr.rating r " +
//            "GROUP BY m.id " +
//            "HAVING AVG(r.ratingStar) BETWEEN :minRating AND :maxRating")
//    List<Movie> findHighestRatingMovies(@Param("minRating") Double minRating, @Param("maxRating") Double maxRating);
//
//    @Query("""
//                SELECT m
//                FROM Movie m
//                JOIN m.movieScheduleList ms
//                WHERE FUNCTION('DATE', ms.startTime) = :date
//            """)
//    List<Movie> findMoviesBySelectedDateSchedule(@Param("date") String date);
}