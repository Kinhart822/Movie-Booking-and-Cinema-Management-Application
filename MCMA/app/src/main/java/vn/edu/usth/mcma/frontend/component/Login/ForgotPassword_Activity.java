package vn.edu.usth.mcma.frontend.component.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.constant.SharedPreferencesKey;
import vn.edu.usth.mcma.frontend.dto.Request.ForgotPasswordRequest;
import vn.edu.usth.mcma.frontend.dto.response.JwtAuthenticationResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class ForgotPassword_Activity extends AppCompatActivity {

    private EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_password);

        editTextEmail = findViewById(R.id.text_email);
        Button resetButton = findViewById(R.id.send_request);

        resetButton.setOnClickListener(view -> {
            String TextEmail = editTextEmail.getText().toString();


            // Validate email format
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(TextEmail).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }
            ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
            forgotPasswordRequest.setEmail(TextEmail);
            ApiService
                    .getAuthApi(this)
                    .forgotPassword(forgotPasswordRequest).enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<JwtAuthenticationResponse> call, @NonNull Response<JwtAuthenticationResponse> response) {
                    if (response.isSuccessful()) {
                        // Get the token from the response body
                        assert response.body() != null;
                        String token = response.body().getAccessToken();
                        // Save the token in SharedPreferences
                        SharedPreferences sharedPreferencesForToken = getSharedPreferences(SharedPreferencesKey.AUTH.name(), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorForToken = sharedPreferencesForToken.edit();
                        editorForToken.putString(SharedPreferencesKey.AUTH_ACCESS_TOKEN.name(), token);
                        editorForToken.apply();

                        Intent intent = new Intent(ForgotPassword_Activity.this, ResetPassword_Activity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(ForgotPassword_Activity.this, "Send request to reset password successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgotPassword_Activity.this, "Wrong Email!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JwtAuthenticationResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ForgotPassword_Activity.this, "Send request to reset password failed!!!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Logger.getLogger(ForgotPassword_Activity.class.getName()).log(Level.SEVERE, "Error occurred, Please enter email again to send request", t);
                }
            });
//            Intent intent = new Intent(this, vn.edu.usth.mcma.frontend.Login.ResetPassword_Activity.class);
//            startActivity(intent);
//            finish();

//            if(validateForgotPassword(TextEmail)){
//
//                SharedPreferences sharedPreferences = getSharedPreferences("ToforgotPassword", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean("isForgotPassword", true);
//                editor.apply();
//
//                Intent intent = new Intent(this, vn.edu.usth.mcma.frontend.Login.ResetPassword_Activity.class);
//                startActivity(intent);
//                finish();
//            }
//            else{
//                Toast.makeText(this, "Please enter email to change password", Toast.LENGTH_SHORT).show();
//            }
        });

        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> onBackPressed());

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

}