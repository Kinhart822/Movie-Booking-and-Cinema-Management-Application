package com.spring.repository;

import com.spring.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c JOIN c.movieRespond mp WHERE mp.user.id = :userId")
    List<Comment> findByUserId(Integer userId);

    @Query("SELECT c FROM Comment c JOIN c.movieRespond mp WHERE mp.user.id = :userId AND mp.movie.id = :movieId")
    Comment findByUserIdAndMovieId(@Param("userId") Integer userId,
                                   @Param("movieId") Integer movieId);

    @Query("SELECT c FROM Comment c JOIN c.movieRespond mp WHERE mp.movie.id = :movieId")
    List<Comment> findByMovieId(@Param("movieId") Integer movieId);
}
