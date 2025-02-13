package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import constants.CommonStatus;
import constants.UserType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.domain.Otp;
import vn.edu.usth.mcma.backend.dto.account.*;
import vn.edu.usth.mcma.backend.dto.unsorted.AdminPresentation;
import vn.edu.usth.mcma.backend.dto.unsorted.UserPresentation;
import vn.edu.usth.mcma.backend.entity.Notification;
import vn.edu.usth.mcma.backend.entity.User;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.NotificationRepository;
import vn.edu.usth.mcma.backend.repository.UserRepository;
import vn.edu.usth.mcma.backend.security.JwtHelper;

import java.time.Instant;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class AccountService {
    private final UserRepository userRepository;
    private final JwtHelper jwtHelper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final EmailService emailService;
    private static final String EMAIL_EXISTENCE_KEY = "emailExisted";
    private static final String OTP_DUE_DATE_KEY = "otpDueDate";
    private static final String VALID_KEY = "isValid";
    private static final RandomStringGenerator numericGenerator = new RandomStringGenerator.Builder().withinRange('0', '9').get();
    private static final Map<String, Otp> sessionOtpMap = new HashMap<>();
    private final NotificationRepository notificationRepository;

    public ApiResponse createAdmin(CreateAdmin request) {
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
        return ApiResponse.ok();
    }

    public Map<String, Boolean> signUpCheckEmailExistence(String query) {
        Map<String, Boolean> response = new HashMap<>();
        response.put(EMAIL_EXISTENCE_KEY, userRepository.existsByEmailIgnoreCase(query));
        return response;
    }
    public Map<String, Instant> signUpBegin(SendOtpRequest request) {
        return sendOtpToEmail(request);
    }
    public Map<String, Boolean> signUpCheckOtp(CheckOtpRequest request) {
        return checkOtp(request);
    }
    public ApiResponse signUpFinish(SignUp request) {
        String sessionId = request.getSessionId();
        if (!sessionOtpMap.containsKey(sessionId)) {
            throw new BusinessException(ApiResponseCode.SESSION_ID_NOT_FOUND);
        }
        Instant now = Instant.now();
        userRepository
                .save(User.builder()
                        .email(sessionOtpMap.get(sessionId).getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .roles(List.of(UserType.USER.name()))
                        .status(CommonStatus.ACTIVE.getStatus())
                        .createdDate(now)
                        .lastModifiedDate(now)
                        .build());
        sessionOtpMap.remove(request.getSessionId());
        return ApiResponse.ok();
    }

    private Map<String, Instant> sendOtpToEmail(SendOtpRequest request) {
        String email = request.getEmail();
        String sessionId = request.getSessionId();
        sessionOtpMap.computeIfAbsent(sessionId, o -> Otp.builder()
                .email(email)
                .otp(numericGenerator.generate(6))
                .dueDate(Instant.now().plusSeconds(60))
                .build());
        Otp otp = sessionOtpMap.get(sessionId);
        emailService.sendEmailVerificationOtp(otp);
        Map<String, Instant> response = new HashMap<>();
        response.put(OTP_DUE_DATE_KEY, otp.getDueDate());
        return response;
    }
    private Map<String, Boolean> checkOtp(CheckOtpRequest request) {
        String sessionId = request.getSessionId();
        if (!sessionOtpMap.containsKey(sessionId)) {
            throw new BusinessException(ApiResponseCode.SESSION_ID_NOT_FOUND);
        }
        Otp otp = sessionOtpMap.get(sessionId);
        Map<String, Boolean> response = new HashMap<>();
        response.put(VALID_KEY, Objects.equals(otp.getOtp(), request.getOtp()) && otp.getDueDate().isAfter(Instant.now()));
        return response;
    }

    public Map<String, Instant> forgotPasswordBegin(SendOtpRequest request) {
        return sendOtpToEmail(request);
    }
    public Map<String, Boolean> forgotPasswordCheckOtp(CheckOtpRequest request) {
        return checkOtp(request);
    }
    public ApiResponse forgotPasswordFinish(ForgotPassword request) {
        String sessionId = request.getSessionId();
        if (!sessionOtpMap.containsKey(sessionId)) {
            throw new BusinessException(ApiResponseCode.SESSION_ID_NOT_FOUND);
        }
        userRepository
                .save(((User) userService
                        .loadUserByUsername(sessionOtpMap.get(sessionId).getEmail()))
                        .toBuilder()
                        .password(passwordEncoder.encode(request.getPassword()))
                        .lastModifiedDate(Instant.now())
                        .build());
        sessionOtpMap.remove(sessionId);
        return ApiResponse.ok();
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
        return ApiResponse.ok();
    }
    public Map<String, Boolean> resetPasswordCheck(ResetPasswordCheck check) {
        String resetKey = check.getResetKey();

        Optional<User> user = userService.resetPasswordCheck(resetKey);
        Map<String, Boolean> response = new HashMap<>();
        response.put(VALID_KEY, user.isPresent());
        return response;
    }
    public ApiResponse resetPasswordFinish(ResetPasswordFinish finish) {
        Optional<User> user = userService.resetPasswordFinish(finish.getResetKey(), passwordEncoder.encode(finish.getNewPassword()));
        if (user.isEmpty()) {
            throw new BusinessException(ApiResponseCode.INVALID_RESET_KEY);
        }
        return ApiResponse.ok();
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
        return ApiResponse.ok();
    }

    public List<Notification> findAllNotifications() {
        Long userId = jwtHelper.getIdUserRequesting();
        return notificationRepository.findAllByUser(userRepository
                .findById(userId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND.setDescription(String.format("User not found with id: %d", userId)))));
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
