package com.spring.service.impl;

import com.spring.dto.Request.booking.*;
import com.spring.entities.*;
import com.spring.enums.*;
import com.spring.repository.*;
import com.spring.service.BookingService;
import com.spring.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingDraftRepository bookingDraftRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private CityRepository cityRepository;

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
    private EmailService emailService;

    // 1. Choose movie/city and cinema
    @Override
    public Movie selectMovie(MovieRequest movieRequest, Integer userId) {
        Movie movie = movieRepository.findById(movieRequest.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie"));

        BookingDraft draft = getOrCreateDraft(userId);
        draft.setMovie(movie);
        bookingDraftRepository.save(draft);

        return movie;
    }

    @Override
    public City selectCity(CityRequest cityRequest, Integer userId) {
        City city = cityRepository.findById(cityRequest.getCityId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid city"));

        BookingDraft draft = getOrCreateDraft(userId);
        draft.setCity(city);
        bookingDraftRepository.save(draft);

        return city;
    }

    @Override
    public Cinema selectCinema(CinemaRequest cinemaRequest, Integer userId) {
        Cinema cinema = cinemaRepository.findByIdAndCityId(cinemaRequest.getCinemaId(), cinemaRequest.getCityId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid cinema for the specified city"));

        BookingDraft draft = getOrCreateDraft(userId);
        draft.setCinema(cinema);
        bookingDraftRepository.save(draft);

        return cinema;
    }

    // 2. Choose ticket, date and time
    @Override
    public List<MovieSchedule> getSchedulesForSelectedDate(ScheduleRequest scheduleRequest, Integer userId) {
        Movie movie = movieRepository.findById(scheduleRequest.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie"));

        List<MovieSchedule> movieSchedules = movieScheduleRepository.findSchedulesByMovieCinemaAndDate(
                scheduleRequest.getMovieId(),
                scheduleRequest.getCinemaId(),
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

        BookingDraft draft = getOrCreateDraft(userId);
        draft.setStartDateTime(startDateTime);
        draft.setEndDateTime(endDateTime);
        draft.setMovieSchedule(selectedSchedule);
        bookingDraftRepository.save(draft);

        return movieSchedules;
    }

    @Override
    public List<Ticket> selectTickets(TicketRequest ticketRequest, Integer userId) {
        List<Ticket> tickets = new ArrayList<>();
        if (ticketRequest.getTicketIds() != null && !ticketRequest.getTicketIds().isEmpty()) {
            tickets = ticketRepository.findAllById(ticketRequest.getTicketIds());
        }

        BookingDraft draft = getOrCreateDraft(userId);
        draft.setTickets(tickets);
        bookingDraftRepository.save(draft);

        return tickets;
    }

    // 3. Choose Seat
    @Override
    public List<Seat> selectSeats(SeatRequest seatRequest, Integer userId) {
        List<Seat> availableSeats = new ArrayList<>();
        for (SeatType seatType : seatRequest.getSeatTypes()) {
            availableSeats.addAll(seatRepository.findBySeatTypeAndSeatStatus(seatType, SeatStatus.Available));
        }
        if (availableSeats.isEmpty()) {
            throw new IllegalArgumentException("No available seats of the selected type.");
        }

        List<Integer> availableSeatIds = availableSeats.stream()
                .map(Seat::getId)
                .toList();

        List<Seat> selectedSeats = seatRepository.findAllById(seatRequest.getSeatIds());

        List<String> unavailableSeats = new ArrayList<>();
        for (Seat seat : selectedSeats) {
            if (!availableSeatIds.contains(seat.getId())) {
                unavailableSeats.add(seat.getName());
            }
        }

        if (!unavailableSeats.isEmpty()) {
            throw new IllegalArgumentException("Selected seats are not available: " + String.join(", ", unavailableSeats));
        }

        BookingDraft draft = getOrCreateDraft(userId);
        draft.setSeats(selectedSeats);
        bookingDraftRepository.save(draft);

        return selectedSeats;
    }

    // 4. Food, Drink Ordering
    @Override
    public List<Food> selectFood(FoodDrinkRequest foodDrinkRequest, Integer userId) {
        List<Food> selectedFoods = new ArrayList<>();
        if (foodDrinkRequest.getFoodIds() != null && !foodDrinkRequest.getFoodIds().isEmpty()) {
            selectedFoods = foodRepository.findAllById(foodDrinkRequest.getFoodIds());
        }

        BookingDraft draft = getOrCreateDraft(userId);
        draft.setFoodList(selectedFoods);
        bookingDraftRepository.save(draft);

        return selectedFoods;
    }

    @Override
    public List<Drink> selectDrinks(FoodDrinkRequest foodDrinkRequest, Integer userId) {
        List<Drink> selectedDrinks = new ArrayList<>();
        if (foodDrinkRequest.getDrinkIds() != null && !foodDrinkRequest.getDrinkIds().isEmpty()) {
            selectedDrinks = drinkRepository.findAllById(foodDrinkRequest.getDrinkIds());
        }

        BookingDraft draft = getOrCreateDraft(userId);
        draft.setDrinks(selectedDrinks);
        bookingDraftRepository.save(draft);

        return selectedDrinks;
    }

    // 5. Choose Coupon and Calculate Total Price
    @Override
    public Double calculateTotalPrice(CouponRequest couponRequest, Integer userId) {
        BookingDraft draft = bookingDraftRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No booking draft found for user"));

        double totalTicketPrice = draft.getTickets().stream()
                .mapToDouble(ticket -> getTicketPrice(ticket.getTicketType()))
                .sum();

        double totalSeatPrice = draft.getSeats().stream()
                .mapToDouble(seat -> getSeatPrice(seat.getSeatType()))
                .sum();

        double totalFoodPrice = 0;
        if (draft.getFoodList() != null && couponRequest.getSizeFood() != null) {
            List<Integer> foodList = new ArrayList<>();

            for (Food food : draft.getFoodList()) {
                Integer foodId = foodRepository.findFoodIdByFood(food.getName());
                if (foodId != null) {
                    foodList.add(foodId);
                }
            }

            List<SizeFoodOrDrink> sizeFood = couponRequest.getSizeFood();

            // Check for list size match to avoid index out of bounds
            if (foodList.size() == sizeFood.size()) {
                for (int i = 0; i < foodList.size(); i++) {
                    Integer foodId = foodList.get(i);
                    Optional<Food> foodOpt = foodRepository.findById(foodId);

                    if (foodOpt.isPresent()) {
                        Food food = foodOpt.get();
                        SizeFoodOrDrink size = sizeFood.get(i);
                        totalFoodPrice += food.getPrice() * getFoodOrDrinkPrice(size);
                    }
                }
            } else {
                throw new IllegalArgumentException("Mismatch between food items and size selections.");
            }
        }

        double totalDrinkPrice = 0;
        if (draft.getDrinks() != null && couponRequest.getSizeDrinks() != null) {
            List<Integer> drinkList = new ArrayList<>();

            for (Drink drink : draft.getDrinks()) {
                Integer drinkId = drinkRepository.findDrinkIdByFood(drink.getName());
                if (drinkId != null) {
                    drinkList.add(drinkId);
                }
            }

            List<SizeFoodOrDrink> sizeDrinks = couponRequest.getSizeDrinks();

            // Check for list size match to avoid index out of bounds
            if (drinkList.size() == sizeDrinks.size()) {
                for (int i = 0; i < drinkList.size(); i++) {
                    Integer drinkId = drinkList.get(i);
                    Optional<Drink> drinkOpt = drinkRepository.findById(drinkId);

                    if (drinkOpt.isPresent()) {
                        Drink drink = drinkOpt.get();
                        SizeFoodOrDrink size = sizeDrinks.get(i);
                        totalDrinkPrice += drink.getPrice() * getFoodOrDrinkPrice(size);
                    }
                }
            } else {
                throw new IllegalArgumentException("Mismatch between drink items and size selections.");
            }
        }

        double totalPrice = totalTicketPrice + totalSeatPrice + totalDrinkPrice + totalFoodPrice;

        // Fetch and validate selected movie coupons
        List<Coupon> selectedMovieCoupons = new ArrayList<>();
        if (couponRequest.getMovieCouponIds() != null && !couponRequest.getMovieCouponIds().isEmpty()) {
            selectedMovieCoupons = couponRepository.findAllById(couponRequest.getMovieCouponIds());

            for (Coupon coupon : selectedMovieCoupons) {
                // Check if the coupon is valid based on date
                if (coupon.getDateAvailable().after(new Date()) || coupon.getDateExpired().before(new Date())) {
                    throw new IllegalArgumentException("Coupon " + coupon.getId() + " is not valid.");
                }

                // Check if the minimum spend requirement is met
                if (totalPrice < coupon.getMinSpendReq()) {
                    throw new IllegalArgumentException("Minimum spend requirement not met for coupon " + coupon.getId() + ".");
                }

                // Apply the discount for each valid coupon
                totalPrice -= coupon.getDiscount().doubleValue();
            }

        }

        // Fetch and validate selected user coupons
        List<Coupon> selectedUserCoupons = new ArrayList<>();
        if (couponRequest.getUserCouponIds() != null && !couponRequest.getUserCouponIds().isEmpty()) {
            selectedUserCoupons = couponRepository.findAllById(couponRequest.getUserCouponIds());

            for (Coupon coupon : selectedUserCoupons) {
                // Check if the coupon is valid based on date
                if (coupon.getDateAvailable().after(new Date()) || coupon.getDateExpired().before(new Date())) {
                    throw new IllegalArgumentException("Coupon " + coupon.getId() + " is not valid.");
                }

                // Check if the minimum spend requirement is met
                if (totalPrice < coupon.getMinSpendReq()) {
                    throw new IllegalArgumentException("Minimum spend requirement not met for coupon " + coupon.getId() + ".");
                }

                // Apply the discount for each valid coupon
                totalPrice -= coupon.getDiscount().doubleValue();
            }
        }

        List<Coupon> allSelectedCoupons = draft.getCoupons() != null ? new ArrayList<>(draft.getCoupons()) : new ArrayList<>();

        if (!selectedMovieCoupons.isEmpty()) {
            allSelectedCoupons.addAll(selectedMovieCoupons);
        }
        if (!selectedUserCoupons.isEmpty()) {
            allSelectedCoupons.addAll(selectedUserCoupons);
        }

        draft = getOrCreateDraft(userId);
        draft.setTotalPrice(totalPrice);
        draft.setCoupons(allSelectedCoupons);

        bookingDraftRepository.save(draft);

        return totalPrice;
    }

    // 6. Complete Booking and Send Confirmation Email
    @Override
    public Booking completeBooking(CompleteRequest completeRequest, Integer userId) {
        BookingDraft draft = bookingDraftRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No booking draft found for user"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        if (draft.getTotalPrice() == null) {
            throw new IllegalArgumentException("Total price is not calculated. Please calculate the total price first.");
        }

        Booking booking = new Booking();
        booking.setBookingNo(generateBookingNo(
                draft.getUser().getLastName(),
                draft.getUser().getFirstName(),
                draft.getMovie().getName())
        );
        booking.setUser(draft.getUser());
        booking.setMovie(draft.getMovie());
        booking.setStartDateTime(draft.getStartDateTime());
        booking.setEndDateTime(draft.getEndDateTime());
        booking.setSeats(new ArrayList<>(draft.getSeats()));
        booking.setFoodList(new ArrayList<>(draft.getFoodList()));
        booking.setDrinks(new ArrayList<>(draft.getDrinks()));
        booking.setTotalPrice(draft.getTotalPrice());
        booking.setPaymentMethod(completeRequest.getPaymentMethod());

        if (completeRequest.getPaymentMethod() == PaymentMethod.Cash) {
            booking.setStatus(BookingStatus.Pending_Payment);
        } else if (completeRequest.getPaymentMethod() == PaymentMethod.Bank_Transfer) {
            booking.setStatus(BookingStatus.Confirmed);
        }

        for (Seat seat : draft.getSeats()) {
            seat.setSeatStatus(SeatStatus.Unavailable);
        }
        seatRepository.saveAll(draft.getSeats());

        bookingRepository.save(booking);
        bookingDraftRepository.delete(draft);

        // Send mail notification
        try {
            emailService.sendSimpleMailMessage(user.getEmail());
            System.out.println("Notification sent to " + user.getEmail());
        } catch (Exception e) {
            System.out.println("Failed to send email notification. Please try again later.");
        }

        return booking;
    }

    @Override
    public void deleteBooking(Integer bookingId) {
        // Check if the booking exists
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found for ID: " + bookingId));

        for (Seat seat : booking.getSeats()) {
            seat.setSeatStatus(SeatStatus.Available);
            seatRepository.save(seat);
        }

        // Delete the booking
        bookingRepository.delete(booking);
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

    private double getTicketPrice(TicketType ticketType) {
        return switch (ticketType) {
            case Adult -> 15.00;
            case Child -> 10.00;
            case Teen -> 12.00;
            case Senior -> 8.00;
            case Student -> 11.00;
            case Couple_Ticket -> 25.00;
            case Family_Ticket -> 40.00;
            default -> throw new IllegalArgumentException("Invalid ticket type");
        };
    }

    private double getSeatPrice(SeatType seatType) {
        return switch (seatType) {
            case Normal -> 10.00;
            case Vip -> 15.00;
            case Twin -> 20.00;
            default -> throw new IllegalArgumentException("Invalid seat type");
        };
    }

    private double getFoodOrDrinkPrice(SizeFoodOrDrink sizeFoodOrDrink) {
        return switch (sizeFoodOrDrink) {
            case Small -> 0.75;
            case Medium -> 1.00;
            case Large -> 1.25;
            default -> throw new IllegalArgumentException("Invalid size of Food or Drink");
        };
    }
}
