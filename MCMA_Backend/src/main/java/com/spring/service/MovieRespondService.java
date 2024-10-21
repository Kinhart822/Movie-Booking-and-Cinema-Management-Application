package com.spring.service;

import com.spring.dto.Request.movieRespond.MovieRespondRequest;
import com.spring.dto.Response.movieRespond.CommentResponse;
import com.spring.dto.Response.movieRespond.MovieRespondResponse;
import com.spring.dto.Response.movieRespond.RatingResponse;

import java.util.List;

public interface MovieRespondService {
    MovieRespondResponse createMovieRespond(Integer userId, MovieRespondRequest movieRespondRequest);
    MovieRespondResponse updateMovieRespond(Integer userId, MovieRespondRequest movieRespondRequest);
    void deleteMovieRespond(Integer userId, MovieRespondRequest movieRespondRequest);

    CommentResponse getMovieCommentByUserIdAndMovieId(Integer userId, MovieRespondRequest movieRespondRequest);
    RatingResponse getMovieRatingByUserIdAndMovieId(Integer userId, MovieRespondRequest movieRespondRequest);
    MovieRespondResponse getMovieRespondsByUserIdAndMovieId(Integer  userId, MovieRespondRequest movieRespondRequest);

    List<CommentResponse> getMovieCommentsByUserId(Integer userId);
    List<CommentResponse> getMovieCommentsByMovieId(MovieRespondRequest movieRespondRequest);

    List<RatingResponse> getMovieRatingsByUserId(Integer userId);
    List<RatingResponse> getMovieRatingsByMovieId(MovieRespondRequest movieRespondRequest);

    List<MovieRespondResponse> getAllMovieRespondsByUserId(Integer userId);
    List<MovieRespondResponse> getAllMovieRespondsByMovieId(MovieRespondRequest movieRespondRequest);
}
