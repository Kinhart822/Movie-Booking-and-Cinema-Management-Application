package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Request;

public class RefreshTokenRequest {
    private String token;

    public RefreshTokenRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
