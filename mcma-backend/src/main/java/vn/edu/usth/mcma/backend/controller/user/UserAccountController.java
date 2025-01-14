package vn.edu.usth.mcma.backend.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.SignUpRequest;
import vn.edu.usth.mcma.backend.dto.UserPresentation;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.service.AccountService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserAccountController {

    private final AccountService accountService;

    @PostMapping("/account/sign-up")
    public ResponseEntity<ApiResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(accountService.userSignUp(signUpRequest));
    }
    @GetMapping("/account/profile")
    public ResponseEntity<UserPresentation> getProfile() {
        return ResponseEntity.ok(accountService.getUser());
    }
}
