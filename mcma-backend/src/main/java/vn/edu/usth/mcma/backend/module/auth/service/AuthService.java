package vn.edu.usth.mcma.backend.module.auth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.usth.mcma.backend.common.constant.ApiResponseCode;
import vn.edu.usth.mcma.backend.common.dto.ApiResponse;
import vn.edu.usth.mcma.backend.common.enums.CommonStatus;
import vn.edu.usth.mcma.backend.common.exception.BusinessException;
import vn.edu.usth.mcma.backend.module.auth.dto.request.RefreshRequest;
import vn.edu.usth.mcma.backend.module.auth.dto.request.SignInRequest;
import vn.edu.usth.mcma.backend.module.auth.entity.RefreshToken;
import vn.edu.usth.mcma.backend.module.auth.entity.User;
import vn.edu.usth.mcma.backend.module.auth.repository.RefreshTokenRepository;
import vn.edu.usth.mcma.backend.security.jwt.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthService {
    private static final String ACCESS_TOKEN_KEY = "accessToken";
    private static final String REFRESH_TOKEN_KEY = "refreshToken";
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    // todo: check blacklist sign-in AND refresh
    public Map<String, String> signIn(SignInRequest signInRequest) {
        String username = signInRequest.getEmail();
        UserDetails userDetails = userService.loadUserByUsername(username);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, signInRequest.getPassword()));
        } catch (BadCredentialsException e) {
            // wrong password
            throw new BusinessException(ApiResponseCode.BAD_CREDENTIALS);
        } catch (Exception e) {
            throw new BusinessException(ApiResponseCode.BAD_REQUEST);
        }
        User user = (User) userDetails;
        Map<String, String> response = new HashMap<>();
        response.put(ACCESS_TOKEN_KEY, jwtUtil.generateAccessToken(userDetails));
        if (refreshTokenRepository.existsByUserAndStatus(user, CommonStatus.ACTIVE.getStatus())) {
            response.put(REFRESH_TOKEN_KEY, null);
            return response;
        }
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        RefreshToken tokenToSave =
                RefreshToken.builder()
                        .refreshToken(refreshToken)
                        .status(CommonStatus.ACTIVE.getStatus())
                        .user(user)
                        .createdDate(jwtUtil.extractIssuedDate(refreshToken))
                        .expirationDate(jwtUtil.extractExpirationDate(refreshToken))
                        .build();
        refreshTokenRepository.save(tokenToSave);
        response.put(REFRESH_TOKEN_KEY, refreshToken);
        return response;
    }

    @Transactional(noRollbackFor = {BusinessException.class, ExpiredJwtException.class})
    public Map<String, String> refresh(RefreshRequest refreshRequest) {
        String refreshToken = refreshRequest.getRefreshToken();
        UserDetails userDetails = userService.loadUserByUsername(refreshRequest.getEmail());
        try {
            if (!Objects.equals(jwtUtil.extractUsername(refreshToken), refreshRequest.getEmail())) {
                throw new BusinessException(ApiResponseCode.INVALID_REFRESH_REQUEST_USERNAME);
            }
        } catch (ExpiredJwtException e) {
            List<RefreshToken> tokensToUpdate =
                    refreshTokenRepository
                            .findAllByUserAndStatus(
                                    (User) userDetails, CommonStatus.ACTIVE.getStatus())
                            .stream()
                            .<RefreshToken>map(
                                    r ->
                                            r.toBuilder()
                                                    .status(CommonStatus.DELETED.getStatus())
                                                    .build())
                            .toList();
            refreshTokenRepository.saveAll(tokensToUpdate);
            throw new BusinessException(ApiResponseCode.INVALID_REFRESH_REQUEST_EXPIRED);
        }
        Map<String, String> response = new HashMap<>();
        response.put(ACCESS_TOKEN_KEY, jwtUtil.generateAccessToken(userDetails));
        return response;
    }

    public ApiResponse signOut(String accessToken) {
        List<RefreshToken> tokensToUpdate =
                refreshTokenRepository
                        .findAllByUserAndStatus(
                                (User)
                                        userService.loadUserByUsername(
                                                jwtUtil.extractUsername(accessToken)),
                                CommonStatus.ACTIVE.getStatus())
                        .stream()
                        .<RefreshToken>map(
                                r -> r.toBuilder().status(CommonStatus.DELETED.getStatus()).build())
                        .toList();
        refreshTokenRepository.saveAll(tokensToUpdate);
        return ApiResponse.ok();
    }
}
