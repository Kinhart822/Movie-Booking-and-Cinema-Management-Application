package com.spring.dto.Request;

import com.spring.enums.PaymentMethod;
import com.spring.enums.SeatType;
import com.spring.enums.SizeFoodOrDrink;
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
    private LocalDate selectedDate;
    private LocalTime selectedTime;

    private List<Integer> ticketIds;
    private List<TicketType> ticketTypes;

    private List<Integer> seatIds;
    private List<SeatType> seatTypes;

    private List<Integer> foodIds;
    private List<Integer> drinkIds;
    private List<SizeFoodOrDrink> sizeFood;
    private List<SizeFoodOrDrink> sizeDrinks;

    private PaymentMethod paymentMethod;
}
