package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import constants.CommonStatus;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.UserRepository;

/**
 * encapsulated in user service
 */
@Service
@AllArgsConstructor
public class UserDetailsCustomService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository
                .findByEmailIgnoreCaseAndStatus(username, CommonStatus.ACTIVE.getStatus())
                .orElseThrow(() -> new BusinessException(ApiResponseCode.USERNAME_NOT_EXISTED_OR_DEACTIVATED));
    }
}
