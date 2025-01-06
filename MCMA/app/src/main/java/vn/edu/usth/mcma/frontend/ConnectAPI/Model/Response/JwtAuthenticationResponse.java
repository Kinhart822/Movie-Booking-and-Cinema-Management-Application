package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;
    private String message;
    private Integer userId;
}
