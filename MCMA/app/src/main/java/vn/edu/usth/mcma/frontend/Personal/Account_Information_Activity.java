package vn.edu.usth.mcma.frontend.Personal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.UserDetailsResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.UserDetailsAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;

public class Account_Information_Activity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "MovieAppPrefs";
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

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        loadInformation();
    }

    private void loadInformation() {
        // Initialize SharedPreferences

        RetrofitService retrofitService = new RetrofitService(this);
        UserDetailsAPI userDetailsAPI = retrofitService.getRetrofit().create(UserDetailsAPI.class);

        userDetailsAPI.getUserDetailByUser().enqueue(new Callback<UserDetailsResponse>() {
            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserDetailsResponse userDetailsResponse = response.body();
                    // Save the user details in SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", userDetailsResponse.getEmail());
                    editor.putString("lastname", userDetailsResponse.getLastName());
                    editor.putString("firstname", userDetailsResponse.getFirstName());
                    editor.putString("phone", userDetailsResponse.getPhone());
                    editor.putString("dob", userDetailsResponse.getDateOfBirth().toString()); // Convert Date to String
                    editor.putString("address", userDetailsResponse.getAddress());
                    editor.putString("gender", userDetailsResponse.getGender().toString()); // Convert Enum to String
                    editor.apply();

                    // Display the user details
                    // Display data in TextViews
//                    tvEmail.setText(userDetailsResponse.getEmail());
//                    tvLastname.setText(userDetailsResponse.getLastName());
//                    tvFirstname.setText(userDetailsResponse.getFirstName());
//                    tvPhone.setText(userDetailsResponse.getPhone());
//                    tvDob.setText(userDetailsResponse.getDateOfBirth().toString());
//                    tvAddress.setText(userDetailsResponse.getAddress());
//                    tvGender.setText(userDetailsResponse.getGender().toString());
                    displayInformation();

                } else {
                    Toast.makeText(Account_Information_Activity.this, "Failed to fetch account information.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                Toast.makeText(Account_Information_Activity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


//        SharedPreferences sharedPreferences = getSharedPreferences("AccountInfo", MODE_PRIVATE);

//        String email = sharedPreferences.getString("email", "N/A");
//        String lastname = sharedPreferences.getString("lastname", "N/A");
//        String firstname = sharedPreferences.getString("firstname", "N/A");
//        String phone = sharedPreferences.getString("phone", "N/A");
//        String dob = sharedPreferences.getString("dob", "N/A");
//        String address = sharedPreferences.getString("address", "N/A");
//        String gender = sharedPreferences.getString("gender", "N/A");
//
//        tvEmail.setText(email);
//        tvLastname.setText(lastname);
//        tvFirstname.setText(firstname);
//        tvPhone.setText(phone);
//        tvDob.setText(dob);
//        tvAddress.setText(address);
//        tvGender.setText(gender);


    }

    private void displayInformation() {

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
