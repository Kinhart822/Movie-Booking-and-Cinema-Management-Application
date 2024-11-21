package vn.edu.usth.mcma.backend.service;

import constants.UserType;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.dao.User;
import vn.edu.usth.mcma.backend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService extends AbstractService<User, Long> {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    public UserDetailsService userDetailsService() {
        return email -> {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(UserType.values()[user.getUserType()].name()));
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    authorities);
        };
    }
}
