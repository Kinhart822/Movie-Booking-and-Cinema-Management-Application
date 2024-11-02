package com.spring.service.impl;

import com.spring.dto.Request.booking.*;
import com.spring.dto.Response.booking.*;
import com.spring.entities.*;
import com.spring.enums.*;
import com.spring.repository.*;
import com.spring.service.BookingService;
import com.spring.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingDraftRepository bookingDraftRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieGenreRepository movieGenreRepository;

    @Autowired
    private MoviePerformerRepository moviePerformerRepository;

    @Autowired
    private MovieRatingDetailRepository movieRatingDetailRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private MovieScheduleRepository movieScheduleRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private EmailService emailService;

    // 1. Choose movie/city and cinema
    @Override
    public List<BookingMovieRespond> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<BookingMovieRespond> bookingMovieResponses = new ArrayList<>();

        for (Movie movie : movies) {
            // Fetch Movie Genres
            List<MovieGenre> movieGenres = movieGenreRepository.findMovieGenresByMovie(movie.getId());
            List<String> movieGenreNameList = movieGenres.stream()
                    .map(movieGenre -> movieGenre.getMovieGenreDetail().getName())
                    .toList();
            List<String> movieGenreImageUrls = movieGenres.stream()
                    .map(movieGenre -> movieGenre.getMovieGenreDetail().getImageUrl())
                    .toList();
            List<String> movieGenreDescriptions = movieGenres.stream()
                    .map(movieGenre -> movieGenre.getMovieGenreDetail().getDescription())
                    .toList();

            // Fetch Movie Performers
            List<MoviePerformer> moviePerformers = moviePerformerRepository.findMoviePerformersByMovieId(movie.getId());
            List<String> moviePerformerNameList = moviePerformers.stream()
                    .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getName())
                    .toList();

            SimpleDateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy");
            List<Date> moviePerformerDobList = moviePerformers.stream()
                    .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getDob())
                    .toList();
            List<String> formatMoviePerformerDobList = new ArrayList<>();
            for (Date dob : moviePerformerDobList) {
                formatMoviePerformerDobList.add(formatterDate.format(dob));
            }

            List<PerformerSex> moviePerformerSex = moviePerformers.stream()
                    .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getPerformerSex())
                    .toList();
            List<PerformerType> moviePerformerType = moviePerformers.stream()
                    .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getPerformerType())
                    .toList();

            // Fetch Movie Rating Details
            List<MovieRatingDetail> movieRatingDetails = movieRatingDetailRepository.findMovieRatingDetailsByMovieId(movie.getId());
            List<String> movieRatingDetailNameList = movieRatingDetails.stream()
                    .map(MovieRatingDetail::getName)
                    .toList();
            List<String> movieRatingDetailDescriptions = movieRatingDetails.stream()
                    .map(MovieRatingDetail::getDescription)
                    .toList();

            // Format date
            SimpleDateFormat publishDateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDatePublish = publishDateFormatter.format(movie.getDatePublish());

            // Movie Respond
            List<Comment> comments = commentRepository.findByMovieId(movie.getId());
            List<String> contents = comments.stream()
                    .map(Comment::getContent)
                    .toList();

            List<Rating> ratings = ratingRepository.findByMovieId(movie.getId());
            OptionalDouble averageRating = ratings.stream()
                    .mapToDouble(Rating::getRatingStar)
                    .average();

            double avg = 0;
            if (averageRating.isPresent()) {
                avg = averageRating.getAsDouble();
                System.out.println("Average rating: " + avg);
            } else {
                System.out.println("No ratings available.");
            }

            // Create and add BookingMovieRespond object
            BookingMovieRespond movieResponse = new BookingMovieRespond(
                    movie.getName(),
                    movie.getLength(),
                    formattedDatePublish,
                    movie.getTrailerLink(),
                    movie.getImageUrl(),
                    movieGenreNameList,
                    movieGenreImageUrls,
                    movieGenreDescriptions,
                    moviePerformerNameList,
                    formatMoviePerformerDobList,
                    moviePerformerSex,
                    moviePerformerType,
                    movieRatingDetailNameList,
                    movieRatingDetailDescriptions,
                    contents,
                    avg
            );
            bookingMovieResponses.add(movieResponse);
        }

        return bookingMovieResponses;
    }

    @Override
    public BookingMovieRespond selectMovie(MovieRequest movieRequest, Integer userId) {
        BookingDraft draft = new BookingDraft();

        try {
            bookingDraftRepository.deleteAllByUserId(userId);

            Movie movie = movieRepository.findById(movieRequest.getMovieId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid movie"));

            draft.setMovie(movie);
            draft.setUser(userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user")));
            bookingDraftRepository.save(draft);

            LocalDate datePublish = movie.getDatePublish().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDatePublish = datePublish.format(formatter);

            List<MovieGenre> movieGenres = movieGenreRepository.findMovieGenresByMovie(movie.getId());
            List<String> movieGenreNameList = movieGenres.stream()
                    .map(movieGenre -> movieGenre.getMovieGenreDetail().getName())
                    .toList();
            List<String> movieGenreImageUrls = movieGenres.stream()
                    .map(movieGenre -> movieGenre.getMovieGenreDetail().getImageUrl())
                    .toList();
            List<String> movieGenreDescriptions = movieGenres.stream()
                    .map(movieGenre -> movieGenre.getMovieGenreDetail().getDescription())
                    .toList();

            List<MoviePerformer> moviePerformers = moviePerformerRepository.findMoviePerformersByMovieId(movie.getId());
            List<String> moviePerformerNameList = moviePerformers.stream()
                    .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getName())
                    .toList();

            SimpleDateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy");
            List<Date> moviePerformerDobList = moviePerformers.stream()
                    .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getDob())
                    .toList();
            List<String> formatMoviePerformerDobList = new ArrayList<>();
            for (Date formatDob : moviePerformerDobList) {
                formatMoviePerformerDobList.add(formatterDate.format(formatDob));
            }
            List<PerformerSex> moviePerformerSex = moviePerformers.stream()
                    .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getPerformerSex())
                    .toList();
            List<PerformerType> moviePerformerType = moviePerformers.stream()
                    .map(moviePerformer -> moviePerformer.getMoviePerformerDetail().getPerformerType())
                    .toList();

            List<MovieRatingDetail> movieRatingDetails = movieRatingDetailRepository.findMovieRatingDetailsByMovieId(movie.getId());
            List<String> movieRatingDetailNameList = movieRatingDetails.stream()
                    .map(MovieRatingDetail::getName)
                    .toList();
            List<String> movieRatingDetailDescriptions = movieRatingDetails.stream()
                    .map(MovieRatingDetail::getDescription)
                    .toList();

            List<Comment> comments = commentRepository.findByMovieId(movieRequest.getMovieId());
            List<String> contents = comments.stream()
                    .map(Comment::getContent)
                    .toList();

            List<Rating> ratings = ratingRepository.findByMovieId(movieRequest.getMovieId());
            OptionalDouble averageRating = ratings.stream()
                    .mapToDouble(Rating::getRatingStar)
                    .average();

            double avg = 0;
            if (averageRating.isPresent()) {
                avg = averageRating.getAsDouble();
                System.out.println("Average rating: " + avg);
            } else {
                System.out.println("No ratings available.");
            }

            return new BookingMovieRespond(
                    movie.getName(),
                    movie.getLength(),
                    formattedDatePublish,
                    movie.getTrailerLink(),
                    movie.getImageUrl(),
                    movieGenreNameList,
                    movieGenreImageUrls,
                    movieGenreDescriptions,
                    moviePerformerNameList,
                    formatMoviePerformerDobList,
                    moviePerformerSex,
                    moviePerformerType,
                    movieRatingDetailNameList,
                    movieRatingDetailDescriptions,
                    contents,
                    avg
            );
        } catch (Exception e) {
            bookingDraftRepository.delete(draft);
            throw new RuntimeException("Error during movie selection: " + e.getMessage(), e);
        }
    }

    @Override
    public List<CityResponse> getAllCities() {
        List<City> cities = cityRepository.findAll();
        List<CityResponse> cityResponses = new ArrayList<>();

        for (City city : cities) {
            List<Cinema> cinemaList = city.getCinemaList();
            List<String> cinemaNameList = cinemaList.stream()
                    .map(Cinema::getName)
                    .toList();

            CityResponse cityResponse = new CityResponse(
                    city.getName(),
                    cinemaNameList
            );
            cityResponses.add(cityResponse);
        }

        return cityResponses;
    }

    @Override
    public CityResponse selectCity(CityRequest cityRequest, Integer userId) {
        BookingDraft draft = getOrCreateDraft(userId);

        if (draft.getMovie() == null) {
            bookingDraftRepository.delete(draft);
            throw new IllegalArgumentException("Booking draft is incomplete.");
        } else {
            City city = cityRepository.findByIdAndMovieId(cityRequest.getCityId(), draft.getMovie().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid city"));
            draft.setCity(city);
            bookingDraftRepository.save(draft);

            List<Cinema> cinemaList = city.getCinemaList();
            List<String> cinemaNameList = cinemaList.stream()
                    .map(Cinema::getName)
                    .toList();

            return new CityResponse(
                    city.getName(),
                    cinemaNameList
            );
        }
    }

    @Override
    public List<CinemaResponse> getAllCinemas() {
        List<Cinema> cinemaList = cinemaRepository.findAll();
        List<CinemaResponse> cinemaResponses = new ArrayList<>();

        for (Cinema cinema : cinemaList) {
            List<Screen> screenList = cinema.getScreenList();
            List<String> screenType = screenList.stream()
                    .map(screen -> screen.getScreenType().getName())
                    .toList();
            List<String> screenDescription = screenList.stream()
                    .map(screen -> screen.getScreenType().getDescription())
                    .toList();

            List<Food> foodList = cinema.getFoodList();
            List<String> foodName = foodList.stream().map(Food::getName).toList();

            List<Drink> drinks = cinema.getDrinks();
            List<String> drinkName = drinks.stream().map(Drink::getName).toList();

            List<MovieSchedule> movieSchedules = movieScheduleRepository.findMovieSchedulesByCinemaId(cinema.getId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
            List<String> formattedSchedules = movieSchedules.stream()
                    .map(schedule -> schedule.getStartTime().format(formatter))
                    .toList();

            CinemaResponse cinemaResponse = new CinemaResponse(
                    cinema.getName(),
                    screenType,
                    screenDescription,
                    foodName,
                    drinkName,
                    formattedSchedules
            );
            cinemaResponses.add(cinemaResponse);
        }

        return cinemaResponses;
    }

    @Override
    public CinemaResponse selectCinema(CinemaRequest cinemaRequest, Integer userId) {
        BookingDraft draft = getOrCreateDraft(userId);

        if (draft.getMovie() == null || draft.getCity() == null) {
            bookingDraftRepository.delete(draft);
            throw new IllegalArgumentException("Booking draft is incomplete.");
        } else {
            Cinema cinema = cinemaRepository.findByIdAndCityId(cinemaRequest.getCinemaId(), draft.getCity().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid cinema for the specified city"));
            draft.setCinema(cinema);
            bookingDraftRepository.save(draft);

            List<Screen> screenList = cinema.getScreenList();
            List<String> screenType = screenList.stream()
                    .map(screen -> screen.getScreenType().getName())
                    .toList();
            List<String> screenDescription = screenList.stream()
                    .map(screen -> screen.getScreenType().getDescription())
                    .toList();

            List<Food> foodList = cinema.getFoodList();
            List<String> foodName = foodList.stream().map(Food::getName).toList();

            List<Drink> drinks = cinema.getDrinks();
            List<String> drinkName = drinks.stream().map(Drink::getName).toList();

            List<MovieSchedule> movieSchedules = movieScheduleRepository.findMovieSchedulesByMovieIdAndCinemaId(
                    draft.getMovie().getId(),
                    cinema.getId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
            List<String> formattedSchedules = movieSchedules.stream()
                    .map(schedule -> schedule.getStartTime().format(formatter))
                    .toList();

            return new CinemaResponse(
                    cinema.getName(),
                    screenType,
                    screenDescription,
                    foodName,
                    drinkName,
                    formattedSchedules
            );
        }
    }

    // 2. Choose screen, ticket, date and time
    @Override
    public ScreenResponse selectScreen(ScreenRequest screenRequest, Integer userId) {
        BookingDraft draft = getOrCreateDraft(userId);

        if (draft.getMovie() == null || draft.getCity() == null || draft.getCinema() == null) {
            bookingDraftRepository.delete(draft);
            throw new IllegalArgumentException("Booking draft is incomplete.");
        } else {
            Screen screen = screenRepository.findByIdAndCinemaId(screenRequest.getScreenId(), draft.getCinema().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid screen for the specified cinema"));
            draft.setScreen(screen);
            bookingDraftRepository.save(draft);

            return new ScreenResponse(
                    screen.getName(),
                    screen.getScreenType().getName(),
                    screen.getScreenType().getDescription()
            );
        }
    }

    @Override
    public ScheduleResponse selectSchedule(ScheduleRequest scheduleRequest, Integer userId) {
        BookingDraft draft = getOrCreateDraft(userId);

        if (draft.getMovie() == null || draft.getCity() == null || draft.getCinema() == null || draft.getScreen() == null) {
            bookingDraftRepository.delete(draft);
            throw new IllegalArgumentException("Booking draft is incomplete.");
        } else {
            Movie movie = movieRepository.findById(draft.getMovie().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid movie"));

            List<MovieSchedule> movieSchedules = movieScheduleRepository.findSchedulesByMovieCinemaAndDate(
                    draft.getMovie().getId(),
                    draft.getCinema().getId(),
                    scheduleRequest.getSelectedDate()
            );
            if (movieSchedules.isEmpty()) {
                throw new IllegalArgumentException("No available schedules for the selected date.");
            }

            LocalTime selectedTime = scheduleRequest.getSelectedTime();
            MovieSchedule selectedSchedule = movieSchedules.stream()
                    .filter(schedule -> schedule.getStartTime().toLocalTime().equals(selectedTime))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No matching movie schedule found for the selected time."));

            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime movieStartTime = selectedSchedule.getStartTime();
            LocalDateTime movieStartTimePlus10Minutes = movieStartTime.plusMinutes(10);

            if (currentTime.isAfter(movieStartTime) && currentTime.isAfter(movieStartTimePlus10Minutes)) {
                throw new IllegalArgumentException("Cannot book a ticket after the movie has started for more than 10 minutes.");
            }

            LocalDateTime startDateTime = scheduleRequest.getSelectedDate().atTime(selectedTime);
            int lengthInMinutes = movie.getLength();
            long lengthInSeconds = lengthInMinutes * 60L;
            Duration movieDuration = Duration.ofSeconds(lengthInSeconds);
            LocalDateTime endDateTime = startDateTime.plus(movieDuration);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a");
            String formattedStartDateTime = startDateTime.format(formatter);
            String formattedEndDateTime = endDateTime.format(formatter);

            draft.setStartDateTime(startDateTime);
            draft.setEndDateTime(endDateTime);
            draft.setMovieSchedule(selectedSchedule);
            bookingDraftRepository.save(draft);

            return new ScheduleResponse(
                    formattedStartDateTime,
                    formattedEndDateTime
            );
        }
    }

    @Override
    public TicketResponse selectTickets(TicketRequest ticketRequest, Integer userId) {
        BookingDraft draft = getOrCreateDraft(userId);

        if (draft.getMovie() == null || draft.getCity() == null || draft.getCinema() == null ||
                draft.getMovieSchedule() == null || draft.getScreen() == null) {
            bookingDraftRepository.delete(draft);
            throw new IllegalArgumentException("Booking draft is incomplete.");
        } else {
            List<Ticket> tickets = new ArrayList<>();
            if (ticketRequest.getTicketIds() != null && !ticketRequest.getTicketIds().isEmpty()) {
                tickets = ticketRepository.findAllById(ticketRequest.getTicketIds());
            }

            List<BookingDraftTicket> draftTickets = tickets.stream()
                    .map(ticket -> {
                        BookingDraftTicket draftTicket = new BookingDraftTicket();
                        draftTicket.setBookingDraft(draft);
                        draftTicket.setTicket(ticket);
                        return draftTicket;
                    })
                    .toList();

            draft.getTickets().addAll(draftTickets);
            bookingDraftRepository.save(draft);

            List<String> ticketTypes = tickets.stream()
                    .map(ticket -> ticket.getTicketType().getName())
                    .toList();

            List<String> ticketDescriptions = tickets.stream()
                    .map(ticket -> ticket.getTicketType().getDescription())
                    .toList();

            return new TicketResponse(ticketTypes, ticketDescriptions);
        }
    }

    // 3. Choose Seat
    @Override
    public SeatResponse selectSeats(SeatRequest seatRequest, Integer userId) {
        BookingDraft draft = getOrCreateDraft(userId);

        if (draft.getMovie() == null || draft.getCity() == null || draft.getCinema() == null ||
                draft.getMovieSchedule() == null || draft.getScreen() == null || draft.getTickets() == null) {
            bookingDraftRepository.delete(draft);
            throw new IllegalArgumentException("Booking draft is incomplete.");
        } else {
            int ticketCount = draft.getTickets().size();
            int seatCount = seatRequest.getSeatIds().size();

            if (seatCount != ticketCount) {
                throw new IllegalArgumentException("The number of selected seats (" + seatCount +
                        ") does not match the number of tickets (" + ticketCount + ").");
            }

            List<Seat> availableSeats = seatRepository.findBySeatStatus(SeatStatus.Available);

            if (availableSeats.isEmpty()) {
                throw new IllegalArgumentException("No available seats of the selected type.");
            }

            List<Integer> availableSeatIds = availableSeats.stream()
                    .map(Seat::getId)
                    .toList();

            List<Seat> selectedSeats = seatRepository.findAllById(seatRequest.getSeatIds())
                    .stream()
                    .filter(seat -> availableSeatIds.contains(seat.getId()))
                    .toList();

            if (selectedSeats.size() != seatCount) {
                throw new IllegalArgumentException("One or more selected seats are unavailable.");
            }

            List<String> unavailableSeats = seatRequest.getSeatIds().stream()
                    .filter(seatId -> !availableSeatIds.contains(seatId))
                    .map(seatId -> "Seat ID: " + seatId)
                    .toList();

            if (!unavailableSeats.isEmpty()) {
                throw new IllegalArgumentException("Selected seats are not available: " + String.join(", ", unavailableSeats));
            }

            List<BookingDraftSeat> bookingDraftSeats = new ArrayList<>();
            for (Seat seat : selectedSeats) {
                BookingDraftSeat bookingDraftSeat = new BookingDraftSeat();
                bookingDraftSeat.setBookingDraft(draft);
                bookingDraftSeat.setSeat(seat);
                bookingDraftSeat.setSeatType(seat.getSeatType());
                bookingDraftSeats.add(bookingDraftSeat);
            }

            draft.getSeatList().addAll(bookingDraftSeats);
            bookingDraftRepository.save(draft);

            return new SeatResponse(
                    unavailableSeats,
                    availableSeats.stream().map(Seat::getName).toList(),
                    selectedSeats.stream().map(Seat::getName).toList(),
                    selectedSeats.stream().map(seat -> seat.getSeatType().getName()).toList()
            );
        }
    }

    // 4. Food, Drink Ordering
    @Override
    public FoodResponse selectFood(FoodDrinkRequest foodDrinkRequest, Integer userId) {
        BookingDraft draft = getOrCreateDraft(userId);

        if (draft.getMovie() == null || draft.getCity() == null || draft.getCinema() == null ||
                draft.getMovieSchedule() == null || draft.getScreen() == null ||
                draft.getTickets() == null || draft.getSeatList() == null) {
            bookingDraftRepository.delete(draft);
            throw new IllegalArgumentException("Booking draft is incomplete.");
        } else {
            List<Food> availableFoods = foodRepository.findByMovieScheduleId(draft.getMovieSchedule().getId());
            List<Integer> availableFoodIds = availableFoods.stream()
                    .map(Food::getId)
                    .toList();

            List<BookingDraftFood> draftFoods = new ArrayList<>();
            List<String> foodNameList = new ArrayList<>();
            List<String> imageUrlList = new ArrayList<>();
            List<String> descriptionList = new ArrayList<>();
            List<SizeFoodOrDrink> sizeFoodList = new ArrayList<>();

            if (foodDrinkRequest.getFoodIds() != null && !foodDrinkRequest.getFoodIds().isEmpty()) {
                for (int i = 0; i < foodDrinkRequest.getFoodIds().size(); i++) {
                    Integer foodId = foodDrinkRequest.getFoodIds().get(i);
                    SizeFoodOrDrink size = foodDrinkRequest.getSizeFoodList().get(i);

                    if (availableFoodIds.contains(foodId)) {
                        Food food = foodRepository.findById(foodId)
                                .orElseThrow(() -> new IllegalArgumentException("Food not found"));

                        BookingDraftFood draftFood = new BookingDraftFood();
                        draftFood.setBookingDraft(draft);
                        draftFood.setFood(food);
                        draftFood.setSizeFood(size);
                        draftFoods.add(draftFood);

                        foodNameList.add(food.getName());
                        imageUrlList.add(food.getImageUrl());
                        descriptionList.add(food.getDescription());
                        sizeFoodList.add(size);
                    }
                }
            }

            draft.getFoodList().addAll(draftFoods);
            bookingDraftRepository.save(draft);

            return new FoodResponse(foodNameList, imageUrlList, descriptionList, sizeFoodList);
        }
    }

    @Override
    public DrinkResponse selectDrinks(FoodDrinkRequest foodDrinkRequest, Integer userId) {
        BookingDraft draft = getOrCreateDraft(userId);

        if (draft.getMovie() == null || draft.getCity() == null || draft.getCinema() == null ||
                draft.getMovieSchedule() == null || draft.getScreen() == null ||
                draft.getTickets() == null || draft.getSeatList() == null) {
            bookingDraftRepository.delete(draft);
            throw new IllegalArgumentException("Booking draft is incomplete.");
        } else {
            List<Drink> availableDrinks = drinkRepository.findByMovieScheduleId(draft.getMovieSchedule().getId());
            List<Integer> availableDrinkIds = availableDrinks.stream()
                    .map(Drink::getId)
                    .toList();

            List<BookingDraftDrink> draftDrinks = new ArrayList<>();
            List<String> drinkNameList = new ArrayList<>();
            List<String> imageUrlList = new ArrayList<>();
            List<String> descriptionList = new ArrayList<>();
            List<SizeFoodOrDrink> sizeDrinkList = new ArrayList<>();

            if (foodDrinkRequest.getDrinkIds() != null && !foodDrinkRequest.getDrinkIds().isEmpty()) {
                for (int i = 0; i < foodDrinkRequest.getDrinkIds().size(); i++) {
                    Integer drinkId = foodDrinkRequest.getDrinkIds().get(i);
                    SizeFoodOrDrink size = foodDrinkRequest.getSizeFoodList().get(i);

                    if (availableDrinkIds.contains(drinkId)) {
                        Drink drink = drinkRepository.findById(drinkId)
                                .orElseThrow(() -> new IllegalArgumentException("Drink not found"));

                        BookingDraftDrink draftDrink = new BookingDraftDrink();
                        draftDrink.setBookingDraft(draft);
                        draftDrink.setDrink(drink);
                        draftDrink.setSizeDrink(size);
                        draftDrinks.add(draftDrink);

                        drinkNameList.add(drink.getName());
                        imageUrlList.add(drink.getImageUrl());
                        descriptionList.add(drink.getDescription());
                        sizeDrinkList.add(size);
                    }
                }
            }

            draft.getDrinks().addAll(draftDrinks);
            bookingDraftRepository.save(draft);

            return new DrinkResponse(drinkNameList, imageUrlList, descriptionList, sizeDrinkList);
        }
    }

    // 5. Choose Coupon and Calculate Total Price
    @Override
    public Double calculateTotalPrice(CouponRequest couponRequest, Integer userId) {
        BookingDraft draft = getOrCreateDraft(userId);

        if (draft.getMovie() == null || draft.getCity() == null || draft.getCinema() == null ||
                draft.getMovieSchedule() == null || draft.getScreen() == null ||
                draft.getTickets() == null || draft.getSeatList() == null) {
            bookingDraftRepository.delete(draft);
            throw new IllegalArgumentException("Booking draft is incomplete.");
        } else {
            double totalScreenPrice = draft.getScreen().getScreenType().getPrice();

            double totalTicketPrice = draft.getTickets().stream()
                    .mapToDouble(ticket -> ticket.getTicket().getTicketType().getPrice())
                    .sum();

            double totalSeatPrice = draft.getSeatList().stream()
                    .mapToDouble(seat -> seat.getSeatType().getPrice())
                    .sum();

            double totalFoodPrice = draft.getFoodList().stream()
                    .mapToDouble(food -> food.getFood().getPrice() * getFoodOrDrinkSize(food.getSizeFood()))
                    .sum();

            double totalDrinkPrice = draft.getDrinks().stream()
                    .mapToDouble(drink -> drink.getDrink().getPrice() * getFoodOrDrinkSize(drink.getSizeDrink()))
                    .sum();

            double totalPrice = totalScreenPrice + totalTicketPrice + totalSeatPrice + totalDrinkPrice + totalFoodPrice;

            // Fetch and validate selected movie coupons
            List<Coupon> selectedMovieCoupons = new ArrayList<>();
            if (couponRequest.getMovieCouponIds() != null && !couponRequest.getMovieCouponIds().isEmpty()) {
                selectedMovieCoupons = couponRepository.findAllById(couponRequest.getMovieCouponIds());
                validateAndApplyCoupons(selectedMovieCoupons, totalPrice);
            }

            // Fetch and validate selected user coupons
            List<Coupon> selectedUserCoupons = new ArrayList<>();
            if (couponRequest.getUserCouponIds() != null && !couponRequest.getUserCouponIds().isEmpty()) {
                selectedUserCoupons = couponRepository.findAllById(couponRequest.getUserCouponIds());
                validateAndApplyCoupons(selectedUserCoupons, totalPrice);
            }

            List<BookingDraftCoupon> bookingDraftCoupons = new ArrayList<>();
            if (!selectedMovieCoupons.isEmpty()) {
                for (Coupon coupon : selectedMovieCoupons) {
                    BookingDraftCoupon bookingDraftCoupon = new BookingDraftCoupon();
                    bookingDraftCoupon.setBookingDraft(draft);
                    bookingDraftCoupon.setCoupon(coupon);
                    bookingDraftCoupons.add(bookingDraftCoupon);
                }
            }

            if (!selectedUserCoupons.isEmpty()) {
                for (Coupon coupon : selectedUserCoupons) {
                    BookingDraftCoupon bookingDraftCoupon = new BookingDraftCoupon();
                    bookingDraftCoupon.setBookingDraft(draft);
                    bookingDraftCoupon.setCoupon(coupon);
                    bookingDraftCoupons.add(bookingDraftCoupon);
                }
            }

            draft.setCoupons(bookingDraftCoupons);
            draft.setTotalPrice(totalPrice);

            bookingDraftRepository.save(draft);

            return totalPrice;
        }
    }

    // 6. Complete Booking and Send Confirmation Email
    @Override
    public BookingResponse completeBooking(CompleteRequest completeRequest, Integer userId) {
        BookingDraft draft = getOrCreateDraft(userId);

        if (draft.getTotalPrice() == null) {
            throw new IllegalArgumentException("Total price is not calculated. Please calculate the total price first.");
        }

        if (draft.getMovie() == null || draft.getCity() == null || draft.getCinema() == null ||
                draft.getMovieSchedule() == null || draft.getScreen() == null ||
                draft.getTickets() == null || draft.getSeatList() == null) {
            bookingDraftRepository.delete(draft);
            throw new IllegalArgumentException("Booking draft is incomplete.");
        } else {
            try {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

                Booking booking = new Booking();
                booking.setBookingNo(generateBookingNo(
                        draft.getUser().getLastName(),
                        draft.getUser().getFirstName(),
                        draft.getMovie().getName())
                );
                booking.setUser(draft.getUser());
                booking.setCity(draft.getCity());
                booking.setCinema(draft.getCinema());
                booking.setMovieSchedule(draft.getMovieSchedule());
                booking.setMovie(draft.getMovie());
                booking.setStartDateTime(draft.getStartDateTime());
                booking.setEndDateTime(draft.getEndDateTime());
                booking.setScreen(draft.getScreen());
                booking.setTotalPrice(draft.getTotalPrice());
                booking.setPaymentMethod(completeRequest.getPaymentMethod());
                booking.setStatus(completeRequest.getPaymentMethod() == PaymentMethod.Cash
                        ? BookingStatus.Pending_Payment
                        : BookingStatus.Confirmed);

                booking.setTickets(draft.getTickets().stream().map(draftTicket -> {
                    BookingTicket bookingTicket = new BookingTicket();
                    bookingTicket.setTicket(draftTicket.getTicket());
                    bookingTicket.setBooking(booking);
                    return bookingTicket;
                }).collect(Collectors.toList()));

                booking.setSeatList(draft.getSeatList().stream().map(draftSeat -> {
                    BookingSeat bookingSeat = new BookingSeat();
                    bookingSeat.setSeat(draftSeat.getSeat());
                    bookingSeat.setSeatType(draftSeat.getSeatType());
                    bookingSeat.setBooking(booking);
                    draftSeat.getSeat().setSeatStatus(SeatStatus.Unavailable);
                    return bookingSeat;
                }).collect(Collectors.toList()));

                booking.setFoodList(draft.getFoodList().stream().map(draftFood -> {
                    BookingFood bookingFood = new BookingFood();
                    bookingFood.setBooking(booking);
                    bookingFood.setFood(draftFood.getFood());
                    bookingFood.setSizeFood(draftFood.getSizeFood());
                    return bookingFood;
                }).collect(Collectors.toList()));

                booking.setDrinks(draft.getDrinks().stream().map(draftDrink -> {
                    BookingDrink bookingDrink = new BookingDrink();
                    bookingDrink.setBooking(booking);
                    bookingDrink.setDrink(draftDrink.getDrink());
                    bookingDrink.setSizeDrink(draftDrink.getSizeDrink());
                    return bookingDrink;
                }).collect(Collectors.toList()));

                booking.setCoupons(draft.getCoupons().stream().map(draftCoupon -> {
                    BookingCoupon bookingCoupon = new BookingCoupon();
                    bookingCoupon.setCoupon(draftCoupon.getCoupon());
                    bookingCoupon.setBooking(booking);
                    return bookingCoupon;
                }).collect(Collectors.toList()));

                List<Seat> seatsToUpdate = draft.getSeatList().stream()
                        .map(BookingDraftSeat::getSeat)
                        .collect(Collectors.toList());
                seatRepository.saveAll(seatsToUpdate);

                bookingRepository.save(booking);

                Notification notification = new Notification();
                notification.setUser(user);
                notification.setMessage("Your booking for " + booking.getMovie().getName() + " is confirmed. Booking Number: " + booking.getBookingNo());
                notification.setDateCreated(LocalDateTime.now());
                notificationRepository.save(notification);

                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a");
                    String formattedStartDateTime = booking.getStartDateTime().format(formatter);
                    String formattedEndDateTime = booking.getEndDateTime().format(formatter);
                    String subject = "Your Movie Booking Confirmation";
                    String body = "Dear " + user.getFirstName() + " " + user.getLastName() + ",\n\n"
                            + "Your booking for the movie \"" + booking.getMovie().getName() + "\" is confirmed!\n"
                            + "Booking Number: " + booking.getBookingNo() + "\n"
                            + "Date & Time: " + formattedStartDateTime + " - " + formattedEndDateTime + "\n"
                            + "Cinema: " + booking.getCinema().getName() + ", " + booking.getCity().getName() + "\n"
                            + "Screen: " + booking.getScreen().getName() + "\n"
                            + "Tickets: " + booking.getTickets().stream()
                            .map(ticket -> ticket.getTicket().getTicketType().getName())
                            .collect(Collectors.joining(", ")) + "\n"
                            + "Seats: " + booking.getSeatList().stream()
                            .map(seat -> seat.getSeat().getName() + " (" + seat.getSeatType().getName() + ")")
                            .collect(Collectors.joining(", ")) + "\n"
                            + "Food: " + booking.getFoodList().stream()
                            .map(food -> food.getFood().getName() + " (" + food.getSizeFood() + ")")
                            .collect(Collectors.joining(", ")) + "\n"
                            + "Drinks: " + booking.getDrinks().stream()
                            .map(drink -> drink.getDrink().getName() + " (" + drink.getSizeDrink() + ")")
                            .collect(Collectors.joining(", ")) + "\n"
                            + "Total Price: $" + booking.getTotalPrice() + "\n\n"
                            + "Thank you for booking with us!\n\n"
                            + "Best regards,\n"
                            + "Your Movie Booking Team";
                    emailService.sendSimpleMailMessage(user.getEmail(), subject, body);
                } catch (Exception e) {
                    System.out.println("Failed to send email notification. Please try again later.");
                }

                return new BookingResponse(
                        booking.getBookingNo(),
                        booking.getMovie().getName(),
                        booking.getCity().getName(),
                        booking.getCinema().getName(),
                        booking.getStartDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a")),
                        booking.getEndDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a")),
                        booking.getScreen().getName(),
                        booking.getTickets().stream().map(ticket -> ticket.getTicket().getTicketType().getName()).toList(),
                        booking.getSeatList().stream().map(seat -> seat.getSeat().getName() + " (" + seat.getSeatType().getName() + ")").toList(),
                        booking.getFoodList().stream().map(food -> food.getFood().getName() + " (" + food.getSizeFood() + ")").toList(),
                        booking.getDrinks().stream().map(drink -> drink.getDrink().getName() + " (" + drink.getSizeDrink() + ")").toList(),
                        booking.getTotalPrice()
                );
            } finally {
                bookingDraftRepository.delete(draft);
            }
        }
    }

    // 7. Delete Booking and Send Confirmation Email
    @Override
    public void deleteBookingDraft(Integer bookingDraftId) {
        BookingDraft draft = bookingDraftRepository.findById(bookingDraftId)
                .orElseThrow(() -> new IllegalArgumentException("No booking draft found for user"));

        List<Seat> seatListChangeToAvailable = new ArrayList<>();
        if (draft.getSeatList() != null) {
            for (BookingDraftSeat seat : draft.getSeatList()) {
                seat.getSeat().setSeatStatus(SeatStatus.Available);
                seatListChangeToAvailable.add(seat.getSeat());
            }
            seatRepository.saveAll(seatListChangeToAvailable);
        }
        // Delete the booking
        bookingDraftRepository.delete(draft);
    }

    @Override
    public void deleteBooking(Integer bookingId, Integer userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found for ID: " + bookingId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        List<Seat> seatListChangeToAvailable = new ArrayList<>();
        for (BookingSeat seat : booking.getSeatList()) {
            seat.getSeat().setSeatStatus(SeatStatus.Available);
            seatListChangeToAvailable.add(seat.getSeat());
        }
        seatRepository.saveAll(seatListChangeToAvailable);

        bookingRepository.delete(booking);

        try {
            emailService.sendDeleteMailMessage(user.getEmail());
            System.out.println("Notification sent to " + user.getEmail());
        } catch (Exception e) {
            System.out.println("Failed to send email notification. Please try again later.");
        }
    }

    private BookingDraft getOrCreateDraft(Integer userId) {
        return bookingDraftRepository.findByUserId(userId)
                .orElseGet(() -> {
                    BookingDraft newDraft = new BookingDraft();
                    newDraft.setUser(userRepository.findUserById(userId));
                    return newDraft;
                });
    }

    private String generateBookingNo(String lastName, String firstName, String movieName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = LocalDate.now().format(formatter);
        String camelCaseMovieName = convertToCamelCase(movieName);
        String randomSuffix = String.valueOf(System.currentTimeMillis());

        return firstName + lastName + "-" + camelCaseMovieName + "-" + datePart + "-" + randomSuffix;
    }

    private String convertToCamelCase(String name) {
        String[] words = name.split(" ");
        StringBuilder camelCaseName = new StringBuilder();

        if (words.length > 0) {
            camelCaseName.append(words[0].toLowerCase());
        }

        for (int i = 1; i < words.length; i++) {
            if (!words[i].isEmpty()) {
                camelCaseName.append(Character.toUpperCase(words[i].charAt(0)));
                camelCaseName.append(words[i].substring(1).toLowerCase());
            }
        }

        return camelCaseName.toString();
    }

    private double getFoodOrDrinkSize(SizeFoodOrDrink sizeFoodOrDrink) {
        return switch (sizeFoodOrDrink) {
            case Small -> 0.75;
            case Medium -> 1.00;
            case Large -> 1.25;
        };
    }

    private void validateAndApplyCoupons(List<Coupon> coupons, double totalPrice) {
        for (Coupon coupon : coupons) {
            if (coupon.getDateAvailable().after(new Date()) || coupon.getDateExpired().before(new Date())) {
                throw new IllegalArgumentException("Coupon " + coupon.getId() + " is not valid.");
            }

            if (totalPrice < coupon.getMinSpendReq()) {
                throw new IllegalArgumentException("Minimum spend requirement not met for coupon " + coupon.getId() + ".");
            }

            totalPrice -= coupon.getDiscount().doubleValue();
        }
    }
}
