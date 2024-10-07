package com.spring.dto.Request;

import com.spring.enums.PaymentMethod;
import com.spring.enums.SeatType;
import com.spring.enums.TicketType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class BookingRequest {
    private Integer userId;
    private Integer movieId;
    private Integer cinemaId;
    private TicketType ticketType;
    private LocalDate selectedDate;
    private LocalTime selectedTime;
    private List<Integer> seatIds;
    private List<SeatType> seatTypes;
    private PaymentMethod paymentMethod;
}
