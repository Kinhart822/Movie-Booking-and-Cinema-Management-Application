package com.spring.service;

import com.spring.dto.Request.BookingRequest;
import com.spring.dto.Response.BookingResponse;
import com.spring.entities.MovieSchedule;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest bookingRequest);
    List<MovieSchedule> getSchedulesForSelectedDate(Integer movieId, Integer cinemaId, LocalDate selectedDate);
}
