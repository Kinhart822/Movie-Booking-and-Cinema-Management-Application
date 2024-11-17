package com.spring.service;

import com.spring.dto.request.booking.BookingRequest;
import com.spring.dto.response.booking.BookingResponse;
import com.spring.dto.response.booking.*;

import java.util.List;

public interface BookingService {
    // TODO: Get Information for a Booking process
    List<MovieResponse> getAllMovies();
    List<CityResponse> getAllCitiesBySelectedMovie(Integer movieId);
    List<CinemaResponse> getAllCinemasBySelectedCity(Integer cityId);
    List<ScreenResponse> getAllScreensBySelectedCinema(Integer cinemaId);
    List<ScheduleResponse> getAllSchedulesBySelectedMovieAndSelectedCinemaAndSelectedScreen(
            Integer movieId,
            Integer cinemaId,
            Integer screenId
    );
    List<TicketResponse> getAllTickets();
    List<SeatResponse> getAllSeatsBySelectedScreen(Integer screenId);
    List<ListFoodAndDrinkToOrderingResponse> getAllFoodsAndDrinksByCinema(Integer cinemaId);
    List<CouponResponse> getAllCouponsByUser(Integer userId);
    List<CouponResponse> getAllCouponsByMovie(Integer movieId);

    // TODO: Process Booking
    SendBookingResponse processingBooking(Integer userId, BookingRequest bookingRequest);
    BookingResponse completeBooking(Integer userId, BookingRequest bookingRequest);

    void updateBookingSeat(BookingRequest bookingRequest);
    void cancelBooking(Integer bookingId, Integer userId);

    void deleteBooking(Integer bookingId, Integer userId);
}
