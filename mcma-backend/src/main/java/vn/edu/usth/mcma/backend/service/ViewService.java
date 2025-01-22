package vn.edu.usth.mcma.backend.service;

import constants.CommonStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.dto.*;
import vn.edu.usth.mcma.backend.entity.Schedule;
import vn.edu.usth.mcma.backend.repository.GenreRepository;
import vn.edu.usth.mcma.backend.repository.MovieRepository;
import vn.edu.usth.mcma.backend.repository.ReviewRepository;
import vn.edu.usth.mcma.backend.repository.ScheduleRepository;

import java.time.Instant;
import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
public class ViewService {
    private final MovieRepository movieRepository;
    private final ScheduleRepository scheduleRepository;
    private final ReviewRepository reviewRepository;
    private final GenreRepository genreRepository;

//    public ViewCityResponse getAvailableCities() {
//        List<City> cities = cityRepository.findAll();
//        List<Integer> cityIds = cities.stream()
//                .map(City::getId)
//                .toList();
//        List<String> cityNames = cities.stream()
//                .map(City::getName)
//                .toList();
//
//        return new ViewCityResponse(cityIds, cityNames);
//    }

//    public ViewCinemaResponse getAvailableCinemaList() {
//        List<Cinema> cinemaList = cinemaRepository.findAll();
//        List<Integer> cinemaIdList = cinemaList.stream()
//                .map(Cinema::getId)
//                .toList();
//        List<String> cinemaNameList = cinemaList.stream()
//                .map(Cinema::getName)
//                .toList();
//        List<String> cityNameList = cinemaList.stream()
//                .map(cinema -> cinema.getCity().getName())
//                .toList();
//        List<String> cinemaAddressList = cinemaList.stream()
//                .map(Cinema::getAddress)
//                .toList();
//
//        return new ViewCinemaResponse(cinemaIdList, cinemaNameList, cityNameList, cinemaAddressList);
//    }

//    public List<ScreenResponse> getAllScreens() {
//        List<Screen> screenList = screenRepository.findAll();
//        List<ScreenResponse> screenResponses = new ArrayList<>();
//
//        for (Screen screen : screenList) {
//            ScreenResponse screenResponse = new ScreenResponse(
//                    screen.getCinema().getName(),
//                    screen.getId(),
//                    screen.getName(),
//                    screen.getScreenType().getName(),
//                    screen.getScreenType().getDescription()
//            );
//            screenResponses.add(screenResponse);
//        }
//
//        return screenResponses;
//    }

//    public ViewCinemaResponse getCinemasByCity(Integer cityId) {
//        List<Cinema> cinemaList = cinemaRepository.findByCityId(cityId);
//        if (cinemaList == null || cinemaList.isEmpty()) {
//            throw new IllegalArgumentException("No cinemas found for given city.");
//        }
//        List<Integer> cinemaIdList = cinemaList.stream()
//                .map(Cinema::getId)
//                .toList();
//        List<String> cinemaNameList = cinemaList.stream()
//                .map(Cinema::getName)
//                .toList();
//        List<String> cityNameList = cinemaList.stream()
//                .map(cinema -> cinema.getCity().getName())
//                .toList();
//        List<String> cinemaAddressList = cinemaList.stream()
//                .map(Cinema::getAddress)
//                .toList();
//
//        return new ViewCinemaResponse(cinemaIdList, cinemaNameList, cityNameList, cinemaAddressList);
//    }

//    public ScheduleSelectedByCinemaResponse getAllSchedulesBySelectedCinema(Integer cinemaId) {
//        Cinema cinema = cinemaRepository.findById(cinemaId)
//                .orElseThrow(() -> new IllegalArgumentException("Cinema not found"));
//
//        List<MovieSchedule> movieSchedules = movieScheduleRepository.findMovieSchedulesByCinemaId(cinemaId);
//        if (movieSchedules == null || movieSchedules.isEmpty()) {
//            throw new IllegalArgumentException("No schedules found for given movie and cinema.");
//        }
//
//        List<String> dayOfWeekList = new ArrayList<>();
//        List<String> dayList = new ArrayList<>();
//        List<String> timeList = new ArrayList<>();
//        List<Integer> scheduleIdList = new ArrayList<>();
//
//        for (MovieSchedule schedule : movieSchedules) {
//            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//            DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");
//
//            String dayOfWeek = schedule.getStartTime().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
//            String day = schedule.getStartTime().format(formatDate);
//            String time = schedule.getStartTime().format(formatTime);
//            dayOfWeekList.add(dayOfWeek);
//            dayList.add(day);
//            timeList.add(time);
//
//            Integer scheduleId = schedule.getId();
//            scheduleIdList.add(scheduleId);
//        }
//
//        return new ScheduleSelectedByCinemaResponse(
//                cinema.getName(),
//                scheduleIdList,
//                dayOfWeekList,
//                dayList,
//                timeList
//        );
//    }

//    public ScheduleResponse getAllSchedulesBySelectedMovieAndSelectedCinema(Integer movieId, Integer cinemaId) {
//        Movie movie = movieRepository.findById(movieId)
//                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));
//
//        Cinema cinema = cinemaRepository.findById(cinemaId)
//                .orElseThrow(() -> new IllegalArgumentException("Cinema not found"));
//
//        List<MovieSchedule> movieSchedules = movieScheduleRepository.findMovieSchedulesByMovieIdAndCinemaId(
//                movieId, cinemaId
//        );
//        if (movieSchedules == null || movieSchedules.isEmpty()) {
//            throw new IllegalArgumentException("No schedules found for given movie and cinema.");
//        }
//
//        List<MovieGenre> movieGenres = movieGenreRepository.findMovieGenresByMovie(movie.getId());
//        List<String> movieGenreNameList = movieGenres.stream()
//                .map(movieGenre -> movieGenre.getMovieGenreDetail().getName())
//                .toList();
//        List<String> movieGenreImageUrls = movieGenres.stream()
//                .map(movieGenre -> movieGenre.getMovieGenreDetail().getImageUrl())
//                .toList();
//        List<String> movieGenreDescriptions = movieGenres.stream()
//                .map(movieGenre -> movieGenre.getMovieGenreDetail().getDescription())
//                .toList();
//
//        // Fetch Movie Performers
//        List<MoviePerformer> moviePerformers = moviePerformerRepository.findMoviePerformersByMovieId(movie.getId());
//        List<String> moviePerformerNameList = moviePerformers.stream()
//                .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getName())
//                .toList();
//
//        List<PerformerType> moviePerformerType = moviePerformers.stream()
//                .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getPerformerType())
//                .toList();
//
//        // Fetch Movie Rating Details
//        List<MovieRatingDetail> movieRatingDetails = movieRatingDetailRepository.findMovieRatingDetailsByMovieId(movie.getId());
//        List<String> movieRatingDetailNameList = movieRatingDetails.stream()
//                .map(MovieRatingDetail::getName)
//                .toList();
//        List<String> movieRatingDetailDescriptions = movieRatingDetails.stream()
//                .map(MovieRatingDetail::getDescription)
//                .toList();
//
//        // Format date
//        SimpleDateFormat publishDateFormatter = new SimpleDateFormat("dd/MM/yyyy");
//        String formattedDatePublish = publishDateFormatter.format(movie.getDatePublish());
//
//        // Movie Respond
//        List<Comment> comments = commentRepository.findByMovieId(movie.getId());
//        List<String> contents = comments.stream()
//                .map(Comment::getContent)
//                .toList();
//
//        List<Rating> ratings = ratingRepository.findByMovieId(movie.getId());
//        OptionalDouble averageRating = ratings.stream()
//                .mapToDouble(Rating::getRatingStar)
//                .average();
//
//        double avg = 0;
//        if (averageRating.isPresent()) {
//            avg = averageRating.getAsDouble();
//            System.out.printf("Average rating: %s%n", avg);
//        } else {
//            System.out.println("No ratings available.");
//        }
//
//        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");
//
//        List<String> dayOfWeekList = new ArrayList<>();
//        List<String> dayList = new ArrayList<>();
//        List<String> timeList = new ArrayList<>();
//        for (MovieSchedule schedule : movie.getMovieScheduleList()) {
//            String dayOfWeek = schedule.getStartTime().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
//            String day = schedule.getStartTime().format(formatDate);
//            String time = schedule.getStartTime().format(formatTime);
//            dayOfWeekList.add(dayOfWeek);
//            dayList.add(day);
//            timeList.add(time);
//        }
//
//        List<Integer> scheduleIdList = new ArrayList<>();
//        List<String> screenNameList = new ArrayList<>();
//        for (MovieSchedule schedule : movie.getMovieScheduleList()) {
//            Integer scheduleId = schedule.getId();
//            String screenName = schedule.getScreen().getName();
//            scheduleIdList.add(scheduleId);
//            screenNameList.add(screenName);
//        }
//
//        return new ScheduleResponse(
//                movie.getId(),
//                movie.getName(),
//                movie.getLength(),
//                movie.getDescription(),
//                formattedDatePublish,
//                movie.getTrailerLink(),
//                movie.getImageUrl(),
//                movie.getBackgroundImageUrl(),
//                movieGenreNameList,
//                movieGenreImageUrls,
//                movieGenreDescriptions,
//                moviePerformerNameList,
//                moviePerformerType,
//                movieRatingDetailNameList,
//                movieRatingDetailDescriptions,
//                contents,
//                avg,
//                cinema.getName(),
//                screenNameList,
//                scheduleIdList,
//                dayOfWeekList,
//                dayList,
//                timeList
//        );
//    }

//    public List<MovieResponse> getAllMovieInformationBySelectedDateSchedule(String date) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        LocalDate localDate = LocalDate.parse(date, formatter);
//
//        String formattedDate = localDate.toString();
//        List<Movie> movies = movieRepository.findMoviesBySelectedDateSchedule(formattedDate);
//        if (movies == null || movies.isEmpty()) {
//            throw new IllegalArgumentException("No movies found for given date schedule.");
//        }
//
//        List<MovieResponse> movieResponses = new ArrayList<>();
//
//        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");
//
//        for (Movie movie : movies) {
//            // Fetch Movie Genres
//            List<MovieGenre> movieGenres = movieGenreRepository.findMovieGenresByMovie(movie.getId());
//            List<String> movieGenreNameList = movieGenres.stream()
//                    .map(movieGenre -> movieGenre.getMovieGenreDetail().getName())
//                    .toList();
//            List<String> movieGenreImageUrls = movieGenres.stream()
//                    .map(movieGenre -> movieGenre.getMovieGenreDetail().getImageUrl())
//                    .toList();
//            List<String> movieGenreDescriptions = movieGenres.stream()
//                    .map(movieGenre -> movieGenre.getMovieGenreDetail().getDescription())
//                    .toList();
//
//            // Fetch Movie Performers
//            List<MoviePerformer> moviePerformers = moviePerformerRepository.findMoviePerformersByMovieId(movie.getId());
//            List<String> moviePerformerNameList = moviePerformers.stream()
//                    .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getName())
//                    .toList();
//
//            SimpleDateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy");
//            List<Date> moviePerformerDobList = moviePerformers.stream()
//                    .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getDob())
//                    .toList();
//            List<String> formatMoviePerformerDobList = new ArrayList<>();
//            for (Date dob : moviePerformerDobList) {
//                formatMoviePerformerDobList.add(formatterDate.format(dob));
//            }
//
//            List<PerformerSex> moviePerformerSex = moviePerformers.stream()
//                    .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getPerformerSex())
//                    .toList();
//            List<PerformerType> moviePerformerType = moviePerformers.stream()
//                    .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getPerformerType())
//                    .toList();
//
//            // Fetch Movie Rating Details
//            List<MovieRatingDetail> movieRatingDetails = movieRatingDetailRepository.findMovieRatingDetailsByMovieId(movie.getId());
//            List<String> movieRatingDetailNameList = movieRatingDetails.stream()
//                    .map(MovieRatingDetail::getName)
//                    .toList();
//            List<String> movieRatingDetailDescriptions = movieRatingDetails.stream()
//                    .map(MovieRatingDetail::getDescription)
//                    .toList();
//
//            // Format date
//            SimpleDateFormat publishDateFormatter = new SimpleDateFormat("dd/MM/yyyy");
//            String formattedDatePublish = publishDateFormatter.format(movie.getDatePublish());
//
//            // Movie Respond
//            List<Comment> comments = commentRepository.findByMovieId(movie.getId());
//            List<String> contents = comments.stream()
//                    .map(Comment::getContent)
//                    .toList();
//
//            List<Rating> ratings = ratingRepository.findByMovieId(movie.getId());
//            OptionalDouble averageRating = ratings.stream()
//                    .mapToDouble(Rating::getRatingStar)
//                    .average();
//
//            double avg = 0;
//            if (averageRating.isPresent()) {
//                avg = averageRating.getAsDouble();
//                System.out.printf("Average rating: %s%n", avg);
//            } else {
//                System.out.println("No ratings available.");
//            }
//
//            List<String> dayOfWeekList = new ArrayList<>();
//            List<String> dayList = new ArrayList<>();
//            List<String> timeList = new ArrayList<>();
//            for (MovieSchedule schedule : movie.getMovieScheduleList()) {
//                String dayOfWeek = schedule.getStartTime().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
//                String day = schedule.getStartTime().format(formatDate);
//                String time = schedule.getStartTime().format(formatTime);
//                dayOfWeekList.add(dayOfWeek);
//                dayList.add(day);
//                timeList.add(time);
//            }
//            // Create and add MovieResponse object
//            MovieResponse movieResponse = new MovieResponse(
//                    movie.getId(),
//                    movie.getName(),
//                    movie.getLength(),
//                    movie.getDescription(),
//                    formattedDatePublish,
//                    movie.getTrailerLink(),
//                    movie.getImageUrl(),
//                    movie.getBackgroundImageUrl(),
//                    dayOfWeekList,
//                    dayList,
//                    timeList,
//                    movieGenreNameList,
//                    movieGenreImageUrls,
//                    movieGenreDescriptions,
//                    moviePerformerNameList,
//                    formatMoviePerformerDobList,
//                    moviePerformerSex,
//                    moviePerformerType,
//                    movieRatingDetailNameList,
//                    movieRatingDetailDescriptions,
//                    contents,
//                    avg
//            );
//            movieResponses.add(movieResponse);
//        }
//
//        return movieResponses;
//    }

//    public List<ListFoodAndDrinkToOrderingResponse> getAllFoodsAndDrinksByCinema(Integer cinemaId) {
//        Cinema cinema = cinemaRepository.findById(cinemaId)
//                .orElseThrow(() -> new IllegalArgumentException("Cinema not found with id: %d".formatted(cinemaId)));
//
//        // Fetch all foods and drinks associated with the cinema
//        List<Food> foodList = foodRepository.findByCinema(cinema);
//        List<Drink> drinkList = drinkRepository.findByCinema(cinema);
//
//        // Prepare lists to hold food and drink details
//        List<Integer> foodIds = new ArrayList<>();
//        List<String> foodNameList = new ArrayList<>();
//        List<String> imageUrlFoodList = new ArrayList<>();
//        List<String> descriptionFoodList = new ArrayList<>();
//        List<SizeFoodOrDrink> sizeFoodList = new ArrayList<>();
//        List<Double> priceFoodList = new ArrayList<>();
//
//        List<Integer> drinkIds = new ArrayList<>();
//        List<String> drinkNameList = new ArrayList<>();
//        List<String> imageUrlDrinkList = new ArrayList<>();
//        List<String> descriptionDrinkList = new ArrayList<>();
//        List<SizeFoodOrDrink> sizeDrinkList = new ArrayList<>();
//        List<Double> priceDrinkList = new ArrayList<>();
//
//        for (Food food : foodList) {
//            foodIds.add(food.getId());
//            foodNameList.add(food.getName());
//            imageUrlFoodList.add(food.getImageUrl());
//            descriptionFoodList.add(food.getDescription());
//            sizeFoodList.add(food.getSize());
//            priceFoodList.add(food.getPrice());
//        }
//
//        for (Drink drink : drinkList) {
//            drinkIds.add(drink.getId());
//            drinkNameList.add(drink.getName());
//            imageUrlDrinkList.add(drink.getImageUrl());
//            descriptionDrinkList.add(drink.getDescription());
//            sizeDrinkList.add(drink.getSize());
//            priceDrinkList.add(drink.getPrice());
//        }
//
//        ListFoodAndDrinkToOrderingResponse response = new ListFoodAndDrinkToOrderingResponse(
//                cinema.getName(),
//                foodIds,
//                foodNameList,
//                imageUrlFoodList,
//                descriptionFoodList,
//                sizeFoodList,
//                priceFoodList,
//                drinkIds,
//                drinkNameList,
//                imageUrlDrinkList,
//                descriptionDrinkList,
//                sizeDrinkList,
//                priceDrinkList
//        );
//
//        return List.of(response);
//    }

//    public List<CouponResponse> getAllCoupons() {
//        List<Coupon> coupons = couponRepository.findAll();
//
//        List<Integer> couponIds = new ArrayList<>();
//        List<String> couponNameList = new ArrayList<>();
//        List<String> couponDescriptionList = new ArrayList<>();
//        List<BigDecimal> discountRateList = new ArrayList<>();
//        List<Integer> minSpendReqList = new ArrayList<>();
//        List<Integer> discountLimitList = new ArrayList<>();
//
//        for (Coupon coupon : coupons) {
//            couponIds.add(coupon.getId());
//            couponNameList.add(coupon.getName());
//            couponDescriptionList.add(coupon.getDescription());
//            discountRateList.add(coupon.getDiscount());
//            minSpendReqList.add(coupon.getMinSpendReq());
//            discountLimitList.add(coupon.getDiscountLimit());
//        }
//
//        CouponResponse couponResponse = new CouponResponse(
//                couponIds,
//                couponNameList,
//                couponDescriptionList,
//                discountRateList,
//                minSpendReqList,
//                discountLimitList
//        );
//
//        return List.of(couponResponse);
//    }

//    public ViewCouponsResponse getAvailableCouponsForUser(Integer userId) {
//        List<Coupon> coupons = couponRepository.findAvailableCouponsForUser(userId);
//        return mapCouponsToResponse(coupons);
//    }

//    public ViewCouponsResponse getAvailableCouponsByMovieId(Integer movieId) {
//        List<Coupon> coupons = couponRepository.findAvailableCouponsByMovieId(movieId);
//        return mapCouponsToResponse(coupons);
//    }

    public List<HighRatingMovie> getHighRatingMovies() {
        List<HighRatingMovieProjection> projections = reviewRepository.findHighestRatingMovies(CommonStatus.ACTIVE.getStatus(), Instant.now());
        Map<Long, Double> avgVotes = new HashMap<>();
        projections.forEach(p -> {
            avgVotes.put(p.getId(), p.getAvgVote());
        });
        return movieRepository
                .findAllById(projections
                        .stream()
                        .map(HighRatingMovieProjection::getId)
                        .toList())
                .stream()
                .map(m-> HighRatingMovie
                        .builder()
                        .id(m.getId())
                        .name(m.getName())
                        .length(m.getLength())
                        .publishDate(m
                                .getPublishDate()
                                .toString()
                                .substring(0,10))
                        .imageBase64(m.getImageBase64())
                        .imageBackgroundBase64(m.getBackgroundImageBase64())
                        .genres(m
                                .getGenreSet()
                                .stream()
                                .map(g -> GenrePresentation
                                        .builder()
                                        .id(g.getId())
                                        .name(g.getName())
                                        .description(g.getDescription())
                                        .imageUrl(g.getImageBase64())
                                        .build())
                                .toList())
                        .avgVote(avgVotes.get(m.getId()))
                        .build())
                .toList();
    }
//
//    public List<MovieGenreResponse> getAllMovieGenres() {
//        List<MovieGenre> genres = movieGenreRepository.findAll();
//        return genres.stream()
//                .map(genre -> new MovieGenreResponse(
//                        genre.getMovieGenreDetail().getId(),
//                        genre.getMovieGenreDetail().getName(),
//                        genre.getMovieGenreDetail().getDescription(),
//                        genre.getMovieGenreDetail().getImageUrl()
//                ))
//                .toList();
//    }

//    public List<MovieDetail> getAvailableComingSoonMovies() {
//        return movieRepository
//                .findAllByPublishDateAfterAndStatusIs(Instant.now(), CommonStatus.ACTIVE.getStatus())
//                .stream()
//                .map(m -> MovieDetail
//                        .builder()
//                        .id(m.getId())
//                        .name(m.getName())
//                        .length(m.getLength())
//                        .description(m.getDescription())
//                        .publishDate(m.getPublishDate())
//                        .trailerUrl(m.getTrailerUrl())
//                        .imageBase64(m.getImageBase64())
//                        .backgroundImageBase64(m.getBackgroundImageBase64())
//                        .genres(m
//                                .getGenreSet()
//                                .stream()
//                                .map(g -> GenrePresentation
//                                        .builder()
//                                        .id(g.getId())
//                                        .name(g.getName())
//                                        .description(g.getDescription())
//                                        .imageUrl(g.getImageBase64())
//                                        .build())
//                                .toList())
//                        .performers(m
//                                .getPerformerSet()
//                                .stream()
//                                .map(p -> PerformerPresentation
//                                        .builder()
//                                        .id(p.getId())
//                                        .name(p.getName())
//                                        .typeId(p.getTypeId())
//                                        .dob(p.getDateOfBirth())
//                                        .sex(p.getSex())
//                                        .build())
//                                .toList())
//                        .rating(RatingPresentation
//                                .builder()
//                                .id(m.getRating().getId())
//                                .name(m.getRating().getName())
//                                .description(m.getRating().getDescription())
//                                .build())
//                        .build())
//                .toList();
//    }

//    public List<BookingResponse> getAllBookings() {
//        List<Booking> bookings = bookingRepository.findAllOrderByDateUpdatedDesc();
//        List<BookingResponse> bookingResponses = new ArrayList<>();
//
//
//        for (Booking book : bookings) {
//            BookingResponse bookResponse = new BookingResponse();
//            bookResponse.setBookingId(book.getId());
//            bookResponse.setBookingNo(book.getBookingNo());
//            bookResponse.setMovieName(book.getMovie().getName());
//            bookResponse.setImageUrlMovie(book.getMovie().getImageUrl());
//            bookResponse.setCityName(book.getCity().getName());
//            bookResponse.setCinemaName(book.getCinema().getName());
//            bookResponse.setStartDateTime(book.getStartDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a")));
//            bookResponse.setEndDateTime(book.getEndDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a")));
//            bookResponse.setScreenName(book.getScreen().getName());
//            bookResponse.setTicketTypeName(book.getTickets().stream().map(ticket -> ticket.getTicket().getTicketType().getName()).toList());
//            bookResponse.setSeatName(book.getSeatList().stream().map(seat -> "%s (%s)".formatted(seat.getSeat().getName(), seat.getSeatType().getName())).toList());
//            bookResponse.setFoodNameList(book.getFoodList().stream().map(food -> "%s - Quantity: %d".formatted(food.getFood().getName(), food.getQuantity())).toList());
//            bookResponse.setDrinkNameList(book.getDrinks().stream().map(drink -> "%s - Quantity: %d".formatted(drink.getDrink().getName(), drink.getQuantity())).toList());
//            bookResponse.setCouponName(book.getCoupons().stream().map(coupon -> "%s (%s)".formatted(coupon.getCoupon().getName(), coupon.getCoupon().getDescription())).toList());
//            bookResponse.setStatus(book.getStatus().toString());
//            bookResponse.setTotalPrice(book.getTotalPrice());
//            bookingResponses.add(bookResponse);
//        }
//
//        return bookingResponses;
//    }

//    public List<BookingResponse> getAllBookingsByUser(Integer userId) {
//        List<Booking> bookings = bookingRepository.findByUserId(userId);
//        if (bookings.isEmpty()) {
//            throw new IllegalArgumentException("No bookings found for given user.");
//        }
//        List<BookingResponse> bookingResponses = new ArrayList<>();
//
//        for (Booking book : bookings) {
//            List<MovieGenre> movieGenres = movieGenreRepository.findMovieGenresByMovie(book.getMovie().getId());
//            if (movieGenres.isEmpty()) {
//                throw new IllegalArgumentException("Movie does not have any genre.");
//            }
//
//            BookingResponse bookResponse = new BookingResponse();
//            bookResponse.setBookingId(book.getId());
//            bookResponse.setBookingNo(book.getBookingNo());
//            bookResponse.setMovieName(book.getMovie().getName());
//            bookResponse.setMovieId(book.getMovie().getId());
//            bookResponse.setImageUrlMovie(book.getMovie().getImageUrl());
//            bookResponse.setCityName(book.getCity().getName());
//            bookResponse.setCinemaName(book.getCinema().getName());
//            bookResponse.setStartDateTime(book.getStartDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a")));
//            bookResponse.setEndDateTime(book.getEndDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a")));
//            bookResponse.setScreenName(book.getScreen().getName());
//            bookResponse.setTicketTypeName(book.getTickets().stream().map(ticket -> ticket.getTicket().getTicketType().getName()).toList());
//            bookResponse.setSeatName(book.getSeatList().stream().map(seat -> "%s (%s)".formatted(seat.getSeat().getName(), seat.getSeatType().getName())).toList());
//            bookResponse.setFoodNameList(book.getFoodList().stream().map(food -> "%s - Quantity: %d".formatted(food.getFood().getName(), food.getQuantity())).toList());
//            bookResponse.setDrinkNameList(book.getDrinks().stream().map(drink -> "%s - Quantity: %d".formatted(drink.getDrink().getName(), drink.getQuantity())).toList());
//            bookResponse.setCouponName(book.getCoupons().stream().map(coupon -> "%s (%s)".formatted(coupon.getCoupon().getName(), coupon.getCoupon().getDescription())).toList());
//            bookResponse.setStatus(book.getStatus().toString());
//            bookResponse.setTotalPrice(book.getTotalPrice());
//            bookingResponses.add(bookResponse);
//        }
//
//        return bookingResponses;
//    }

//    public List<BookingResponse> getAllCompletedBookingsThatHaveStartDateTimeHigherThanNowByUser(Integer userId) {
//        List<Booking> bookings = bookingRepository.findByUserId(userId);
//        if (bookings.isEmpty()) {
//            throw new IllegalArgumentException("No bookings found for given user.");
//        }
//        List<BookingResponse> bookingResponses = new ArrayList<>();
//
//        bookings.removeIf(booking -> !booking.getStatus().equals(BookingStatus.Completed));
//        bookings.removeIf(booking -> booking.getStartDateTime().isBefore(LocalDateTime.now()) || booking.getStartDateTime().isEqual(LocalDateTime.now()));
//
//        for (Booking book : bookings) {
//            List<MovieGenre> movieGenres = movieGenreRepository.findMovieGenresByMovie(book.getMovie().getId());
//            if (movieGenres.isEmpty()) {
//                throw new IllegalArgumentException("Movie does not have any genre.");
//            }
//
//            BookingResponse bookResponse = new BookingResponse();
//            bookResponse.setBookingId(book.getId());
//            bookResponse.setBookingNo(book.getBookingNo());
//            bookResponse.setMovieName(book.getMovie().getName());
//            bookResponse.setMovieId(book.getMovie().getId());
//            bookResponse.setImageUrlMovie(book.getMovie().getImageUrl());
//            bookResponse.setCityName(book.getCity().getName());
//            bookResponse.setCinemaName(book.getCinema().getName());
//            bookResponse.setStartDateTime(book.getStartDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a")));
//            bookResponse.setEndDateTime(book.getEndDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a")));
//            bookResponse.setScreenName(book.getScreen().getName());
//            bookResponse.setTicketTypeName(book.getTickets().stream().map(ticket -> ticket.getTicket().getTicketType().getName()).toList());
//            bookResponse.setSeatName(book.getSeatList().stream().map(seat -> "%s (%s)".formatted(seat.getSeat().getName(), seat.getSeatType().getName())).toList());
//            bookResponse.setFoodNameList(book.getFoodList().stream().map(food -> "%s - Quantity: %d".formatted(food.getFood().getName(), food.getQuantity())).toList());
//            bookResponse.setDrinkNameList(book.getDrinks().stream().map(drink -> "%s - Quantity: %d".formatted(drink.getDrink().getName(), drink.getQuantity())).toList());
//            bookResponse.setCouponName(book.getCoupons().stream().map(coupon -> "%s (%s)".formatted(coupon.getCoupon().getName(), coupon.getCoupon().getDescription())).toList());
//            bookResponse.setStatus(book.getStatus().toString());
//            bookResponse.setTotalPrice(book.getTotalPrice());
//            bookingResponses.add(bookResponse);
//        }
//
//        return bookingResponses;
//    }

//    public List<BookingResponse> getAllBookingsThatHaveStartDateTimeHigherThanNowByUser(Integer userId) {
//        List<Booking> bookings = bookingRepository.findByUserId(userId);
//        if (bookings.isEmpty()) {
//            throw new IllegalArgumentException("No bookings found for given user.");
//        }
//        List<BookingResponse> bookingResponses = new ArrayList<>();
//
//        bookings.removeIf(booking -> booking.getStartDateTime().isBefore(LocalDateTime.now()) || booking.getStartDateTime().isEqual(LocalDateTime.now()));
//
//        for (Booking book : bookings) {
//            List<MovieGenre> movieGenres = movieGenreRepository.findMovieGenresByMovie(book.getMovie().getId());
//            if (movieGenres.isEmpty()) {
//                throw new IllegalArgumentException("Movie does not have any genre.");
//            }
//
//            BookingResponse bookResponse = new BookingResponse();
//            bookResponse.setBookingId(book.getId());
//            bookResponse.setBookingNo(book.getBookingNo());
//            bookResponse.setMovieName(book.getMovie().getName());
//            bookResponse.setMovieId(book.getMovie().getId());
//            bookResponse.setImageUrlMovie(book.getMovie().getImageUrl());
//            bookResponse.setCityName(book.getCity().getName());
//            bookResponse.setCinemaName(book.getCinema().getName());
//            bookResponse.setStartDateTime(book.getStartDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a")));
//            bookResponse.setEndDateTime(book.getEndDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a")));
//            bookResponse.setScreenName(book.getScreen().getName());
//            bookResponse.setTicketTypeName(book.getTickets().stream().map(ticket -> ticket.getTicket().getTicketType().getName()).toList());
//            bookResponse.setSeatName(book.getSeatList().stream().map(seat -> "%s (%s)".formatted(seat.getSeat().getName(), seat.getSeatType().getName())).toList());
//            bookResponse.setFoodNameList(book.getFoodList().stream().map(food -> "%s - Quantity: %d".formatted(food.getFood().getName(), food.getQuantity())).toList());
//            bookResponse.setDrinkNameList(book.getDrinks().stream().map(drink -> "%s - Quantity: %d".formatted(drink.getDrink().getName(), drink.getQuantity())).toList());
//            bookResponse.setCouponName(book.getCoupons().stream().map(coupon -> "%s (%s)".formatted(coupon.getCoupon().getName(), coupon.getCoupon().getDescription())).toList());
//            bookResponse.setStatus(book.getStatus().toString());
//            bookResponse.setTotalPrice(book.getTotalPrice());
//            bookingResponses.add(bookResponse);
//        }
//
//        return bookingResponses;
//    }

//    public List<BookingResponse> getAllBookingsCanceledByUser(BookingStatus bookingStatus, Integer userId) {
//
//        List<Booking> bookings = bookingRepository.findByStatusAndByUser(BookingStatus.CANCELLED, userId);
//        if (bookings.isEmpty()) {
//            throw new IllegalArgumentException("No bookings found for given status.");
//        }
//        List<BookingResponse> bookingResponses = new ArrayList<>();
//
//        for (Booking book : bookings) {
//            List<MovieGenre> movieGenres = movieGenreRepository.findMovieGenresByMovie(book.getMovie().getId());
//            if (movieGenres.isEmpty()) {
//                throw new IllegalArgumentException("Movie does not have any genre.");
//            }
//
//            BookingResponse bookResponse = new BookingResponse();
//            bookResponse.setBookingId(book.getId());
//            bookResponse.setBookingNo(book.getBookingNo());
//            bookResponse.setMovieName(book.getMovie().getName());
//            bookResponse.setMovieId(book.getMovie().getId());
//            bookResponse.setImageUrlMovie(book.getMovie().getImageUrl());
//            bookResponse.setCityName(book.getCity().getName());
//            bookResponse.setCinemaName(book.getCinema().getName());
//            bookResponse.setStartDateTime(book.getStartDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a")));
//            bookResponse.setEndDateTime(book.getEndDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a")));
//            bookResponse.setScreenName(book.getScreen().getName());
//            bookResponse.setTicketTypeName(book.getTickets().stream().map(ticket -> ticket.getTicket().getTicketType().getName()).toList());
//            bookResponse.setSeatName(book.getSeatList().stream().map(seat -> "%s (%s)".formatted(seat.getSeat().getName(), seat.getSeatType().getName())).toList());
//            bookResponse.setFoodNameList(book.getFoodList().stream().map(food -> "%s - Quantity: %d".formatted(food.getFood().getName(), food.getQuantity())).toList());
//            bookResponse.setDrinkNameList(book.getDrinks().stream().map(drink -> "%s - Quantity: %d".formatted(drink.getDrink().getName(), drink.getQuantity())).toList());
//            bookResponse.setCouponName(book.getCoupons().stream().map(coupon -> "%s (%s)".formatted(coupon.getCoupon().getName(), coupon.getCoupon().getDescription())).toList());
//            bookResponse.setStatus(book.getStatus().toString());
//            bookResponse.setTotalPrice(book.getTotalPrice());
//            bookingResponses.add(bookResponse);
//        }
//
//        return bookingResponses;
//    }
//
//    private ViewCouponsResponse mapCouponsToResponse(List<Coupon> coupons) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//
//        List<String> nameCoupons = coupons.stream()
//                .map(Coupon::getName)
//                .toList();
//
//        List<String> descriptionCoupons = coupons.stream()
//                .map(Coupon::getDescription)
//                .toList();
//
//        List<String> expirationDates = coupons.stream()
//                .map(coupon -> dateFormat.format(coupon.getDateExpired()))
//                .toList();
//
//        List<BigDecimal> discounts = coupons.stream()
//                .map(Coupon::getDiscount)
//                .toList();
//
//        List<String> discountLimits = coupons.stream()
//                .map(coupon -> coupon.getDiscountLimit() != null ? coupon.getDiscountLimit().toString() : "N/A")
//                .toList();
//
//        return new ViewCouponsResponse(nameCoupons, descriptionCoupons, expirationDates, discounts, discountLimits);
//    }
}
