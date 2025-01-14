package vn.edu.usth.mcma.backend.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.security.JwtHelper;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<Long> {

    private final JwtHelper jwtUtil;

    public AuditorAwareImpl(JwtHelper jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @NotNull
    @Override
    public Optional<Long> getCurrentAuditor() {
        try {
            return Optional.of(jwtUtil.getIdUserRequesting());
        } catch (BusinessException e) {
            return Optional.of(0L);
        }
    }
}
