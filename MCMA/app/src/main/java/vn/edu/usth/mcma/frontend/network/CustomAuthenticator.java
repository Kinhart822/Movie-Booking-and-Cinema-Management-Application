package vn.edu.usth.mcma.frontend.network;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.edu.usth.mcma.frontend.constant.IP;
import vn.edu.usth.mcma.frontend.dto.Request.RefreshRequest;
import vn.edu.usth.mcma.frontend.dto.response.RefreshResponse;
import vn.edu.usth.mcma.frontend.network.apis.AuthApi;

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
    public Request authenticate(@Nullable Route route, @NonNull Response response) throws IOException {

        if (response.code() == 401 || response.code() == 403) {
            Log.d(TAG, "authenticate: need refresh");
            synchronized (this) {
                if (authPrefsManager.getRefreshToken() == null) {
                    Log.d(TAG, "authenticate: bug: refresh token is null, navigating to sign in screen");
                    authPrefsManager.saveIsLoggedIn(false);
                    callback.onRefreshFailed();
                    return null;
                }
                Retrofit retrofit = new Retrofit.Builder()
                        .client(new OkHttpClient.Builder()
                                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                                .build())
                        .baseUrl(IP.MINOXD_LAPTOP.getIp())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                AuthApi authApi = retrofit.create(AuthApi.class);
                retrofit2.Response<RefreshResponse> refreshResponse = authApi
                        .refresh(RefreshRequest.builder()
                                .email(authPrefsManager.getEmail())
                                .refreshToken(authPrefsManager.getRefreshToken())
                                .build())
                        .execute();
                // server respond with new access token, client use it to continue with the request
                if (refreshResponse.code() == 200) {
                    assert refreshResponse.body() != null;
                    authPrefsManager.removeAccessToken();
                    String newAccessToken = refreshResponse.body().getAccessToken();
                    authPrefsManager.saveAccessToken(newAccessToken);
                    Log.d(TAG, "authenticate: accessToken is refreshed");
                    return response
                            .request().newBuilder()
                            .header("Authorization", "Bearer " + newAccessToken)
                            .build();
                }
                // server did not respond with new access token, client try to sign-in again
                if (refreshResponse.code() == 401) {
                    Log.d(TAG, "authenticate: got 401 twice in a row means that refresh token expired");
                    authPrefsManager.saveAccessToken(null);
                    authPrefsManager.saveRefreshToken(null);
                    authPrefsManager.saveIsLoggedIn(false);
                    callback.onRefreshFailed();
                }
            }
        }
        return null;
    }
}
