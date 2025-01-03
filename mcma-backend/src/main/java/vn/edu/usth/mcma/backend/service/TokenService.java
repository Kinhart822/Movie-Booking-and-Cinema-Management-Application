package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.entity.Token;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.TokenRepository;

@Service
@Transactional
@AllArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    public Token checkTokenExistenceByRequest(HttpServletRequest hsRequest) {
        String authHeader = hsRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BusinessException(ApiResponseCode.INVALID_HTTP_REQUEST);
        }
        String value = authHeader.substring(7);
        return tokenRepository.findByValue(value).orElse(null);
    }
}
