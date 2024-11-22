package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import constants.EntityStatus;
import constants.UserType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.dao.Token;
import vn.edu.usth.mcma.backend.dao.User;
import vn.edu.usth.mcma.backend.dto.*;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.TokenRepository;
import vn.edu.usth.mcma.backend.repository.UserRepository;
import vn.edu.usth.mcma.backend.security.JwtService;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final UserService userService;

    private void saveUserToken(String value, String email) {
        Token token = new Token();
        token.setValue(value);
        token.setLoggedOut(false);
        token.setUserId(userService.findUserByEmail(email).getId());
        tokenRepository.save(token);
    }

    private void revokeAllTokenByEmail(String email) {
        List<Token> validTokensByUser = tokenRepository.findAllLoggedInByEmail(email);
        if (!validTokensByUser.isEmpty()) {
            validTokensByUser.forEach(t -> t.setLoggedOut(true));
            tokenRepository.saveAll(validTokensByUser);
        }
    }

    public String signUp(SignUpRequest signUpRequest, Integer type) {
        if (type != UserType.ADMIN.getValue() && type != UserType.USER.getValue()) {
            throw new BusinessException(ApiResponseCode.ILLEGAL_TYPE);
        }
        String email = signUpRequest.getEmail();
        if (userService.checkEmailExistence(email)) {
            throw new BusinessException(ApiResponseCode.EMAIL_EXISTED);
        }
        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setSex(signUpRequest.getSex());
        user.setDateOfBirth(signUpRequest.getDateOfBirth());
        user.setEmail(email);
        user.setPhone(signUpRequest.getPhone());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setAddress(signUpRequest.getAddress());
        user.setUserType(type);
        user.setStatus(EntityStatus.CREATED.getStatus());
        Instant now = Instant.now();
        user.setCreatedDate(now);
        user.setLastModifiedDate(now);
        user = userRepository.save(user);
        email = user.getEmail();//email of the saved entity, not from request
        String token = jwtService.generateToken(email);
        saveUserToken(token, email);
        return "User created successfully";
    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        String email = signInRequest.getEmail();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                email,
                signInRequest.getPassword()
        ));
        List<Token> loggedOutTokens = tokenRepository.findAllLoggedOutByEmail(email);
        if (!loggedOutTokens.isEmpty()) {
            tokenRepository.deleteAll(loggedOutTokens);
        }
        String token = jwtService.generateToken(email);
        revokeAllTokenByEmail(email);
        saveUserToken(token, email);

        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setToken(token);
        response.setUserId(userService.findUserByEmail(email).getId());
        return response;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String email = jwtService.extractUsername(refreshTokenRequest.getToken());
        if (!jwtService.isTokenValid(refreshTokenRequest.getToken(), email)) {
            throw new BusinessException(ApiResponseCode.INVALID_TOKEN);
        }
        String token = jwtService.generateToken(email);
        revokeAllTokenByEmail(email);
        saveUserToken(token, email);

        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setToken(token);
        return response;
    }

    public JwtAuthenticationResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        String email = forgotPasswordRequest.getEmail();
        String token = jwtService.generateToken(email);
        this.saveUserToken(token, email);

        response.setToken(token);
        response.setMessage("Success!");
        return response;
    }

    public String resetPassword(ResetPasswordRequest request) {
        String email = jwtService.extractUsername(request.getToken());
        User user = userService.findUserByEmail(email);
        if (!jwtService.isTokenValid(request.getToken(), email)) {
            throw new IllegalArgumentException("Invalid or expired token");
        }
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as the old password");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return "Password has been reset successfully. You can log in again!";
    }

    public void updateAccount(Long userId, UpdateAccountRequest updateAccountRequest) {
        User user = userService.findById(userId);
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
            user.setPhone(updateAccountRequest.getPhone());
        }
        if (updateAccountRequest.getDateOfBirth() != null) {
            user.setDateOfBirth(updateAccountRequest.getDateOfBirth());
        }
        if (updateAccountRequest.getSex() != null) {
            user.setSex(updateAccountRequest.getSex());
        }
        if (updateAccountRequest.getAddress() != null) {
            user.setAddress(updateAccountRequest.getAddress());
        }
        user.setLastModifiedDate(Instant.now());
        user.setLastModifiedBy(userId);
        userRepository.save(user);
    }

    public void changeNewPassword(Long userId, UpdatePasswordRequest updatePasswordRequest) {
        User user = userService.findById(userId);
        if (passwordEncoder.matches(updatePasswordRequest.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as the old password");
        }
        user.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

    public void deleteAccount(Long userId) {
        User user = userService.findById(userId);

        List<Token> allTokens = tokenRepository.findAllByUser(userId);
        tokenRepository.deleteAll(allTokens);

        userRepository.delete(user);
        //TODO: set status
    }
}
