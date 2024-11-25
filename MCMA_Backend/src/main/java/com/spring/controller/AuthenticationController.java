package com.spring.controller;

import com.spring.config.JwtUtil;
import com.spring.dto.request.*;
import com.spring.dto.response.JwtAuthenticationResponse;
import com.spring.dto.response.UserResponse;
import com.spring.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signUp")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/signIn")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<JwtAuthenticationResponse> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return ResponseEntity.ok(authenticationService.forgotPassword(forgotPasswordRequest));
    }

    @Transactional
    @RequestMapping(value = "/reset-password", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return ResponseEntity.ok(authenticationService.resetPassword(resetPasswordRequest));
    }

    @PutMapping("/update-account/{userId}")
    public ResponseEntity<String> updateAccount(@PathVariable Integer userId, @RequestBody UpdateAccountRequest updateAccountRequest) {
        authenticationService.updateAccount(userId, updateAccountRequest);
        return ResponseEntity.ok("Account updated successfully");
    }

    @Transactional
    @RequestMapping(value = "/update-password", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<String> updatePassword(HttpServletRequest request, @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        authenticationService.changeNewPassword(userId, updatePasswordRequest);
        return ResponseEntity.ok("Password updated successfully");
    }

    @GetMapping(value = "/getUserInformation")
    public ResponseEntity<UserResponse> getUserInformation(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserIdFromToken(request);
        return ResponseEntity.ok(authenticationService.getUserInformation(userId));
    }

    @GetMapping(value = "/getAllUserInformation")
    public ResponseEntity<List<UserResponse>> getAllUserInformation() {
        return ResponseEntity.ok(authenticationService.getAllUsers());
    }

    @DeleteMapping("/delete-account/{userId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Integer userId) {
        authenticationService.deleteAccount(userId);
        return ResponseEntity.ok("Account deleted successfully");
    }
}
