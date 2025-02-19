package vn.edu.usth.mcma.frontend.Coupon;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import vn.edu.usth.mcma.R;
public class User_Coupon_Details_Activity extends AppCompatActivity {
    private int couponId;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_coupon_details);
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
        TextView expiredView = findViewById(R.id.dateExpired12);
        ImageView imageView = findViewById(R.id.voucher_image);
        nameView.setText(couponName);
        descriptionView.setText(description);
        pointsView.setText("Points to exchange: " + points + " " + "points");
        expiredView.setText("Date Expired: " + dateExpired);
        Glide.with(this)
                .load(backgroundImageUrl == null || backgroundImageUrl.isEmpty() ? R.drawable.couponhahas : backgroundImageUrl)
                .error(R.drawable.couponhahas)
                .into(imageView);
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> onBackPressed());
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}