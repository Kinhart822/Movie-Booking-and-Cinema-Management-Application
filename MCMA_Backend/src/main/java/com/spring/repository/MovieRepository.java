package com.spring.repository;

import com.spring.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query(nativeQuery = true, value = """
                SELECT m.id,
                       m.name,
                       m.length,
                       m.image_url,
                       m.trailer_link,
                       m.date_publish,
                       mrd.name AS rating_name,
                       mrd.description AS rating_description,
                       mgd.name AS genre_name,
                       mpd.name AS performer_name,
                       mpd.performer_type,
                       mpd.performer_sex
                FROM movie m
                LEFT JOIN set_movie_rating_detail srd ON m.id = srd.movie_id
                LEFT JOIN movie_rating_detail mrd ON srd.movie_rating_detail_id = mrd.id
                LEFT JOIN set_movie_genre g ON m.id = g.movie_id
                LEFT JOIN movie_genre mg ON g.movie_genre_id = mg.id
                LEFT JOIN movie_genre_detail mgd ON mg.movie_genre_detail_id = mgd.id
                LEFT JOIN set_movie_performer p ON m.id = p.movie_id
                LEFT JOIN movie_performer mp ON p.movie_performer_id = mp.id
                LEFT JOIN movie_performer_detail mpd ON mp.movie_performer_detail_id = mpd.id
                WHERE (:title IS NULL OR :title = '' OR m.name LIKE CONCAT('%', :title, '%'))
                ORDER BY m.date_updated DESC
                LIMIT :limit OFFSET :offset
            """)
    List<Object[]> getAllMovies(
            @Param("title") String title,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset);


    @Query(nativeQuery = true, value = """
                 SELECT m.id,
                        m.name,
                        m.length,
                        m.image_url,
                        m.trailer_link,
                        m.date_publish,
                        mrd.name AS rating_name,
                        mrd.description AS rating_description,
                        mgd.name AS genre_name,
                        mpd.name AS performer_name,
                        mpd.performer_type,
                        mpd.performer_sex
                 FROM movie m
                 LEFT JOIN set_movie_rating_detail srd ON m.id = srd.movie_id
                 LEFT JOIN movie_rating_detail mrd ON srd.movie_rating_detail_id = mrd.id
                 LEFT JOIN set_movie_genre g ON m.id = g.movie_id
                 LEFT JOIN movie_genre mg ON g.movie_genre_id = mg.id
                 LEFT JOIN movie_genre_detail mgd ON mg.movie_genre_detail_id = mgd.id
                 LEFT JOIN set_movie_performer p ON m.id = p.movie_id
                 LEFT JOIN movie_performer mp ON p.movie_performer_id = mp.id
                 LEFT JOIN movie_performer_detail mpd ON mp.movie_performer_detail_id = mpd.id
                 WHERE mg.id = :movieGenreId
                 ORDER BY m.date_updated DESC
            \s""")
    List<Object[]> getAllMoviesByMovieGenreSet(@Param("movieGenreId") Integer movieGenreId);




    @Query(nativeQuery = true, value = """
                SELECT m.id,
                       m.name,
                       m.length,
                       m.image_url,
                       m.trailer_link,
                       m.date_publish,
                       mrd.name AS rating_name,
                       mrd.description AS rating_description,
                       mgd.name AS genre_name,
                       mpd.name AS performer_name,
                       mpd.performer_type,
                       mpd.performer_sex
                FROM movie m
                LEFT JOIN set_movie_rating_detail srd ON m.id = srd.movie_id
                LEFT JOIN movie_rating_detail mrd ON srd.movie_rating_detail_id = mrd.id
                LEFT JOIN set_movie_genre g ON m.id = g.movie_id
                LEFT JOIN movie_genre mg ON g.movie_genre_id = mg.id
                LEFT JOIN movie_genre_detail mgd ON mg.movie_genre_detail_id = mgd.id
                LEFT JOIN set_movie_performer p ON m.id = p.movie_id
                LEFT JOIN movie_performer mp ON p.movie_performer_id = mp.id
                LEFT JOIN movie_performer_detail mpd ON mp.movie_performer_detail_id = mpd.id
                WHERE (:name IS NULL OR :name = '' OR mgd.name LIKE CONCAT('%', :name, '%'))
                ORDER BY m.date_updated DESC
                LIMIT :limit OFFSET :offset
           \s""")
    List<Object[]> getAllMoviesByMovieGenreName(
            @Param("name") String name,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset
    );


    List<Movie> findByDatePublishBefore(Date date);

    @Query("SELECT m FROM Movie m WHERE m.datePublish > :futureStartDate")
    List<Movie> findComingSoonMovies(@Param("futureStartDate") Date futureStartDate);

    @Query("SELECT m FROM Movie m " +
            "JOIN m.movieResponds mr " +
            "JOIN mr.rating r " +
            "GROUP BY m.id " +
            "HAVING AVG(r.ratingStar) BETWEEN :minRating AND :maxRating")
    List<Movie> findHighestRatingMovies(@Param("minRating") Double minRating, @Param("maxRating") Double maxRating);
}

