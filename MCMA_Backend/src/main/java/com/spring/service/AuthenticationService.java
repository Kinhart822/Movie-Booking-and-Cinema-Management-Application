package com.spring.service;

import com.spring.dto.request.*;
import com.spring.dto.response.JwtAuthenticationResponse;
import com.spring.dto.response.UserResponse;

import java.util.List;

public interface AuthenticationService {
    void signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    JwtAuthenticationResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    String resetPassword(ResetPasswordRequest request);

    void updateAccount(Integer userId, UpdateAccountRequest updateAccountRequest);

    void changeNewPassword(Integer userId, UpdatePasswordRequest updatePasswordRequest);

    UserResponse getUserInformation(Integer userId);

    List<UserResponse> getAllUsers();

    void deleteAccount(Integer userId);
}
