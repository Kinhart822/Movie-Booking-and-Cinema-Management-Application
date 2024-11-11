package com.spring.service;

import com.spring.dto.request.booking.*;
import com.spring.dto.response.booking.*;
import com.spring.dto.response.booking.bookingSelected.*;

import java.util.List;

public interface BookingService {
    List<MovieResponse> getAllMovies();

    SelectedMovieResponse selectMovie(MovieRequest movieRequest, Integer userId);

    List<CityResponse> getAllCitiesBySelectedMovie(Integer userId);

    SelectedCityResponse selectCity(CityRequest cityRequest, Integer userId);

    List<CinemaResponse> getAllCinemasBySelectedCity(Integer userId);

    SelectedCinemaResponse selectCinema(CinemaRequest cinemaRequest, Integer userId);

    List<ScreenResponse> getAllScreensBySelectedCinema(Integer userId);

    SelectedScreenResponse selectScreen(ScreenRequest screenRequest, Integer userId);

    List<ScheduleResponse> getAllSchedulesBySelectedMovieAndSelectedCinemaAndSelectedScreen(Integer userId);

    SelectedScheduleResponse selectSchedule(ScheduleRequest scheduleRequest, Integer userId);

    List<TicketResponse> getAllTickets();

    SelectedTicketResponse selectTickets(TicketRequest ticketRequest, Integer userId);

    List<SeatResponse> getAllSeatsBySelectedScreen(Integer userId);

    SelectedSeatsResponse selectSeats(SeatRequest seatRequest, Integer userId);

    List<ListFoodAndDrinkToOrderingResponse> getAllFoodsAndDrinksByCinema(Integer userId);

    SelectedFoodResponse selectFood(FoodDrinkRequest foodDrinkRequest, Integer userId);

    SelectedDrinkResponse selectDrinks(FoodDrinkRequest foodDrinkRequest, Integer userId);

    List<CouponResponse> getAllCouponsByUser(Integer userId);

    List<CouponResponse> getAllCouponsByMovie(Integer movieId);

    CalculateResponse calculateTotalPrice(CouponRequest couponRequest, Integer userId);

    BookingResponse completeBooking(CompleteRequest completeRequest, Integer userId);

    void deleteBookingDraft(Integer bookingDraftId);

    void cancelBooking(Integer bookingId, Integer userId);

    void deleteBooking(Integer bookingId, Integer userId);
}
