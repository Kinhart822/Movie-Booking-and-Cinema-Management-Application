package vn.edu.usth.mcma.frontend.component.Personal;

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
import vn.edu.usth.mcma.frontend.dto.request.ChangePasswordRequest;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class Change_Password_Account_Info_Activity extends AppCompatActivity {
    private EditText editCurrent_pass, editNew_pass, editConfirm_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_change_password_account_info);

        editCurrent_pass = findViewById(R.id.current_password_input);
        editNew_pass = findViewById(R.id.new_password_input);
        editConfirm_pass = findViewById(R.id.confirm_password_input);

        Button button_UpdatePass = findViewById(R.id.btn_updatepass);
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

            ApiService.getAccountApi(this)
                    .updatePassword(changePasswordRequest)
                    .enqueue(new Callback<>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Toast.makeText(Change_Password_Account_Info_Activity.this, response.body(), Toast.LENGTH_SHORT).show();
                                // Optionally navigate back or reset fields
                                onBackPressed();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                            Toast.makeText(Change_Password_Account_Info_Activity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            Logger.getLogger(Change_Password_Account_Info_Activity.class.getName()).log(Level.SEVERE, null, t);
                        }
                    });
        });

        ImageButton backButton = findViewById(R.id.change_password_back_button);
        backButton.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
