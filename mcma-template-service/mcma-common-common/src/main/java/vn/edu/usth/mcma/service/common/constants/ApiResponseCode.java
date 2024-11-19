package vn.edu.usth.mcma.service.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiResponseCode {
    SUCCESS("200", "SUCCESS"),
    ENTITY_NOT_FOUND("404", "ENTITY_NOT_FOUND"),;
    private final String status;
    private final String message;
}
