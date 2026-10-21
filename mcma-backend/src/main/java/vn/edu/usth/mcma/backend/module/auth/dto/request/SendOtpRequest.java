package vn.edu.usth.mcma.backend.module.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class SendOtpRequest {
    private String sessionId;
    @NotBlank @Email private String email;
}
