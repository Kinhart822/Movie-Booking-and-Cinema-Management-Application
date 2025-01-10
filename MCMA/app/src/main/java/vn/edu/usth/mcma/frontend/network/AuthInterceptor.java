package vn.edu.usth.mcma.frontend.network;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final TokenManager tokenManager;

    public AuthInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        String token = tokenManager.getToken();
        Request originalRequest = chain.request();
        if (token == null) {
            return chain.proceed(originalRequest);
        }
        Request newRequest = originalRequest
                .newBuilder()
                .header("Authorization", "Bearer " + token)
                .build();
        return chain.proceed(newRequest);
    }
}
