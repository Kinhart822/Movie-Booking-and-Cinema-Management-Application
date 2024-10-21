package com.spring.repository;

import com.spring.entities.Movie;
import com.spring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<Movie> findByName(String name);

    @Query(nativeQuery = true, value = """
                SELECT m.id,
                       m.name,
                       m.length,
                       m.trailer_link,
                       m.date_publish,
                       mrd.name AS rating_name,
                       mrd.description AS rating_description,
                       mgd.name AS genre_name,
                       mpd.name AS performer_name,
                       mpd.performer_type,
                       mpd.performer_sex,
                       m.movie_rating_detail_id
                FROM movie m
                JOIN movie_rating_detail mrd ON m.movie_rating_detail_id = mrd.id
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
                       m.trailer_link,
                       m.date_publish,
                       mrd.name AS rating_name,
                       mrd.description AS rating_description,
                       mgd.name AS genre_name,
                       mpd.name AS performer_name,
                       mpd.performer_type,
                       mpd.performer_sex,
                       m.movie_rating_detail_id
                FROM movie m
                JOIN movie_rating_detail mrd ON m.movie_rating_detail_id = mrd.id
                JOIN set_movie_genre g ON m.id = g.movie_id
                JOIN movie_genre mg ON g.movie_genre_id = mg.id
                JOIN movie_genre_detail mgd ON mg.movie_genre_detail_id = mgd.id
                LEFT JOIN set_movie_performer p ON m.id = p.movie_id
                LEFT JOIN movie_performer mp ON p.movie_performer_id = mp.id
                LEFT JOIN movie_performer_detail mpd ON mp.movie_performer_detail_id = mpd.id
                WHERE mg.id = :movieGenreId
                ORDER BY m.date_updated DESC
            """)
    List<Object[]> getAllMoviesByMovieGenreSet(@Param("movieGenreId") Integer movieGenreId);

    List<Movie> findByDatePublishBefore(Date date);

    @Query("SELECT m FROM Movie m WHERE m.datePublish > :futureStartDate")
    List<Movie> findComingSoonMovies(@Param("futureStartDate") Date futureStartDate);
}

