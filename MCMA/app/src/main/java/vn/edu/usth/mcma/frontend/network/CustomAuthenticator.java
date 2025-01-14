package vn.edu.usth.mcma.frontend.network;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * triggered when server responds with 401 Unauthorized
 */
@RequiredArgsConstructor
public class CustomAuthenticator implements Authenticator {
    private final AuthPrefsManager authPrefsManager;
    private final String TAG = CustomAuthenticator.class.getName();
    @Setter
    private TokenRefreshCallback callback;
    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NonNull Response response) {

        if (authPrefsManager.getRefreshToken() != null) {
            return null;
        }
        if (response.code() != 401) {
            return null;
        }
        synchronized (this) {
            authPrefsManager.refreshAccessToken();
            String newAccessToken = authPrefsManager.getAccessToken();
            Log.d(TAG, "authenticate: newAccessToken: "+newAccessToken);
            if (newAccessToken != null) {
                return response.request().newBuilder()
                        .header("Authorization", "Bearer " + newAccessToken)
                        .build();
            }
            assert callback != null;
            callback.onRefreshFailed();
            return null;
        }
    }
}
