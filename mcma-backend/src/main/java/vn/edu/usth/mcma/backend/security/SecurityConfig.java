package vn.edu.usth.mcma.backend.security;

import constants.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import vn.edu.usth.mcma.backend.service.UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsCustomizer())
                .csrf(csrfCustomizer())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(//todo: separate auth/account && clean up && see if swagger need permit && create admin need auth, user dont need
                                ApiEndpoints.PERMITTED.getApis()).permitAll()
                        .requestMatchers("/api/v1/admin/**").hasAuthority(UserType.ADMIN.name())
                        .requestMatchers("/api/v1/user/**").hasAuthority(UserType.USER.name())
                        .anyRequest().authenticated()
                )
                // after login, only need jwt to authenticate subsequence requests
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }

    // Customize CORS configuration
    private Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer() {
        return (cors) -> cors
                .configurationSource(request -> {
                    var corsConfig = new CorsConfiguration();
                    corsConfig.addAllowedOrigin("http://localhost:3000"); // Frontend origin
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
        authenticationProvider.setUserDetailsService(userService.getUserDetailsCustomService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // used in auth service: sign in
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
