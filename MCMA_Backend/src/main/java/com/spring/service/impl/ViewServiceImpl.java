package com.spring.service.impl;

import com.spring.dto.Request.view.ViewCinemaRequest;
import com.spring.dto.Request.view.ViewCouponRequest;
import com.spring.dto.Response.view.*;
import com.spring.entities.*;
import com.spring.repository.CinemaRepository;
import com.spring.repository.CityRepository;
import com.spring.repository.CouponRepository;
import com.spring.repository.MovieRepository;
import com.spring.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ViewServiceImpl implements ViewService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public ViewCityResponse getAvailableCities() {
        List<City> cities = cityRepository.findAll();
        List<Integer> cityIds = cities.stream()
                .map(City::getId)
                .toList();
        List<String> cityNames = cities.stream()
                .map(City::getName)
                .toList();

        return new ViewCityResponse(cityIds, cityNames);
    }

    @Override
    public ViewCinemaResponse getAvailableCinemaList() {
        List<Cinema> cinemaList = cinemaRepository.findAll();
        List<String> cinemaNameList = cinemaList.stream()
                .map(Cinema::getName)
                .toList();

        return new ViewCinemaResponse(cinemaNameList);
    }

    @Override
    public ViewCinemaResponse getCinemasByCity(ViewCinemaRequest viewCinemaRequest) {
        List<Cinema> cinemaList = cinemaRepository.findByCityId(viewCinemaRequest.getCityId());
        List<String> cinemaNameList = cinemaList.stream()
                .map(Cinema::getName)
                .toList();

        return new ViewCinemaResponse(cinemaNameList);
    }

    @Override
    public ViewCouponsResponse getAvailableCouponsForUser(Integer userId) {
        List<Coupon> coupons = couponRepository.findAvailableCouponsForUser(userId);
        return mapCouponsToResponse(coupons);
    }
    @Override
    public ViewCouponsResponse getAvailableCouponsByMovieId(ViewCouponRequest viewCouponRequest) {
        List<Coupon> coupons = couponRepository.findAvailableCouponsByMovieIds(viewCouponRequest.getMovieIds());
        return mapCouponsToResponse(coupons);
    }

    @Override
    public List<NowShowingResponse> getAvailableNowShowingMovies() {
        Date currentDate = new Date();
        List<Movie> nowShowingMovies = movieRepository.findByDatePublishBefore(currentDate);

        return nowShowingMovies.stream().map(movie -> {
            NowShowingResponse response = new NowShowingResponse();
            response.setMovieName(movie.getName());
            response.setMovieLength(movie.getLength());
            response.setPublishedDate(new SimpleDateFormat("dd/MM/yyyy").format(movie.getDatePublish()));
            response.setTrailerLink(movie.getTrailerLink());

            response.setMovieGenreNameList(
                    movie.getMovieGenreSet().stream()
                            .map(movieGenre -> movieGenre.getMovieGenreDetail().getName())
                            .toList());

            response.setMovieGenreDescriptions(
                    movie.getMovieGenreSet().stream()
                            .map(movieGenre -> movieGenre.getMovieGenreDetail().getDescription())
                            .toList());

            response.setMoviePerformerNameList(
                    movie.getMoviePerformerSet().stream()
                            .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getName())
                            .toList());

            response.setMoviePerformerDobList(
                    movie.getMoviePerformerSet().stream()
                            .map(moviePerformer -> new SimpleDateFormat("dd/MM/yyyy")
                                    .format(moviePerformer.getMoviePerformerDetail().getDob()))
                            .toList());

            response.setMoviePerformerSex(
                    movie.getMoviePerformerSet().stream()
                            .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getPerformerSex())
                            .toList());

            response.setMoviePerformerType(
                    movie.getMoviePerformerSet().stream()
                            .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getPerformerType())
                            .toList());

            response.setMovieRatingDetailNameList(
                    movie.getMovieRatingDetailSet().stream()
                            .map(MovieRatingDetail::getName)
                            .toList());

            response.setMovieRatingDetailDescriptions(
                    movie.getMovieRatingDetailSet().stream()
                            .map(MovieRatingDetail::getDescription)
                            .toList());

            return response;
        }).toList();
    }

    @Override
    public List<ComingSoonResponse> getAvailableComingSoonMovies() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date futureStartDate = calendar.getTime();
        List<Movie> comingSoonMovies = movieRepository.findComingSoonMovies(futureStartDate);

        return comingSoonMovies.stream().map(movie -> {
            ComingSoonResponse response = new ComingSoonResponse();
            response.setMovieName(movie.getName());
            response.setMovieLength(movie.getLength());
            response.setPublishedDate(new SimpleDateFormat("dd/MM/yyyy").format(movie.getDatePublish()));
            response.setTrailerLink(movie.getTrailerLink());

            response.setMovieGenreNameList(
                    movie.getMovieGenreSet().stream()
                            .map(movieGenre -> movieGenre.getMovieGenreDetail().getName())
                            .toList());

            response.setMovieGenreDescriptions(
                    movie.getMovieGenreSet().stream()
                            .map(movieGenre -> movieGenre.getMovieGenreDetail().getDescription())
                            .toList());

            response.setMoviePerformerNameList(
                    movie.getMoviePerformerSet().stream()
                            .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getName())
                            .toList());

            response.setMoviePerformerDobList(
                    movie.getMoviePerformerSet().stream()
                            .map(moviePerformer -> new SimpleDateFormat("dd/MM/yyyy")
                                    .format(moviePerformer.getMoviePerformerDetail().getDob()))
                            .toList());

            response.setMoviePerformerSex(
                    movie.getMoviePerformerSet().stream()
                            .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getPerformerSex())
                            .toList());

            response.setMoviePerformerType(
                    movie.getMoviePerformerSet().stream()
                            .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getPerformerType())
                            .toList());

            response.setMovieRatingDetailNameList(
                    movie.getMovieRatingDetailSet().stream()
                            .map(MovieRatingDetail::getName)
                            .toList());

            response.setMovieRatingDetailDescriptions(
                    movie.getMovieRatingDetailSet().stream()
                            .map(MovieRatingDetail::getDescription)
                            .toList());

            return response;
        }).toList();
    }

    private ViewCouponsResponse mapCouponsToResponse(List<Coupon> coupons) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        List<String> nameCoupons = coupons.stream()
                .map(Coupon::getName)
                .toList();

        List<String> descriptionCoupons = coupons.stream()
                .map(Coupon::getDescription)
                .toList();

        List<String> expirationDates = coupons.stream()
                .map(coupon -> dateFormat.format(coupon.getDateExpired()))
                .toList();

        List<BigDecimal> discounts = coupons.stream()
                .map(Coupon::getDiscount)
                .toList();

        List<String> discountLimits = coupons.stream()
                .map(coupon -> coupon.getDiscountLimit() != null ? coupon.getDiscountLimit().toString() : "N/A")
                .toList();

        return new ViewCouponsResponse(nameCoupons, descriptionCoupons, expirationDates, discounts, discountLimits);
    }
}
