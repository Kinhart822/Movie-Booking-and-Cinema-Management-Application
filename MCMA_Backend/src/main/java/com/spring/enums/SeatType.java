package com.spring.enums;

import lombok.Getter;

@Getter
public enum SeatType {
    Unavailable(-1),
    Available(0),
    Normal(1),
    Vip(2),
    Twin(3);

    private final int value;

    SeatType(int value) {
        this.value = value;
    }

    public static SeatType fromValue(int value) {
        for (SeatType seatType : SeatType.values()) {
            if (seatType.getValue() == value) {
                return seatType;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}
