package com.spring.service;

import com.spring.dto.Request.*;
import com.spring.dto.Response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    JwtAuthenticationResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    String resetPassword(ResetPasswordRequest request);

    void updateAccount(int userId, UpdateAccountRequest updateAccountRequest);

    void deleteAccount(int userId);
}
