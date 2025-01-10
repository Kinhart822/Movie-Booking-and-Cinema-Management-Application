package vn.edu.usth.mcma.frontend.network.apis;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import vn.edu.usth.mcma.frontend.dto.Request.ForgotPasswordRequest;
import vn.edu.usth.mcma.frontend.dto.Request.RefreshTokenRequest;
import vn.edu.usth.mcma.frontend.dto.Request.ResetPasswordRequest;
import vn.edu.usth.mcma.frontend.dto.Request.SignInRequest;
import vn.edu.usth.mcma.frontend.dto.Request.SignUpRequest;
import vn.edu.usth.mcma.frontend.dto.Response.JwtAuthenticationResponse;

public interface AuthenticationApi {
    @POST("/api/v1/auth/sign-in")
    Call<JwtAuthenticationResponse> signIn(@Body SignInRequest signInRequest);

    @POST("/api/v1/auth/refresh")
    Call<JwtAuthenticationResponse> refresh(@Body RefreshTokenRequest refreshTokenRequest);

    @POST("/api/v1/auth/sign-up")
    Call<Void> signUp(@Body SignUpRequest signUpRequest);

    @POST("/api/v1/auth/forgot-password")
    Call<JwtAuthenticationResponse> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @POST("/api/v1/auth/reset-password")
    Call<String> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);

    @GET("api/v1/logout")
    Call<Void> logout();
}
