package vn.edu.usth.mcma.backend.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import constants.ApiResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
public class ApiResponse {
    private String status;
    private String message;
    private String description;
    private String stackTrace;
    public static ApiResponse success() {
        return ApiResponse
                .builder()
                .status(ApiResponseCode.SUCCESS.getStatus())
                .message(ApiResponseCode.SUCCESS.name())
                .description(ApiResponseCode.SUCCESS.getDescription())
                .build();
    }
}
