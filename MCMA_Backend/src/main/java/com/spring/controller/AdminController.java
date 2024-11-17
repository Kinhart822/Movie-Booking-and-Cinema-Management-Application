package com.spring.controller;

import com.spring.config.JwtUtil;
import com.spring.dto.request.respond.MovieRespondRequest;
import com.spring.dto.response.SearchMovieByGenreResponse;
import com.spring.dto.response.SearchMovieByNameResponse;
import com.spring.dto.response.movieRespond.CommentResponse;
import com.spring.dto.response.movieRespond.MovieRespondResponse;
import com.spring.dto.response.movieRespond.RatingResponse;
import com.spring.dto.response.view.*;
import com.spring.service.MovieRespondService;
import com.spring.service.MovieService;
import com.spring.service.NotificationService;
import com.spring.service.ViewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MovieService movieService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ViewService viewService;

    @Autowired
    private MovieRespondService movieRespondService;

    @GetMapping
    public ResponseEntity<String> sayHello(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        return ResponseEntity.ok("Hello, Admin! Your ID is: " + userId);
    }

    // Search Movies
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

    // View
    @GetMapping("/view/cityList")
    public ResponseEntity<ViewCityResponse> getCities() {
        ViewCityResponse viewCityResponse = viewService.getAvailableCities();
        return ResponseEntity.ok(viewCityResponse);
    }

    @GetMapping("/view/cinemaList")
    public ResponseEntity<ViewCinemaResponse> getCinemaList() {
        ViewCinemaResponse viewCinemaResponse = viewService.getAvailableCinemaList();
        return ResponseEntity.ok(viewCinemaResponse);
    }

    @GetMapping("/view/cinemaListByCity/{cityId}")
    public ResponseEntity<ViewCinemaResponse> getCinemaListByCity(@PathVariable Integer cityId) {
        ViewCinemaResponse viewCinemaResponse = viewService.getCinemasByCity(cityId);
        return ResponseEntity.ok(viewCinemaResponse);
    }

    @GetMapping("/view/coupons")
    public ResponseEntity<ViewCouponsResponse> getCouponsByUser(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        ViewCouponsResponse viewCouponsResponse = viewService.getAvailableCouponsForUser(userId);
        return ResponseEntity.ok(viewCouponsResponse);
    }

    @GetMapping("/view/couponsByMovie/{movieId}")
    public ResponseEntity<ViewCouponsResponse> getCouponsByMovie(@PathVariable Integer movieId) {
        ViewCouponsResponse viewCouponsResponse = viewService.getAvailableCouponsByMovieId(movieId);
        return ResponseEntity.ok(viewCouponsResponse);
    }

    @GetMapping("/view/nowShowingMovies")
    public ResponseEntity<List<NowShowingResponse>> getAvailableNowShowingMovies() {
        List<NowShowingResponse> nowShowingMovies = viewService.getAvailableNowShowingMovies();
        return ResponseEntity.ok(nowShowingMovies);
    }

    @GetMapping("/view/comingSoonMovies")
    public ResponseEntity<List<ComingSoonResponse>> getAvailableComingSoonMovies() {
        List<ComingSoonResponse> comingSoonMovies = viewService.getAvailableComingSoonMovies();
        return ResponseEntity.ok(comingSoonMovies);
    }

    @GetMapping("/view/notifications")
    public ResponseEntity<NotificationResponse> getNotifications(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        NotificationResponse notificationResponse = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notificationResponse);
    }

    @GetMapping("/view/viewCommentByMovie/{movieId}")
    public ResponseEntity<List<CommentResponse>> viewCommentByMovie(@PathVariable Integer movieId) {
        List<CommentResponse> commentResponses = movieRespondService.getMovieCommentsByMovieId(movieId);
        return ResponseEntity.ok(commentResponses);
    }

    @GetMapping("/view/viewRatingByMovie/{movieId}")
    public ResponseEntity<List<RatingResponse>> viewRatingByMovie(@PathVariable Integer movieId) {
        List<RatingResponse> ratingResponses = movieRespondService.getMovieRatingsByMovieId(movieId);
        return ResponseEntity.ok(ratingResponses);
    }

    @GetMapping("/view/viewMovieRespondByMovie/{movieId}")
    public ResponseEntity<List<MovieRespondResponse>> viewMovieRespondByMovie(@PathVariable Integer movieId) {
        List<MovieRespondResponse> movieRespondResponses = movieRespondService.getAllMovieRespondsByMovieId(movieId);
        return ResponseEntity.ok(movieRespondResponses);
    }
}
