package vn.edu.usth.mcma.frontend.Coupon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.CouponResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;

import static vn.edu.usth.mcma.frontend.component.Personal.PersonalFragment.userPoints;

public class Coupon_Details_Activity extends AppCompatActivity {
    private int couponId;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_details);

        Intent intent = getIntent();
        couponId = intent.getIntExtra("couponId", -1);
        String couponName = intent.getStringExtra("couponName");
        String backgroundImageUrl = intent.getStringExtra("backgroundImageUrl");
        String description = intent.getStringExtra("description");
        String dateExpired = intent.getStringExtra("dateExpired");

        int points = intent.getIntExtra("points", 0);

        TextView nameView = findViewById(R.id.exchange_voucher_text);
        TextView descriptionView = findViewById(R.id.voucher_details);
        TextView pointsView = findViewById(R.id.points_text);
        TextView expiredView = findViewById(R.id.dateExpired);
        ImageView imageView = findViewById(R.id.voucher_image);

        nameView.setText(couponName);
        descriptionView.setText(description);
        pointsView.setText("Points to exchange: " + points + " points");
        expiredView.setText("Date Expired: " + dateExpired);

        Glide.with(this)
                .load(backgroundImageUrl == null || backgroundImageUrl.isEmpty() ? R.drawable.couponhahas : backgroundImageUrl)
                .error(R.drawable.couponhahas)
                .into(imageView);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> onBackPressed());

        Button exchangeButton = findViewById(R.id.exchange_voucher_button);
        exchangeButton.setOnClickListener(view -> exchangeCoupon());
    }

    private void fetchUserPoints(OnPointsFetchedListener listener) {
        ApiService
                .getBookingApi(this)
                .getUserPoints()
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            userPoints = response.body();
                            listener.onFetched(userPoints);
                        } else {
                            Toast.makeText(Coupon_Details_Activity.this, "Failed to fetch user points", Toast.LENGTH_SHORT).show();
                            listener.onFetched(-1);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                        Toast.makeText(Coupon_Details_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        listener.onFetched(-1);
                    }
                });
    }

    private void exchangeCoupon() {
        ApiService
                .getBookingApi(this)
                .exchangeCoupon(couponId)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<CouponResponse> call, @NonNull Response<CouponResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(Coupon_Details_Activity.this, "Coupon exchanged successfully!", Toast.LENGTH_SHORT).show();

                            fetchUserPoints(points -> {
                                if (points != -1) {
                                    userPoints = points;
                                    onBackPressed();
                                } else {
                                    Toast.makeText(Coupon_Details_Activity.this, "Failed to update points", Toast.LENGTH_SHORT).show();
                                }
                            });

//                    fetchUserPoints();
                        } else {
                            new AlertDialog.Builder(Coupon_Details_Activity.this)
                                    .setTitle("Coupon Already Exchanged")
                                    .setMessage("You have already exchanged this coupon.")
                                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                    .show();
//                    Toast.makeText(Coupon_Details_Activity.this, "Failed to exchange coupon. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CouponResponse> call, @NonNull Throwable t) {
                        new AlertDialog.Builder(Coupon_Details_Activity.this)
                                .setTitle("Coupon Already Exchanged")
                                .setMessage("You have already exchanged this coupon.")
                                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                .show();
//                Toast.makeText(Coupon_Details_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

interface OnPointsFetchedListener {
    void onFetched(int points);
}
