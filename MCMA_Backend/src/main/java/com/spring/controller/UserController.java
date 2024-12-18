package com.spring.controller;

import com.spring.config.JwtUtil;
import com.spring.dto.request.booking.BookingRequest;
import com.spring.dto.request.respond.MovieRespondRequest;
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
        return ResponseEntity.ok("Hello, User! Your ID is: %d".formatted(userId));
    }

    // TODO: Search Movies
    @GetMapping("/search-movie-by-name")
    public ResponseEntity<List<SearchMovieByNameResponse>> getAllMovies(
            @RequestParam(required = false, name = "title") String title,
            @RequestParam(required = false, name = "limit", defaultValue = "100") Integer limit,
            @RequestParam(required = false, name = "offset", defaultValue = "0") Integer offset) {
        return ResponseEntity.ok(movieService.getAllMovies(title, limit, offset));
    }

    @GetMapping("/search-movie-by-genre")
    public ResponseEntity<List<SearchMovieByGenreResponse>> getAllMoviesByMovieGenreSet(
            @RequestParam(required = false, name = "movieGenreId") Integer movieGenreId) {
        return ResponseEntity.ok(movieService.getAllMoviesByMovieGenreSet(movieGenreId));
    }

    @GetMapping("/search-movie-by-movie-genre-name")
    public ResponseEntity<List<SearchMovieByGenreResponse>> getAllMoviesByMovieGenreName(
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "limit", defaultValue = "100") Integer limit,
            @RequestParam(required = false, name = "offset", defaultValue = "0") Integer offset
    ) {
        return ResponseEntity.ok(movieService.getAllMoviesByMovieGenreName(name, limit, offset));
    }

    // TODO: Booking ticket(s)
    @GetMapping("/information/allMovies")
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        List<MovieResponse> movieResponds = bookingService.getAllMovies();
        return ResponseEntity.ok(movieResponds);
    }

    @GetMapping("/booking/allCitiesByMovie/{movieId}")
    public ResponseEntity<List<CityResponse>> getAllCitiesByMovie(@PathVariable Integer movieId) {
        List<CityResponse> cityResponses = bookingService.getAllCitiesBySelectedMovie(movieId);
        return ResponseEntity.ok(cityResponses);
    }

    @GetMapping("/booking/allCinemasByCity/{cityId}")
    public ResponseEntity<List<CinemaResponse>> getAllCinemasByCity(@PathVariable Integer cityId) {
        List<CinemaResponse> movies = bookingService.getAllCinemasBySelectedCity(cityId);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/booking/allScreenByCinema/{cinemaId}")
    public ResponseEntity<List<ScreenResponse>> getAllScreenByCinema(@PathVariable Integer cinemaId) {
        List<ScreenResponse> screenResponses = bookingService.getAllScreensBySelectedCinema(cinemaId);
        return ResponseEntity.ok(screenResponses);
    }

    @GetMapping("/booking/allSchedulesByMovieAndCinemaAndScreen")
    public ResponseEntity<List<ScheduleResponse>> getAllScheduleByScreen(
            @RequestParam(required = false, name = "movieId") Integer movieId,
            @RequestParam(required = false, name = "cinemaId") Integer cinemaId,
            @RequestParam(required = false, name = "screenId") Integer screenId
    ) {
        List<ScheduleResponse> scheduleResponses = bookingService.getAllSchedulesBySelectedMovieAndSelectedCinemaAndSelectedScreen(
                movieId, cinemaId, screenId
        );
        return ResponseEntity.ok(scheduleResponses);
    }

    @GetMapping("/booking/allTickets")
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        List<TicketResponse> ticketResponses = bookingService.getAllTickets();
        return ResponseEntity.ok(ticketResponses);
    }

    @GetMapping("/booking/allUnavailableSeatsByScreen/{screenId}")
    public ResponseEntity<List<UnavailableSeatResponse>> getAllUnavailableSeatsByScreen(@PathVariable Integer screenId) {
        List<UnavailableSeatResponse> unavailableSeatResponses = bookingService.getAllUnavailableSeatsBySelectedScreen(screenId);
        return ResponseEntity.ok(unavailableSeatResponses);
    }

    @GetMapping("/booking/allHeldSeatsByScreen/{screenId}")
    public ResponseEntity<List<HeldSeatResponse>> getAllHeldSeatsByScreen(@PathVariable Integer screenId) {
        List<HeldSeatResponse> heldSeatResponses = bookingService.getAllHeldSeatsBySelectedScreen(screenId);
        return ResponseEntity.ok(heldSeatResponses);
    }

    @GetMapping("/booking/allAvailableSeatsByScreen/{screenId}")
    public ResponseEntity<List<AvailableSeatResponse>> getAllAvailableSeatsByScreen(@PathVariable Integer screenId) {
        List<AvailableSeatResponse> availableSeatResponses = bookingService.getAllAvailableSeatsBySelectedScreen(screenId);
        return ResponseEntity.ok(availableSeatResponses);
    }

    @GetMapping("/booking/allFoodsAndDrinksByCinema/{cinemaId}")
    public ResponseEntity<List<ListFoodAndDrinkToOrderingResponse>> getAllFoodsAndDrinksByCinema(@PathVariable Integer cinemaId) {
        List<ListFoodAndDrinkToOrderingResponse> listFoodAndDrinkToOrderingResponses = bookingService.getAllFoodsAndDrinksByCinema(cinemaId);
        return ResponseEntity.ok(listFoodAndDrinkToOrderingResponses);
    }

    @GetMapping("/booking/allCouponsByUser")
    public ResponseEntity<List<CouponResponse>> getAllCouponsByUser(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<CouponResponse> couponResponses = bookingService.getAllCouponsByUser(userId);
        return ResponseEntity.ok(couponResponses);
    }

    @GetMapping("/booking/allCouponsByMovie/{movieId}")
    public ResponseEntity<List<CouponResponse>> getAllCoupons(@PathVariable Integer movieId) {
        List<CouponResponse> couponResponses = bookingService.getAllCouponsByMovie(movieId);
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

    @PostMapping("/booking/updateBookingSeat")
    public ResponseEntity<String> updateBookingSeat(@RequestBody BookingRequest bookingRequest) {
        bookingService.updateBookingSeat(bookingRequest);
        return ResponseEntity.ok("Seat(s) updated successfully");
    }

    @PostMapping("/booking/cancel-booking/{bookingId}")
    public ResponseEntity<String> cancelBooking(HttpServletRequest request, @PathVariable Integer bookingId) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        bookingService.cancelBooking(bookingId, userId);
        return ResponseEntity.ok("Booking canceled successfully");
    }

    @PostMapping("/booking/revoke-cancel-booking/{bookingId}")
    public ResponseEntity<String> revokeCancelBooking(HttpServletRequest request, @PathVariable Integer bookingId) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        bookingService.cancelBooking(bookingId, userId);
        return ResponseEntity.ok("Booking reinstated successfully");
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

    @DeleteMapping("/movieRespond/delete/{movieId}")
    public ResponseEntity<String> deleteRespond(HttpServletRequest request, @PathVariable Integer movieId) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        movieRespondService.deleteMovieRespond(userId, movieId);
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

    @GetMapping("/view/cinemaListByCity/{cityId}")
    public ResponseEntity<ViewCinemaResponse> getCinemasByCity(@PathVariable Integer cityId) {
        ViewCinemaResponse viewCinemaResponse = viewService.getCinemasByCity(cityId);
        return ResponseEntity.ok(viewCinemaResponse);
    }

    @GetMapping("/view/allScreen")
    public ResponseEntity<List<ScreenResponse>> getAllScreen() {
        List<ScreenResponse> screenResponses = viewService.getAllScreens();
        return ResponseEntity.ok(screenResponses);
    }

    @GetMapping("/view/allScheduleByCinemaAndMovie")
    public ResponseEntity<List<ScheduleResponse>> getAllScheduleByCinemaAndMovie(
            @RequestParam Integer movieId,
            @RequestParam Integer cinemaId
    ) {
        List<ScheduleResponse> screenResponses = viewService.getAllSchedulesBySelectedMovieAndSelectedCinema(movieId, cinemaId);
        return ResponseEntity.ok(screenResponses);
    }

    @GetMapping("/view/allScheduleByCinema/{cinemaId}")
    public ResponseEntity<List<ScheduleResponse>> getAllScheduleByCinema(@PathVariable Integer cinemaId) {
        List<ScheduleResponse> screenResponses = viewService.getAllSchedulesBySelectedCinema(cinemaId);
        return ResponseEntity.ok(screenResponses);
    }

    @GetMapping("/view/getAllFoodsAndDrinksByCinema/{cinemaId}")
    public ResponseEntity<List<ListFoodAndDrinkToOrderingResponse>> viewAllFoodsAndDrinksByCinema(@PathVariable Integer cinemaId) {
        List<ListFoodAndDrinkToOrderingResponse> listFoodAndDrinkToOrderingResponse = viewService.getAllFoodsAndDrinksByCinema(cinemaId);
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

    @GetMapping("/view/highRatingMovies")
    public ResponseEntity<List<HighRatingMovieResponse>> getHighRatingMovies() {
        List<HighRatingMovieResponse> highRatingMovieResponses = viewService.getHighRatingMovies();
        return ResponseEntity.ok(highRatingMovieResponses);
    }

    @GetMapping("/view/allMovieGenres")
    public ResponseEntity<List<MovieGenreResponse>> getAllMovieGenres() {
        List<MovieGenreResponse> movieGenreResponses = viewService.getAllMovieGenres();
        return ResponseEntity.ok(movieGenreResponses);
    }

    @GetMapping("/view/notifications")
    public ResponseEntity<NotificationResponse> getNotifications(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        NotificationResponse notificationResponse = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notificationResponse);
    }

    @GetMapping("/view/allBookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookingResponse> bookingResponses = viewService.getAllBookings();
        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/view/allBookingsByUser")
    public ResponseEntity<List<BookingResponse>> getAllBookingsByUser(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<BookingResponse> bookingResponses = viewService.getAllBookingsByUser(userId);
        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/view/viewCommentByUserAndMovie/{movieId}")
    public ResponseEntity<CommentResponse> viewCommentByUserAndMovie(HttpServletRequest request, @PathVariable Integer movieId) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        CommentResponse commentResponse = movieRespondService.getMovieCommentByUserIdAndMovieId(userId, movieId);
        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("/view/viewCommentByUser")
    public ResponseEntity<List<CommentResponse>> viewCommentByUser(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<CommentResponse> commentResponses = movieRespondService.getMovieCommentsByUserId(userId);
        return ResponseEntity.ok(commentResponses);
    }

    @GetMapping("/view/viewCommentByMovie/{movieId}")
    public ResponseEntity<List<CommentResponse>> viewCommentByMovie(@PathVariable Integer movieId) {
        List<CommentResponse> commentResponses = movieRespondService.getMovieCommentsByMovieId(movieId);
        return ResponseEntity.ok(commentResponses);
    }

    @GetMapping("/view/viewRatingByUserAndMovie/{movieId}")
    public ResponseEntity<RatingResponse> viewRatingByUserAndMovie(HttpServletRequest request, @PathVariable Integer movieId) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        RatingResponse ratingResponse = movieRespondService.getMovieRatingByUserIdAndMovieId(userId, movieId);
        return ResponseEntity.ok(ratingResponse);
    }

    @GetMapping("/view/viewRatingByUser")
    public ResponseEntity<List<RatingResponse>> viewRatingByUser(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<RatingResponse> ratingResponses = movieRespondService.getMovieRatingsByUserId(userId);
        return ResponseEntity.ok(ratingResponses);
    }

    @GetMapping("/view/viewRatingByMovie/{movieId}")
    public ResponseEntity<List<RatingResponse>> viewRatingByMovie(@PathVariable Integer movieId) {
        List<RatingResponse> ratingResponses = movieRespondService.getMovieRatingsByMovieId(movieId);
        return ResponseEntity.ok(ratingResponses);
    }

    @GetMapping("/view/viewMovieRespondByUserAndMovie/{movieId}")
    public ResponseEntity<MovieRespondResponse> viewMovieRespondByUserAndMovie(HttpServletRequest request, @PathVariable Integer movieId) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        MovieRespondResponse movieRespondResponse = movieRespondService.getMovieRespondsByUserIdAndMovieId(userId, movieId);
        return ResponseEntity.ok(movieRespondResponse);
    }

    @GetMapping("/view/viewMovieRespondByUser")
    public ResponseEntity<List<MovieRespondResponse>> viewMovieRespondByUser(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<MovieRespondResponse> movieRespondResponses = movieRespondService.getAllMovieRespondsByUserId(userId);
        return ResponseEntity.ok(movieRespondResponses);
    }

    @GetMapping("/view/viewMovieRespondByMovie/{movieId}")
    public ResponseEntity<List<MovieRespondResponse>> viewMovieRespondByMovie(@PathVariable Integer movieId) {
        List<MovieRespondResponse> movieRespondResponses = movieRespondService.getAllMovieRespondsByMovieId(movieId);
        return ResponseEntity.ok(movieRespondResponses);
    }
}

