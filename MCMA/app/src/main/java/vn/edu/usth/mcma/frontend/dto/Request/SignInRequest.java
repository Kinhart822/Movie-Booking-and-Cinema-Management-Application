package vn.edu.usth.mcma.frontend.dto.Request;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
