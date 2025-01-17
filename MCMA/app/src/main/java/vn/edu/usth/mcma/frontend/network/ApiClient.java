package vn.edu.usth.mcma.frontend.network;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import vn.edu.usth.mcma.frontend.MainActivity;
import vn.edu.usth.mcma.frontend.constant.IP;

public class ApiClient {
    private static final String BASE_URL = IP.MINOXD_LAPTOP.getIp();
    private static Retrofit retrofit;
    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            AuthPrefsManager authPrefsManager = new AuthPrefsManager(context);
            CustomAuthenticator authenticator = getCustomAuthenticator(context, authPrefsManager);
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient
                    .Builder()
                    .authenticator(authenticator)
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(new AuthInterceptor(authPrefsManager))
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .callTimeout(60, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
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
}
