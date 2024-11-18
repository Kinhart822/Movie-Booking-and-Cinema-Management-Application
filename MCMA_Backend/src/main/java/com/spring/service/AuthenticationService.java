package com.spring.service;

import com.spring.dto.request.*;
import com.spring.dto.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    JwtAuthenticationResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    String resetPassword(ResetPasswordRequest request);

    void updateAccount(Integer userId, UpdateAccountRequest updateAccountRequest);

    void changeNewPassword(Integer userId, UpdatePasswordRequest updatePasswordRequest);

    void deleteAccount(Integer userId);
}
