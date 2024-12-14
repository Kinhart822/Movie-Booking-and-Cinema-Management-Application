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
                        (String) result[3],         // image Url
                        (String) result[4],         // trailerLink
                        result[5] != null ? dateFormat.format((Date) result[5]) : null, //  datePublish
                        (String) result[6],         // ratingName
                        (String) result[7],         // ratingDescription
                        (String) result[8],         // genreName
                        (String) result[9],         // performerName
                        result[10] != null ? String.valueOf(PerformerType.values()[(Byte) result[10]]) : null, // performerType
                        result[11] != null ? String.valueOf(PerformerSex.values()[(Byte) result[11]]) : null // performerSex
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
                        (String) result[3],         // image Url
                        (String) result[4],         // trailerLink
                        result[5] != null ? dateFormat.format((Date) result[5]) : null, //  datePublish
                        (String) result[6],         // ratingName
                        (String) result[7],         // ratingDescription
                        (String) result[8],         // genreName
                        (String) result[9],         // performerName
                        result[10] != null ? String.valueOf(PerformerType.values()[(Byte) result[10]]) : null, // performerType
                        result[11] != null ? String.valueOf(PerformerSex.values()[(Byte) result[11]]) : null // performerSex
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<SearchMovieByGenreResponse> getAllMoviesByMovieGenreName(String name, Integer limit, Integer offset) {
        List<Object[]> results = movieRepository.getAllMoviesByMovieGenreName(name, limit, offset);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return results.stream()
                .map(result -> new SearchMovieByGenreResponse(
                        (Integer) result[0],        // id
                        (String) result[1],         // name
                        (Integer) result[2],        // length
                        (String) result[3],         // imageUrl
                        (String) result[4],         // trailerLink
                        result[5] != null ? dateFormat.format((Date) result[5]) : null, // datePublish
                        (String) result[6],         // ratingName
                        (String) result[7],         // ratingDescription
                        (String) result[8],         // genreName
                        (String) result[9],         // performerName
                        result[10] != null ? String.valueOf(PerformerType.values()[(Byte) result[10]]) : null, // performerType
                        result[11] != null ? String.valueOf(PerformerSex.values()[(Byte) result[11]]) : null // performerSex
                ))
                .collect(Collectors.toList());    }
}
