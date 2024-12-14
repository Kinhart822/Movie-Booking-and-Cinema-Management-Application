package com.spring.service.impl;

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

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private  MovieGenreRepository movieGenreRepository;

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
                    screen.getCinema().getName(),
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
    public ViewCinemaResponse getCinemasByCity(Integer cityId) {
        List<Cinema> cinemaList = cinemaRepository.findByCityId(cityId);
        if (cinemaList == null || cinemaList.isEmpty()) {
            throw new IllegalArgumentException("No cinemas found for given city.");
        }
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
        if (movieSchedules == null || movieSchedules.isEmpty()) {
            throw new IllegalArgumentException("No schedules found for given movie and cinema.");
        }
        List<ScheduleResponse> scheduleResponses = new ArrayList<>();

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

        for (MovieSchedule schedule : movieSchedules) {
            ScheduleResponse scheduleResponse = new ScheduleResponse(
                    schedule.getMovie().getName(),
                    schedule.getCinema().getName(),
                    schedule.getScreen().getName(),
                    schedule.getId(),
                    schedule.getStartTime().format(formatterDate),
                    schedule.getStartTime().format(formatterTime)
            );
            scheduleResponses.add(scheduleResponse);
        }

        return scheduleResponses;
    }

    @Override
    public List<ListFoodAndDrinkToOrderingResponse> getAllFoodsAndDrinksByCinema(Integer cinemaId) {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new IllegalArgumentException("Cinema not found with id: %d".formatted(cinemaId)));

        // Fetch all foods and drinks associated with the cinema
        List<Food> foodList = foodRepository.findByCinema(cinema);
        List<Drink> drinkList = drinkRepository.findByCinema(cinema);

        // Prepare lists to hold food and drink details
        List<Integer> foodIds = new ArrayList<>();
        List<String> foodNameList = new ArrayList<>();
        List<String> imageUrlFoodList = new ArrayList<>();
        List<String> descriptionFoodList = new ArrayList<>();
        List<SizeFoodOrDrink> sizeFoodList = new ArrayList<>();
        List<Double> priceFoodList = new ArrayList<>();

        List<Integer> drinkIds = new ArrayList<>();
        List<String> drinkNameList = new ArrayList<>();
        List<String> imageUrlDrinkList = new ArrayList<>();
        List<String> descriptionDrinkList = new ArrayList<>();
        List<SizeFoodOrDrink> sizeDrinkList = new ArrayList<>();
        List<Double> priceDrinkList = new ArrayList<>();

        for (Food food : foodList) {
            foodIds.add(food.getId());
            foodNameList.add(food.getName());
            imageUrlFoodList.add(food.getImageUrl());
            descriptionFoodList.add(food.getDescription());
            sizeFoodList.add(food.getSize());
            priceFoodList.add(food.getPrice());
        }

        for (Drink drink : drinkList) {
            drinkIds.add(drink.getId());
            drinkNameList.add(drink.getName());
            imageUrlDrinkList.add(drink.getImageUrl());
            descriptionDrinkList.add(drink.getDescription());
            sizeDrinkList.add(drink.getSize());
            priceDrinkList.add(drink.getPrice());
        }

        ListFoodAndDrinkToOrderingResponse response = new ListFoodAndDrinkToOrderingResponse(
                cinema.getName(),
                foodIds,
                foodNameList,
                imageUrlFoodList,
                descriptionFoodList,
                sizeFoodList,
                priceFoodList,
                drinkIds,
                drinkNameList,
                imageUrlDrinkList,
                descriptionDrinkList,
                sizeDrinkList,
                priceDrinkList
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
    public ViewCouponsResponse getAvailableCouponsByMovieId(Integer movieId) {
        List<Coupon> coupons = couponRepository.findAvailableCouponsByMovieId(movieId);
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
            response.setMovieRatingDetailNameList(
                    movie.getMovieRatingDetailSet().stream()
                            .map(MovieRatingDetail::getName)
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
            response.setBackgroundUrl(movie.getBackgroundImageUrl());
            response.setMovieGenreNameList(
                    movie.getMovieGenreSet().stream()
                            .map(movieGenre -> movieGenre.getMovieGenreDetail().getName())
                            .toList());
            return response;
        }).toList();
    }

    public List<MovieGenreResponse> getAllMovieGenres() {
        List<MovieGenre> genres = movieGenreRepository.findAll();
        return genres.stream()
                .map(genre -> new MovieGenreResponse(
                        genre.getMovieGenreDetail().getId(),
                        genre.getMovieGenreDetail().getName(),
                        genre.getMovieGenreDetail().getDescription(),
                        genre.getMovieGenreDetail().getImageUrl()
                ))
                .toList();
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
            response.setMovieRatingDetailNameList(
                    movie.getMovieRatingDetailSet().stream()
                            .map(MovieRatingDetail::getName)
                            .toList());
            return response;
        }).toList();
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingResponse> bookingResponses = new ArrayList<>();

        for (Booking book : bookings) {
            BookingResponse bookResponse = new BookingResponse();
            bookResponse.setBookingNo(book.getBookingNo());
            bookResponse.setMovieName(book.getMovie().getName());
            bookResponse.setImageUrlMovie(book.getMovie().getImageUrl());
            bookResponse.setCityName(book.getCity().getName());
            bookResponse.setCinemaName(book.getCinema().getName());
            bookResponse.setStartDateTime(book.getStartDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a")));
            bookResponse.setEndDateTime(book.getEndDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a")));
            bookResponse.setScreenName(book.getScreen().getName());
            bookResponse.setTicketTypeName(book.getTickets().stream().map(ticket -> ticket.getTicket().getTicketType().getName()).toList());
            bookResponse.setSeatName(book.getSeatList().stream().map(seat -> "%s (%s)".formatted(seat.getSeat().getName(), seat.getSeatType().getName())).toList());
            bookResponse.setFoodNameList(book.getFoodList().stream().map(food -> food.getFood().getName()).toList());
            bookResponse.setDrinkNameList(book.getDrinks().stream().map(drink -> drink.getDrink().getName()).toList());
            bookResponse.setStatus(book.getStatus().toString());
            bookResponse.setTotalPrice(book.getTotalPrice());
            bookingResponses.add(bookResponse);
        }

        return bookingResponses;
    }

    @Override
    public List<BookingResponse> getAllBookingsByUser(Integer userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        if (bookings.isEmpty()) {
            throw new IllegalArgumentException("No bookings found for given user.");
        }
        List<BookingResponse> bookingResponses = new ArrayList<>();

        for (Booking book : bookings) {
            List<MovieGenre> movieGenres = movieGenreRepository.findMovieGenresByMovie(book.getMovie().getId());
            if (movieGenres.isEmpty()) {
                throw new IllegalArgumentException("Movie does not have any genre.");
            }

            BookingResponse bookResponse = new BookingResponse();
            bookResponse.setBookingNo(book.getBookingNo());
            bookResponse.setMovieName(book.getMovie().getName());
            bookResponse.setMovieId(book.getMovie().getId());
            bookResponse.setImageUrlMovie(book.getMovie().getImageUrl());
            bookResponse.setCityName(book.getCity().getName());
            bookResponse.setCinemaName(book.getCinema().getName());
            bookResponse.setStartDateTime(book.getStartDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a")));
            bookResponse.setEndDateTime(book.getEndDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a")));
            bookResponse.setScreenName(book.getScreen().getName());
            bookResponse.setTicketTypeName(book.getTickets().stream().map(ticket -> ticket.getTicket().getTicketType().getName()).toList());
            bookResponse.setSeatName(book.getSeatList().stream().map(seat -> "%s (%s)".formatted(seat.getSeat().getName(), seat.getSeatType().getName())).toList());
            bookResponse.setFoodNameList(book.getFoodList().stream().map(food -> food.getFood().getName()).toList());
            bookResponse.setDrinkNameList(book.getDrinks().stream().map(drink -> drink.getDrink().getName()).toList());
            bookResponse.setStatus(book.getStatus().toString());
            bookResponse.setTotalPrice(book.getTotalPrice());
            bookingResponses.add(bookResponse);
        }

        return bookingResponses;
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
