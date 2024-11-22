package constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiResponseCode {
    SUCCESS("200", "SUCCESS"),
    ENTITY_NOT_FOUND("404", "ENTITY_NOT_FOUND"),
    USER_NOT_FOUND("404", "USER_NOT_FOUND"),
    EMAIL_EXISTED("409", "EMAIL_EXISTED"),
    ILLEGAL_TYPE("403", "ILLEGAL_TYPE"),
    INVALID_TOKEN("400", "INVALID_TOKEN"),;
    private final String status;
    private final String message;
}
