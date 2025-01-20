package vn.edu.usth.mcma.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.*;
import vn.edu.usth.mcma.backend.dto.account.*;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.service.AccountService;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Tag(name = "Account Management")
public class AccountController {
    private final AccountService accountService;

    /*
     * Reset Password
     */
    @Operation(summary = "Reset password: request", description = "Initiate reset password process")
    @PostMapping("/reset-password/request")
    public ResponseEntity<ApiResponse> resetPasswordRequest(@RequestBody @Valid ResetPasswordRequest request) {
        return ResponseEntity.ok(accountService.resetPasswordRequest(request));
    }
    @Operation(summary = "Reset password: Check OTP", description = "Validate OTP entered by user")
    @GetMapping("/reset-password/check")
    public ResponseEntity<Map<String, Boolean>> resetPasswordCheck(@RequestBody ResetPasswordCheck check) {
        return ResponseEntity.ok(accountService.resetPasswordCheck(check));
    }
    @Operation(summary = "Reset password: proceed to change password", description = "OTP is correct")
    @PostMapping("/reset-password/finish")
    public ResponseEntity<ApiResponse> resetPasswordFinish(@RequestBody @Valid ResetPasswordFinish finish) {
        return ResponseEntity.ok(accountService.resetPasswordFinish(finish));
    }

    /*
     * Update account details
     */
    @Operation(summary = "Update current logged in admin or user detail")
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateAccount(@RequestBody @Valid AccountUpdateRequest request) {
        return ResponseEntity.ok(accountService.updateAccount(request));
    }

    /*
     * ADMIN specific
     */
    @Operation(summary = "Create an admin account", description = "Admin can only be created by other admin")
    @PostMapping("/admin/create")
    public ResponseEntity<ApiResponse> createAdmin(@RequestBody @Valid CreateAdmin request) {
        return ResponseEntity.ok(accountService.createAdmin(request));
    }

    @Operation(summary = "Get profile of the current logged in admin")
    @GetMapping("/admin/profile")
    public ResponseEntity<AdminPresentation> getAdmin() {
        return ResponseEntity.ok(accountService.getAdmin());
    }

    /*
     * USER specific
     */
    @Operation(summary = "Sign Up 1. Check email existence", description = "Check if the email is registered")
    @GetMapping("/user/sign-up/check-email-existence")
    public ResponseEntity<Map<String, Boolean>> signUpCheckEmailExistence(@RequestParam String query) {
        return ResponseEntity.ok(accountService.signUpCheckEmailExistence(query));
    }
    @Operation(summary = "Sign Up 2. Begin sign up process", description = "Send user an OTP")
    @PostMapping("/user/sign-up/begin")
    public ResponseEntity<Map<String, Instant>> signUpBegin(@RequestBody SendOtpRequest request) {
        return ResponseEntity.ok(accountService.signUpBegin(request));
    }
    @Operation(summary = "Sign Up 3. Check OTP", description = "Check if user entered the right OTP")
    @PostMapping("/user/sign-up/check-otp")
    public ResponseEntity<Map<String, Boolean>> signUpCheckOtp(@RequestBody CheckOtpRequest request) {
        return ResponseEntity.ok(accountService.signUpCheckOtp(request));
    }
    @Operation(summary = "Sign Up 4. Finish creating an user account", description = "User enter password")
    @PostMapping("/user/sign-up/finish")
    public ResponseEntity<ApiResponse> signUpFinish(@RequestBody @Valid SignUp request) {
        return ResponseEntity.ok(accountService.signUpFinish(request));
    }

    @PostMapping("/user/forgot-password/begin")
    public ResponseEntity<Map<String, Instant>> forgotPasswordBegin(@RequestBody SendOtpRequest request) {
        return ResponseEntity.ok(accountService.forgotPasswordBegin(request));
    }
    @PostMapping("/user/forgot-password/check-otp")
    public ResponseEntity<Map<String, Boolean>> forgotPasswordCheckOtp(@RequestBody CheckOtpRequest request) {
        return ResponseEntity.ok(accountService.forgotPasswordCheckOtp(request));
    }
    @PostMapping("/user/forgot-password/finish")
    public ResponseEntity<ApiResponse> forgotPasswordFinish(@RequestBody ForgotPassword request) {
        return ResponseEntity.ok(accountService.forgotPasswordFinish(request));
    }

    @Operation(summary = "Get profile of the current logged in user")
    @GetMapping("/user/profile")
    public ResponseEntity<UserPresentation> getUser() {
        return ResponseEntity.ok(accountService.getUser());
    }
}
