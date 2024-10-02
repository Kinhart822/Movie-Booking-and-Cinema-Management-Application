package com.spring.service;

import com.spring.dto.Request.BookingRequest;
import com.spring.dto.Response.BookingResponse;

public interface BookingService {
    BookingResponse createBooking(BookingRequest bookingRequest);
}
