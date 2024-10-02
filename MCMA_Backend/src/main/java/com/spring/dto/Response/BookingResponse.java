package com.spring.dto.Response;

import com.spring.enums.BookingStatus;
import com.spring.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private String bookingNo;
    private String movieName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private PaymentMethod paymentMethod;
    private BookingStatus status;
}
