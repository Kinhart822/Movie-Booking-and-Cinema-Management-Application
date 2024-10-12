//package com.spring.config;
//
//import com.spring.entities.Token;
//import com.spring.repository.TokenRepository;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//@Component
//public class AuthTokenInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    private TokenRepository tokenRepository;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            throw new IllegalArgumentException("Authorization header is missing or invalid.");
//        }
//
//        String token = authHeader.substring(7);
//
//        // Get stored token from database
//        Token storedToken = tokenRepository.findByToken(token).orElse(null);
//
//        if (storedToken != null) {
//            response.setHeader("Authorization", "Bearer " + storedToken);
//        } else {
//            throw new IllegalArgumentException("Token is missing, invalid, or expired.");
//        }
//
//        return true;
//    }
//}
//
