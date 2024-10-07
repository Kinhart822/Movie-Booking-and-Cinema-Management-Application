package com.spring.dto.Response;

import com.spring.entities.Seat;
import com.spring.enums.BookingStatus;
import com.spring.enums.PaymentMethod;
import com.spring.enums.SeatType;
import com.spring.enums.TicketType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private String bookingNo;
    private String movieName;
    private String startDateTime;
    private String endDateTime;
    private PaymentMethod paymentMethod;
    private BookingStatus status;
    private TicketType ticketType;
    private Double ticketPrice;
    private List<Integer> seatIds;
    private List<SeatType> seatTypes;
}
