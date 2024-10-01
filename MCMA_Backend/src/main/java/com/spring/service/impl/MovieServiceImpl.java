package com.spring.service.impl;

import com.spring.dto.Response.SearchMovieByGenreResponse;
import com.spring.dto.Response.SearchMovieByNameResponse;
import com.spring.repository.MovieRepository;
import com.spring.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<SearchMovieByNameResponse> getAllMovies(String title, Integer limit, Integer offset) {
        List<Object[]> results = movieRepository.getAllMovies(title, limit, offset);
        return results.stream()
                .map(result -> new SearchMovieByNameResponse(
                        (Integer) result[0],
                        (String) result[1],
                        (Integer) result[2],
                        (String) result[3],
                        (Date) result[4],
                        (String) result[5],
                        (String) result[6]
                ))
                .collect(Collectors.toList());
    }


    @Override
    public List<SearchMovieByGenreResponse> getAllMoviesByMovieGenreSet(Integer movieGenreId) {
        List<Object[]> results = movieRepository.getAllMoviesByMovieGenreSet(movieGenreId);
        return results.stream()
                .map(result -> new SearchMovieByGenreResponse(
                        (Integer) result[0],
                        (String) result[1],
                        (Integer) result[2],
                        (String) result[3],
                        (Date) result[4],
                        (String) result[5],
                        (String) result[6],
                        (String) result[7]
                ))
                .collect(Collectors.toList());
    }
}
