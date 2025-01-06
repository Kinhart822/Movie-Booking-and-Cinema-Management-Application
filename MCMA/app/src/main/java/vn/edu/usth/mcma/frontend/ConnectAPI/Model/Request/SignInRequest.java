package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Request;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
