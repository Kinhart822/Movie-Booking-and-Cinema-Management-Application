package com.spring.dto.Request;

import com.spring.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {
    private Integer movieId;
    private PaymentMethod paymentMethod;
    private Integer userId;
}
