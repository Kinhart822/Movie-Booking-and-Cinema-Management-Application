package vn.edu.usth.mcma.frontend.component.Login;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.constant.Gender;
import vn.edu.usth.mcma.frontend.constant.SharedPreferencesKey;
import vn.edu.usth.mcma.frontend.dto.Request.SignUpRequest;
import vn.edu.usth.mcma.frontend.network.ApiService;


public class Register_Activity extends AppCompatActivity {
    private Spinner spinnerGender;
    private EditText editFirstName, editLastName, editPhone, editDateOfBirth, editAddress, editTextEmail, editTextPassword, editTextConfirmPassword;
    public static int phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editPhone = findViewById(R.id.editPhone);
        editDateOfBirth = findViewById(R.id.editDateOfBirth);
        editAddress = findViewById(R.id.editAddress);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        Button buttonRegister = findViewById(R.id.RegisterForAccount);
        spinnerGender = findViewById(R.id.spinnerGender);

        buttonRegister.setOnClickListener(view -> {
            String firstname = editFirstName.getText().toString();
            String LastName = editLastName.getText().toString();
            String Phone = editPhone.getText().toString();
            String DateOfBirth = editDateOfBirth.getText().toString();
            String Address = editAddress.getText().toString();
            String TextEmail = editTextEmail.getText().toString();
            String TextPassword = editTextPassword.getText().toString();
            String TextConfirmPassword = editTextConfirmPassword.getText().toString();

            // Validate date of birth format (MM/dd/yyyy)
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            Date dateOfBirth;
            try {
                dateOfBirth = dateFormat.parse(DateOfBirth);
            } catch (ParseException e) {
                Toast.makeText(this, "Date of birth must be in the format dd/MM/yyyy", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate password length
            if (TextPassword.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate if passwords match
            if (!TextPassword.equals(TextConfirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate phone number (Vietnam format)
            if (!Phone.matches("^(0|\\+84)[35789][0-9]{8}$")) {
                Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate email format
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(TextEmail).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }

            String userType = "USER";
            // Retrieve selected gender
            String genderSelection = spinnerGender.getSelectedItem().toString();
            Gender gender = switch (genderSelection) {
                case "Male" -> Gender.Male;
                case "Female" -> Gender.Female;
                case "Other" -> Gender.Other;
                default -> null;
            };

            if (gender == null) {
                Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
                return;
            }

            assert dateOfBirth != null;
            String formattedDate = dateFormat.format(dateOfBirth);

            SignUpRequest signUpRequest = new SignUpRequest();
            signUpRequest.setFirstName(firstname);
            signUpRequest.setLastName(LastName);
            signUpRequest.setPhone(Phone);
            signUpRequest.setDateOfBirth(formattedDate);
            signUpRequest.setAddress(Address);
            signUpRequest.setEmail(TextEmail);
            signUpRequest.setPassword(TextPassword);
            signUpRequest.setConfirmPassword(TextConfirmPassword);
            signUpRequest.setGender(gender);
            signUpRequest.setType(userType);

            ApiService
                    .getAuthApi(this)
                    .signUp(signUpRequest).enqueue(new Callback<>() {
                        @Override
                        public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(Register_Activity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                phoneNumber = Integer.parseInt(Phone);
                                SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferencesKey.PROFILE.name(), MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(SharedPreferencesKey.PROFILE_FIRST_NAME.name(), firstname);
                                editor.putString(SharedPreferencesKey.PROFILE_LAST_NAME.name(), LastName);
                                editor.apply();

                                Fragment loginFragment = new LoginFragment();
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(android.R.id.content, loginFragment);
                                transaction.commit();
                            } else {
                                Toast.makeText(Register_Activity.this, "Wrong Format!!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                            Toast.makeText(Register_Activity.this, "Registration failed!!!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            Logger.getLogger(Register_Activity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                        }
                    });


//            if (validateRegister(firstname) && validateRegister(LastName) && validateRegister(Phone) && validateRegister(DateOfBirth)
//                    && validateRegister(Address) && validateRegister(TextEmail) && validateRegister(TextPassword) && validateRegister(TextConfirmPassword)){
//
//                SharedPreferences sharedPreferences = getSharedPreferences("toRegister", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean("isRegister", true);
//                editor.apply();
//
//                Fragment loginFragment = new LoginFragment();
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(android.R.id.content, loginFragment);
//                transaction.commit();
//            }
//            else{
//                Toast.makeText(this, "Please enter information to sign up", Toast.LENGTH_SHORT).show();
//            }

        });



        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    private boolean validateRegister(String text) {
        return !text.isEmpty();
    }
}