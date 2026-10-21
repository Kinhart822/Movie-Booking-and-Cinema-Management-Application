package vn.edu.usth.mcma.backend.common.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import vn.edu.usth.mcma.backend.common.exception.BusinessException;
import vn.edu.usth.mcma.backend.security.jwt.JwtHelper;

@Component
public class AuditorAwareImpl implements AuditorAware<Long> {

    private final JwtHelper jwtUtil;

    public AuditorAwareImpl(JwtHelper jwtHelper) {
        this.jwtUtil = jwtHelper;
    }

    @Override
    public Optional<Long> getCurrentAuditor() {
        try {
            return Optional.ofNullable(jwtUtil.getIdUserRequesting());
        } catch (BusinessException e) {
            return Optional.empty();
        }
    }
}
