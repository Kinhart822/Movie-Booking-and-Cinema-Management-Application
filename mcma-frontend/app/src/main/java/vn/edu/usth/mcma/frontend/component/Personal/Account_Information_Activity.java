package vn.edu.usth.mcma.frontend.component.Personal;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.response.UserDetailsResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;

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
        ApiService
                .getAccountApi(this)
                .getUserDetailByUser()
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<UserDetailsResponse> call, @NonNull Response<UserDetailsResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            UserDetailsResponse userDetailsResponse = response.body();
                            tvEmail.setText(userDetailsResponse.getEmail());
                            tvLastname.setText(userDetailsResponse.getLastName());
                            tvFirstname.setText(userDetailsResponse.getFirstName());
                            tvPhone.setText(userDetailsResponse.getPhone());
                            tvDob.setText(userDetailsResponse.getDateOfBirth());
                            tvAddress.setText(userDetailsResponse.getAddress());
                            tvGender.setText(userDetailsResponse.getGender());

                        } else {
                            Toast.makeText(Account_Information_Activity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    public void onFailure(@NonNull Call<UserDetailsResponse> call, @NonNull Throwable t) {
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
