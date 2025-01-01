package vn.edu.usth.mcma.frontend.Showtimes.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.PaymentMethodAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Models.PaymentMethod;

public class PayingMethodActivity extends AppCompatActivity {
    private RecyclerView paymentMethodsRecyclerView;
    private PaymentMethodAdapter paymentMethodAdapter;
    private CheckBox termsCheckbox;
    private Button completePaymentButton;
    private PaymentMethod selectedPaymentMethod;
    private ImageView moviePosterIV;
    private TextView movieTitleTV, theaterNameTV, screenNumberTV, movieDateTV, movieShowtimeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paying_method);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> onBackPressed());

        initializeViews();
        setupPaymentMethodsRecyclerView();
        handlePaymentCompletion();
    }

    private void initializeViews() {
        movieTitleTV = findViewById(R.id.movieTitle);
        theaterNameTV = findViewById(R.id.theaterName);
        screenNumberTV = findViewById(R.id.screen_number);
        movieDateTV = findViewById(R.id.movieDate);
        movieShowtimeTV = findViewById(R.id.movieDuration);
        termsCheckbox = findViewById(R.id.termsCheckbox);
        completePaymentButton = findViewById(R.id.completePaymentButton);
        paymentMethodsRecyclerView = findViewById(R.id.paymentMethodsRecyclerView);

        // Set movie details from intent
        int movieBannerResId = getIntent().getIntExtra("MOVIE_BANNER", 0);
        if (movieBannerResId != 0) {
            moviePosterIV.setImageResource(movieBannerResId);
        }

        movieTitleTV.setText(getIntent().getStringExtra("MOVIE_TITLE"));
        theaterNameTV.setText(getIntent().getStringExtra("THEATER_NAME"));
        screenNumberTV.setText(getIntent().getStringExtra("SELECTED_SCREEN_ROOM"));

        setDateAndShowtime();
    }

    private void setDateAndShowtime() {
        // Set today's date
        SimpleDateFormat dateFormat = new SimpleDateFormat("d 'tháng' M, yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(new Date());
        movieDateTV.setText(formattedDate);

        // Set showtime
        String selectedShowtime = getIntent().getStringExtra("SELECTED_SHOWTIME");
        if (selectedShowtime != null) {
            String[] showtimeParts = selectedShowtime.split(":");
            int startHour = Integer.parseInt(showtimeParts[0]);
            int endHour = startHour + 2;
            movieShowtimeTV.setText(String.format("%02d:00 - %02d:00", startHour, endHour));
        }
    }

    private void setupPaymentMethodsRecyclerView() {
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        paymentMethods.add(new PaymentMethod("Cổng VNPAY", R.drawable.ic_vnpay));
        paymentMethods.add(new PaymentMethod("Ví Momo", R.drawable.ic_momo));
        paymentMethods.add(new PaymentMethod("Ví Zalopay", R.drawable.ic_zalopay));
        paymentMethods.add(new PaymentMethod("Thẻ ATM nội địa (Internet Banking)", R.drawable.ic_atm));
        paymentMethods.add(new PaymentMethod("Thẻ quốc tế (Visa, Master, Amex, JCB)", R.drawable.ic_credit_card));
        paymentMethods.add(new PaymentMethod("Ví ShopeePay", R.drawable.ic_shopeepay));

        Set<String> uniqueNames = new LinkedHashSet<>();
        List<PaymentMethod> uniquePaymentMethods = new ArrayList<>();
        for (PaymentMethod method : paymentMethods) {
            if (uniqueNames.add(method.getName())) {
                uniquePaymentMethods.add(method);
            }
        }

        paymentMethodAdapter = new PaymentMethodAdapter(uniquePaymentMethods);
        paymentMethodAdapter.setOnPaymentMethodSelectedListener(paymentMethod -> {
            selectedPaymentMethod = paymentMethod;
            Toast.makeText(this, "Selected: " + paymentMethod.getName(), Toast.LENGTH_SHORT).show();
        });

        paymentMethodsRecyclerView.setHasFixedSize(true);
        paymentMethodsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        paymentMethodsRecyclerView.setAdapter(paymentMethodAdapter);
    }

    private void handlePaymentCompletion() {
        completePaymentButton.setOnClickListener(v -> {
            if (selectedPaymentMethod == null) {
                Toast.makeText(this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!termsCheckbox.isChecked()) {
                Toast.makeText(this, "Vui lòng đồng ý với điều khoản", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, vn.edu.usth.mcma.frontend.MainActivity.class);
            intent.putExtra("navigate_to", "HomeFragment");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}