package vn.edu.usth.mcma.frontend.dto.response;

import lombok.Data;

@Data
public class SignInResponse {
    private String accessToken;
    private String refreshToken;
}
