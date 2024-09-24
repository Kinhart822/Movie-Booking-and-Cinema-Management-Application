package com.spring.service.impl;

import com.spring.dto.Request.*;
import com.spring.dto.Response.UserDetailsResponse;
import com.spring.entities.User;
import com.spring.enums.Type;
import com.spring.dto.Response.JwtAuthenticationResponse;
import com.spring.repository.UserRepository;
import com.spring.service.AuthenticationService;
import com.spring.service.EmailService;
import com.spring.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;

    @Override
    public User signUp(SignUpRequest signUpRequest) {
        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("Password and confirm password do not match");
        }

        List<User> existingUsers = userRepository.findUserByEmail(signUpRequest.getEmail());
        if (!existingUsers.isEmpty()) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setGender(signUpRequest.getGender());
        user.setPhoneNumber(signUpRequest.getPhone());
        user.setDateOfBirth(signUpRequest.getDateOfBirth());
        user.setDateCreated(new Date());
        user.setAddress(signUpRequest.getAddress());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        try {
            Type userType = Type.valueOf(signUpRequest.getType());
            user.setUserType(userType);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Incorrect Role: " + signUpRequest.getType());
        }

        return userRepository.save(user);
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(), signInRequest.getPassword()
        ));
        var user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Email or Password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        return jwtAuthenticationResponse;
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

            return jwtAuthenticationResponse;
        }
        return null;
    }

    @Override
    public JwtAuthenticationResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        try {
            User user = userRepository.findByEmail(forgotPasswordRequest.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with this email"));

            String resetToken = jwtService.generateResetToken(new HashMap<>(), user);
            jwtAuthenticationResponse.setResetToken(resetToken);

            String resetPasswordUrl = String.format("http://localhost:8080/api/v1/auth/reset-password?token=%s", resetToken);
            emailService.sendPasswordResetEmail(forgotPasswordRequest.getEmail(), resetPasswordUrl);

            jwtAuthenticationResponse.setMessage("We've sent a password reset link to your email");
            return jwtAuthenticationResponse;
        } catch (Exception e) {
            jwtAuthenticationResponse.setError("An error occurred while processing your request.");
            return jwtAuthenticationResponse;
        }
    }

    @Override
    public String resetPassword(ResetPasswordRequest request) {
        String email = jwtService.extractUsername(request.getToken());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (!jwtService.isTokenValid(request.getToken(), user)) {
            throw new IllegalArgumentException("Invalid or expired token");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return "Password has been reset successfully. You can log in again!";
    }

    //TODO: UPDATE CODE
    @Override
    public UserDetailsResponse updateAccount(UpdateAccountRequest updateAccountRequest) {
        User user = userRepository.findByEmail(updateAccountRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Update user fields based on the request
        user.setFirstName(updateAccountRequest.getFirstName());
        user.setLastName(updateAccountRequest.getLastName());
        user.setPhoneNumber(updateAccountRequest.getPhone());
        user.setDateOfBirth(updateAccountRequest.getDateOfBirth());
        user.setAddress(updateAccountRequest.getAddress());
        user.setGender(updateAccountRequest.getGender());
        user.setDateUpdated(new Date());
        userRepository.save(user);

        return UserDetailsResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .address(user.getAddress())
                .dateUpdated(user.getDateUpdated())
                .build();
    }
}
