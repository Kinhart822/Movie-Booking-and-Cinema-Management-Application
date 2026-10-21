package vn.edu.usth.mcma.backend.module.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    @Email @NotBlank private String email;
}
