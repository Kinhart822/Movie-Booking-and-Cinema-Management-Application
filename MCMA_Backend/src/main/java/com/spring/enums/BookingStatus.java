package com.spring.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum BookingStatus {
    Confirmed,
    Pending_Payment,
    Completed,
    CANCELLED
}
