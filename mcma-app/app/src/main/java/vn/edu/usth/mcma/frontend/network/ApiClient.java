package vn.edu.usth.mcma.frontend.network;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import vn.edu.usth.mcma.frontend.component.main.MainActivity;
import vn.edu.usth.mcma.frontend.constant.IP;

public class ApiClient {
    private static final String BASE_URL = IP.MINOXD_ZEROTIER.getIp();
    private static Retrofit authenticatedRetrofit;
    private static Retrofit unauthenticatedRetrofit;
    public static Retrofit getAuthenticatedClient(Context context) {
        if (authenticatedRetrofit == null) {
            AuthPrefsManager authPrefsManager = new AuthPrefsManager(context);
            OkHttpClient client = new OkHttpClient.Builder()
                    .authenticator(getCustomAuthenticator(context, authPrefsManager))
                    .addInterceptor(new AuthInterceptor(authPrefsManager))
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .callTimeout(60, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();
            authenticatedRetrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return authenticatedRetrofit;
    }
    @NonNull
    private static CustomAuthenticator getCustomAuthenticator(Context context, AuthPrefsManager authPrefsManager) {
        CustomAuthenticator authenticator = new CustomAuthenticator(authPrefsManager);
        authenticator.setCallback(() -> {
            Handler handler = new Handler(Looper.getMainLooper());
            handler
                    .post(() -> Toast
                            .makeText(context, "Session expired. Please sign in again.", Toast.LENGTH_SHORT)
                            .show());
            handler
                    .postDelayed(() -> {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }, 2000);

        });
        return authenticator;
    }

    public static Retrofit getUnauthenticatedClient() {
        if (unauthenticatedRetrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
            unauthenticatedRetrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return unauthenticatedRetrofit;
    }
}
