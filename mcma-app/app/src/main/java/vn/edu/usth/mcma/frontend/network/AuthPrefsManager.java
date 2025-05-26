package vn.edu.usth.mcma.frontend.network;

import android.content.Context;
import android.content.SharedPreferences;

import vn.edu.usth.mcma.frontend.constant.SharedPreferencesKey;

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

    /**
     * used when sign out
     */
    public void removeAll() {
        removeEmail();
        removeAccessToken();
        removeRefreshToken();
        saveIsLoggedIn(false);
    }
}
