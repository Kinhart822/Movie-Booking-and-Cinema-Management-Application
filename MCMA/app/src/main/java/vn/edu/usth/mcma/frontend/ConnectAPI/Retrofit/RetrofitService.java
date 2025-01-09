package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;


import java.util.concurrent.TimeUnit;

import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import vn.edu.usth.mcma.frontend.ConnectAPI.Enum.IP;

@Getter
public class RetrofitService {
    private static final String BASE_URL = IP.MINOXD_PX6A.getIp();
    private final Retrofit retrofit;
    public RetrofitService(Context context) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        SharedPreferences sharedPreferences = context.getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("auth_token", "");
        okHttpClient.addInterceptor(loggingInterceptor);
        if (!token.isEmpty()) {
            okHttpClient
                    .addInterceptor(chain -> chain.proceed(chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build()));
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }
}
