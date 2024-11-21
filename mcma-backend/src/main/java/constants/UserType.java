package constants;

import lombok.Getter;

@Getter
public enum UserType {
    ADMIN(0),
    USER(1),;
    private final int value;
    UserType(int value) {
        this.value = value;
    }
}
