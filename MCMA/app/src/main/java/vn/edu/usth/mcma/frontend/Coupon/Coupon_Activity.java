package vn.edu.usth.mcma.frontend.Coupon;

import static vn.edu.usth.mcma.frontend.component.Personal.PersonalFragment.userPoints;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.CouponResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class Coupon_Activity extends AppCompatActivity {
    private CouponAdapter couponAdapter;
    private List<CouponItem> couponList;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

//        userPoints = getIntent().getIntExtra("USER_POINTS", -1);
        TextView textView = findViewById(R.id.user_points);
        textView.setText("User Points: " + userPoints + " points");

        RecyclerView recyclerView = findViewById(R.id.recyclerview_voucher_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        couponList = new ArrayList<>();
        couponAdapter = new CouponAdapter(this, couponList);
        recyclerView.setAdapter(couponAdapter);

        fetchCoupons();

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> finish());

        LinearLayout myCouponButton = findViewById(R.id.my_coupon_button);
        myCouponButton.setOnClickListener(view -> {
            Intent intent = new Intent(Coupon_Activity.this, My_Voucher_Activity.class);
            intent.putExtra("USER_POINTS", userPoints);
            startActivity(intent);
        });
    }

    private void fetchCoupons() {
        ApiService
                .getBookingApi(this)
                .getAllCoupon()
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<CouponResponse>> call, @NonNull Response<List<CouponResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Clear the current list
                            couponList.clear();

                            // Map data from CouponResponse to CouponItem
                            for (CouponResponse couponResponse : response.body()) {
                                List<Integer> ids = couponResponse.getCouponIds();
                                List<String> names = couponResponse.getCouponNameList();
                                List<String> images = couponResponse.getImageUrlList();
                                List<Integer> points = couponResponse.getPointToExchangeList();
                                List<String> dateAvailableList = couponResponse.getDateAvailableList();
                                List<String> expirationDates = couponResponse.getExpirationDates();

                                if (ids != null && names != null && images != null && points != null) {
                                    for (int i = 0; i < ids.size(); i++) {
                                        Integer id = ids.get(i);
                                        String name = names.get(i);
                                        String image = images.get(i);
                                        Integer point = points.get(i);
                                        String dateAvailable = dateAvailableList.get(i);
                                        String expirationDate = expirationDates.get(i);

                                        // Add each item to couponList
                                        couponList.add(new CouponItem(id, name, point, dateAvailable, expirationDate, image));
                                    }
                                }
                            }
                            // Notify adapter about data changes
                            couponAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(Coupon_Activity.this, "Failed to fetch coupons", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<CouponResponse>> call, @NonNull Throwable t) {
                        Toast.makeText(Coupon_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
