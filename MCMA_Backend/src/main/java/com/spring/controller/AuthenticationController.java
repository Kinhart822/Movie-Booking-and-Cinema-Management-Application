package com.spring.controller;

import com.spring.dto.Request.*;
import com.spring.dto.Response.JwtAuthenticationResponse;
import com.spring.service.AuthenticationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

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
    public ResponseEntity<String> updateAccount(@PathVariable int userId, @RequestBody UpdateAccountRequest updateAccountRequest) {
        authenticationService.updateAccount(userId, updateAccountRequest);
        return ResponseEntity.ok("Account updated successfully");
    }

    @DeleteMapping("/delete-account/{userId}")
    public ResponseEntity<String> deleteAccount(@PathVariable int userId) {
        authenticationService.deleteAccount(userId);
        return ResponseEntity.ok("Account deleted successfully");
    }
}
