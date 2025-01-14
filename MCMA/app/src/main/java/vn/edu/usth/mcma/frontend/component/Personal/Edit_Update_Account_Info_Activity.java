package vn.edu.usth.mcma.frontend.component.Personal;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.constant.Gender;
import vn.edu.usth.mcma.frontend.constant.SharedPreferencesKey;
import vn.edu.usth.mcma.frontend.dto.Request.UpdateAccountRequest;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class Edit_Update_Account_Info_Activity extends AppCompatActivity {
    private EditText editEmail, editLastname, editFirstname, editPhoneNumber, editDob, editAddress;
    private Spinner genderSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_update_account_info);

        editEmail = findViewById(R.id.etEmail);
        editLastname = findViewById(R.id.etLastName);
        editFirstname = findViewById(R.id.etFirstName);
        editPhoneNumber = findViewById(R.id.etPN);
        editDob = findViewById(R.id.etDOB);
        editAddress = findViewById(R.id.etAddress);
        genderSpinner = findViewById(R.id.genderSpinner);

        Button buttonUpdate_Info = findViewById(R.id.btnUpdateInfo);

        ImageButton backButton = findViewById(R.id.edit_back_button);
        backButton.setOnClickListener(view -> onBackPressed());

        buttonUpdate_Info.setOnClickListener(view -> updateAccountInfo());
    }

    private void updateAccountInfo() {
        String firstName = editFirstname.getText().toString().trim();
        String lastName = editLastname.getText().toString().trim();
        String phone = editPhoneNumber.getText().toString().trim();
        String dateOfBirthString = editDob.getText().toString().trim();
        String address = editAddress.getText().toString().trim();
        String email = editEmail.getText().toString().trim();

        // Validate input
        if (!isInputValid()) {
            return;
        }

        // Parse and validate date of birth
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);
        Date dateOfBirth;
        try {
            dateOfBirth = dateFormat.parse(dateOfBirthString);
        } catch (ParseException e) {
            Toast.makeText(this, "Date of birth must be in the format MM/dd/yyyy", Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve selected gender
        String genderSelection = genderSpinner.getSelectedItem().toString();
        Gender gender;
        switch (genderSelection) {
            case "Male":
                gender = Gender.Male;
                break;
            case "Female":
                gender = Gender.Female;
                break;
            case "Other":
                gender = Gender.Other;
                break;
            default:
                Toast.makeText(this, "Please select a valid gender", Toast.LENGTH_SHORT).show();
                return;
        }


        // Prepare request object
        UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest();
        updateAccountRequest.setFirstName(firstName);
        updateAccountRequest.setLastName(lastName);
        updateAccountRequest.setPhone(phone);
        updateAccountRequest.setDateOfBirth(dateOfBirth);
        updateAccountRequest.setAddress(address);
        updateAccountRequest.setEmail(email);
        updateAccountRequest.setGender(gender);

        int userId = getUserIdFromPreferences();
        ApiService
                .getAccountApi(this)
                .updateAccount(userId, updateAccountRequest).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(Edit_Update_Account_Info_Activity.this, "Account updated successfully!", Toast.LENGTH_SHORT).show();
                            saveInformationToSharedPreferences(firstName, lastName, phone, dateOfBirthString, address, genderSelection, email);
                            onBackPressed();
                        } else {
                            Toast.makeText(Edit_Update_Account_Info_Activity.this, "Failed to update account. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Toast.makeText(Edit_Update_Account_Info_Activity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void saveInformationToSharedPreferences(String firstName, String lastName, String phone, String dob, String address, String gender, String email) {
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferencesKey.PROFILE.name(), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SharedPreferencesKey.PROFILE_FIRST_NAME.name(), firstName);
        editor.putString(SharedPreferencesKey.PROFILE_LAST_NAME.name(), lastName);
        editor.putString(SharedPreferencesKey.PROFILE_PHONE.name(), phone);
        editor.putString(SharedPreferencesKey.PROFILE_DOB.name(), dob);
        editor.putString(SharedPreferencesKey.PROFILE_ADDRESS.name(), address);
        editor.putString(SharedPreferencesKey.PROFILE_SEX.name(), gender);
//        editor.putString(SharedPreferencesKey.PROFILE_EMAIL.name(), email);

        editor.apply();
    }


    private int getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferencesKey.PROFILE.name(), MODE_PRIVATE);
        return sharedPreferences.getInt(SharedPreferencesKey.PROFILE_ID.name(), 8); // Default userId is 0 if not set
    }

    private boolean isInputValid() {
        return !TextUtils.isEmpty(editEmail.getText().toString().trim()) &&
                !TextUtils.isEmpty(editLastname.getText().toString().trim()) &&
                !TextUtils.isEmpty(editFirstname.getText().toString().trim()) &&
                !TextUtils.isEmpty(editPhoneNumber.getText().toString().trim()) &&
                !TextUtils.isEmpty(editDob.getText().toString().trim()) &&
                !TextUtils.isEmpty(editAddress.getText().toString().trim());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
