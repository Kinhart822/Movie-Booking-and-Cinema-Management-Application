package vn.edu.usth.mcma.frontend.Personal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.mcma.R;

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

            Toast.makeText(this, "Update password successfully!", Toast.LENGTH_SHORT).show();
            onBackPressed();
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
