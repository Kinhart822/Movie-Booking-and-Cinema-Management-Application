package vn.edu.usth.mcma.backend.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.security.JwtUtil;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<Long> {

    private final JwtUtil jwtUtil;

    public AuditorAwareImpl(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @NotNull
    @Override
    public Optional<Long> getCurrentAuditor() {
        try {
            return Optional.of(jwtUtil.getUserIdFromToken());
        } catch (BusinessException e) {
            return Optional.of(0L);
        }
    }
}
