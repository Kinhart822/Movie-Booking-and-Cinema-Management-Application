package vn.edu.usth.mcma.frontend.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.edu.usth.mcma.frontend.constant.IP;
import vn.edu.usth.mcma.frontend.constant.SharedPreferencesKey;
import vn.edu.usth.mcma.frontend.dto.Request.RefreshRequest;
import vn.edu.usth.mcma.frontend.dto.response.JwtAuthenticationResponse;
import vn.edu.usth.mcma.frontend.dto.response.RefreshResponse;
import vn.edu.usth.mcma.frontend.network.apis.AuthApi;

public class AuthPrefsManager {
    private final SharedPreferences authPrefs;
    private Context context;
    private final String TAG = AuthPrefsManager.class.getName();

    public AuthPrefsManager(Context context) {
        this.context = context;
        authPrefs = context.getSharedPreferences(SharedPreferencesKey.AUTH.name(), Context.MODE_PRIVATE);
    }

    public String getEmail() {
        return authPrefs.getString(SharedPreferencesKey.AUTH_EMAIL.name(), null);
    }
    public void saveEmail(String email) {
        authPrefs.edit().putString(SharedPreferencesKey.AUTH_EMAIL.name(), email).apply();
    }
    public void removeEmail() {
        authPrefs.edit().remove(SharedPreferencesKey.AUTH_EMAIL.name()).apply();
    }

    public String getAccessToken() {
        return authPrefs.getString(SharedPreferencesKey.AUTH_ACCESS_TOKEN.name(), null);
    }
    public void saveAccessToken(String accessToken) {
        authPrefs.edit().putString(SharedPreferencesKey.AUTH_ACCESS_TOKEN.name(), accessToken).apply();
    }
    public void removeAccessToken() {
        authPrefs.edit().remove(SharedPreferencesKey.AUTH_ACCESS_TOKEN.name()).apply();
    }

    public String getRefreshToken() {
        return authPrefs.getString(SharedPreferencesKey.AUTH_REFRESH_TOKEN.name(), null);
    }
    public void saveRefreshToken(String refreshToken) {
        authPrefs.edit().putString(SharedPreferencesKey.AUTH_REFRESH_TOKEN.name(), refreshToken).apply();
    }
    public void removeRefreshToken() {
        authPrefs.edit().remove(SharedPreferencesKey.AUTH_REFRESH_TOKEN.name()).apply();
    }

    public boolean isLoggedIn() {
        return authPrefs.getBoolean(SharedPreferencesKey.AUTH_IS_LOGGED_IN.name(), false);
    }
    public void saveIsLoggedIn(boolean isLoggedIn) {
        authPrefs.edit().putBoolean(SharedPreferencesKey.AUTH_IS_LOGGED_IN.name(), isLoggedIn).apply();
    }

//    public void refreshAccessToken() {
//        Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl(IP.MINOXD_LAPTOP.getIp())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();
//        AuthApi authApi = retrofit.create(AuthApi.class);
//        authApi
//                .refresh(RefreshRequest.builder()
//                        .email(getEmail())
//                        .refreshToken(getRefreshToken())
//                        .build())
//                .enqueue(new Callback<>() {
//                    @Override
//                    public void onResponse(@NonNull Call<RefreshResponse> call, @NonNull Response<RefreshResponse> response) {
//                        if (response.code() == 200) {
//                            assert response.body() != null;
//                            saveAccessToken(response.body().getAccessToken());
//                            Log.d(TAG, "refreshAccessToken: onResponse: "+response.body().getAccessToken());
//                        }
//                        if (response.code() == 401) {
//                            saveAccessToken(null);
//                            saveRefreshToken(null);
//                            saveIsLoggedIn(false);
//                            Log.d(TAG, "refreshAccessToken: onResponse: "+response.body());
//                        }
//                    }
//                    @Override
//                    public void onFailure(@NonNull Call<RefreshResponse> call, @NonNull Throwable throwable) {
//                        Toast.makeText(context, "Send request to refresh failed: ", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
}
