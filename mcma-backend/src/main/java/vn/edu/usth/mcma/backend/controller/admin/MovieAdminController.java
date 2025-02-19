package vn.edu.usth.mcma.backend.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.admin.dto.cinema.FilterScreen;
import vn.edu.usth.mcma.backend.dto.admin.dto.movie.FilterMovie;
import vn.edu.usth.mcma.backend.dto.admin.dto.movie.MovieShortResponse;
import vn.edu.usth.mcma.backend.dto.movie.MovieRequest;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class MovieAdminController {
    private final MovieService movieService;

    /*
     * ========
     * movie
     * ========
     */
    @GetMapping("/movie")
    public ResponseEntity<List<MovieShortResponse>> findAllMovies() {
        return ResponseEntity.ok(movieService.findAllMovies());
    }
    @PutMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse> updateMovie(@PathVariable Long movieId, @RequestBody MovieRequest movieRequest) {
        return ResponseEntity.ok(movieService.updateMovie(movieId, movieRequest));
    }

    @GetMapping("/movie/filter")
    public ResponseEntity<List<FilterMovie>> filterByMovie() {
        return ResponseEntity.ok(movieService.filterByMovie());
    }
}
