package constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum ApiResponseCode {
    SUCCESS("200", "SUCCESS"),
    SESSION_ID_NOT_FOUND("404", "Unknown session ID!"),
    ENTITY_NOT_FOUND("404", "ENTITY_NOT_FOUND"),
    SEAT_TYPE_NOT_FOUND("404", "SEAT_TYPE_NOT_FOUND"),
    USERNAME_NOT_EXISTED_OR_DEACTIVATED("404", "Username not found or inactive"),
    USERNAME_EXISTED("409", "USERNAME_EXISTED"),
    BAD_CREDENTIALS("400", "Wrong password"),
    INVALID_HTTP_REQUEST("400", "INVALID_HTTP_REQUEST"),
    INVALID_HTTP_REQUEST_HEADER("400", "Authorization Header not found, or wrong type"),
    INVALID_TYPE("403", "INVALID_TYPE"),
    INVALID_REFRESH_REQUEST_USERNAME("401", "MISMATCH: Refresh token's subject and username"),
    INVALID_REFRESH_REQUEST_EXPIRED("401", "Refresh token is expired"),
    INVALID_SEAT_MAP("400", "INVALID_SEAT_MAP"),
    INVALID_START_TIME("400", "INVALID_START_TIME"),
    MOVIE_NOT_PUBLISHED("400", "MOVIE_NOT_PUBLISHED"),
    SCREEN_OCCUPIED("400", "SCREEN_OCCUPIED"),
    BAD_REQUEST("400", "BAD_REQUEST"),
    INVALID_RESET_KEY("404", "Reset key is invalid"),
    INITIATED_SEAT_MAP("400", "SeatMap of this screen has already been initiated"),
    BUSY_SCREEN("400", "Cannot update SeatMap: Screen is going to be used"),
    INVALID_BOOKING_REQUEST("400", "Invalid booking request"),
    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),
    ;
    private final String status;
    private String description;
    ApiResponseCode(String status, String defaultDescription) {
        this.status = status;
        this.description = defaultDescription;
    }
    public ApiResponseCode setDescription(String description) {
        this.description = description;
        return this;
    }
}
