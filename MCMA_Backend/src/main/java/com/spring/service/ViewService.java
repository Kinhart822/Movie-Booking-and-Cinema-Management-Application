package com.spring.service;

import com.spring.dto.Request.view.ViewCinemaRequest;
import com.spring.dto.Request.view.ViewCouponRequest;
import com.spring.dto.Response.view.*;

import java.util.List;

public interface ViewService {
    ViewCityResponse getAvailableCities();
    ViewCinemaResponse getAvailableCinemaList();
    ViewCinemaResponse getCinemasByCity(ViewCinemaRequest viewCinemaRequest);
    ViewCouponsResponse getAvailableCouponsForUser(Integer userId);
    ViewCouponsResponse getAvailableCouponsByMovieId(ViewCouponRequest viewCouponRequest);
    List<NowShowingResponse> getAvailableNowShowingMovies();
    List<ComingSoonResponse> getAvailableComingSoonMovies();
}
