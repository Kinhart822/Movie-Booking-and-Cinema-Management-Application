package com.spring.service.impl;

import com.spring.dto.Request.BookingRequest;
import com.spring.dto.Response.BookingResponse;
import com.spring.entities.*;
import com.spring.enums.*;
import com.spring.repository.*;
import com.spring.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

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

    @Override
    public List<MovieSchedule> getSchedulesForSelectedDate(Integer movieId, Integer cinemaId, LocalDate selectedDate) {
        return movieScheduleRepository.findSchedulesByMovieCinemaAndDate(movieId, cinemaId, selectedDate);
    }

    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {
// 1. Choose city and cinema
        Movie movie = movieRepository.findById(bookingRequest.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID"));

        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Cinema cinema = cinemaRepository.findByIdAndCityId(bookingRequest.getCinemaId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid cinema for the specified city"));

// 2. Choose ticket, date and time
        List<MovieSchedule> movieSchedules = getSchedulesForSelectedDate(
                bookingRequest.getMovieId(),
                bookingRequest.getCinemaId(),
                bookingRequest.getSelectedDate()
        );
        if (movieSchedules.isEmpty()) {
            throw new IllegalArgumentException("No movie schedules found for the selected date.");
        }

        LocalTime selectedTime = bookingRequest.getSelectedTime();
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

        LocalDateTime startDateTime = bookingRequest.getSelectedDate().atTime(selectedTime);
        int lengthInMinutes = movie.getLength();
        long lengthInSeconds = lengthInMinutes * 60L;
        Duration movieDuration = Duration.ofSeconds(lengthInSeconds);
        LocalDateTime endDateTime = startDateTime.plus(movieDuration);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a");
        String formattedStartDateTime = startDateTime.format(formatter);
        String formattedEndDateTime = endDateTime.format(formatter);

        List<Ticket> selectedTickets = new ArrayList<>();
        if (bookingRequest.getTicketIds() != null && !bookingRequest.getTicketIds().isEmpty()) {
            selectedTickets = ticketRepository.findAllById(bookingRequest.getTicketIds());
        }
// 3. Choose Seat
        List<Seat> availableSeats = new ArrayList<>();

        for (SeatType seatType : bookingRequest.getSeatTypes()) {
            List<Seat> seatsOfType = seatRepository.findBySeatTypeAndSeatStatus(seatType, SeatStatus.Available);
            availableSeats.addAll(seatsOfType);
        }

        if (availableSeats.isEmpty()) {
            throw new IllegalArgumentException("No available seats of the selected type.");
        }

        List<Integer> availableSeatIds = availableSeats.stream()
                .map(Seat::getId)
                .toList();

        List<Seat> selectedSeats = seatRepository.findAllById(bookingRequest.getSeatIds());

        List<String> unavailableSeats = new ArrayList<>();
        for (Seat seat : selectedSeats) {
            if (!availableSeatIds.contains(seat.getId())) {
                unavailableSeats.add(seat.getName());
            }
        }

        if (!unavailableSeats.isEmpty()) {
            String unavailableSeatsMessage = String.join(", ", unavailableSeats);
            throw new IllegalArgumentException("Selected seats are not available or do not match the requested type(s): " + unavailableSeatsMessage);
        }

// 4. Food, Drink Ordering
        List<Food> selectedFoods = new ArrayList<>();
        if (bookingRequest.getFoodIds() != null && !bookingRequest.getFoodIds().isEmpty()) {
            selectedFoods = foodRepository.findAllById(bookingRequest.getFoodIds());
        }

        // Select drinks if drinkIds are provided
        List<Drink> selectedDrinks = new ArrayList<>();
        if (bookingRequest.getDrinkIds() != null && !bookingRequest.getDrinkIds().isEmpty()) {
            selectedDrinks = drinkRepository.findAllById(bookingRequest.getDrinkIds());
        }

// 7. Calculate Total Price
        double totalTicketPrice = selectedTickets.stream().mapToDouble(ticket -> getTicketPrice(ticket.getTicketType())).sum();
        double totalSeatPrice = selectedSeats.stream().mapToDouble(seat -> getSeatPrice(seat.getSeatType())).sum();
        double totalFoodPrice = 0;
        if (bookingRequest.getFoodIds() != null && bookingRequest.getSizeFood() != null) {
            for (int i = 0; i < bookingRequest.getFoodIds().size(); i++) {
                Integer foodId = bookingRequest.getFoodIds().get(i);
                Optional<Food> foodOpt = foodRepository.findById(foodId);
                if (foodOpt.isPresent()) {
                    Food food = foodOpt.get();
                    SizeFoodOrDrink size = bookingRequest.getSizeFood().get(i);
                    totalFoodPrice += food.getPrice() * getFoodOrDrinkPrice(size);
                }
            }
        }
        double totalDrinkPrice = 0;
        if (bookingRequest.getDrinkIds() != null && bookingRequest.getSizeDrinks() != null) {
            for (int i = 0; i < bookingRequest.getDrinkIds().size(); i++) {
                Integer drinkId = bookingRequest.getDrinkIds().get(i);
                Optional<Drink> drinkOpt = drinkRepository.findById(drinkId);
                if (drinkOpt.isPresent()) {
                    Drink drink = drinkOpt.get();
                    SizeFoodOrDrink size = bookingRequest.getSizeDrinks().get(i);
                    totalDrinkPrice += drink.getPrice() * getFoodOrDrinkPrice(size);
                }
            }
        }
        double totalPrice = totalTicketPrice + totalSeatPrice + totalDrinkPrice + totalFoodPrice;

// 8. Choose Payment, Create a new Booking
        String bookingNo = generateBookingNo(user.getLastName(), user.getFirstName(), movie.getName());
        Booking booking = new Booking();
        booking.setBookingNo(bookingNo);
        booking.setStartDateTime(startDateTime);
        booking.setPaymentMethod(bookingRequest.getPaymentMethod());

        if (bookingRequest.getPaymentMethod() == PaymentMethod.Cash) {
            booking.setStatus(BookingStatus.Pending_Payment);
        } else if (bookingRequest.getPaymentMethod() == PaymentMethod.Bank_Transfer) {
            booking.setStatus(BookingStatus.Confirmed);
        }

        booking.setUser(user);
        booking.setCinema(cinema);
        booking.setMovieSchedule(selectedSchedule);
        booking.setTickets(selectedTickets);
        booking.setSeats(selectedSeats);
        booking.setFoodList(selectedFoods);
        booking.setDrinks(selectedDrinks);
        bookingRepository.save(booking);

//        for (Seat seat : selectedSeats) {
//            seat.setSeatStatus(SeatStatus.Unavailable);
//        }
//        seatRepository.saveAll(selectedSeats);

        return new BookingResponse(
                bookingNo,
                movie.getName(),
                formattedStartDateTime,
                formattedEndDateTime,
                booking.getPaymentMethod(),
                booking.getStatus(),
                selectedTickets.stream().map(Ticket::getId).toList(),
                selectedSeats.stream().map(Seat::getId).toList(),
                selectedSeats.stream().map(Seat::getSeatType).toList(),
                selectedFoods.stream().map(Food::getId).toList(),
                selectedDrinks.stream().map(Drink::getId).toList(),
                bookingRequest.getSizeFood(),
                bookingRequest.getSizeDrinks(),
                totalPrice
        );
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
