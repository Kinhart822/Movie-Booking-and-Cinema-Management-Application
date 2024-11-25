package com.spring.service.impl;

import com.spring.dto.request.*;
import com.spring.dto.response.UserResponse;
import com.spring.entities.Token;
import com.spring.entities.User;
import com.spring.enums.Type;
import com.spring.dto.response.JwtAuthenticationResponse;
import com.spring.repository.TokenRepository;
import com.spring.repository.UserRepository;
import com.spring.service.AuthenticationService;
import com.spring.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenRepository tokenRepository;

    private void saveUserToken(String jwt, User user) {
        Token saveToken = new Token();
        saveToken.setToken(jwt);
        saveToken.setLoggedOut(false);
        saveToken.setUser(user);
        tokenRepository.save(saveToken);
    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokensByUser = tokenRepository.findAllValidTokenByUser(user.getId());

        if (!validTokensByUser.isEmpty()) {
            validTokensByUser.forEach(t -> t.setLoggedOut(true));
        } else {
            return;
        }

        tokenRepository.saveAll(validTokensByUser);
    }

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest signUpRequest) {
        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("Password and confirm password do not match");
        }

        List<User> existingUsers = userRepository.findUserByEmail(signUpRequest.getEmail());
        if (!existingUsers.isEmpty()) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setGender(signUpRequest.getGender());
        user.setPhoneNumber(signUpRequest.getPhone());
        user.setDateOfBirth(signUpRequest.getDateOfBirth());
        user.setDateCreated(new Date());
        user.setDateUpdated(new Date());
        user.setAddress(signUpRequest.getAddress());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        try {
            Type userType = Type.valueOf(signUpRequest.getType());
            user.setUserType(userType);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Incorrect Role: %s".formatted(signUpRequest.getType()));
        }
        var saveUser = userRepository.save(user);

        var jwt = jwtService.generateToken(user);
        saveUserToken(jwt, saveUser);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);

        return jwtAuthenticationResponse;
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(),
                signInRequest.getPassword()
        ));
        var user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid User: %s".formatted(signInRequest.getEmail())));
        List<Token> loggedOutTokens = tokenRepository.findAllLoggedOutTokensByUser(user.getId());
        if (!loggedOutTokens.isEmpty()) {
            tokenRepository.deleteAll(loggedOutTokens);
        }

        var jwt = jwtService.generateToken(user);
        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setUserId(user.getId());

        return jwtAuthenticationResponse;
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateRefreshToken(user);
            revokeAllTokenByUser(user);
            saveUserToken(jwt, user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);

            return jwtAuthenticationResponse;
        }
        return null;
    }

    @Override
    public JwtAuthenticationResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        try {
            User user = userRepository.findByEmail(forgotPasswordRequest.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with this email"));

            String resetToken = jwtService.generateResetToken(user);
            saveUserToken(resetToken, user);

            jwtAuthenticationResponse.setToken(resetToken);

            jwtAuthenticationResponse.setMessage("Success!");
            return jwtAuthenticationResponse;
        } catch (Exception e) {
            jwtAuthenticationResponse.setError("An error occurred while processing your request.");
            return jwtAuthenticationResponse;
        }
    }

    @Override
    public String resetPassword(ResetPasswordRequest request) {
        String email = jwtService.extractUsername(request.getToken());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        if (!jwtService.isTokenValid(request.getToken(), user)) {
            throw new IllegalArgumentException("Invalid or expired token");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as the old password");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return "Password has been reset successfully. You can log in again!";
    }

    @Override
    public void updateAccount(Integer userId, UpdateAccountRequest updateAccountRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (updateAccountRequest.getEmail() != null) {
            user.setEmail(updateAccountRequest.getEmail());
        }
        if (updateAccountRequest.getFirstName() != null) {
            user.setFirstName(updateAccountRequest.getFirstName());
        }
        if (updateAccountRequest.getLastName() != null) {
            user.setLastName(updateAccountRequest.getLastName());
        }
        if (updateAccountRequest.getPhone() != null) {
            user.setPhoneNumber(updateAccountRequest.getPhone());
        }
        if (updateAccountRequest.getDateOfBirth() != null) {
            user.setDateOfBirth(updateAccountRequest.getDateOfBirth());
        }
        if (updateAccountRequest.getGender() != null) {
            user.setGender(updateAccountRequest.getGender());
        }
        if (updateAccountRequest.getAddress() != null) {
            user.setAddress(updateAccountRequest.getAddress());
        }
        user.setDateUpdated(new Date());

        userRepository.save(user);
    }

    @Override
    public void changeNewPassword(Integer userId, UpdatePasswordRequest updatePasswordRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        if (passwordEncoder.matches(updatePasswordRequest.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as the old password");
        }

        if (!updatePasswordRequest.getNewPassword().equals(updatePasswordRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public UserResponse getUserInformation(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new UserResponse(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getGender().toString(),
                        user.getAddress(),
                        user.getDateOfBirth().toString(),
                        user.getUserType().toString()
                );
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getGender().toString(),
                        user.getAddress(),
                        user.getDateOfBirth().toString(),
                        user.getUserType().toString()
                ))
                .toList();
    }

    @Override
    public void deleteAccount(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Token> allTokens = tokenRepository.findAllByUser(userId);
        tokenRepository.deleteAll(allTokens);

        userRepository.delete(user);
    }
}
