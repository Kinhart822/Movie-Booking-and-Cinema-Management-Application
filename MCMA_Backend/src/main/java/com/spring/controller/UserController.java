package com.spring.controller;

import com.spring.config.JwtUtil;
import com.spring.dto.Request.booking.*;
import com.spring.dto.Request.view.ViewCinemaRequest;
import com.spring.dto.Request.view.ViewCouponRequest;
import com.spring.dto.Response.SearchMovieByGenreResponse;
import com.spring.dto.Response.SearchMovieByNameResponse;
import com.spring.dto.Response.booking.*;
import com.spring.dto.Response.view.*;
import com.spring.entities.Booking;
import com.spring.service.BookingService;
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
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<String> sayHello(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        return ResponseEntity.ok("Hello, User! Your ID is: " + userId);
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

    // Booking ticket(s)
    @PostMapping("/booking/choose-movie")
    public ResponseEntity<BookingMovieRespond> selectMovie(HttpServletRequest request, @RequestBody MovieRequest movieRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        BookingMovieRespond movieRespond = bookingService.selectMovie(movieRequest, userId);
        return ResponseEntity.ok(movieRespond);
    }

    @PostMapping("/booking/select-city")
    public ResponseEntity<CityResponse> selectCity(HttpServletRequest request, @RequestBody CityRequest cityRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        CityResponse cityResponse = bookingService.selectCity(cityRequest, userId);
        return ResponseEntity.ok(cityResponse);
    }

    @PostMapping("/booking/select-cinema")
    public ResponseEntity<CinemaResponse> selectCinema(HttpServletRequest request, @RequestBody CinemaRequest cinemaRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        CinemaResponse cinemaResponse = bookingService.selectCinema(cinemaRequest, userId);
        return ResponseEntity.ok(cinemaResponse);
    }

    @PostMapping("/booking/select-screen")
    public ResponseEntity<ScreenResponse> selectScreen(HttpServletRequest request, @RequestBody ScreenRequest screenRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        ScreenResponse screenResponse = bookingService.selectScreen(screenRequest, userId);
        return ResponseEntity.ok(screenResponse);
    }

    @PostMapping("/booking/select-schedule")
    public ResponseEntity<ScheduleResponse> selectSchedule(HttpServletRequest request, @RequestBody ScheduleRequest scheduleRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        ScheduleResponse scheduleResponse = bookingService.selectSchedule(scheduleRequest, userId);
        return ResponseEntity.ok(scheduleResponse);
    }

    @PostMapping("/booking/select-tickets")
    public ResponseEntity<TicketResponse> selectTickets(HttpServletRequest request, @RequestBody TicketRequest ticketRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        TicketResponse ticketResponse = bookingService.selectTickets(ticketRequest, userId);
        return ResponseEntity.ok(ticketResponse);
    }

    @PostMapping("/booking/select-seats")
    public ResponseEntity<SeatResponse> selectSeats(HttpServletRequest request, @RequestBody SeatRequest seatRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        SeatResponse seatResponse = bookingService.selectSeats(seatRequest, userId);
        return ResponseEntity.ok(seatResponse);
    }

    @PostMapping("/booking/select-food")
    public ResponseEntity<FoodResponse> selectFood(HttpServletRequest request, @RequestBody FoodDrinkRequest foodDrinkRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        FoodResponse foodResponse = bookingService.selectFood(foodDrinkRequest, userId);
        return ResponseEntity.ok(foodResponse);
    }

    @PostMapping("/booking/select-drinks")
    public ResponseEntity<DrinkResponse> selectDrinks(HttpServletRequest request, @RequestBody FoodDrinkRequest foodDrinkRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        DrinkResponse drinkResponse = bookingService.selectDrinks(foodDrinkRequest, userId);
        return ResponseEntity.ok(drinkResponse);
    }

    @PostMapping("/booking/calculate-total-price")
    public ResponseEntity<Double> getTotalPrice(HttpServletRequest request, @RequestBody CouponRequest couponRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        Double updatedPrice = bookingService.calculateTotalPrice(couponRequest, userId);
        return ResponseEntity.ok(updatedPrice);
    }

    @PostMapping("/booking/complete-booking")
    public ResponseEntity<BookingResponse> completeBooking(HttpServletRequest request, @RequestBody CompleteRequest completeRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        BookingResponse bookingResponse = bookingService.completeBooking(completeRequest, userId);
        return ResponseEntity.ok(bookingResponse);
    }

    @DeleteMapping("booking/delete-booking/{bookingId}")
    public ResponseEntity<String> deleteBooking(HttpServletRequest request, @PathVariable Integer bookingId) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        bookingService.deleteBooking(bookingId, userId);
        return ResponseEntity.ok("Booking deleted successfully");
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
}

