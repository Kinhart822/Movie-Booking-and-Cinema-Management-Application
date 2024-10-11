package com.spring.service;

import com.spring.dto.Request.booking.*;
import com.spring.dto.Response.booking.*;
import com.spring.entities.Booking;

public interface BookingService {
    BookingMovieRespond selectMovie(MovieRequest movieRequest, Integer userId);
    CityResponse selectCity(CityRequest cityRequest, Integer userId);
    CinemaResponse selectCinema(CinemaRequest cinemaRequest, Integer userId);
    ScreenResponse selectScreen(ScreenRequest screenRequest, Integer userId);
    ScheduleResponse selectSchedule(ScheduleRequest scheduleRequest, Integer userId);
    TicketResponse selectTickets(TicketRequest ticketRequest, Integer userId);
    SeatResponse selectSeats(SeatRequest seatRequest, Integer userId);
    FoodResponse selectFood(FoodDrinkRequest foodDrinkRequest, Integer userId);
    DrinkResponse selectDrinks(FoodDrinkRequest foodDrinkRequest, Integer userId);
    Double calculateTotalPrice(CouponRequest couponRequest, Integer userId);
    Booking completeBooking(CompleteRequest completeRequest, Integer userId);
    void deleteBooking(Integer bookingId, Integer userId);
}
