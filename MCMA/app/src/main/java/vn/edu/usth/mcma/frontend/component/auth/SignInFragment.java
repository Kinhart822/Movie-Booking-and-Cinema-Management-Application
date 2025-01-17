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
import vn.edu.usth.mcma.frontend.constant.SharedPreferencesKey;
import vn.edu.usth.mcma.frontend.dto.Request.SignInRequest;
import vn.edu.usth.mcma.frontend.dto.response.SignInResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;
import vn.edu.usth.mcma.frontend.MainActivity;
import vn.edu.usth.mcma.frontend.network.AuthPrefsManager;

public class SignInFragment extends Fragment {
    private EditText editTextEmail, editTextPassword;
    private AuthPrefsManager authPrefsManager;
    private final String TAG = SignInFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        authPrefsManager = new AuthPrefsManager(requireContext());
        // Chỗ này tránh click lỗi sẽ nhảy sang layout khác
        view.setOnClickListener(v -> {
        });

        editTextEmail = view.findViewById(R.id.editText);
        editTextPassword = view.findViewById(R.id.editText2);

        Button buttonSignIn = view.findViewById(R.id.sign_in_button);
        buttonSignIn.setOnClickListener(v -> {
            ApiService
                    .getAuthApi(requireContext())
                    .signIn(SignInRequest
                            .builder()
                            .email(editTextEmail.getText().toString())
                            .password(editTextPassword.getText().toString())
                            .build())
                    .enqueue(new Callback<>() {
                        @Override
                        public void onResponse(@NonNull Call<SignInResponse> call, @NonNull Response<SignInResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getActivity(), "Sign in successful!", Toast.LENGTH_SHORT).show();
                                assert response.body() != null;

                                saveAuthPrefs(response.body(), editTextEmail.getText().toString());
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
            // Check token expiration
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