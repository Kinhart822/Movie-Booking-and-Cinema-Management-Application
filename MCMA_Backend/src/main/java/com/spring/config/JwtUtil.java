package com.spring.config;

import com.spring.entities.Token;
import com.spring.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    @Autowired
    private TokenRepository tokenRepository;

    public Integer getUserIdFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is missing or invalid.");
        }

        String token = authHeader.substring(7);

        // Get stored token from database
        Token storedToken = tokenRepository.findByToken(token).orElse(null);

        if (storedToken != null) {
            return storedToken.getUser().getId();
        } else {
            throw new IllegalArgumentException("Token is missing, invalid, or expired.");
        }
    }
}
