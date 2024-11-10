package com.spring.service.impl;

import com.spring.dto.response.SearchMovieByGenreResponse;
import com.spring.dto.response.SearchMovieByNameResponse;
import com.spring.enums.PerformerSex;
import com.spring.enums.PerformerType;
import com.spring.repository.MovieRepository;
import com.spring.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return results.stream()
                .map(result -> new SearchMovieByNameResponse(
                        (Integer) result[0],        // id
                        (String) result[1],         // name
                        (Integer) result[2],        // length
                        (String) result[3],         // trailerLink
                        result[4] != null ? dateFormat.format((Date) result[4]) : null, //  datePublish
                        (String) result[5],         // ratingName
                        (String) result[6],         // ratingDescription
                        (String) result[7],         // genreName
                        (String) result[8],         // performerName
                        result[9] != null ? String.valueOf(PerformerType.values()[(Byte) result[9]]) : null, // performerType
                        result[10] != null ? String.valueOf(PerformerSex.values()[(Byte) result[10]]) : null // performerSex
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<SearchMovieByGenreResponse> getAllMoviesByMovieGenreSet(Integer movieGenreId) {
        List<Object[]> results = movieRepository.getAllMoviesByMovieGenreSet(movieGenreId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return results.stream()
                .map(result -> new SearchMovieByGenreResponse(
                        (Integer) result[0],        // id
                        (String) result[1],         // name
                        (Integer) result[2],        // length
                        (String) result[3],         // trailerLink
                        result[4] != null ? dateFormat.format((Date) result[4]) : null, // datePublish
                        (String) result[5],         // ratingName
                        (String) result[6],         // ratingDescription
                        (String) result[7],         // genreName
                        (String) result[8],         // performerName
                        result[9] != null ? String.valueOf(PerformerType.values()[(Byte) result[9]]) : null, // performerType
                        result[10] != null ? String.valueOf(PerformerSex.values()[(Byte) result[10]]) : null // performerSex
                ))
                .collect(Collectors.toList());
    }
}
