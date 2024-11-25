package vn.edu.usth.mcma.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.repository.TokenRepository;
import vn.edu.usth.mcma.backend.service.UserService;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

import static vn.edu.usth.mcma.backend.config.AppConfig.dotenv;

@Service
@RequiredArgsConstructor
@Transactional
public class JwtService {
    private final UserService userService;
    private final String JWT_SECRET_KEY = dotenv().get("JWT_SECRET_KEY");
    private static final long JWT_EXPIRATION_TIME = Long.parseLong(Objects.requireNonNull(dotenv().get("JWT_EXPIRATION_TIME")));

    private final TokenRepository tokenRepository;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(String email) {
        return buildToken(email);
    }

    private String buildToken(String email) {
        UserDetails userDetails = userService.makeUserDetailsByEmail(email);
        return Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String value, String email) {
        UserDetails userDetails = userService.makeUserDetailsByEmail(email);
        final String username = extractUsername(value);
        boolean isValidToken = tokenRepository.findByValue(value).map(t -> !t.isLoggedOut()).orElse(false);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(value) && isValidToken;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
