package com.spring.service;

import com.spring.dto.Response.SearchMovieByGenreResponse;
import com.spring.dto.Response.SearchMovieByNameResponse;

import java.util.List;

public interface MovieService {
    List<SearchMovieByNameResponse> getAllMovies(String title, Integer limit, Integer offset);
    List<SearchMovieByGenreResponse> getAllMoviesByMovieGenreSet(Integer movieGenreId);
}
