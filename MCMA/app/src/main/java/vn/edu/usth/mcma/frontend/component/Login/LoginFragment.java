package vn.edu.usth.mcma.frontend.component.Login;

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
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.constant.SharedPreferencesKey;
import vn.edu.usth.mcma.frontend.dto.Request.RefreshTokenRequest;
import vn.edu.usth.mcma.frontend.dto.Request.SignInRequest;
import vn.edu.usth.mcma.frontend.dto.Response.JwtAuthenticationResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;
import vn.edu.usth.mcma.frontend.MainActivity;

public class LoginFragment extends Fragment {
    private EditText editTextEmail, editTextPassword;

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
        Button buttonLogin = view.findViewById(R.id.login_button);

        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            // Create the SignInRequest object
            SignInRequest signInRequest = new SignInRequest();
            signInRequest.setEmail(email);
            signInRequest.setPassword(password);

            ApiService
                    .getAuthApi(requireContext())
                    .signIn(signInRequest)
                    .enqueue(new Callback<>() {
                        @Override
                        public void onResponse(@NonNull Call<JwtAuthenticationResponse> call, @NonNull Response<JwtAuthenticationResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getActivity(), "Login successful!", Toast.LENGTH_SHORT).show();
                                assert response.body() != null;
                                String token = response.body().getToken();
                                int userId = response.body().getUserId();
                                saveUserIdToPreferences(userId);

                                saveAuthToken(token);

                                // Lưu trạng thái đăng nhập và thời gian hết hạn trong SharedPreferences
                                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SharedPreferencesKey.AUTH.name(), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean(SharedPreferencesKey.AUTH_IS_LOGGED_IN.name(), true);
                                editor.apply(); // Save userId persistently

                                // Tính thời gian hết hạn từ thời điểm hiện tại
                                long expirationTime = System.currentTimeMillis() + 1440000; // 1440000ms = 24 phút
                                editor.putLong(SharedPreferencesKey.AUTH_EXPIRATION_TIME.name(), expirationTime);
                                editor.apply();

                                // Điều hướng
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                requireActivity().finish();
                            } else {
                                Toast.makeText(getActivity(), "Wrong email or password", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<JwtAuthenticationResponse> call, @NonNull Throwable t) {
                            Toast.makeText(getActivity(), "Login failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

            // Check token expiration
            checkAndRefreshToken();
        });
        TextView create_account = view.findViewById(R.id.create_account);
        create_account.setOnClickListener(view1 -> {
            Intent i = new Intent(requireContext(), Register_Activity.class );
            startActivity(i);
        });

        TextView forgot_password = view.findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(view2 -> {
            Intent i = new Intent(requireContext(), ForgotPassword_Activity.class );
            startActivity(i);
        });

        return view;
    }

    private void saveAuthToken(String token) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SharedPreferencesKey.AUTH.name(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SharedPreferencesKey.AUTH_TOKEN.name(), token);
        editor.apply();
    }

    private void saveUserIdToPreferences(int userId) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(SharedPreferencesKey.PROFILE.name(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SharedPreferencesKey.PROFILE_ID.name(), userId);
        editor.apply();
    }

    private void checkAndRefreshToken() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SharedPreferencesKey.AUTH.name(), Context.MODE_PRIVATE);
        long expirationTime = sharedPreferences.getLong(SharedPreferencesKey.AUTH_EXPIRATION_TIME.name(), 0);
        if (System.currentTimeMillis() <= expirationTime) {
            return;
        }
        String refreshToken = getRefreshTokenFromPreferences();
        if (refreshToken == null) {
            return;
        }
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(refreshToken);
        ApiService
                .getAuthApi(requireContext())
                .refresh(refreshTokenRequest).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<JwtAuthenticationResponse> call, @NonNull Response<JwtAuthenticationResponse> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            String newToken = response.body().getToken();
                            saveAuthToken(newToken);
                            // Update expiration time
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            long newExpirationTime = System.currentTimeMillis() + 1440000; // 24 minutes
                            editor.putLong(SharedPreferencesKey.AUTH_EXPIRATION_TIME.name(), newExpirationTime);
                            editor.apply();
                            Toast.makeText(getActivity(), "Token refreshed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Failed to refresh token", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JwtAuthenticationResponse> call, @NonNull Throwable t) {
                        Toast.makeText(getActivity(), "Failed to refresh token: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getRefreshTokenFromPreferences() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SharedPreferencesKey.AUTH.name(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(SharedPreferencesKey.AUTH_TOKEN.name(), null);
    }
}