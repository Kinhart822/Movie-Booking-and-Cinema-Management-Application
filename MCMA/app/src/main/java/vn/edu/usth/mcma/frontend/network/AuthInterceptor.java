package vn.edu.usth.mcma.frontend.network;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final AuthPrefsManager authPrefsManager;

    public AuthInterceptor(AuthPrefsManager authPrefsManager) {
        this.authPrefsManager = authPrefsManager;
    }
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        String accessToken = authPrefsManager.getAccessToken();
        Request originalRequest = chain.request();
        if (accessToken == null) {
            return chain.proceed(originalRequest);
        }
        Request newRequest = originalRequest
                .newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .build();
        return chain.proceed(newRequest);
    }
}
