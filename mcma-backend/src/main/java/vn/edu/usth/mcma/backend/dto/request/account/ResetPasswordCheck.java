package vn.edu.usth.mcma.backend.dto.request.account;

import lombok.Data;

@Data
public class ResetPasswordCheck {
    private String resetKey;
}
