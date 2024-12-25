package vn.edu.usth.mcma.backend.security;

import constants.ApiResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import vn.edu.usth.mcma.backend.entity.Token;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.service.TokenService;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final TokenService tokenService;
    /**
     * Extracts the user ID from the Bearer token in the current HTTP request's Authorization header.
     *
     * @return the user ID associated with the token
     * @throws BusinessException
     * BusinessException if the token is missing, invalid, or expired
     */
    public Long getUserIdFromToken() {
        HttpServletRequest request = getCurrentHttpRequest();
        if (request == null) {
            throw new BusinessException(ApiResponseCode.INVALID_HTTP_REQUEST);
        }
        Token storedToken = tokenService.checkTokenExistenceByRequest(request);
        if (storedToken == null) {
            throw new BusinessException(ApiResponseCode.INVALID_TOKEN);
        }
        return storedToken.getUserId();
    }

    /**
     * Retrieves the current HttpServletRequest from the RequestContextHolder.
     *
     * @return the current HttpServletRequest
     */
    private HttpServletRequest getCurrentHttpRequest() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return (attributes != null) ? attributes.getRequest() : null;
    }
}
