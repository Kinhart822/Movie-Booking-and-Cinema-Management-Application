package vn.edu.usth.mcma.frontend.Login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Request.RefreshTokenRequest;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Request.SignInRequest;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.JwtAuthenticationResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.AuthenticationApi;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;
import vn.edu.usth.mcma.frontend.MainActivity;

public class LoginFragment extends Fragment {
    private AuthenticationApi authenticationApi;
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Chỗ này tránh click lỗi sẽ nhảy sang layout khác
        view.setOnClickListener(v -> {

        });

        editTextEmail = view.findViewById(R.id.editText);
        editTextPassword = view.findViewById(R.id.editText2);
        buttonLogin = view.findViewById(R.id.login_button);
        ImageView imageView = view.findViewById(R.id.imageView);

        RetrofitService retrofitService = new RetrofitService(requireActivity());
        authenticationApi = retrofitService.getRetrofit().create(AuthenticationApi.class);

        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            // Create the SignInRequest object
            SignInRequest signInRequest = new SignInRequest();
            signInRequest.setEmail(email);
            signInRequest.setPassword(password);

            authenticationApi.signIn(signInRequest).enqueue(new Callback<JwtAuthenticationResponse>() {
                @Override
                public void onResponse(@NonNull Call<JwtAuthenticationResponse> call, @NonNull Response<JwtAuthenticationResponse> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getActivity(), "Login successful!", Toast.LENGTH_SHORT).show();
                        assert response.body() != null;
                        String token = response.body().getToken();
                        int userId = response.body().getUserId();
                        saveUserIdToPreferences(userId);

                        saveAuthToken(token);

                        // Lưu trạng thái đăng nhập và thời gian hết hạn trong SharedPreferences
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TOLogin", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply(); // Save userId persistently

                        // Tính thời gian hết hạn từ thời điểm hiện tại
                        long expirationTime = System.currentTimeMillis() + 1440000; // 1440000ms = 24 phút
                        editor.putLong("expirationTime", expirationTime);
                        editor.apply();

                        // Điều hướng
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Wrong email or password", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JwtAuthenticationResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Login failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

            // Check token expiration
            checkAndRefreshToken();
        });
        TextView create_account = view.findViewById(R.id.create_account);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Login.Register_Activity.class );
                startActivity(i);
            }
        });

        TextView forgot_password = view.findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Login.ForgotPassword_Activity.class );
                startActivity(i);
            }
        });

        return view;
    }

    private void saveAuthToken(String token) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("auth_token", token);
        editor.apply();
    }

    private void saveUserIdToPreferences(int userId) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userId", userId);
        editor.apply();
    }

    private void checkAndRefreshToken() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TOLogin", Context.MODE_PRIVATE);
        long expirationTime = sharedPreferences.getLong("expirationTime", 0);

        if (System.currentTimeMillis() > expirationTime) {
            String refreshToken = getRefreshTokenFromPreferences();
            if (refreshToken != null) {
                RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(refreshToken);
                authenticationApi.refresh(refreshTokenRequest).enqueue(new Callback<JwtAuthenticationResponse>() {
                    @Override
                    public void onResponse(Call<JwtAuthenticationResponse> call, Response<JwtAuthenticationResponse> response) {
                        if (response.isSuccessful()) {
                            String newToken = response.body().getToken();
                            saveAuthToken(newToken);
                            // Update expiration time
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            long newExpirationTime = System.currentTimeMillis() + 1440000; // 24 minutes
                            editor.putLong("expirationTime", newExpirationTime);
                            editor.apply();
                            Toast.makeText(getActivity(), "Token refreshed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Failed to refresh token", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JwtAuthenticationResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), "Failed to refresh token: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private String getRefreshTokenFromPreferences() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("auth_token", null);
    }
}