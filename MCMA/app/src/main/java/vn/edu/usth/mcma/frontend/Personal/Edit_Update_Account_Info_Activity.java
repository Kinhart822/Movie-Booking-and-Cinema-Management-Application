package vn.edu.usth.mcma.frontend.Personal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.mcma.R;

public class Edit_Update_Account_Info_Activity extends AppCompatActivity {
    private EditText editEmail, editLastname, editFirstname, editphoneNumber, editDob, editAddress;
    private Spinner genderSpinner;
    private Button buttonUpdate_Info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_update_account_info);

        editEmail = findViewById(R.id.etEmail);
        editLastname = findViewById(R.id.etLastName);
        editFirstname = findViewById(R.id.etFirstName);
        editphoneNumber = findViewById(R.id.etPN);
        editDob = findViewById(R.id.etDOB);
        editAddress = findViewById(R.id.etAddress);
        genderSpinner = findViewById(R.id.genderSpinner);

        buttonUpdate_Info = findViewById(R.id.btnUpdateInfo);

        ImageButton backButton = findViewById(R.id.edit_back_button);
        backButton.setOnClickListener(view -> onBackPressed());

        buttonUpdate_Info.setOnClickListener(view -> {
            if (isInputValid()) {
                saveInformation();
                Toast.makeText(this, "Update Information Successfully", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                Toast.makeText(this, "Please enter full information", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isInputValid() {
        return !TextUtils.isEmpty(editEmail.getText().toString().trim()) &&
                !TextUtils.isEmpty(editLastname.getText().toString().trim()) &&
                !TextUtils.isEmpty(editFirstname.getText().toString().trim()) &&
                !TextUtils.isEmpty(editphoneNumber.getText().toString().trim()) &&
                !TextUtils.isEmpty(editDob.getText().toString().trim()) &&
                !TextUtils.isEmpty(editAddress.getText().toString().trim());
    }

    private void saveInformation() {
        SharedPreferences sharedPreferences = getSharedPreferences("AccountInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("email", editEmail.getText().toString().trim());
        editor.putString("lastname", editLastname.getText().toString().trim());
        editor.putString("firstname", editFirstname.getText().toString().trim());
        editor.putString("phone", editphoneNumber.getText().toString().trim());
        editor.putString("dob", editDob.getText().toString().trim());
        editor.putString("address", editAddress.getText().toString().trim());

        String selectedGender = genderSpinner.getSelectedItem().toString();
        editor.putString("gender", selectedGender);

        editor.apply();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
