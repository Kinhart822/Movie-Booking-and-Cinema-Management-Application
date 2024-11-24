package vn.edu.usth.mcma.frontend.Login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.AddMovie;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Request.SignInRequest;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.JwtAuthenticationResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.AuthenticationApi;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;
import vn.edu.usth.mcma.frontend.LoadingPageActivity;
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

        editTextEmail = view.findViewById(R.id.editText);
        editTextPassword = view.findViewById(R.id.editText2);
        buttonLogin = view.findViewById(R.id.login_button);

        RetrofitService retrofitService = new RetrofitService(requireActivity());
        authenticationApi = retrofitService.getRetrofit().create(AuthenticationApi.class);

        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

//            if (validateLogin(email, password)) {
//
//                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("123", getContext().MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean("isLoggedIn", true);
//                editor.apply();
//
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
//                getActivity().finish();
//            } else {
//
//                Toast.makeText(getActivity(), "Enter email or phone number to log in", Toast.LENGTH_SHORT).show();
//            }

            // Create the SignInRequest object
            SignInRequest signInRequest = new SignInRequest();
            signInRequest.setEmail(email);
            signInRequest.setPassword(password);

            authenticationApi.signIn(signInRequest).enqueue(new Callback<JwtAuthenticationResponse>() {
                @Override
                public void onResponse(Call<JwtAuthenticationResponse> call, Response<JwtAuthenticationResponse> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getActivity(), "Login successful!", Toast.LENGTH_SHORT).show();
                        String token = response.body().getToken();

                        // Assuming this is where you receive the token after successful login
                        SharedPreferences sharedTokenPreferences = getActivity().getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorToken = sharedTokenPreferences.edit();
                        editorToken.putString("auth_token", token);
                        editorToken.apply();
                        // Lưu trạng thái đăng nhập và thời gian hết hạn trong SharedPreferences
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TOLogin", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", true);
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

    private boolean validateLogin(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }
}