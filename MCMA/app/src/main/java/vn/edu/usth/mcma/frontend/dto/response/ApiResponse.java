package vn.edu.usth.mcma.frontend.dto.response;

import lombok.Data;

@Data
public class ApiResponse {
    private String status;
    private String message;
    private String description;
    private String stackTrace;
}
