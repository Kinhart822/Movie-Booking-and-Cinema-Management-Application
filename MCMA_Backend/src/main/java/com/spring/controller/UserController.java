package com.spring.controller;

import com.spring.config.JwtUtil;
import com.spring.dto.Request.booking.*;
import com.spring.dto.Response.SearchMovieByGenreResponse;
import com.spring.dto.Response.SearchMovieByNameResponse;
import com.spring.entities.*;
import com.spring.service.BookingService;
import com.spring.service.MovieService;
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
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<String> sayHello(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        return ResponseEntity.ok("Hello, User! Your ID is: " + userId);
    }

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

    @PostMapping("/booking/choose-movie")
    public ResponseEntity<Movie> selectMovie(HttpServletRequest request, @RequestBody MovieRequest movieRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        Movie movie = bookingService.selectMovie(movieRequest, userId);
        return ResponseEntity.ok(movie);
    }

    @PostMapping("/booking/select-city")
    public ResponseEntity<City> selectCity(HttpServletRequest request, @RequestBody CityRequest cityRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        City city = bookingService.selectCity(cityRequest, userId);
        return ResponseEntity.ok(city);
    }

    @PostMapping("/booking/select-cinema")
    public ResponseEntity<Cinema> selectCinema(HttpServletRequest request, @RequestBody CinemaRequest cinemaRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        Cinema cinema = bookingService.selectCinema(cinemaRequest, userId);
        return ResponseEntity.ok(cinema);
    }

    @PostMapping("/booking/select-schedule")
    public ResponseEntity<List<MovieSchedule>> selectSchedule(HttpServletRequest request, @RequestBody ScheduleRequest scheduleRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<MovieSchedule> schedules = bookingService.getSchedulesForSelectedDate(scheduleRequest, userId);
        return ResponseEntity.ok(schedules);
    }

    @PostMapping("/booking/select-tickets")
    public ResponseEntity<List<Ticket>> selectTickets(HttpServletRequest request, @RequestBody TicketRequest ticketRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<Ticket> tickets = bookingService.selectTickets(ticketRequest, userId);
        return ResponseEntity.ok(tickets);
    }

    @PostMapping("/booking/select-seats")
    public ResponseEntity<List<Seat>> selectSeats(HttpServletRequest request, @RequestBody SeatRequest seatRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<Seat> seats = bookingService.selectSeats(seatRequest, userId);
        return ResponseEntity.ok(seats);
    }

    @PostMapping("/booking/select-food")
    public ResponseEntity<List<Food>> selectFood(HttpServletRequest request, @RequestBody FoodDrinkRequest foodDrinkRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<Food> foods = bookingService.selectFood(foodDrinkRequest, userId);
        return ResponseEntity.ok(foods);
    }

    @PostMapping("/booking/select-drinks")
    public ResponseEntity<List<Drink>> selectDrinks(HttpServletRequest request, @RequestBody FoodDrinkRequest foodDrinkRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        List<Drink> drinks = bookingService.selectDrinks(foodDrinkRequest, userId);
        return ResponseEntity.ok(drinks);
    }

    @PostMapping("/booking/calculate-total-price")
    public ResponseEntity<Double> getTotalPrice(HttpServletRequest request, @RequestBody CouponRequest couponRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        Double updatedPrice = bookingService.calculateTotalPrice(couponRequest, userId);
        return ResponseEntity.ok(updatedPrice);
    }

    @PostMapping("/booking/complete-booking")
    public ResponseEntity<Booking> completeBooking(HttpServletRequest request, @RequestBody CompleteRequest completeRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        Booking booking = bookingService.completeBooking(completeRequest, userId);
        return ResponseEntity.ok(booking);
    }

    @DeleteMapping("booking/delete-booking/{bookingId}")
    public ResponseEntity<String> deleteBooking(@PathVariable Integer bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.ok("Booking deleted successfully");
    }
}

