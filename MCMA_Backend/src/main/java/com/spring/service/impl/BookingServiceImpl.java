package com.spring.service.impl;


import com.spring.dto.Request.BookingRequest;
import com.spring.dto.Response.BookingResponse;
import com.spring.entities.Booking;
import com.spring.entities.Movie;
import com.spring.entities.User;
import com.spring.enums.BookingStatus;
import com.spring.enums.PaymentMethod;
import com.spring.repository.BookingRepository;
import com.spring.repository.MovieRepository;
import com.spring.repository.UserRepository;
import com.spring.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        Movie movie = movieRepository.findById(bookingRequest.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID"));

        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        String bookingNo = generateBookingNo(user.getLastName(), user.getFirstName(), movie.getName());

        int lengthInMinutes = movie.getLength();
        long lengthInSeconds = lengthInMinutes * 60L;
        Duration movieDuration = Duration.ofSeconds(lengthInSeconds);
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = currentDateTime.plus(movieDuration);

        Booking booking = new Booking();
        booking.setBookingNo(bookingNo);
        booking.setStartDateTime(LocalDateTime.now());
        booking.setEndDateTime(endDateTime);

        booking.setPaymentMethod(bookingRequest.getPaymentMethod());
        if(bookingRequest.getPaymentMethod() == PaymentMethod.Cash){
            booking.setStatus(BookingStatus.Pending_Payment);
        } else if(bookingRequest.getPaymentMethod() == PaymentMethod.Bank_Transfer){
            booking.setStatus(BookingStatus.Confirmed);

        }
        booking.setUser(user);

        bookingRepository.save(booking);

        // Return a booking response
        return new BookingResponse(
                bookingNo,
                movie.getName(),
                booking.getStartDateTime(),
                booking.getEndDateTime(),
                booking.getPaymentMethod(),
                booking.getStatus()
        );
    }

    private String generateBookingNo(String lastName, String firstName, String movieName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = LocalDate.now().format(formatter);
        String camelCaseMovieName = convertToCamelCase(movieName);

        return firstName + lastName + "-" + camelCaseMovieName + "-" + datePart;
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
