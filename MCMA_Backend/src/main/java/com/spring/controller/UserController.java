package com.spring.controller;

import com.spring.dto.Response.SearchMovieByGenreResponse;
import com.spring.dto.Response.SearchMovieByNameResponse;
import com.spring.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello, User!");
    }

    // Other user-related API endpoints
    @GetMapping("/search-movie-by-name")
    public ResponseEntity<List<SearchMovieByNameResponse>> getAllMovies(
            @RequestParam(required = false, name = "title") String title,
            @RequestParam(required = false, name = "limit", defaultValue = "10") Integer limit,
            @RequestParam(required = false, name = "offset", defaultValue = "0") Integer offset)
    {
        return ResponseEntity.ok(movieService.getAllMovies(title, limit, offset));
    }

    @GetMapping("/search-movie-by-genre")
    public ResponseEntity<List<SearchMovieByGenreResponse>> getAllMoviesByMovieGenreSet(
            @RequestParam(required = false, name = "movieGenreId") Integer movieGenreId) {
        return ResponseEntity.ok(movieService.getAllMoviesByMovieGenreSet(movieGenreId));
    }
}
