package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.entity.Token;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.TokenRepository;

@Service
@Transactional
public class TokenService extends AbstractService<Token, Long> {
    private final TokenRepository tokenRepository;
    public TokenService(TokenRepository tokenRepository) {
        super(tokenRepository);
        this.tokenRepository = tokenRepository;
    }
    public Token checkTokenExistenceByRequest(HttpServletRequest hsRequest) {
        String authHeader = hsRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BusinessException(ApiResponseCode.INVALID_HTTP_REQUEST);
        }
        String value = authHeader.substring(7);
        return tokenRepository.findByValue(value).orElse(null);
    }
}
