package vn.edu.usth.mcma.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthResponse {
    private String accessToken;
    private String message;
//    private String error; u used it once
    private Long userId;
}
