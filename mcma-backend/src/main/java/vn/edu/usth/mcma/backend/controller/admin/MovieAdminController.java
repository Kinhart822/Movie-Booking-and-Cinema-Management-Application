package vn.edu.usth.mcma.backend.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.MovieRequest;
import vn.edu.usth.mcma.backend.dto.MovieScheduleRequest;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.service.MovieService;

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
    @PutMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse> updateMovie(@PathVariable Long movieId, @RequestBody MovieRequest movieRequest) {
        return ResponseEntity.ok(movieService.updateMovie(movieId, movieRequest));
    }
    /*
     * ========
     * schedule
     * ========
     */
    @PostMapping("/movie/schedule")
    public ApiResponse addMovieToSchedule(@RequestBody MovieScheduleRequest request) {
        return movieService.addMovieToSchedule(request);
    }
}
