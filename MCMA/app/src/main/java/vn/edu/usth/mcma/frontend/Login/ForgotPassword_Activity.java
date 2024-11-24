package vn.edu.usth.mcma.frontend.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

public class ForgotPassword_Activity extends AppCompatActivity {

    private EditText editTextEmail;

    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_password);

        editTextEmail = findViewById(R.id.text_email);
        resetButton = findViewById(R.id.send_request);

        resetButton.setOnClickListener(view -> {
            String TextEmail = editTextEmail.getText().toString();

            if(validateForgotPassword(TextEmail)){

                SharedPreferences sharedPreferences = getSharedPreferences("ToforgotPassword", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isForgotPassword", true);
                editor.apply();

                Intent intent = new Intent(this, vn.edu.usth.mcma.frontend.Login.ResetPassword_Activity.class);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(this, "Please enter email to change password", Toast.LENGTH_SHORT).show();
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

    private boolean validateForgotPassword(String text) {
        return !text.isEmpty();
    }
}