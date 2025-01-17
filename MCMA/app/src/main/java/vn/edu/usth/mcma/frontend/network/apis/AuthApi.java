package vn.edu.usth.mcma.frontend.network.apis;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import vn.edu.usth.mcma.frontend.dto.Request.ForgotPasswordRequest;
import vn.edu.usth.mcma.frontend.dto.Request.RefreshRequest;
import vn.edu.usth.mcma.frontend.dto.Request.ResetPasswordRequest;
import vn.edu.usth.mcma.frontend.dto.Request.SignInRequest;
import vn.edu.usth.mcma.frontend.dto.Request.SignUpRequest;
import vn.edu.usth.mcma.frontend.dto.response.JwtAuthenticationResponse;
import vn.edu.usth.mcma.frontend.dto.response.RefreshResponse;
import vn.edu.usth.mcma.frontend.dto.response.SignInResponse;

public interface AuthApi {
    @POST("/api/v1/auth/sign-in")
    Call<SignInResponse> signIn(@Body SignInRequest signInRequest);
    @PUT("/api/v1/auth/refresh")
    Call<RefreshResponse> refresh(@Body RefreshRequest refreshRequest);
    @PUT("/api/v1/auth/sign-out")
    Call<Void> signOut();

    @POST("/api/v1/auth/sign-up")
    Call<Void> signUp(@Body SignUpRequest signUpRequest);

    @POST("/api/v1/auth/forgot-password")
    Call<JwtAuthenticationResponse> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @POST("/api/v1/auth/reset-password")
    Call<String> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);
}
