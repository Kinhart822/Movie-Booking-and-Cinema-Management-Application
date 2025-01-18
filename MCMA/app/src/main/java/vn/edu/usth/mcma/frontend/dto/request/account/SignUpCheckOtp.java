package vn.edu.usth.mcma.frontend.dto.request.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpCheckOtp {
    private String sessionId;
    private String otp;
}
