package vn.edu.usth.mcma.frontend.Personal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.mcma.R;

public class Account_Information_Activity extends AppCompatActivity {

    private TextView tvEmail, tvLastname, tvFirstname, tvPhone, tvDob, tvAddress, tvGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);

        tvEmail = findViewById(R.id.tvEmail);
        tvLastname = findViewById(R.id.tvLastName);
        tvFirstname = findViewById(R.id.tvFirstName);
        tvPhone = findViewById(R.id.tvPhoneNumber);
        tvDob = findViewById(R.id.tvDOB);
        tvAddress = findViewById(R.id.tvAddress);
        tvGender = findViewById(R.id.tvGender); // Thêm TextView hiển thị giới tính

        ImageButton backButton = findViewById(R.id.detail_back_button);
        backButton.setOnClickListener(view -> onBackPressed());

        loadInformation();
    }

    private void loadInformation() {
        SharedPreferences sharedPreferences = getSharedPreferences("AccountInfo", MODE_PRIVATE);

        String email = sharedPreferences.getString("email", "N/A");
        String lastname = sharedPreferences.getString("lastname", "N/A");
        String firstname = sharedPreferences.getString("firstname", "N/A");
        String phone = sharedPreferences.getString("phone", "N/A");
        String dob = sharedPreferences.getString("dob", "N/A");
        String address = sharedPreferences.getString("address", "N/A");
        String gender = sharedPreferences.getString("gender", "N/A");

        tvEmail.setText(email);
        tvLastname.setText(lastname);
        tvFirstname.setText(firstname);
        tvPhone.setText(phone);
        tvDob.setText(dob);
        tvAddress.setText(address);
        tvGender.setText(gender);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
