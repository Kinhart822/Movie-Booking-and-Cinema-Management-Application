package constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiResponseCode {
    SUCCESS("200", "SUCCESS"),
    ENTITY_NOT_FOUND("404", "ENTITY_NOT_FOUND"),
    EMAIL_NOT_FOUND("404", "EMAIL_NOT_FOUND"),
    EMAIL_EXISTED("409", "EMAIL_EXISTED"),
    BAD_CREDENTIALS("401", "BAD_CREDENTIALS"),
    ILLEGAL_TYPE("403", "ILLEGAL_TYPE"),
    INVALID_TOKEN("400", "INVALID_TOKEN"),
    BAD_REQUEST("400", "BAD_REQUEST"),;
    private final String status;
    private final String message;
}
