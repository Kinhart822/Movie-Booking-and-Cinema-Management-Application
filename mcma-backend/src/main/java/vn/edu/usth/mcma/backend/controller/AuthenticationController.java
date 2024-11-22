package vn.edu.usth.mcma.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.*;
import vn.edu.usth.mcma.backend.security.JwtUtil;
import vn.edu.usth.mcma.backend.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtUtil jwtUtil;
    private final AuthenticationService authenticationService;

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
    public ResponseEntity<String> updateAccount(@PathVariable Long userId, @RequestBody UpdateAccountRequest updateAccountRequest) {
        authenticationService.updateAccount(userId, updateAccountRequest);
        return ResponseEntity.ok("Account updated successfully");
    }

    @Transactional
    @RequestMapping(value = "/update-password", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<String> updatePassword(HttpServletRequest hsRequest,  @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        Long userId = jwtUtil.getUserIdFromToken(hsRequest);
        authenticationService.changeNewPassword(userId, updatePasswordRequest);
        return ResponseEntity.ok("Password updated successfully");
    }

    @DeleteMapping("/delete-account/{userId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long userId) {
        authenticationService.deleteAccount(userId);
        return ResponseEntity.ok("Account deleted successfully");
    }
}
