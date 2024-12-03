package vn.edu.usth.mcma.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.entity.User;
import vn.edu.usth.mcma.backend.dto.*;
import vn.edu.usth.mcma.backend.security.JwtUtil;
import vn.edu.usth.mcma.backend.service.AuthService;
import vn.edu.usth.mcma.backend.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthService authService;
    private final UserService userService;

    @GetMapping("/auth/profile")
    public ResponseEntity<User> getProfile(HttpServletRequest request) {
        return ResponseEntity.ok(userService.findById(jwtUtil.getUserIdFromToken(request)));
    }

    @PostMapping("/auth/sign-up")
    public ResponseEntity<CommonResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/auth/sign-in")
    public ResponseEntity<JwtAuthResponse> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }
    // TODO
    @PostMapping("/auth/refresh")
    public ResponseEntity<JwtAuthResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/auth/reset-password/request")
    public ResponseEntity<CommonResponse> resetPasswordRequest(@RequestBody @Valid ResetPasswordRequest request) {
        return ResponseEntity.ok(authService.resetPasswordRequest(request));
    }
    @GetMapping("/auth/reset-password/check")
    public ResponseEntity<Map<String, Boolean>> resetPasswordCheck(@RequestBody ResetPasswordCheck check) {
        return ResponseEntity.ok(authService.resetPasswordCheck(check));
    }
    @PostMapping("/auth/reset-password/finish")
    public ResponseEntity<CommonResponse> resetPasswordFinish(@RequestBody @Valid ResetPasswordFinish finish) {
        return ResponseEntity.ok(authService.resetPasswordFinish(finish));
    }
    // TODO
    @PutMapping("/auth/update-account/{userId}")
    public ResponseEntity<String> updateAccount(@PathVariable Long userId, @RequestBody UpdateAccountRequest updateAccountRequest) {
        authService.updateAccount(userId, updateAccountRequest);
        return ResponseEntity.ok("Account updated successfully");
    }
    // TODO
    @Transactional
    @RequestMapping(value = "/update-password", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<String> updatePassword(HttpServletRequest hsRequest,  @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        Long userId = jwtUtil.getUserIdFromToken(hsRequest);
        authService.changeNewPassword(userId, updatePasswordRequest);
        return ResponseEntity.ok("Password updated successfully");
    }
    // TODO
    @DeleteMapping("/delete-account/{userId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long userId) {
        authService.deleteAccount(userId);
        return ResponseEntity.ok("Account deleted successfully");
    }
}
