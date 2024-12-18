package com.spring.service;

import com.spring.dto.response.booking.*;
import com.spring.dto.response.view.*;

import java.util.List;

public interface ViewService {
    ViewCityResponse getAvailableCities();

    ViewCinemaResponse getAvailableCinemaList();

    ViewCinemaResponse getCinemasByCity(Integer cityId);

    List<ScreenResponse> getAllScreens();

    List<ScheduleResponse> getAllSchedulesBySelectedCinema(Integer cinemaId);

    List<ScheduleResponse> getAllSchedulesBySelectedMovieAndSelectedCinema(Integer movieId, Integer cinemaId);

    List<ListFoodAndDrinkToOrderingResponse> getAllFoodsAndDrinksByCinema(Integer cinemaId);

    List<CouponResponse> getAllCoupons();

    ViewCouponsResponse getAvailableCouponsForUser(Integer userId);

    ViewCouponsResponse getAvailableCouponsByMovieId(Integer movieId);

    List<NowShowingResponse> getAvailableNowShowingMovies();

    List<ComingSoonResponse> getAvailableComingSoonMovies();

    List<HighRatingMovieResponse> getHighRatingMovies();

    List<MovieGenreResponse> getAllMovieGenres();

    List<BookingResponse> getAllBookings();

    List<BookingResponse> getAllBookingsByUser(Integer userId);
}
