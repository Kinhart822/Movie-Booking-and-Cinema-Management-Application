package vn.edu.usth.mcma.frontend.Login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Request.ResetPasswordRequest;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.JwtAuthenticationResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.AuthenticationApi;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;

public class ResetPassword_Activity extends AppCompatActivity {

    private EditText editNewPassword, editConfirmPassword;

    private Button changepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initializeComponents();

        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void initializeComponents() {
        editNewPassword = findViewById(R.id.text_password1);
        editConfirmPassword = findViewById(R.id.text_password2);
        changepassword = findViewById(R.id.change_password);

        RetrofitService retrofitService = new RetrofitService(this);
        AuthenticationApi authenticationApi = retrofitService.getRetrofit().create(AuthenticationApi.class);

        // Retrieve the token from SharedPreferences
        SharedPreferences sharedPreferencesForToken = getSharedPreferences("AuthPreferences", Context.MODE_PRIVATE);
        String token = sharedPreferencesForToken.getString("authToken", null);

        changepassword.setOnClickListener(view -> {
            String newPassword = editNewPassword.getText().toString();
            String confirmPassword = editConfirmPassword.getText().toString();

            // Validate password length
            if (newPassword.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate if passwords match
            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create the ResetPasswordRequest object
            ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
            resetPasswordRequest.setToken(token);
            resetPasswordRequest.setNewPassword(newPassword);
            resetPasswordRequest.setConfirmPassword(confirmPassword);

            // Call the API
            authenticationApi.resetPassword(resetPasswordRequest).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        // Password reset was successful
                        Toast.makeText(ResetPassword_Activity.this, "Reset Password successful!", Toast.LENGTH_SHORT).show();
                        Fragment loginFragment = new LoginFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(android.R.id.content, loginFragment);
                        transaction.commit();
                    } else {

                        Toast.makeText(ResetPassword_Activity.this, "Reset Password failed.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(ResetPassword_Activity.this, "Reset Password failed!!!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Logger.getLogger(Register_Activity.class.getName()).log(Level.SEVERE, "Error occurred, Please enter password again to change", t);
                }
            });
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private boolean validateResetPassword(String text) {
        return !text.isEmpty();
    }
}