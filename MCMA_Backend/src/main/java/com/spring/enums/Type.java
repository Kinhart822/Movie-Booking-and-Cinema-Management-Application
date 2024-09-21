package com.spring.enums;

import lombok.Getter;

@Getter
public enum Type {
    ADMIN(1),
    USER(2);

    private final int value;

    Type(int value) {
        this.value = value;
    }

    public static Type fromValue(int value) {
        for (Type type : Type.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}
