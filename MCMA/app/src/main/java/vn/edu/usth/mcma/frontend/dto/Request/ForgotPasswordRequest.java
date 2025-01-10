package vn.edu.usth.mcma.frontend.dto.Request;

public class ForgotPasswordRequest {
    private String token;
    private String email;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return "SignInRequest{" +
                "email='" + email + '\'' +
                '}';
    }
}
