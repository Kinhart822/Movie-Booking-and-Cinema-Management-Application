package vn.edu.usth.mcma.frontend.component.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import vn.edu.usth.mcma.frontend.component.account.ForgotPassword_Activity;
import vn.edu.usth.mcma.frontend.component.account.SignUpStepOneActivity;
import vn.edu.usth.mcma.frontend.constant.SharedPreferencesKey;
import vn.edu.usth.mcma.frontend.dto.request.account.SignInRequest;
import vn.edu.usth.mcma.frontend.dto.response.SignInResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;
import vn.edu.usth.mcma.frontend.MainActivity;
import vn.edu.usth.mcma.frontend.network.AuthPrefsManager;

public class SignInFragment extends Fragment {
    private final String TAG = SignInFragment.class.getName();
    private EditText emailEditText, passwordEditText;
    private AuthPrefsManager authPrefsManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        authPrefsManager = new AuthPrefsManager(requireContext());
        emailEditText = view.findViewById(R.id.edit_text_sign_in_email);
        passwordEditText = view.findViewById(R.id.edit_text_sign_in_password);
        Button signInButton = view.findViewById(R.id.button_sign_in);
        TextView signUpTextView = view.findViewById(R.id.text_view_sign_up);
        TextView resetPasswordTextView = view.findViewById(R.id.text_view_reset_password);

        // Chỗ này tránh click lỗi sẽ nhảy sang layout khác
        // TODO: really?
        view.setOnClickListener(v -> {
        });

        signInButton
                .setOnClickListener(v -> signIn());

        signUpTextView
                .setOnClickListener(view1 -> {
                    Intent intent = new Intent(requireContext(), SignUpStepOneActivity.class );
                    startActivity(intent);
        });

        resetPasswordTextView
                .setOnClickListener(view2 -> {
                    Intent intent = new Intent(requireContext(), ForgotPassword_Activity.class);
                    startActivity(intent);
        });

        return view;
    }
    private void signIn() {
        ApiService
                .getAuthApi(requireContext())
                .signIn(SignInRequest.builder()
                        .email(emailEditText.getText().toString())
                        .password(passwordEditText.getText().toString())
                        .build())
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<SignInResponse> call, @NonNull Response<SignInResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getActivity(), "Sign in successful!", Toast.LENGTH_SHORT).show();
                            assert response.body() != null;

                            saveAuthPrefs(response.body(), emailEditText.getText().toString());
    //                                saveProfilePrefs(response.body().getUserId());

                            Intent intent = new Intent(requireContext(), MainActivity.class);
                            startActivity(intent);
                            requireActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), "Wrong email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<SignInResponse> call, @NonNull Throwable t) {
                        Toast.makeText(getActivity(), "Sign in failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void saveAuthPrefs(SignInResponse response, String email) {
        authPrefsManager.saveEmail(email);
        authPrefsManager.saveAccessToken(response.getAccessToken());
        authPrefsManager.saveIsLoggedIn(true);
//                .putBoolean(SharedPreferencesKey.AUTH_IS_LOGGED_IN.name(), true)
//                .putLong(SharedPreferencesKey.AUTH_EXPIRATION_TIME.name(), System.currentTimeMillis() + JWT_EXPIRATION_TIME)
        if (response.getRefreshToken() != null) {
            authPrefsManager.saveRefreshToken(response.getRefreshToken());
        }
        Log.d(TAG, "saveAuthPrefs: response" + response);
    }

    private void saveProfilePrefs(int userId) {
        SharedPreferences profilePrefs = requireContext()
                .getSharedPreferences(SharedPreferencesKey.PROFILE.name(), Context.MODE_PRIVATE);
        profilePrefs.edit()
                .putInt(SharedPreferencesKey.PROFILE_ID.name(), userId)
                .apply();
    }
}