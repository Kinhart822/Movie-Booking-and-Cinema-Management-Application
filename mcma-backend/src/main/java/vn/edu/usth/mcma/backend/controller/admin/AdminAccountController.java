package vn.edu.usth.mcma.backend.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.*;
import vn.edu.usth.mcma.backend.dto.account.AccountCreateRequest;
import vn.edu.usth.mcma.backend.dto.account.AccountUpdateRequest;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.service.AccountService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Account Management for Admin")
public class AdminAccountController {
    private final AccountService accountService;

    @Operation(summary = "Create an admin account", description = "Admin can only be created by other admin")
    @PostMapping("/account/create")
    public ResponseEntity<ApiResponse> createAdmin(@RequestBody @Valid AccountCreateRequest request) {
        return ResponseEntity.ok(accountService.createAdmin(request));
    }
    @Operation(summary = "Get profile of the current logged in admin")
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

    @Operation(summary = "Update current logged in admin detail")
    @PutMapping("/account/update")
    public ResponseEntity<ApiResponse> updateAdmin(@RequestBody @Valid AccountUpdateRequest request) {
        return ResponseEntity.ok(accountService.updateAccount(request));
    }
//    // TODO admin self delete admin delete other
//    @DeleteMapping("/delete-account/{userId}")
//    public ResponseEntity<String> deleteAccount(@PathVariable Long userId) {
//        authService.deleteAccount(userId);
//        return ResponseEntity.ok("Account deleted successfully");
//    }
}
