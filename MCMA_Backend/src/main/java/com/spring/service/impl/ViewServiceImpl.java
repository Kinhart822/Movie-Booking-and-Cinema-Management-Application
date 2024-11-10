package com.spring.service.impl;

import com.spring.dto.request.view.ViewCinemaRequest;
import com.spring.dto.request.view.ViewCouponRequest;
import com.spring.dto.request.view.ViewFoodAndDrinkRequest;
import com.spring.dto.response.booking.*;
import com.spring.dto.response.view.*;
import com.spring.entities.*;
import com.spring.enums.SizeFoodOrDrink;
import com.spring.repository.*;
import com.spring.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    @Autowired
    private MovieScheduleRepository movieScheduleRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private DrinkRepository drinkRepository;

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
    public List<ScreenResponse> getAllScreens() {
        List<Screen> screenList = screenRepository.findAll();
        List<ScreenResponse> screenResponses = new ArrayList<>();

        for (Screen screen : screenList) {
            ScreenResponse screenResponse = new ScreenResponse(
                    screen.getId(),
                    screen.getName(),
                    screen.getScreenType().getName(),
                    screen.getScreenType().getDescription()
            );
            screenResponses.add(screenResponse);
        }

        return screenResponses;
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
    public List<ScheduleResponse> getAllSchedulesBySelectedMovieAndSelectedCinema(Integer movieId, Integer cinemaId) {
        List<MovieSchedule> movieSchedules = movieScheduleRepository.findMovieSchedulesByMovieIdAndCinemaId(
                movieId, cinemaId
        );
        List<ScheduleResponse> scheduleResponses = new ArrayList<>();

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

        for (MovieSchedule schedule : movieSchedules) {
            ScheduleResponse scheduleResponse = new ScheduleResponse(
                    schedule.getId(),
                    schedule.getStartTime().format(formatterDate),
                    schedule.getStartTime().format(formatterTime)
            );
            scheduleResponses.add(scheduleResponse);
        }

        return scheduleResponses;
    }

    @Override
    public List<ListFoodAndDrinkToOrderingResponse> getAllFoodsAndDrinksByCinema(ViewFoodAndDrinkRequest viewFoodAndDrinkRequest) {
        Integer cinemaId = viewFoodAndDrinkRequest.getCinemaId();
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new IllegalArgumentException("Cinema not found with id: " + cinemaId));

        // Fetch all foods and drinks associated with the cinema
        List<Food> foodList = foodRepository.findByCinema(cinema);
        List<Drink> drinkList = drinkRepository.findByCinema(cinema);

        // Prepare lists to hold food and drink details
        List<Integer> foodIds = new ArrayList<>();
        List<String> foodNameList = new ArrayList<>();
        List<String> imageUrlFoodList = new ArrayList<>();
        List<String> descriptionFoodList = new ArrayList<>();
        List<SizeFoodOrDrink> sizeFoodList = new ArrayList<>();

        List<Integer> drinkIds = new ArrayList<>();
        List<String> drinkNameList = new ArrayList<>();
        List<String> imageUrlDrinkList = new ArrayList<>();
        List<String> descriptionDrinkList = new ArrayList<>();
        List<SizeFoodOrDrink> sizeDrinkList = new ArrayList<>();

        for (Food food : foodList) {
            foodIds.add(food.getId());
            foodNameList.add(food.getName());
            imageUrlFoodList.add(food.getImageUrl());
            descriptionFoodList.add(food.getDescription());
            sizeFoodList.add(food.getSize());
        }

        for (Drink drink : drinkList) {
            drinkIds.add(drink.getId());
            drinkNameList.add(drink.getName());
            imageUrlDrinkList.add(drink.getImageUrl());
            descriptionDrinkList.add(drink.getDescription());
            sizeDrinkList.add(drink.getSize());
        }

        ListFoodAndDrinkToOrderingResponse response = new ListFoodAndDrinkToOrderingResponse(
                foodIds,
                foodNameList,
                imageUrlFoodList,
                descriptionFoodList,
                sizeFoodList,
                drinkIds,
                drinkNameList,
                imageUrlDrinkList,
                descriptionDrinkList,
                sizeDrinkList
        );

        return List.of(response);
    }

    @Override
    public List<CouponResponse> getAllCoupons() {
        List<Coupon> coupons = couponRepository.findAll();

        List<Integer> couponIds = new ArrayList<>();
        List<String> couponNameList = new ArrayList<>();
        List<String> couponDescriptionList = new ArrayList<>();
        List<BigDecimal> discountRateList = new ArrayList<>();
        List<Integer> minSpendReqList = new ArrayList<>();
        List<Integer> discountLimitList = new ArrayList<>();

        for (Coupon coupon : coupons) {
            couponIds.add(coupon.getId());
            couponNameList.add(coupon.getName());
            couponDescriptionList.add(coupon.getDescription());
            discountRateList.add(coupon.getDiscount());
            minSpendReqList.add(coupon.getMinSpendReq());
            discountLimitList.add(coupon.getDiscountLimit());
        }

        CouponResponse couponResponse = new CouponResponse(
                couponIds,
                couponNameList,
                couponDescriptionList,
                discountRateList,
                minSpendReqList,
                discountLimitList
        );

        return List.of(couponResponse);
    }

    @Override
    public ViewCouponsResponse getAvailableCouponsForUser(Integer userId) {
        List<Coupon> coupons = couponRepository.findAvailableCouponsForUser(userId);
        return mapCouponsToResponse(coupons);
    }

    @Override
    public ViewCouponsResponse getAvailableCouponsByMovieId(ViewCouponRequest viewCouponRequest) {
        List<Coupon> coupons = couponRepository.findAvailableCouponsByMovieId(viewCouponRequest.getMovieId());
        return mapCouponsToResponse(coupons);
    }

    @Override
    public List<NowShowingResponse> getAvailableNowShowingMovies() {
        Date currentDate = new Date();
        List<Movie> nowShowingMovies = movieRepository.findByDatePublishBefore(currentDate);

        return nowShowingMovies.stream().map(movie -> {
            NowShowingResponse response = new NowShowingResponse();
            response.setMovieId(movie.getId());
            response.setMovieName(movie.getName());
            response.setMovieLength(movie.getLength());
            response.setPublishedDate(new SimpleDateFormat("dd/MM/yyyy").format(movie.getDatePublish()));
            response.setImageUrl(movie.getImageUrl());
            response.setMovieGenreNameList(
                    movie.getMovieGenreSet().stream()
                            .map(movieGenre -> movieGenre.getMovieGenreDetail().getName())
                            .toList());
            return response;
        }).toList();
    }

    @Override
    public List<HighRatingMovieResponse> getHighRatingMovies() {
        List<Movie> highRatingMovies = movieRepository.findHighestRatingMovies(4.5, 5.0);

        return highRatingMovies.stream().map(movie -> {
            HighRatingMovieResponse response = new HighRatingMovieResponse();
            response.setMovieId(movie.getId());
            response.setMovieName(movie.getName());
            response.setMovieLength(movie.getLength());
            response.setPublishedDate(new SimpleDateFormat("dd/MM/yyyy").format(movie.getDatePublish()));
            response.setImageUrl(movie.getImageUrl());
            response.setMovieGenreNameList(
                    movie.getMovieGenreSet().stream()
                            .map(movieGenre -> movieGenre.getMovieGenreDetail().getName())
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
            response.setMovieId(movie.getId());
            response.setMovieName(movie.getName());
            response.setMovieLength(movie.getLength());
            response.setPublishedDate(new SimpleDateFormat("dd/MM/yyyy").format(movie.getDatePublish()));
            response.setImageUrl(movie.getImageUrl());
            response.setMovieGenreNameList(
                    movie.getMovieGenreSet().stream()
                            .map(movieGenre -> movieGenre.getMovieGenreDetail().getName())
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
