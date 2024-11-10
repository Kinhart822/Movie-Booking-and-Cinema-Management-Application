package com.spring.dto.request.booking;

import com.spring.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompleteRequest {
    private PaymentMethod paymentMethod;
}
