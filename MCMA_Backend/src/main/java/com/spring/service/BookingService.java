package com.spring.service;

import com.spring.dto.Request.booking.*;
import com.spring.entities.*;

import java.util.List;

public interface BookingService {
    Movie selectMovie(MovieRequest movieRequest, Integer userId);
    City selectCity(CityRequest cityRequest, Integer userId);
    Cinema selectCinema(CinemaRequest cinemaRequest, Integer userId);
    List<MovieSchedule> getSchedulesForSelectedDate(ScheduleRequest scheduleRequest, Integer userId);
    List<Ticket> selectTickets(TicketRequest ticketRequest, Integer userId);
    List<Seat> selectSeats(SeatRequest seatRequest, Integer userId);
    List<Food> selectFood(FoodDrinkRequest foodDrinkRequest, Integer userId);
    List<Drink> selectDrinks(FoodDrinkRequest foodDrinkRequest, Integer userId);
    Double calculateTotalPrice(CouponRequest couponRequest, Integer userId);
    Booking completeBooking(CompleteRequest completeRequest, Integer userId);
    void deleteBooking(Integer bookingId);
}
