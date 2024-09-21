package com.spring.enums;

import lombok.Getter;

@Getter
public enum PerformerType {
    Director(1),
    Actor(2);

    private final int value;

    PerformerType(int value) {
        this.value = value;
    }

    public static PerformerType fromValue(int value) {
        for (PerformerType performerType : PerformerType.values()) {
            if (performerType.getValue() == value) {
                return performerType;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}
