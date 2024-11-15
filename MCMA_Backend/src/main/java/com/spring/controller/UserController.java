package com.spring.controller;

import com.spring.config.JwtUtil;
import com.spring.dto.request.booking.BookingRequest;
import com.spring.dto.request.booking.*;
import com.spring.dto.request.movieRespond.MovieRespondRequest;
import com.spring.dto.request.view.ViewCinemaRequest;
import com.spring.dto.request.view.ViewCouponRequest;
import com.spring.dto.request.view.ViewFoodAndDrinkRequest;
import com.spring.dto.response.booking.BookingResponse;
import com.spring.dto.response.SearchMovieByGenreResponse;
import com.spring.dto.response.SearchMovieByNameResponse;
import com.spring.dto.response.booking.*;
import com.spring.dto.response.movieRespond.CommentResponse;
import com.spring.dto.response.movieRespond.MovieRespondResponse;
import com.spring.dto.response.movieRespond.RatingResponse;
import com.spring.dto.response.view.*;
import com.spring.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ViewService viewService;

    @Autowired
    private MovieRespondService movieRespondService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<String> sayHello(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        return ResponseEntity.ok("Hello, User! Your ID is: " + userId);
    }

    // TODO: Search Movies
    @GetMapping("/search-movie-by-name")
    public ResponseEntity<List<SearchMovieByNameResponse>> getAllMovies(
            @RequestParam(required = false, name = "title") String title,
            @RequestParam(required = false, name = "limit", defaultValue = "10") Integer limit,
            @RequestParam(required = false, name = "offset", defaultValue = "0") Integer offset) {
        return ResponseEntity.ok(movieService.getAllMovies(title, limit, offset));
    }

    @GetMapping("/search-movie-by-genre")
    public ResponseEntity<List<SearchMovieByGenreResponse>> getAllMoviesByMovieGenreSet(
            @RequestParam(required = false, name = "movieGenreId") Integer movieGenreId) {
        return ResponseEntity.ok(movieService.getAllMoviesByMovieGenreSet(movieGenreId));
    }

    // TODO: Booking ticket(s)
    @GetMapping("/information/allMovies")
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        List<MovieResponse> movieResponds = bookingService.getAllMovies();
        return ResponseEntity.ok(movieResponds);
    }

    @GetMapping("/booking/allCitiesByMovie")
    public ResponseEntity<List<CityResponse>> getAllCitiesByMovie(@RequestBody MovieRequest movieRequest) {
        List<CityResponse> cityResponses = bookingService.getAllCitiesBySelectedMovie(movieRequest);
        return ResponseEntity.ok(cityResponses);
    }

    @GetMapping("/booking/allCinemasByCity")
    public ResponseEntity<List<CinemaResponse>> getAllCinemasByCity(@RequestBody CityRequest cityRequest) {
        List<CinemaResponse> movies = bookingService.getAllCinemasBySelectedCity(cityRequest);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/booking/allScreenByCinema")
    public ResponseEntity<List<ScreenResponse>> getAllScreenByCinema(@RequestBody CinemaRequest cinemaRequest) {
        List<ScreenResponse> screenResponses = bookingService.getAllScreensBySelectedCinema(cinemaRequest);
        return ResponseEntity.ok(screenResponses);
    }

    @GetMapping("/booking/allSchedulesByMovieAndCinemaAndScreen")
    public ResponseEntity<List<ScheduleResponse>> getAllScheduleByScreen(
            @RequestBody MovieRequest movieRequest,
            @RequestBody CinemaRequest cinemaRequest,
            @RequestBody ScreenRequest screenRequest
    ) {
        List<ScheduleResponse> scheduleResponses = bookingService.getAllSchedulesBySelectedMovieAndSelectedCinemaAndSelectedScreen(
                movieRequest, cinemaRequest, screenRequest
        );
        return ResponseEntity.ok(scheduleResponses);
    }

    @GetMapping("/booking/allTickets")
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        List<TicketResponse> ticketResponses = bookingService.getAllTickets();
        return ResponseEntity.ok(ticketResponses);
    }

    @GetMapping("/booking/allSeatsByScreen")
    public ResponseEntity<List<SeatResponse>> getAllSeatsByScreen(@RequestBody ScreenRequest screenRequest) {
        List<SeatResponse> seatResponses = bookingService.getAllSeatsBySelectedScreen(screenRequest);
        return ResponseEntity.ok(seatResponses);
    }

    @GetMapping("/booking/allFoodsAndDrinksByCinema")
    public ResponseEntity<List<ListFoodAndDrinkToOrderingResponse>> getAllFoodsAndDrinksByCinema(@RequestBody CinemaRequest cinemaRequest) {
        List<ListFoodAndDrinkToOrderingResponse> listFoodAndDrinkToOrderingResponses = bookingService.getAllFoodsAndDrinksByCinema(cinemaRequest);
        return ResponseEntity.ok(listFoodAndDrinkToOrderingResponses);
    }

    @GetMapping("/booking/allCouponsByUser")
    public ResponseEntity<List<CouponResponse>> getAllCouponsByUser(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<CouponResponse> couponResponses = bookingService.getAllCouponsByUser(userId);
        return ResponseEntity.ok(couponResponses);
    }

    @GetMapping("/booking/allCouponsByMovie")
    public ResponseEntity<List<CouponResponse>> getAllCoupons(@RequestBody MovieRequest movieRequest) {
        List<CouponResponse> couponResponses = bookingService.getAllCouponsByMovie(movieRequest);
        return ResponseEntity.ok(couponResponses);
    }

    @PostMapping("/booking/processingBooking")
    public ResponseEntity<SendBookingResponse> processingBooking(HttpServletRequest request, @RequestBody BookingRequest bookingRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        SendBookingResponse bookingResponse = bookingService.processingBooking(userId, bookingRequest);
        return ResponseEntity.ok(bookingResponse);
    }

    @PostMapping("/booking/completeBooking")
    public ResponseEntity<BookingResponse> completeBooking(HttpServletRequest request, @RequestBody BookingRequest bookingRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        BookingResponse bookingResponse = bookingService.completeBooking(userId, bookingRequest);
        return ResponseEntity.ok(bookingResponse);
    }

    @PostMapping("/booking/cancel-booking/{bookingId}")
    public ResponseEntity<String> cancelBooking(HttpServletRequest request, @PathVariable Integer bookingId) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        bookingService.cancelBooking(bookingId, userId);
        return ResponseEntity.ok("Booking canceled successfully");
    }

    @DeleteMapping("booking/delete-booking/{bookingId}")
    public ResponseEntity<String> deleteBooking(HttpServletRequest request, @PathVariable Integer bookingId) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        bookingService.deleteBooking(bookingId, userId);
        return ResponseEntity.ok("Booking deleted successfully");
    }

    // TODO: Comments and Ratings for a Movie
    @PostMapping("/movieRespond/add")
    public ResponseEntity<MovieRespondResponse> addRespond(HttpServletRequest request, @RequestBody MovieRespondRequest movieRespondRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        MovieRespondResponse movieRespondResponse = movieRespondService.createMovieRespond(userId, movieRespondRequest);
        return ResponseEntity.ok(movieRespondResponse);
    }

    @PutMapping("/movieRespond/update")
    public ResponseEntity<MovieRespondResponse> updateRespond(HttpServletRequest request, @RequestBody MovieRespondRequest movieRespondRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        MovieRespondResponse movieRespondResponse = movieRespondService.updateMovieRespond(userId, movieRespondRequest);
        return ResponseEntity.ok(movieRespondResponse);
    }

    @DeleteMapping("/movieRespond/delete")
    public ResponseEntity<String> deleteRespond(HttpServletRequest request, @RequestBody MovieRespondRequest movieRespondRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        movieRespondService.deleteMovieRespond(userId, movieRespondRequest);
        return ResponseEntity.ok("Delete Movie Respond Successfully");
    }

    // TODO: View
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
    public ResponseEntity<ViewCinemaResponse> getCinemasByCity(@RequestBody ViewCinemaRequest viewCinemaRequest) {
        ViewCinemaResponse viewCinemaResponse = viewService.getCinemasByCity(viewCinemaRequest);
        return ResponseEntity.ok(viewCinemaResponse);
    }

    @GetMapping("/view/allScreen")
    public ResponseEntity<List<ScreenResponse>> getAllScreen() {
        List<ScreenResponse> screenResponses = viewService.getAllScreens();
        return ResponseEntity.ok(screenResponses);
    }

    @GetMapping("/view/allScheduleByCinemaAndMovie")
    public ResponseEntity<List<ScheduleResponse>> getAllScreenByCinemaAndMovie(
            @RequestParam Integer movieId,
            @RequestParam Integer cinemaId
    ) {
        List<ScheduleResponse> screenResponses = viewService.getAllSchedulesBySelectedMovieAndSelectedCinema(movieId, cinemaId);
        return ResponseEntity.ok(screenResponses);
    }

    @GetMapping("/view/getAllFoodsAndDrinksByCinema")
    public ResponseEntity<List<ListFoodAndDrinkToOrderingResponse>> getAllFoodsAndDrinksByCinema(@RequestBody ViewFoodAndDrinkRequest viewFoodAndDrinkRequest) {
        List<ListFoodAndDrinkToOrderingResponse> listFoodAndDrinkToOrderingResponse = viewService.getAllFoodsAndDrinksByCinema(viewFoodAndDrinkRequest);
        return ResponseEntity.ok(listFoodAndDrinkToOrderingResponse);
    }

    @GetMapping("/view/allCoupons")
    public ResponseEntity<List<CouponResponse>> getAllCoupons() {
        List<CouponResponse> couponResponses = viewService.getAllCoupons();
        return ResponseEntity.ok(couponResponses);
    }

    @GetMapping("/view/couponsByUser")
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

    @GetMapping("/view/highRatingMovies")
    public ResponseEntity<List<HighRatingMovieResponse>> getHighRatingMovies() {
        List<HighRatingMovieResponse> highRatingMovieResponses = viewService.getHighRatingMovies();
        return ResponseEntity.ok(highRatingMovieResponses);
    }

    @GetMapping("/view/notifications")
    public ResponseEntity<NotificationResponse> getNotifications(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        NotificationResponse notificationResponse = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notificationResponse);
    }

    @GetMapping("/view/viewCommentByUserAndMovie")
    public ResponseEntity<CommentResponse> viewCommentByUserAndMovie(HttpServletRequest request, @RequestBody MovieRespondRequest movieRespondRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        CommentResponse commentResponse = movieRespondService.getMovieCommentByUserIdAndMovieId(userId, movieRespondRequest);
        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("/view/viewCommentByUser")
    public ResponseEntity<List<CommentResponse>> viewCommentByUser(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<CommentResponse> commentResponses = movieRespondService.getMovieCommentsByUserId(userId);
        return ResponseEntity.ok(commentResponses);
    }

    @GetMapping("/view/viewCommentByMovie")
    public ResponseEntity<List<CommentResponse>> viewCommentByMovie(@RequestBody MovieRespondRequest movieRespondRequest) {
        List<CommentResponse> commentResponses = movieRespondService.getMovieCommentsByMovieId(movieRespondRequest);
        return ResponseEntity.ok(commentResponses);
    }

    @GetMapping("/view/viewRatingByUserAndMovie")
    public ResponseEntity<RatingResponse> viewRatingByUserAndMovie(HttpServletRequest request, @RequestBody MovieRespondRequest movieRespondRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        RatingResponse ratingResponse = movieRespondService.getMovieRatingByUserIdAndMovieId(userId, movieRespondRequest);
        return ResponseEntity.ok(ratingResponse);
    }

    @GetMapping("/view/viewRatingByUser")
    public ResponseEntity<List<RatingResponse>> viewRatingByUser(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<RatingResponse> ratingResponses = movieRespondService.getMovieRatingsByUserId(userId);
        return ResponseEntity.ok(ratingResponses);
    }

    @GetMapping("/view/viewRatingByMovie")
    public ResponseEntity<List<RatingResponse>> viewRatingByMovie(@RequestBody MovieRespondRequest movieRespondRequest) {
        List<RatingResponse> ratingResponses = movieRespondService.getMovieRatingsByMovieId(movieRespondRequest);
        return ResponseEntity.ok(ratingResponses);
    }

    @GetMapping("/view/viewMovieRespondByUserAndMovie")
    public ResponseEntity<MovieRespondResponse> viewMovieRespondByUserAndMovie(HttpServletRequest request, @RequestBody MovieRespondRequest movieRespondRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        MovieRespondResponse movieRespondResponse = movieRespondService.getMovieRespondsByUserIdAndMovieId(userId, movieRespondRequest);
        return ResponseEntity.ok(movieRespondResponse);
    }

    @GetMapping("/view/viewMovieRespondByUser")
    public ResponseEntity<List<MovieRespondResponse>> viewMovieRespondByUser(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<MovieRespondResponse> movieRespondResponses = movieRespondService.getAllMovieRespondsByUserId(userId);
        return ResponseEntity.ok(movieRespondResponses);
    }

    @GetMapping("/view/viewMovieRespondByMovie")
    public ResponseEntity<List<MovieRespondResponse>> viewMovieRespondByMovie(@RequestBody MovieRespondRequest movieRespondRequest) {
        List<MovieRespondResponse> movieRespondResponses = movieRespondService.getAllMovieRespondsByMovieId(movieRespondRequest);
        return ResponseEntity.ok(movieRespondResponses);
    }
}

