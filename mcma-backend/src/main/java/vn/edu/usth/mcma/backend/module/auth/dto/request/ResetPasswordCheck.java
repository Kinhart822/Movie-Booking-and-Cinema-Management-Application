package vn.edu.usth.mcma.backend.module.auth.dto.request;

import lombok.Data;

@Data
public class ResetPasswordCheck {
    private String resetKey;
}
