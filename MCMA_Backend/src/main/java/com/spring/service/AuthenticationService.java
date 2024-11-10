package com.spring.service;

import com.spring.dto.request.*;
import com.spring.dto.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    JwtAuthenticationResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    String resetPassword(ResetPasswordRequest request);

    void updateAccount(int userId, UpdateAccountRequest updateAccountRequest);

    void deleteAccount(int userId);
}
