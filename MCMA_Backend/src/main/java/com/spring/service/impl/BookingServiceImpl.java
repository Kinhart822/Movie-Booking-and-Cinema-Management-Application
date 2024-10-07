package com.spring.service.impl;

import com.spring.dto.Request.BookingRequest;
import com.spring.dto.Response.BookingResponse;
import com.spring.entities.*;
import com.spring.enums.BookingStatus;
import com.spring.enums.PaymentMethod;
import com.spring.enums.SeatStatus;
import com.spring.enums.SeatType;
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
    private MovieScheduleRepository movieScheduleRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public List<MovieSchedule> getSchedulesForSelectedDate(Integer movieId, Integer cinemaId, LocalDate selectedDate) {
        return movieScheduleRepository.findSchedulesByMovieCinemaAndDate(movieId, cinemaId, selectedDate);
    }

    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        Movie movie = movieRepository.findById(bookingRequest.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID"));

        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Cinema cinema = cinemaRepository.findByIdAndCityId(bookingRequest.getCinemaId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid cinema for the specified city"));

        List<MovieSchedule> movieSchedules = getSchedulesForSelectedDate(
                bookingRequest.getMovieId(),
                bookingRequest.getCinemaId(),
                bookingRequest.getSelectedDate()
        );
        if (movieSchedules.isEmpty()) {
            throw new IllegalArgumentException("No movie schedules found for the selected date.");
        }

        // Lọc ra các lịch chiếu có startTime trùng với thời gian được chọn
        LocalTime selectedTime = bookingRequest.getSelectedTime();
        MovieSchedule selectedSchedule = movieSchedules.stream()
                .filter(schedule -> schedule.getStartTime().toLocalTime().equals(selectedTime))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No matching movie schedule found for the selected time."));

        // Check if the booking is being made within 10 minutes after the movie starts
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime movieStartTime = selectedSchedule.getStartTime();
        LocalDateTime movieStartTimePlus10Minutes = movieStartTime.plusMinutes(10);

        if (currentTime.isAfter(movieStartTime) && currentTime.isAfter(movieStartTimePlus10Minutes)) {
            throw new IllegalArgumentException("Cannot book a ticket after the movie has started for more than 10 minutes.");
        }

        // Tính toán thời gian kết thúc dựa trên độ dài phim
        LocalDateTime startDateTime = bookingRequest.getSelectedDate().atTime(selectedTime);
        int lengthInMinutes = movie.getLength();
        long lengthInSeconds = lengthInMinutes * 60L;
        Duration movieDuration = Duration.ofSeconds(lengthInSeconds);
        LocalDateTime endDateTime = startDateTime.plus(movieDuration);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a");
        String formattedStartDateTime = startDateTime.format(formatter);
        String formattedEndDateTime = endDateTime.format(formatter);

        // Lấy danh sách ghế trống dựa trên loại ghế người dùng đã chọn
        List<Seat> availableSeats = new ArrayList<>();

        // Collect available seats for each selected seat type
        for (SeatType seatType : bookingRequest.getSeatTypes()) {
            List<Seat> seatsOfType = seatRepository.findBySeatTypeAndSeatStatus(seatType, SeatStatus.Available);
            availableSeats.addAll(seatsOfType);
        }

        if (availableSeats.isEmpty()) {
            throw new IllegalArgumentException("No available seats of the selected type.");
        }

        // Hiển thị các ghế có sẵn cho người dùng dựa trên loại ghế đã chọn
        List<Integer> availableSeatIds = availableSeats.stream()
                .map(Seat::getId)
                .toList();

        // Xác nhận các ghế mà người dùng đã chọn nằm trong danh sách ghế có sẵn
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

        String bookingNo = generateBookingNo(user.getLastName(), user.getFirstName(), movie.getName());

        // Tạo booking
        Booking booking = new Booking();
        booking.setBookingNo(bookingNo);
        booking.setStartDateTime(startDateTime);
        booking.setTicketType(bookingRequest.getTicketType());
        booking.setPaymentMethod(bookingRequest.getPaymentMethod());

        if (bookingRequest.getPaymentMethod() == PaymentMethod.Cash) {
            booking.setStatus(BookingStatus.Pending_Payment);
        } else if (bookingRequest.getPaymentMethod() == PaymentMethod.Bank_Transfer) {
            booking.setStatus(BookingStatus.Confirmed);
        }

        booking.setUser(user);
        booking.setCinema(cinema);
        booking.setMovieSchedule(selectedSchedule);
        booking.setSeats(selectedSeats);

        bookingRepository.save(booking);

//        for (Seat seat : selectedSeats) {
//            seat.setSeatStatus(SeatStatus.Unavailable);
//        }
//        seatRepository.saveAll(selectedSeats);

        List<Integer> selectedSeatIds = selectedSeats.stream()
                .map(Seat::getId)
                .toList();

        List<SeatType> bookedSeatTypes = selectedSeats.stream()
                .map(Seat::getSeatType)
                .toList();

        return new BookingResponse(
                bookingNo,
                movie.getName(),
                formattedStartDateTime,
                formattedEndDateTime,
                booking.getPaymentMethod(),
                booking.getStatus(),
                booking.getTicketType(),
                booking.getTicketPrice(bookingRequest.getTicketType()),
                selectedSeatIds,
                bookedSeatTypes
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
}
