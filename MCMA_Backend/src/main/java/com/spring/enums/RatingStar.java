package com.spring.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum RatingStar {
    one_star(1),
    two_stars(2),
    three_stars(3),
    four_stars(4),
    five_stars(5);

    private final int value;

    RatingStar(int value) {
        this.value = value;
    }

    public static RatingStar fromValue(int value) {
        for (RatingStar ratingStar : RatingStar.values()) {
            if (ratingStar.getValue() == value) {
                return ratingStar;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }

}
