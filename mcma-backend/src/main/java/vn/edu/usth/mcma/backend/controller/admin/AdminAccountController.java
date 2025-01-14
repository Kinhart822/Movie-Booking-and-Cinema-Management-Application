package vn.edu.usth.mcma.backend.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.*;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.service.AccountService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Account Management for Admin")
public class AdminAccountController {
    private final AccountService accountService;

    @PostMapping("/account/create")
    public ResponseEntity<ApiResponse> createAdmin(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(accountService.createAdmin(signUpRequest));
    }
    @GetMapping("/account/profile")
    public ResponseEntity<AdminPresentation> getAdmin() {
        return ResponseEntity.ok(accountService.getAdmin());
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
//    // TODO
//    @PutMapping("/auth/update-account/{userId}")
//    public ResponseEntity<String> updateAccount(@PathVariable Long userId, @RequestBody UpdateAccountRequest updateAccountRequest) {
//        authService.updateAccount(userId, updateAccountRequest);
//        return ResponseEntity.ok("Account updated successfully");
//    }
//    // TODO admin self delete admin delete other
//    @DeleteMapping("/delete-account/{userId}")
//    public ResponseEntity<String> deleteAccount(@PathVariable Long userId) {
//        authService.deleteAccount(userId);
//        return ResponseEntity.ok("Account deleted successfully");
//    }
}
