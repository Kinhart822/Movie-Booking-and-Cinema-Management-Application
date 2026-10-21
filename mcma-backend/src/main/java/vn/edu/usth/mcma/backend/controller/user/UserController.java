package vn.edu.usth.mcma.backend.controller.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.usth.mcma.backend.dto.response.cinema.CinemaPresentation;
import vn.edu.usth.mcma.backend.dto.response.cinema.CityPresentation;
import vn.edu.usth.mcma.backend.dto.response.movie.MoviePresentation;
import vn.edu.usth.mcma.backend.dto.response.cinema.SchedulePresentation;
import vn.edu.usth.mcma.backend.dto.response.cinema.ScreenPresentation;
import vn.edu.usth.mcma.backend.security.jwt.JwtHelper;
import vn.edu.usth.mcma.backend.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final BookingService bookingService;
    private final JwtHelper jwtUtil;

    @GetMapping
    public ResponseEntity<String> sayHello() {
        Long userId = jwtUtil.getIdUserRequesting();
        return ResponseEntity.ok("Hello, User! Your ID is: %d".formatted(userId));
    }

    @GetMapping("/information/movie-information/{movieId}")
    public ResponseEntity<MoviePresentation> getAllInformationOfSelectedMovie(
            @PathVariable @NonNull Long movieId) {
        return ResponseEntity.ok(bookingService.getAllInformationOfSelectedMovie(movieId));
    }

    @GetMapping("/booking/allCitiesByMovie/{movieId}")
    public ResponseEntity<List<CityPresentation>> getAllCitiesByMovie(
            @PathVariable @NonNull Long movieId) {
        List<CityPresentation> cityResponses = bookingService.getAllCitiesBySelectedMovie(movieId);
        return ResponseEntity.ok(cityResponses);
    }

    @GetMapping("/booking/allCinemasByMovieAndCity")
    public ResponseEntity<List<CinemaPresentation>> getAllCinemasByMovieAndCity(
            @RequestParam(required = false, name = "movieId") @NonNull Long movieId,
            @RequestParam(required = false, name = "cityId") @NonNull Long cityId) {
        return ResponseEntity.ok(
                bookingService.getAllCinemasBySelectedMovieAndSelectedCity(movieId, cityId));
    }

    @GetMapping("/booking/allScreenByCinema/{cinemaId}")
    public ResponseEntity<List<ScreenPresentation>> getAllScreenByCinema(
            @PathVariable @NonNull Long cinemaId) {
        return ResponseEntity.ok(bookingService.getAllScreensBySelectedCinema(cinemaId));
    }

    @GetMapping("/booking/allSchedulesByMovieAndCinemaAndScreen")
    public ResponseEntity<List<SchedulePresentation>> getAllSchedulesByMovieAndScreen(
            @RequestParam(required = false, name = "movieId") @NonNull Long movieId,
            @RequestParam(required = false, name = "screenId") @NonNull Long screenId) {
        return ResponseEntity.ok(bookingService.getAllSchedulesByMovieAndScreen(movieId, screenId));
    }
}
