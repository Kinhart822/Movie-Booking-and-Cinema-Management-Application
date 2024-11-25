package vn.edu.usth.mcma.frontend.Personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Request.ChangePasswordRequest;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.JwtAuthenticationResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.AuthenticationApi;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.UpdatePasswordAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;
import vn.edu.usth.mcma.frontend.Login.LoginFragment;
import vn.edu.usth.mcma.frontend.Login.Register_Activity;
import vn.edu.usth.mcma.frontend.Login.ResetPassword_Activity;

public class Change_Password_Account_Info_Activity extends AppCompatActivity {
    private EditText editCurrent_pass, editNew_pass, editConfirm_pass;
    private Button button_UpdatePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_change_password_account_info);

        editCurrent_pass = findViewById(R.id.current_password_input);
        editNew_pass = findViewById(R.id.new_password_input);
        editConfirm_pass = findViewById(R.id.confirm_password_input);

        RetrofitService retrofitService = new RetrofitService(this);
        UpdatePasswordAPI updatePasswordAPI = retrofitService.getRetrofit().create(UpdatePasswordAPI.class);


        button_UpdatePass = findViewById(R.id.btn_updatepass);
        button_UpdatePass.setOnClickListener(view -> {
            String currentPass = editCurrent_pass.getText().toString().trim();
            String newPass = editNew_pass.getText().toString().trim();
            String confirmPass = editConfirm_pass.getText().toString().trim();

            if (currentPass.isEmpty()) {
                Toast.makeText(this, "Please enter current password!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "Please enter new password and confirm password!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, "New password and Confirm password are not the same!", Toast.LENGTH_SHORT).show();
                return;
            }

            ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
            changePasswordRequest.setNewPassword(newPass);
            changePasswordRequest.setConfirmPassword(confirmPass);

            updatePasswordAPI.updatePassword(changePasswordRequest).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(Change_Password_Account_Info_Activity.this, response.body(), Toast.LENGTH_SHORT).show();
                        // Optionally navigate back or reset fields
                        onBackPressed();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(Change_Password_Account_Info_Activity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Logger.getLogger(Change_Password_Account_Info_Activity.class.getName()).log(Level.SEVERE, null, t);
                }
            });




        });

        ImageButton backButton = findViewById(R.id.change_password_back_button);
        backButton.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
