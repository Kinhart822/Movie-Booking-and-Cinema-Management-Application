package vn.edu.usth.mcma.frontend.component.account;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.auth.SignInFragment;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.dto.request.account.SignUpFinish;
import vn.edu.usth.mcma.frontend.dto.response.ApiResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;


public class SignUpStepThreeActivity extends AppCompatActivity {
    private String email;
    private String sessionId;
    private EditText passwordEditText, confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_step_three);
        email = getIntent().getStringExtra(IntentKey.SIGN_UP_EMAIL.name());
        sessionId = getIntent().getStringExtra(IntentKey.SIGN_UP_SESSION_ID.name());
        ImageButton backButton = findViewById(R.id.button_back);
        passwordEditText = findViewById(R.id.edit_text_password);
        confirmPasswordEditText = findViewById(R.id.edit_text_confirm_password);
        Button finishButton = findViewById(R.id.button_finish);

        backButton.setOnClickListener(view -> onBackPressed());
        finishButton.setOnClickListener(view -> {
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();
            // Validate password length
            if (password.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                return;
            }
            // Validate if passwords match
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }
            signUpFinish(password);
        });
    }
    private void signUpFinish(String password) {
        ApiService
                .getAccountApi(this)
                .signUpFinish(SignUpFinish.builder()
                        .sessionId(sessionId)
                        .email(email)
                        .password(password)
                        .build())
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(SignUpStepThreeActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                            Fragment signInFragment = new SignInFragment();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(android.R.id.content, signInFragment);
                            transaction.commit();
                        } else {
                            Toast.makeText(SignUpStepThreeActivity.this, "Wrong Format!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                        Toast.makeText(SignUpStepThreeActivity.this, "Registration failed!!! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Logger.getLogger(SignUpStepThreeActivity.class.getName()).log(Level.SEVERE, "Error occurred ", t);
                    }
                });
    }
}