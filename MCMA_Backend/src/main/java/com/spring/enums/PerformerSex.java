package com.spring.enums;

import lombok.Getter;

@Getter
public enum PerformerSex {
    Male(0),
    Female(1);

    private final int value;

    PerformerSex(int value) {
        this.value = value;
    }

    public static PerformerSex fromValue(int value) {
        for (PerformerSex performerSex : PerformerSex.values()) {
            if (performerSex.getValue() == value) {
                return performerSex;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}
