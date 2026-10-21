package vn.edu.usth.mcma.backend.module.auth.dto.request;

import lombok.Data;

@Data
public class CheckOtpRequest {
    private String sessionId;
    private String otp;
}
