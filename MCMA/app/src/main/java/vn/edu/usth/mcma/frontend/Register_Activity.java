package vn.edu.usth.mcma.frontend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import vn.edu.usth.mcma.R;


public class Register_Activity extends AppCompatActivity {

    private EditText editFirstName, editLastName, editPhone, editDateOfBirth, editAdress, editTextEmail, editTextPassword, editTextConfirmPassword;

    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editPhone = findViewById(R.id.editPhone);
        editDateOfBirth = findViewById(R.id.editDateOfBirth);
        editAdress = findViewById(R.id.editAddress);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        buttonRegister = findViewById(R.id.RegisterForAccount);

        buttonRegister.setOnClickListener(view -> {
            String firstname = editFirstName.getText().toString();
            String LastName = editLastName.getText().toString();
            String Phone = editPhone.getText().toString();
            String DateOfBirth = editDateOfBirth.getText().toString();
            String Adress = editAdress.getText().toString();
            String TextEmail = editTextEmail.getText().toString();
            String TextPassword = editTextPassword.getText().toString();
            String TextConfirmPassword = editTextConfirmPassword.getText().toString();

            if (validateRegister(firstname) && validateRegister(LastName) && validateRegister(Phone) && validateRegister(DateOfBirth)
                    && validateRegister(Adress) && validateRegister(TextEmail) && validateRegister(TextPassword) && validateRegister(TextConfirmPassword)){

                SharedPreferences sharedPreferences = getSharedPreferences("toRegister", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isRegister", true);
                editor.apply();

                Fragment loginFragment = new LoginFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(android.R.id.content, loginFragment);
                transaction.commit();
            }
            else{
                Toast.makeText(this, "Please enter information to sign up", Toast.LENGTH_SHORT).show();
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

    private boolean validateRegister(String text) {
        return !text.isEmpty();
    }
}