package vn.edu.usth.mcma.backend.dto.account;

import lombok.Data;

@Data
public class CheckOtpRequest {
    private String sessionId;
    private String otp;
}
