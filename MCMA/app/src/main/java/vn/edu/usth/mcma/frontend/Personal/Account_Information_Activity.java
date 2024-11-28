package vn.edu.usth.mcma.frontend.Personal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.UserDetailsResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.UserDetailsAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;

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
        tvGender = findViewById(R.id.tvGender);

        loadInformation();

        ImageButton backButton = findViewById(R.id.detail_back_button);
        backButton.setOnClickListener(view -> onBackPressed());
    }

    private void loadInformation() {
        RetrofitService retrofitService = new RetrofitService(this);
        UserDetailsAPI userDetailsAPI = retrofitService.getRetrofit().create(UserDetailsAPI.class);

        userDetailsAPI.getUserDetailByUser()
                .enqueue(new Callback<UserDetailsResponse>() {
                    @Override
                    public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            UserDetailsResponse userDetailsResponse = response.body();
                            tvEmail.setText(userDetailsResponse.getEmail());
                            tvLastname.setText(userDetailsResponse.getLastName());
                            tvFirstname.setText(userDetailsResponse.getFirstName());
                            tvPhone.setText(userDetailsResponse.getPhone());
                            tvDob.setText(userDetailsResponse.getDateOfBirth().toString());
                            tvAddress.setText(userDetailsResponse.getAddress());
                            tvGender.setText(userDetailsResponse.getGender().toString());

                        } else {
                            Toast.makeText(Account_Information_Activity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                        Toast.makeText(Account_Information_Activity.this, "Failed to fetch data!!!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Logger.getLogger(Account_Information_Activity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
