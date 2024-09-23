package com.spring.service;

import com.spring.entities.User;
import com.spring.payload.JwtAuthenticationResponse;
import com.spring.payload.RefreshTokenRequest;
import com.spring.payload.SignInRequest;
import com.spring.payload.SignUpRequest;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
