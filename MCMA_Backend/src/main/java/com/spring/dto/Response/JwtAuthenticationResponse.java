package com.spring.dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {
    private String token;
    private String message;
    private String error;
    private Integer userId;
}
