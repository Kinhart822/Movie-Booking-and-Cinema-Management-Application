package vn.edu.usth.mcma.backend.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class ApiErrorResponse {
    private String status;
    private String message;
    private String description;
}
