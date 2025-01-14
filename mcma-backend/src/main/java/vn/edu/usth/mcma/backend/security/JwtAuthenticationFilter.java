package vn.edu.usth.mcma.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.edu.usth.mcma.backend.service.UserService;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest hsRequest, @NotNull HttpServletResponse hsResponse, @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        if (Arrays
                .stream(ApiEndpoints.PERMITTED.getApis())
                .toList()
                .contains(hsRequest.getRequestURI())) {
            filterChain.doFilter(hsRequest, hsResponse);
            return;
        }
        String authHeader = hsRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            unauthorizedRequest(hsResponse);
            return;
        }
        String accessToken = authHeader.substring(7);
        String username = jwtUtil.extractUsername(accessToken);
        if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            unauthorizedRequest(hsResponse);
            return;
        }
        UserDetails userDetails = userService.loadUserByUsername(username);
        if (!jwtUtil.isAccessTokenValid(accessToken, userDetails)) {
            unauthorizedRequest(hsResponse);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(hsRequest));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(hsRequest, hsResponse);
    }
    private void unauthorizedRequest(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized: Invalid or missing token");
        response.getWriter().flush();
    }
}
