package vn.edu.usth.mcma.backend.module.movie.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import vn.edu.usth.mcma.backend.common.dto.ApiResponse;
import vn.edu.usth.mcma.backend.module.movie.dto.request.FilterMovie;
import vn.edu.usth.mcma.backend.module.movie.dto.request.MovieRequest;
import vn.edu.usth.mcma.backend.module.movie.dto.response.MovieShortResponse;
import vn.edu.usth.mcma.backend.module.movie.service.MovieService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class MovieAdminController {
    private final MovieService movieService;

    /*
     * ========
     * Movie
     * ========
     */
    @GetMapping("/movie")
    public ResponseEntity<List<MovieShortResponse>> findAllMovies() {
        return ResponseEntity.ok(movieService.findAllMovies());
    }

    @PutMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse> updateMovie(
            @PathVariable @NonNull Long movieId, @RequestBody MovieRequest movieRequest) {
        return ResponseEntity.ok(movieService.updateMovie(movieId, movieRequest));
    }

    @GetMapping("/movie/filter")
    public ResponseEntity<List<FilterMovie>> filterByMovie() {
        return ResponseEntity.ok(movieService.filterByMovie());
    }
}
