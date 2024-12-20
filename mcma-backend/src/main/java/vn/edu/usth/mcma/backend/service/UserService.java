package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import constants.CommonStatus;
import constants.UserType;
import jakarta.transaction.Transactional;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.entity.User;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.UserRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static vn.edu.usth.mcma.backend.config.AppConfig.dotenv;

@Service
@Transactional
public class UserService extends AbstractService<User, Long> {
    private static final int resetKeyTimeout = Integer.parseInt(Objects.requireNonNull(dotenv().get("RESET_KEY_TIMEOUT")));
    private final RandomStringGenerator numericGenerator = new RandomStringGenerator.Builder().withinRange('0', '9').get();
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
    }
    public boolean checkEmailExistence(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }
    public User findUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElseThrow(
                () -> new BusinessException(ApiResponseCode.EMAIL_NOT_FOUND));
    }
    public UserDetails makeUserDetailsByEmail(String email) {
        return this.getUserDetailsService().loadUserByUsername(email);
    }
    public UserDetailsService getUserDetailsService() {
        return this.userDetailsService();
    }
    private UserDetailsService userDetailsService() {
        return email -> {
            User user = userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new BusinessException(ApiResponseCode.EMAIL_NOT_FOUND));
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(UserType.values()[user.getUserType()].name()));
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    authorities);
        };
    }

    public Optional<User> resetPasswordRequest(String email, Integer type) {
        return userRepository
                .findOneByEmailIgnoreCaseAndUserType(email, type)
                .filter(u -> u.getStatus().equals(CommonStatus.ACTIVE.getStatus()))
                .map(u -> {
                    u.setResetKey(numericGenerator.generate(6));
                    u.setResetDate(Instant.now());
                    return u;
                });
    }

    public Optional<User> resetPasswordCheck(String resetKey, Integer type) {
        return userRepository
                .findOneByResetKeyAndUserType(resetKey, type)
                .filter(u -> u.getResetDate().isAfter(Instant.now().minusSeconds(resetKeyTimeout)));
    }

    public Optional<User> resetPasswordFinish(String resetKey, Integer type, String encodedPassword) {
        return userRepository
                .findOneByResetKeyAndUserType(resetKey, type)
                .filter(u -> u.getResetDate().isAfter(Instant.now().minusSeconds(resetKeyTimeout)))
                .map (u -> {
                    u.setPassword(encodedPassword);
                    u.setResetKey(null);
                    u.setResetDate(null);
                    return u;
                });
    }
}
