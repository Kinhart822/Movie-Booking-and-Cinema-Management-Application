package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import constants.CommonStatus;
import constants.UserType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.entity.Token;
import vn.edu.usth.mcma.backend.entity.User;
import vn.edu.usth.mcma.backend.dto.*;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.TokenRepository;
import vn.edu.usth.mcma.backend.repository.UserRepository;
import vn.edu.usth.mcma.backend.security.JwtService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final UserService userService;

    private void saveUserToken(String value, String email) {
        Token token = new Token();
        token.setValue(value);
        token.setLoggedOut(false);
        token.setUser(userService.findUserByEmail(email));
        tokenRepository.save(token);
    }

    private void revokeAllTokenByEmail(String email) {
        List<Token> validTokensByUser = tokenRepository.findAllLoggedInByEmail(email);
        if (!validTokensByUser.isEmpty()) {
            validTokensByUser.forEach(t -> t.setLoggedOut(true));
            tokenRepository.saveAll(validTokensByUser);
        }
    }

    public ApiResponse signUp(SignUpRequest signUpRequest) {
        Integer type = signUpRequest.getType();
        if (type != UserType.ADMIN.getCode() && type != UserType.USER.getCode()) {
            throw new BusinessException(ApiResponseCode.INVALID_TYPE);
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
        user.setStatus(CommonStatus.ACTIVE.getStatus());
        Instant now = Instant.now();
        user.setCreatedDate(now);
        user.setLastModifiedDate(now);
        user = userRepository.save(user);
        email = user.getEmail();//email of the saved entity, not from request
        String token = jwtService.generateToken(email);
        saveUserToken(token, email);
        return ApiResponse.success();
    }

    public JwtAuthResponse signIn(SignInRequest signInRequest) {
        String email = signInRequest.getEmail();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    email,
                    signInRequest.getPassword()
            ));
        } catch (BadCredentialsException e){
            throw new BusinessException(ApiResponseCode.BAD_CREDENTIALS);
        } catch (Exception e) {
            throw new BusinessException(ApiResponseCode.BAD_REQUEST);
        }
        List<Token> loggedOutTokens = tokenRepository.findAllLoggedOutByEmail(email);
        if (!loggedOutTokens.isEmpty()) {
            tokenRepository.deleteAll(loggedOutTokens);
        }
        String token = jwtService.generateToken(email);
        revokeAllTokenByEmail(email);
        saveUserToken(token, email);
        //todo
        JwtAuthResponse response = new JwtAuthResponse();
        response.setAccessToken(token);
        response.setUserId(userService.findUserByEmail(email).getId());
        return response;
    }

    public JwtAuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String email = jwtService.extractUsername(refreshTokenRequest.getToken());
        if (!jwtService.isTokenValid(refreshTokenRequest.getToken(), email)) {
            throw new BusinessException(ApiResponseCode.INVALID_TOKEN);
        }
        String token = jwtService.generateToken(email);
        revokeAllTokenByEmail(email);
        saveUserToken(token, email);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setAccessToken(token);
        response.setUserId(userService.findUserByEmail(email).getId());
        return response;
    }

//    public JwtAuthResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
//        JwtAuthResponse response = new JwtAuthResponse();
//        String email = forgotPasswordRequest.getEmail();
//        String token = jwtService.generateToken(email);
//        this.saveUserToken(token, email);
//
//        response.setAccessToken(token);
//        response.setMessage("Success!");
//        return response;
//    }

    public ApiResponse resetPasswordRequest(ResetPasswordRequest request) {
        Integer type = request.getType();
        if (type != UserType.ADMIN.getCode() && type != UserType.USER.getCode()) {
            throw new BusinessException(ApiResponseCode.INVALID_TYPE);
        }
        Optional<User> user = userService.resetPasswordRequest(request.getEmail(), type);
        if (user.isEmpty()) {
            throw new BusinessException(ApiResponseCode.EMAIL_NOT_FOUND);
        }
        emailService.sendResetPasswordMail(user.get());
        return ApiResponse.success();
    }
    public Map<String, Boolean> resetPasswordCheck(ResetPasswordCheck check) {
        Integer type = check.getType();
        if (type != UserType.ADMIN.getCode() && type != UserType.USER.getCode()) {
            throw new BusinessException(ApiResponseCode.INVALID_TYPE);
        }
        Map<String, Boolean> response = new HashMap<>();
        Optional<User> user = userService.resetPasswordCheck(check.getResetKey(), type);
        response.put("isValid", user.isPresent());
        return response;
    }
    public ApiResponse resetPasswordFinish(ResetPasswordFinish finish) {
        Integer type = finish.getType();
        if (type != UserType.ADMIN.getCode() && type != UserType.USER.getCode()) {
            throw new BusinessException(ApiResponseCode.INVALID_TYPE);
        }
        Optional<User> user = userService.resetPasswordFinish(finish.getResetKey(), type, passwordEncoder.encode(finish.getNewPassword()));
        if (user.isEmpty()) {
            throw new BusinessException(ApiResponseCode.RESET_KEY_NOT_FOUND);
        }
        return ApiResponse.success();
    }

    public void updateAccount(Long userId, UpdateAccountRequest updateAccountRequest) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
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
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        if (passwordEncoder.matches(updatePasswordRequest.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as the old password");
        }
        user.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

    public void deleteAccount(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        List<Token> allTokens = tokenRepository.findAllByUser(userId);
        tokenRepository.deleteAll(allTokens);

        userRepository.delete(user);
        //TODO: set status
    }
}
