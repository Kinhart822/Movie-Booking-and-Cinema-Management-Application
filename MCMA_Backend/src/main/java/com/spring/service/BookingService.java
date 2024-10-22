package com.spring.service;

import com.spring.dto.Request.booking.*;
import com.spring.dto.Response.booking.*;

import java.util.List;

public interface BookingService {
    List<BookingMovieRespond> getAllMovies();
    BookingMovieRespond selectMovie(MovieRequest movieRequest, Integer userId);

    List<CityResponse> getAllCities();
    CityResponse selectCity(CityRequest cityRequest, Integer userId);

    List<CinemaResponse> getAllCinemas();
    CinemaResponse selectCinema(CinemaRequest cinemaRequest, Integer userId);

    ScreenResponse selectScreen(ScreenRequest screenRequest, Integer userId);
    ScheduleResponse selectSchedule(ScheduleRequest scheduleRequest, Integer userId);
    TicketResponse selectTickets(TicketRequest ticketRequest, Integer userId);
    SeatResponse selectSeats(SeatRequest seatRequest, Integer userId);
    FoodResponse selectFood(FoodDrinkRequest foodDrinkRequest, Integer userId);
    DrinkResponse selectDrinks(FoodDrinkRequest foodDrinkRequest, Integer userId);
    Double calculateTotalPrice(CouponRequest couponRequest, Integer userId);
    BookingResponse completeBooking(CompleteRequest completeRequest, Integer userId);
    void deleteBookingDraft(Integer bookingDraftId);
    void deleteBooking(Integer bookingId, Integer userId);
}
