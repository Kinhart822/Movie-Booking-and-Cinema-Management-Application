package com.spring.service;

import com.spring.dto.request.booking.BookingRequest;
import com.spring.dto.request.booking.*;
import com.spring.dto.response.booking.BookingResponse;
import com.spring.dto.response.booking.*;

import java.util.List;

public interface BookingService {
    // TODO: Get Information for a Booking process
    List<MovieResponse> getAllMovies();
    List<CityResponse> getAllCitiesBySelectedMovie(MovieRequest movieRequest);
    List<CinemaResponse> getAllCinemasBySelectedCity(CityRequest cityRequest);
    List<ScreenResponse> getAllScreensBySelectedCinema(CinemaRequest cinemaRequest);
    List<ScheduleResponse> getAllSchedulesBySelectedMovieAndSelectedCinemaAndSelectedScreen(
            MovieRequest movieRequest,
            CinemaRequest cinemaRequest,
            ScreenRequest screenRequest
    );
    List<TicketResponse> getAllTickets();
    List<SeatResponse> getAllSeatsBySelectedScreen(ScreenRequest screenRequest);
    List<ListFoodAndDrinkToOrderingResponse> getAllFoodsAndDrinksByCinema(CinemaRequest cinemaRequest);
    List<CouponResponse> getAllCouponsByUser(Integer userId);
    List<CouponResponse> getAllCouponsByMovie(MovieRequest movieRequest);

    // TODO: Process Booking
    SendBookingResponse processingBooking(Integer userId, BookingRequest bookingRequest);
    BookingResponse completeBooking(Integer userId, BookingRequest bookingRequest);

    void updateBookingSeat(BookingRequest bookingRequest);
    void cancelBooking(Integer bookingId, Integer userId);

    void deleteBooking(Integer bookingId, Integer userId);
}
