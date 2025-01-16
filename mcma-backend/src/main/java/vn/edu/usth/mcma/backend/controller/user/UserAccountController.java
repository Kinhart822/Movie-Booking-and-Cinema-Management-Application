package vn.edu.usth.mcma.backend.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.ResetPasswordCheck;
import vn.edu.usth.mcma.backend.dto.ResetPasswordFinish;
import vn.edu.usth.mcma.backend.dto.ResetPasswordRequest;
import vn.edu.usth.mcma.backend.dto.account.AccountCreateRequest;
import vn.edu.usth.mcma.backend.dto.UserPresentation;
import vn.edu.usth.mcma.backend.dto.account.AccountUpdateRequest;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.service.AccountService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "Account Management for User")
public class UserAccountController {
    private final AccountService accountService;

    @Operation(summary = "Create an user account", description = "User create an account for themselves")
    @PostMapping("/account/sign-up")
    public ResponseEntity<ApiResponse> signUp(@RequestBody @Valid AccountCreateRequest signUpRequest) {
        return ResponseEntity.ok(accountService.userSignUp(signUpRequest));
    }
    @Operation(summary = "Get profile of the current logged in user")
    @GetMapping("/account/profile")
    public ResponseEntity<UserPresentation> getUser() {
        return ResponseEntity.ok(accountService.getUser());
    }

    @Operation(summary = "Reset password: request", description = "Initiate reset password process")
    @PostMapping("/account/reset-password/request")
    public ResponseEntity<ApiResponse> resetPasswordRequest(@RequestBody @Valid ResetPasswordRequest request) {
        return ResponseEntity.ok(accountService.resetPasswordRequest(request));
    }
    @Operation(summary = "Reset password: Check OTP", description = "Validate OTP entered by user")
    @GetMapping("/account/reset-password/check")
    public ResponseEntity<Map<String, Boolean>> resetPasswordCheck(@RequestBody ResetPasswordCheck check) {
        return ResponseEntity.ok(accountService.resetPasswordCheck(check));
    }
    @Operation(summary = "Reset password: proceed to change password", description = "OTP is correct")
    @PostMapping("/account/reset-password/finish")
    public ResponseEntity<ApiResponse> resetPasswordFinish(@RequestBody @Valid ResetPasswordFinish finish) {
        return ResponseEntity.ok(accountService.resetPasswordFinish(finish));
    }

    @Operation(summary = "Update current logged in user detail")
    @PutMapping("/account/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody @Valid AccountUpdateRequest request) {
        return ResponseEntity.ok(accountService.updateAccount(request));
    }
}
