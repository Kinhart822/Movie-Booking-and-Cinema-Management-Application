package com.spring.dto.Request.booking;

import com.spring.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompleteRequest {
    private PaymentMethod paymentMethod;
}
