package com.spring.service;

public interface BookingCleanUpScheduleService {
    void releaseSeatsForAfterMovieEnds();
    void holdBookingTemporarily();
}
