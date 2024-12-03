package vn.edu.usth.mcma.backend.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import vn.edu.usth.mcma.backend.entity.Token;
import vn.edu.usth.mcma.backend.repository.TokenRepository;
import vn.edu.usth.mcma.backend.service.TokenService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final TokenService tokenService;

    @Override
    public void logout(HttpServletRequest hsRequest, HttpServletResponse response, Authentication authentication) {
        Token storedToken = tokenService.checkTokenExistenceByRequest(hsRequest);
        try {
            if (storedToken != null) {
                storedToken.setLoggedOut(true);
                tokenRepository.save(storedToken);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Logout successful");
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token invalid");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
