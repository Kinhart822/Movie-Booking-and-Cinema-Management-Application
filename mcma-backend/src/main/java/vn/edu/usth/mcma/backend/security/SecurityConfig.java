package vn.edu.usth.mcma.backend.security;

import constants.UserType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import vn.edu.usth.mcma.backend.service.UserService;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;
    private final CustomLogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsCustomizer())
                .csrf(csrfCustomizer())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api/v1/auth/sign-in",
                                "/api/v1/auth/sign-up",
                                "/api/v1/auth/reset-password/**",
                                "/api/v1/user/search-movie-by-name",
                                "/api/v1/user/search-movie-by-genre",
                                "/api/v1/user/search-movie-by-movie-genre-name",
                                "/api/v1/user/booking/**",
                                "/api/v1/user/view/**").permitAll()
                        .requestMatchers("/api/v1/admin").hasAuthority(UserType.ADMIN.name())
                        .requestMatchers("/api/v1/user").hasAuthority(UserType.USER.name())
                        .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                )
                .logout(l -> l.logoutUrl("/api/v1/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(this::customLogoutSuccessHandler)
                )
        ;
        return http.build();
    }

    // Customize CORS configuration
    private Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer() {
        return (cors) -> cors
                .configurationSource(request -> {
                    var corsConfig = new CorsConfiguration();
                    corsConfig.addAllowedOrigin("http://localhost:5173"); // Frontend origin
                    corsConfig.addAllowedMethod("*"); // All HTTP methods
                    corsConfig.addAllowedHeader("*"); // All headers allowed
                    corsConfig.setAllowCredentials(true); // Allow credentials
                    corsConfig.setMaxAge(1800L); // Preflight request cache duration (30 minutes)
                    return corsConfig;
                });
    }

    // Customize CSRF configuration
    private Customizer<CsrfConfigurer<HttpSecurity>> csrfCustomizer() {
        return AbstractHttpConfigurer::disable;  // Disable CSRF protection
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.getUserDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String role = authentication.getAuthorities().stream()
                    .findFirst()
                    .get()
                    .getAuthority();

            if (role.equals("USER")) {
                response.sendRedirect("/api/v1/user");
            } else if (role.equals("ADMIN")) {
                response.sendRedirect("/api/v1/admin");
            } else {
                response.sendRedirect("/signUp");
            }
        };
    }

    private void customLogoutSuccessHandler(HttpServletRequest hsRequest, HttpServletResponse response, Authentication authentication) throws IOException {
        SecurityContextHolder.clearContext();

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Create JSON response message
        String jsonResponse = "{\"message\": \"Logout successful\"}";

        // Write the response back
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
