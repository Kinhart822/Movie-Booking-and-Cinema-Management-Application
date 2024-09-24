package com.spring.controller;

import com.spring.dto.Request.*;
import com.spring.dto.Response.JwtAuthenticationResponse;
import com.spring.dto.Response.UserDetailsResponse;
import com.spring.entities.User;
import com.spring.service.AuthenticationService;
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
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest) {
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
        JwtAuthenticationResponse response = authenticationService.forgotPassword(forgotPasswordRequest);
        if (response.getError() != null) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> changePassword(@RequestBody ResetPasswordRequest request) {
        String responseMessage = authenticationService.resetPassword(request);
        return ResponseEntity.ok(responseMessage);
    }
            //TODO: UPDATE CODE
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(@RequestBody LogOutRequest logOutRequest) {
//        return ResponseEntity.ok(authenticationService.logout(logOutRequest));
//    }
//
//    @PostMapping("/remove-account")
//    public ResponseEntity<JwtAuthenticationResponse> removeToken(@RequestBody RemoveAccountRequest removeAccountRequest) {
//        return ResponseEntity.ok(authenticationService.removeToken(removeAccountRequest));
//    }

    @PostMapping("/update-account")
    public ResponseEntity<UserDetailsResponse> updateAccount(@RequestBody UpdateAccountRequest updateAccountRequest) {
        UserDetailsResponse updatedUserDetails = authenticationService.updateAccount(updateAccountRequest);
        return ResponseEntity.ok(updatedUserDetails);
    }
}
