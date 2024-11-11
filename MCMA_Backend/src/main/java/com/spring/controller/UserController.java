package com.spring.controller;

import com.spring.config.JwtUtil;
import com.spring.dto.request.booking.*;
import com.spring.dto.request.movieRespond.MovieRespondRequest;
import com.spring.dto.request.view.ViewCinemaRequest;
import com.spring.dto.request.view.ViewCouponRequest;
import com.spring.dto.request.view.ViewFoodAndDrinkRequest;
import com.spring.dto.response.SearchMovieByGenreResponse;
import com.spring.dto.response.SearchMovieByNameResponse;
import com.spring.dto.response.booking.*;
import com.spring.dto.response.booking.bookingSelected.*;
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

    @PostMapping("/booking/choose-movie")
    public ResponseEntity<SelectedMovieResponse> selectMovie(HttpServletRequest request, @RequestBody MovieRequest movieRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        SelectedMovieResponse movieRespond = bookingService.selectMovie(movieRequest, userId);
        return ResponseEntity.ok(movieRespond);
    }
    @GetMapping("/booking/allCitiesByMovie")
    public ResponseEntity<List<CityResponse>> getAllCitiesByMovie(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<CityResponse> cityResponses = bookingService.getAllCitiesBySelectedMovie(userId);
        return ResponseEntity.ok(cityResponses);
    }

    @PostMapping("/booking/select-city")
    public ResponseEntity<SelectedCityResponse> selectCity(HttpServletRequest request, @RequestBody CityRequest cityRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        SelectedCityResponse cityResponse = bookingService.selectCity(cityRequest, userId);
        return ResponseEntity.ok(cityResponse);
    }

    @GetMapping("/booking/allCinemasByCity")
    public ResponseEntity<List<CinemaResponse>> getAllCinemasByCity(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<CinemaResponse> movies = bookingService.getAllCinemasBySelectedCity(userId);
        return ResponseEntity.ok(movies);
    }

    @PostMapping("/booking/select-cinema")
    public ResponseEntity<SelectedCinemaResponse> selectCinema(HttpServletRequest request, @RequestBody CinemaRequest cinemaRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        SelectedCinemaResponse cinemaResponse = bookingService.selectCinema(cinemaRequest, userId);
        return ResponseEntity.ok(cinemaResponse);
    }

    @GetMapping("/booking/allScreenByCinema")
    public ResponseEntity<List<ScreenResponse>> getAllScreenByCinema(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<ScreenResponse> screenResponses = bookingService.getAllScreensBySelectedCinema(userId);
        return ResponseEntity.ok(screenResponses);
    }

    @PostMapping("/booking/select-screen")
    public ResponseEntity<SelectedScreenResponse> selectScreen(HttpServletRequest request, @RequestBody ScreenRequest screenRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        SelectedScreenResponse screenResponse = bookingService.selectScreen(screenRequest, userId);
        return ResponseEntity.ok(screenResponse);
    }

    @GetMapping("/booking/allSchedulesByMovieAndCinemaAndScreen")
    public ResponseEntity<List<ScheduleResponse>> getAllScheduleByScreen(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<ScheduleResponse> scheduleResponses = bookingService.getAllSchedulesBySelectedMovieAndSelectedCinemaAndSelectedScreen(userId);
        return ResponseEntity.ok(scheduleResponses);
    }

    @PostMapping("/booking/select-schedule")
    public ResponseEntity<SelectedScheduleResponse> selectSchedule(HttpServletRequest request, @RequestBody ScheduleRequest scheduleRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        SelectedScheduleResponse selectedScheduleResponse = bookingService.selectSchedule(scheduleRequest, userId);
        return ResponseEntity.ok(selectedScheduleResponse);
    }

    @GetMapping("/booking/allTickets")
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        List<TicketResponse> ticketResponses = bookingService.getAllTickets();
        return ResponseEntity.ok(ticketResponses);
    }

    @PostMapping("/booking/select-tickets")
    public ResponseEntity<SelectedTicketResponse> selectTickets(HttpServletRequest request, @RequestBody TicketRequest ticketRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        SelectedTicketResponse ticketResponse = bookingService.selectTickets(ticketRequest, userId);
        return ResponseEntity.ok(ticketResponse);
    }

    @GetMapping("/booking/allSeatsByScreen")
    public ResponseEntity<List<SeatResponse>> getAllSeatsByScreen(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<SeatResponse> seatResponses = bookingService.getAllSeatsBySelectedScreen(userId);
        return ResponseEntity.ok(seatResponses);
    }

    @PostMapping("/booking/select-seats")
    public ResponseEntity<SelectedSeatsResponse> selectSeats(HttpServletRequest request, @RequestBody SeatRequest seatRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        SelectedSeatsResponse selectedSeatsResponse = bookingService.selectSeats(seatRequest, userId);
        return ResponseEntity.ok(selectedSeatsResponse);
    }

    @GetMapping("/booking/allFoodsAndDrinksByCinema")
    public ResponseEntity<List<ListFoodAndDrinkToOrderingResponse>> getAllFoodsAndDrinksByCinema(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<ListFoodAndDrinkToOrderingResponse> listFoodAndDrinkToOrderingResponses = bookingService.getAllFoodsAndDrinksByCinema(userId);
        return ResponseEntity.ok(listFoodAndDrinkToOrderingResponses);
    }

    @PostMapping("/booking/select-food")
    public ResponseEntity<SelectedFoodResponse> selectFood(HttpServletRequest request, @RequestBody FoodDrinkRequest foodDrinkRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        SelectedFoodResponse selectedFoodResponse = bookingService.selectFood(foodDrinkRequest, userId);
        return ResponseEntity.ok(selectedFoodResponse);
    }

    @PostMapping("/booking/select-drinks")
    public ResponseEntity<SelectedDrinkResponse> selectDrinks(HttpServletRequest request, @RequestBody FoodDrinkRequest foodDrinkRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        SelectedDrinkResponse selectedDrinkResponse = bookingService.selectDrinks(foodDrinkRequest, userId);
        return ResponseEntity.ok(selectedDrinkResponse);
    }

    @GetMapping("/booking/allCouponsByUser")
    public ResponseEntity<List<CouponResponse>> getAllCouponsByUser(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<CouponResponse> couponResponses = bookingService.getAllCouponsByUser(userId);
        return ResponseEntity.ok(couponResponses);
    }

    @GetMapping("/booking/allCouponsByMovie")
    public ResponseEntity<List<CouponResponse>> getAllCoupons(@RequestParam Integer movieId) {
        List<CouponResponse> couponResponses = bookingService.getAllCouponsByMovie(movieId);
        return ResponseEntity.ok(couponResponses);
    }

    @PostMapping("/booking/calculate-total-price")
    public ResponseEntity<CalculateResponse> getTotalPrice(HttpServletRequest request, @RequestBody CouponRequest couponRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        CalculateResponse updatedPrice = bookingService.calculateTotalPrice(couponRequest, userId);
        return ResponseEntity.ok(updatedPrice);
    }

    @PostMapping("/booking/complete-booking")
    public ResponseEntity<BookingResponse> completeBooking(HttpServletRequest request, @RequestBody CompleteRequest completeRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        BookingResponse bookingResponse = bookingService.completeBooking(completeRequest, userId);
        return ResponseEntity.ok(bookingResponse);
    }

    @PostMapping("/booking/cancel-booking/{bookingId}")
    public ResponseEntity<String> cancelBooking(HttpServletRequest request, @PathVariable Integer bookingId) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        bookingService.cancelBooking(bookingId, userId);
        return ResponseEntity.ok("Booking canceled successfully");
    }

    @DeleteMapping("booking/delete-booking-draft/{bookingDraftId}")
    public ResponseEntity<String> deleteBookingDraft(@PathVariable Integer bookingDraftId) {
        bookingService.deleteBookingDraft(bookingDraftId);
        return ResponseEntity.ok("Booking Draft deleted successfully");
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

