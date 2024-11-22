package vn.edu.usth.mcma.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {
    private String token;
    private String message;
//    private String error; u used it once
    private Long userId;
}
