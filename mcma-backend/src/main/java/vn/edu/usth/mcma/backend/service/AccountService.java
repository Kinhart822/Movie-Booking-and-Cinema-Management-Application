package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import constants.CommonStatus;
import constants.UserType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.dto.*;
import vn.edu.usth.mcma.backend.dto.account.AccountCreateRequest;
import vn.edu.usth.mcma.backend.dto.account.AccountUpdateRequest;
import vn.edu.usth.mcma.backend.entity.User;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.UserRepository;
import vn.edu.usth.mcma.backend.security.JwtHelper;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final JwtHelper jwtHelper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final EmailService emailService;

    public ApiResponse createAdmin(AccountCreateRequest request) {
        String email = request.getEmail();
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new BusinessException(ApiResponseCode.USERNAME_EXISTED);
        }
        Long userId = jwtHelper.getIdUserRequesting();
        Instant now = Instant.now();
        userRepository
                .save(User.builder()
                        .email(email)
                        .password(passwordEncoder.encode(request.getPassword()))
                        .roles(List.of(UserType.ADMIN.name()))
                        .status(CommonStatus.ACTIVE.getStatus())
                        .createdBy(userId)
                        .createdDate(now)
                        .lastModifiedBy(userId)
                        .lastModifiedDate(now)
                        .build());
        return ApiResponse.success();
    }
    public ApiResponse userSignUp(AccountCreateRequest request) {
        String email = request.getEmail();
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new BusinessException(ApiResponseCode.USERNAME_EXISTED);
        }
        Long userId = jwtHelper.getIdUserRequesting();
        Instant now = Instant.now();
        userRepository
                .save(User.builder()
                        .email(email)
                        .password(passwordEncoder.encode(request.getPassword()))
                        .roles(List.of(UserType.USER.name()))
                        .status(CommonStatus.ACTIVE.getStatus())
                        .createdBy(userId)
                        .createdDate(now)
                        .lastModifiedBy(userId)
                        .lastModifiedDate(now)
                        .build());
        return ApiResponse.success();
    }

    public AdminPresentation getAdmin() {
        User admin = userRepository
                .findById(jwtHelper.getIdUserRequesting())
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        return AdminPresentation.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .sex(admin.getSex())
                .dateOfBirth(admin.getDateOfBirth())
                .phone(admin.getPhone())
                .address(admin.getAddress())
                .status(admin.getStatus())
                .createdBy(admin.getCreatedBy())
                .lastModifiedBy(admin.getLastModifiedBy())
                .createdDate(admin.getCreatedDate())
                .lastModifiedDate(admin.getLastModifiedDate())
                .build();
    }
    public UserPresentation getUser() {
        User user = userRepository
                .findById(jwtHelper.getIdUserRequesting())
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        return UserPresentation.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .sex(user.getSex())
                .dateOfBirth(user.getDateOfBirth())
                .phone(user.getPhone())
                .address(user.getAddress())
                .createdDate(user.getCreatedDate())
                .lastModifiedDate(user.getLastModifiedDate())
                .build();
    }

    public ApiResponse resetPasswordRequest(ResetPasswordRequest request) {
        Optional<User> user = userService.resetPasswordRequest(request.getEmail());
        if (user.isEmpty()) {
            throw new BusinessException(ApiResponseCode.USERNAME_NOT_EXISTED_OR_DEACTIVATED);
        }
        emailService.sendResetPasswordMail(user.get());
        return ApiResponse.success();
    }
    public Map<String, Boolean> resetPasswordCheck(ResetPasswordCheck check) {
        Optional<User> user = userService.resetPasswordCheck(check.getResetKey());
        Map<String, Boolean> response = new HashMap<>();
        response.put("isValid", user.isPresent());
        return response;
    }
    public ApiResponse resetPasswordFinish(ResetPasswordFinish finish) {
        Optional<User> user = userService.resetPasswordFinish(finish.getResetKey(), passwordEncoder.encode(finish.getNewPassword()));
        if (user.isEmpty()) {
            throw new BusinessException(ApiResponseCode.INVALID_RESET_KEY);
        }
        return ApiResponse.success();
    }

    /**
     * use this to update account detail for both admin and user
     * @param request contains firstName, lastName, sex, dateOfBirth, phone, address
     * @return ApiResponse
     */
    public ApiResponse updateAccount(AccountUpdateRequest request) {
        Long id = jwtHelper.getIdUserRequesting();
        userRepository
                .save(userRepository
                        .findById(id)
                        .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND))
                        .toBuilder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .sex(request.getSex())
                        .dateOfBirth(request.getDateOfBirth())
                        .phone(request.getPhone())
                        .address(request.getAddress())
                        .lastModifiedBy(id)
                        .lastModifiedDate(Instant.now())
                        .build());
        return ApiResponse.success();
    }
//    public void deleteAccount(Long userId) {
//        User user = userRepository
//                .findById(userId)
//                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
//        List<Token> allTokens = tokenRepository.findAllByUser(userId);
//        tokenRepository.deleteAll(allTokens);
//
//        userRepository.delete(user);
//        //TODO: set status
//    }
}
