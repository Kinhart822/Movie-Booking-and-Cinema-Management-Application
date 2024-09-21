package com.spring.enums;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE(1),
    INACTIVE(-1);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public static Status fromValue(int value) {
        for (Status status : Status.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}

