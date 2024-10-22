package com.spring.controller;

import com.spring.config.JwtUtil;
import com.spring.dto.Request.movieRespond.MovieRespondRequest;
import com.spring.dto.Request.view.ViewCinemaRequest;
import com.spring.dto.Request.view.ViewCouponRequest;
import com.spring.dto.Response.SearchMovieByGenreResponse;
import com.spring.dto.Response.SearchMovieByNameResponse;
import com.spring.dto.Response.movieRespond.CommentResponse;
import com.spring.dto.Response.movieRespond.MovieRespondResponse;
import com.spring.dto.Response.movieRespond.RatingResponse;
import com.spring.dto.Response.view.*;
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

    @GetMapping("/view/cinemaListByCity")
    public ResponseEntity<ViewCinemaResponse> getCoupons(@RequestBody ViewCinemaRequest viewCinemaRequest) {
        ViewCinemaResponse viewCinemaResponse = viewService.getCinemasByCity(viewCinemaRequest);
        return ResponseEntity.ok(viewCinemaResponse);
    }

    @GetMapping("/view/coupons")
    public ResponseEntity<ViewCouponsResponse> getCoupons(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        ViewCouponsResponse viewCouponsResponse = viewService.getAvailableCouponsForUser(userId);
        return ResponseEntity.ok(viewCouponsResponse);
    }

    @GetMapping("/view/couponsByMovie")
    public ResponseEntity<ViewCouponsResponse> getCoupons(@RequestBody ViewCouponRequest viewCouponRequest) {
        ViewCouponsResponse viewCouponsResponse = viewService.getAvailableCouponsByMovieId(viewCouponRequest);
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

    @GetMapping("/view/viewCommentByMovie")
    public ResponseEntity<List<CommentResponse>> viewCommentByMovie(@RequestBody MovieRespondRequest movieRespondRequest) {
        List<CommentResponse> commentResponses = movieRespondService.getMovieCommentsByMovieId(movieRespondRequest);
        return ResponseEntity.ok(commentResponses);
    }

    @GetMapping("/view/viewRatingByMovie")
    public ResponseEntity<List<RatingResponse>> viewRatingByMovie(@RequestBody MovieRespondRequest movieRespondRequest) {
        List<RatingResponse> ratingResponses = movieRespondService.getMovieRatingsByMovieId(movieRespondRequest);
        return ResponseEntity.ok(ratingResponses);
    }

    @GetMapping("/view/viewMovieRespondByMovie")
    public ResponseEntity<List<MovieRespondResponse>> viewMovieRespondByMovie(@RequestBody MovieRespondRequest movieRespondRequest) {
        List<MovieRespondResponse> movieRespondResponses = movieRespondService.getAllMovieRespondsByMovieId(movieRespondRequest);
        return ResponseEntity.ok(movieRespondResponses);
    }
}
