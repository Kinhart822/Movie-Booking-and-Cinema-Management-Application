package vn.edu.usth.mcma.backend.exception;

import lombok.Getter;
import constants.ApiResponseCode;

@Getter
public class BusinessException extends RuntimeException {
    private final String status;
    private final String message;
    private final String description;
    public BusinessException(ApiResponseCode apiResponseCode) {
        this.status = apiResponseCode.getStatus();
        this.message = apiResponseCode.name();
        this.description = apiResponseCode.getDescription();
    }
}
