package vn.edu.usth.mcma.backend.module.auth.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.usth.mcma.backend.common.dto.ApiResponse;
import vn.edu.usth.mcma.backend.module.auth.dto.request.RefreshRequest;
import vn.edu.usth.mcma.backend.module.auth.dto.request.SignInRequest;
import vn.edu.usth.mcma.backend.module.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "sign-in, refresh, sign-out")
public class AuthController {
    private final AuthService authService;

    @Operation
    @PostMapping("/sign-in")
    public ResponseEntity<Map<String, String>> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }

    @PutMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestBody RefreshRequest refreshRequest) {
        return ResponseEntity.ok(authService.refresh(refreshRequest));
    }

    @PutMapping("/sign-out")
    public ResponseEntity<ApiResponse> signOut(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(authService.signOut(token.replace("Bearer ", "")));
    }
}
