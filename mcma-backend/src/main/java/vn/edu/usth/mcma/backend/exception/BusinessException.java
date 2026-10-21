package vn.edu.usth.mcma.backend.exception;

import vn.edu.usth.mcma.backend.common.constant.ApiResponseCode;

import lombok.Getter;

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
