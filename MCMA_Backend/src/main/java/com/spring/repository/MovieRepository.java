package com.spring.repository;

import com.spring.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query(nativeQuery = true, value = """
                SELECT m.id,
                       m.name,
                       m.length,
                       m.trailer_link,
                       m.date_publish,
                       mrd.name AS rating_name,
                       mrd.description AS rating_description,
                       m.movie_rating_detail_id
                FROM movie m
                JOIN movie_rating_detail mrd ON m.movie_rating_detail_id = mrd.id
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
                       m.movie_rating_detail_id
                FROM movie m
                JOIN movie_rating_detail mrd ON m.movie_rating_detail_id = mrd.id
                JOIN set_movie_genre g ON m.id = g.movie_id
                JOIN movie_genre mg ON g.movie_genre_id = mg.id
                JOIN movie_genre_detail mgd ON mg.movie_genre_detail_id = mgd.id
                WHERE mg.id = :movieGenreId
                ORDER BY m.date_updated DESC
            """)
    List<Object[]> getAllMoviesByMovieGenreSet(@Param("movieGenreId") Integer movieGenreId);
}

