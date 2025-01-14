package vn.edu.usth.mcma.backend.security;

import lombok.Getter;

@Getter
public enum ApiEndpoints {
    PERMITTED(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api/v1/auth/sign-in",
            "/api/v1/auth/refresh",
            "/api/v1/admin/account/create",
            "/api/v1/user/account/sign-up",
            "/api/v1/admin/account/reset-password/**",
            "/api/v1/user/account/reset-password/**",
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
