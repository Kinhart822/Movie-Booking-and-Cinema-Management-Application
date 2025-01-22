package com.spring.service;

import com.spring.dto.request.respond.MovieRespondRequest;
import com.spring.dto.response.movieRespond.CommentResponse;
import com.spring.dto.response.movieRespond.MovieRespondResponse;
import com.spring.dto.response.movieRespond.RatingResponse;

import java.util.List;

public interface MovieRespondService {
    MovieRespondResponse createMovieRespond(Integer userId, MovieRespondRequest movieRespondRequest);
    MovieRespondResponse updateMovieRespond(Integer userId, Integer movieRespondId, MovieRespondRequest movieRespondRequest);
    void deleteMovieRespond(Integer userId, Integer movieRespondId);

    CommentResponse getMovieCommentByUserIdAndMovieId(Integer userId, Integer movieId);
    RatingResponse getMovieRatingByUserIdAndMovieId(Integer userId, Integer movieId);
    MovieRespondResponse getMovieRespondsByUserIdAndMovieId(Integer  userId, Integer movieId);

    List<CommentResponse> getMovieCommentsByUserId(Integer userId);
    List<CommentResponse> getMovieCommentsByMovieId(Integer movieId);

    List<RatingResponse> getMovieRatingsByUserId(Integer userId);
    List<RatingResponse> getMovieRatingsByMovieId(Integer movieId);

    List<MovieRespondResponse> getAllMovieRespondsByUserId(Integer userId);
    List<MovieRespondResponse> getAllMovieRespondsByMovieId(Integer movieId);
}
