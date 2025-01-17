package vn.edu.usth.mcma.frontend.Coupon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.CouponResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.CouponAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;
import static vn.edu.usth.mcma.frontend.Personal.PersonalFragment.userPoints;

public class Coupon_Details_Activity extends AppCompatActivity {
    private int couponId;
    private CouponAPI couponAPI;
//    private int updatePoints;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_details);

        RetrofitService retrofitService = new RetrofitService(this);
        couponAPI = retrofitService.getRetrofit().create(CouponAPI.class);

        Intent intent = getIntent();
        couponId = intent.getIntExtra("couponId", -1);
        String couponName = intent.getStringExtra("couponName");
        String imageUrl = intent.getStringExtra("imageUrl");
        String backgroundImageUrl = intent.getStringExtra("backgroundImageUrl");
        String description = intent.getStringExtra("description");
        String dateExpired = intent.getStringExtra("dateExpired");
        String dateAvailable = intent.getStringExtra("dateAvailable");

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
        RetrofitService retrofitService = new RetrofitService(this);
        CouponAPI couponAPI = retrofitService.getRetrofit().create(CouponAPI.class);

        Call<Integer> call = couponAPI.getUserPoints();
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userPoints = response.body();
                    listener.onFetched(userPoints);
                } else {
                    Toast.makeText(Coupon_Details_Activity.this, "Failed to fetch user points", Toast.LENGTH_SHORT).show();
                    listener.onFetched(-1);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(Coupon_Details_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                listener.onFetched(-1);
            }
        });
    }

    private void exchangeCoupon() {
        Call<CouponResponse> call = couponAPI.exchangeCoupon(couponId);
        call.enqueue(new Callback<CouponResponse>() {
            @Override
            public void onResponse(Call<CouponResponse> call, Response<CouponResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(Coupon_Details_Activity.this, "Coupon exchanged successfully!", Toast.LENGTH_SHORT).show();

                    fetchUserPoints(new OnPointsFetchedListener() {
                        @Override
                        public void onFetched(int points) {
                            if (points != -1) {
                                userPoints = points;
                                onBackPressed();
                            } else {
                                Toast.makeText(Coupon_Details_Activity.this, "Failed to update points", Toast.LENGTH_SHORT).show();
                            }
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
            public void onFailure(Call<CouponResponse> call, Throwable t) {
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
