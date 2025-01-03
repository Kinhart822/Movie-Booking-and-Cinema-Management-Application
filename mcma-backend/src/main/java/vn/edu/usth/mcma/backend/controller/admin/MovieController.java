package vn.edu.usth.mcma.backend.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.usth.mcma.backend.dto.MovieScheduleRequest;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.service.MovieService;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

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
