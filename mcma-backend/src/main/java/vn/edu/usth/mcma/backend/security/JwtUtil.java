package vn.edu.usth.mcma.backend.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.edu.usth.mcma.backend.entity.Token;
import vn.edu.usth.mcma.backend.service.TokenService;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final TokenService tokenService;

    public Long getUserIdFromToken(HttpServletRequest hsRequest) {
        Token storedToken = tokenService.checkTokenExistenceByRequest(hsRequest);
        if (storedToken != null) {
            return storedToken.getUserId();
        } else {
            throw new IllegalArgumentException("Token is missing, invalid, or expired.");
        }
    }
}
