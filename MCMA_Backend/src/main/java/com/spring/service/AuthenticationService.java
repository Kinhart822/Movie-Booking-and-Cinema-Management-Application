package com.spring.service;

import com.spring.dto.Request.*;
import com.spring.dto.Response.UserDetailsResponse;
import com.spring.entities.User;
import com.spring.dto.Response.JwtAuthenticationResponse;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    JwtAuthenticationResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
    String resetPassword(ResetPasswordRequest request);

    //TODO: UPDATE CODE
    UserDetailsResponse updateAccount(UpdateAccountRequest updateAccountRequest);
}
