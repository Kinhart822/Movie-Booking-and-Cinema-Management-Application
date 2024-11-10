package com.spring.service;

import com.spring.dto.request.view.ViewCinemaRequest;
import com.spring.dto.request.view.ViewCouponRequest;
import com.spring.dto.request.view.ViewFoodAndDrinkRequest;
import com.spring.dto.response.booking.*;
import com.spring.dto.response.view.*;

import java.util.List;

public interface ViewService {
    ViewCityResponse getAvailableCities();

    ViewCinemaResponse getAvailableCinemaList();

    ViewCinemaResponse getCinemasByCity(ViewCinemaRequest viewCinemaRequest);

    List<ScreenResponse> getAllScreens();

    List<ScheduleResponse> getAllSchedulesBySelectedMovieAndSelectedCinema(Integer movieId, Integer cinemaId);

    List<ListFoodAndDrinkToOrderingResponse> getAllFoodsAndDrinksByCinema(ViewFoodAndDrinkRequest viewFoodAndDrinkRequest);

    List<CouponResponse> getAllCoupons();

    ViewCouponsResponse getAvailableCouponsForUser(Integer userId);

    ViewCouponsResponse getAvailableCouponsByMovieId(ViewCouponRequest viewCouponRequest);

    List<NowShowingResponse> getAvailableNowShowingMovies();

    List<ComingSoonResponse> getAvailableComingSoonMovies();

    List<HighRatingMovieResponse> getHighRatingMovies();
}
