package com.spring.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum BookingStatus {
    Confirmed,
    In_Processing,
    Pending_Payment,
    Completed,
    CANCELLED
}
