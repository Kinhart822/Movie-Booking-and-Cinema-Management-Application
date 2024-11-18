package com.spring.service;

import com.spring.dto.response.SearchMovieByGenreResponse;
import com.spring.dto.response.SearchMovieByNameResponse;

import java.util.List;

public interface MovieService {
    List<SearchMovieByNameResponse> getAllMovies(String title, Integer limit, Integer offset);
    List<SearchMovieByGenreResponse> getAllMoviesByMovieGenreSet(Integer movieGenreId);
    List<SearchMovieByGenreResponse> getAllMoviesByMovieGenreName(String name, Integer limit, Integer offset);
}
