package vn.edu.usth.mcma.backend.service;

import constants.CommonStatus;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.entity.User;
import vn.edu.usth.mcma.backend.repository.UserRepository;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import static vn.edu.usth.mcma.backend.config.AppConfig.dotenv;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    private static final int resetKeyTimeout = Integer.parseInt(Objects.requireNonNull(dotenv().get("RESET_KEY_TIMEOUT")));
    private static final RandomStringGenerator numericGenerator = new RandomStringGenerator.Builder().withinRange('0', '9').get();
    private final UserRepository userRepository;
    private final UserDetailsCustomService userDetailsCustomService;

    public UserDetailsService getUserDetailsCustomService() {
        return userDetailsCustomService;
    }
    public UserDetails loadUserByUsername(String username) {
        return this.userDetailsCustomService.loadUserByUsername(username);
    }

    public Optional<User> resetPasswordRequest(String email) {
        String resetKey = numericGenerator.generate(6);
        while (userRepository.existsByResetKey(resetKey)) {
            resetKey = numericGenerator.generate(6);
        }
        String finalResetKey = resetKey;
        return userRepository
                .findByEmailIgnoreCaseAndStatus(email, CommonStatus.ACTIVE.getStatus())
                .map(u -> u.toBuilder()
                        .resetKey(finalResetKey)
                        .resetDueDate(Instant.now().plusSeconds(resetKeyTimeout))
                        .build());
    }
    public Optional<User> resetPasswordCheck(String resetKey) {
        return userRepository
                .findByResetKeyAndStatusAndResetDueDateIsAfter(resetKey, CommonStatus.ACTIVE.getStatus(), Instant.now());
    }
    public Optional<User> resetPasswordFinish(String resetKey, String encodedPassword) {
        return userRepository //todo: dont check for due
                .findByResetKeyAndStatusAndResetDueDateIsAfter(resetKey, CommonStatus.ACTIVE.getStatus(), Instant.now())
                .map (u -> u
                        .toBuilder()
                        .password(encodedPassword)
                        .resetKey(null)
                        .resetDueDate(null)
                        .build());
    }
}
