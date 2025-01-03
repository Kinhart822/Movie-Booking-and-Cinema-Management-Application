package constants;

import lombok.Getter;
import vn.edu.usth.mcma.backend.exception.BusinessException;

@Getter
public enum UserType {
    ADMIN(0),
    USER(1),;
    private final int code;
    UserType(int code) {
        this.code = code;
    }
    public static String getName(int code) {
        for (UserType userType : UserType.values()) {
            if (userType.code == code) {
                return userType.name();
            }
        }
        throw new BusinessException(ApiResponseCode.INVALID_TYPE);
    }
}
