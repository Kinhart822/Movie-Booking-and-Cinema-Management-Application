package vn.edu.usth.mcma.frontend;

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

import vn.edu.usth.mcma.R;

public class ResetPassword_Activity extends AppCompatActivity {

    private EditText editNewPassword, editConfirmPassword;

    private Button changepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reset_password);

        editNewPassword = findViewById(R.id.text_password1);
        editConfirmPassword = findViewById(R.id.text_password2);

        changepassword = findViewById(R.id.change_password);

        changepassword.setOnClickListener(view -> {
            String newPassword = editNewPassword.getText().toString();
            String confirmPassword = editConfirmPassword.getText().toString();

            if(validateResetPassword(newPassword) && validateResetPassword(confirmPassword)){
                SharedPreferences sharedPreferences = getSharedPreferences("toResetpassword", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isChange", true);
                editor.apply();

                if(newPassword.equals(confirmPassword)){
                    Fragment loginFragment = new LoginFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(android.R.id.content, loginFragment);
                    transaction.commit();
                }
                else{
                    Toast.makeText(this, "Make sure you entered the same password", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Please enter password to change", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    private boolean validateResetPassword(String text) {
        return !text.isEmpty();
    }
}