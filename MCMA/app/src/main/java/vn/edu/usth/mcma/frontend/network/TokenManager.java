package vn.edu.usth.mcma.frontend.network;

import android.content.Context;
import android.content.SharedPreferences;

import vn.edu.usth.mcma.frontend.constant.SharedPreferencesKey;

public class TokenManager {
    private final SharedPreferences authPrefs;

    public TokenManager(Context context) {
        authPrefs = context.getSharedPreferences(SharedPreferencesKey.AUTH.name(), Context.MODE_PRIVATE);
    }
    public void saveToken(String token) {
        authPrefs.edit().putString(SharedPreferencesKey.AUTH_TOKEN.name(), token).apply();
    }
    public String getToken() {
        return authPrefs.getString(SharedPreferencesKey.AUTH_TOKEN.name(), null);
    }
    public void removeToken() {
        authPrefs.edit().remove(SharedPreferencesKey.AUTH_TOKEN.name()).apply();
    }
}
