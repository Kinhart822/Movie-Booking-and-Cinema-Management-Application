package vn.edu.usth.mcma.frontend.Coupon;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import vn.edu.usth.mcma.R;

public class Coupon_Details_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_details);

        ImageView voucherImage = findViewById(R.id.voucher_image);
        String imageUrl = getIntent().getStringExtra("imageUrl"); // Lấy imageUrl từ Intent
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this).load(imageUrl).into(voucherImage); // Dùng Glide để tải ảnh
        }

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> onBackPressed());

        Button exchangeButton = findViewById(R.id.exchange_voucher_button);
        exchangeButton.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Quay lại trang trước đó
    }
}
