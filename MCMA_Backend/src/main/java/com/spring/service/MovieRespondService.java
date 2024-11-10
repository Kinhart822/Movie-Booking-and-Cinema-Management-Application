package com.spring.service;

import com.spring.dto.request.movieRespond.MovieRespondRequest;
import com.spring.dto.response.booking.MovieResponse;
import com.spring.dto.response.movieRespond.CommentResponse;
import com.spring.dto.response.movieRespond.MovieRespondResponse;
import com.spring.dto.response.movieRespond.RatingResponse;
import com.spring.entities.MovieRespond;

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
