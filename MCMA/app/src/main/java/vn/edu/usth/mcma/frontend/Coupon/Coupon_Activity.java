package vn.edu.usth.mcma.frontend.Coupon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;

public class Coupon_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CouponAdapter couponAdapter;
    private List<CouponItem> couponList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        recyclerView = findViewById(R.id.recyclerview_voucher_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        couponList = new ArrayList<>();
        couponList.add(new CouponItem("Coupon 1", 100, "https://example.com/image1.jpg"));
        couponList.add(new CouponItem("Coupon 2", 200, "https://example.com/image2.jpg"));
        couponList.add(new CouponItem("Coupon 3", 300, "https://example.com/image3.jpg"));

        couponAdapter = new CouponAdapter(this, couponList);
        recyclerView.setAdapter(couponAdapter);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> onBackPressed());

        LinearLayout myCouponButton = findViewById(R.id.my_coupon_button);
        myCouponButton.setOnClickListener(view -> {
            Intent intent = new Intent(Coupon_Activity.this, My_Voucher_Activity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
