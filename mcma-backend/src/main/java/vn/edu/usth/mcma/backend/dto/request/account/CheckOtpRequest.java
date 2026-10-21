package vn.edu.usth.mcma.backend.dto.request.account;

import lombok.Data;

@Data
public class CheckOtpRequest {
    private String sessionId;
    private String otp;
}
