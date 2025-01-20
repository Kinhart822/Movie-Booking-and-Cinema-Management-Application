package vn.edu.usth.mcma.backend.security;

import lombok.Getter;

@Getter
public enum ApiEndpoints {
    PERMITTED(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api/v1/auth/sign-in",
            "/api/v1/auth/refresh",
            "/api/v1/account/admin/create",
            "/api/v1/account/user/sign-up/**",
            "/api/v1/account/user/forgot-password/**",
            "/api/v1/user/search-movie-by-name",
            "/api/v1/user/search-movie-by-genre",
            "/api/v1/user/search-movie-by-movie-genre-name",
            "/api/v1/user/booking/**"
//            "/api/v1/user/view/**"
    ),;
    private final String[] apis;
    ApiEndpoints(String... apis) {
        this.apis = apis;
    }
}
