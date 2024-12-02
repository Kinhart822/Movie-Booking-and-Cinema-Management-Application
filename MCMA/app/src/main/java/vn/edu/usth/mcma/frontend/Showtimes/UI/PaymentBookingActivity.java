package vn.edu.usth.mcma.frontend.Showtimes.UI;

import static vn.edu.usth.mcma.frontend.Showtimes.Models.SeatType.COUPLE;
import static vn.edu.usth.mcma.frontend.Showtimes.Models.SeatType.STAND;
import static vn.edu.usth.mcma.frontend.Showtimes.Models.SeatType.VIP;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.PaymentMethodAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Models.ComboItem;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Movie;
import vn.edu.usth.mcma.frontend.Showtimes.Models.MovieDetails;
import vn.edu.usth.mcma.frontend.Showtimes.Models.PaymentMethod;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Seat;
import vn.edu.usth.mcma.frontend.Showtimes.Models.SeatType;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Theater;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.PriceCalculator;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.MovieDataProvider;

public class PaymentBookingActivity extends AppCompatActivity {
    private RecyclerView paymentMethodsRecyclerView;
    private PaymentMethodAdapter paymentMethodAdapter;
    private CheckBox termsCheckbox;
    private Button completePaymentButton;
    private PaymentMethod selectedPaymentMethod;
    private Movie selectedMovie;
    private Theater selectedTheater;
    private List<Seat> selectedSeats;
    private List<ComboItem> selectedComboItems;
    private double totalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_booking);
        // Retrieve intent extras
        retrieveIntentExtras();

        // Initialize and set up views
        initializeViews();
        // Initialize views
        paymentMethodsRecyclerView = findViewById(R.id.paymentMethodsRecyclerView);
        termsCheckbox = findViewById(R.id.termsCheckbox);
        completePaymentButton = findViewById(R.id.completePaymentButton);
        // Setup RecyclerView
        setupPaymentMethodsRecyclerView();
        // Setup complete payment button
        completePaymentButton.setOnClickListener(v -> handlePaymentCompletion());
    }
    private void retrieveIntentExtras() {
        selectedMovie = (Movie) getIntent().getSerializableExtra("SELECTED_MOVIE");
        selectedTheater = (Theater) getIntent().getSerializableExtra("SELECTED_THEATER");
        selectedSeats = getIntent().getParcelableArrayListExtra("SELECTED_SEATS");
        selectedComboItems = getIntent().getParcelableArrayListExtra("SELECTED_COMBO_ITEMS");
        totalPrice = getIntent().getDoubleExtra("TOTAL_PRICE", 0);
    }

    private void initializeViews() {
        // Movie Details
        TextView movieTitleTV = findViewById(R.id.movieTitle);
        ImageView moviePosterIV = findViewById(R.id.moviePoster);
        TextView theaterNameTV = findViewById(R.id.theaterName);

        // Set movie title
        if (selectedMovie != null) {
            movieTitleTV.setText(selectedMovie.getTitle());

            // Set movie poster from MovieDataProvider
            MovieDetails movieDetails = MovieDataProvider.getMovieDetails(selectedMovie.getTitle());
            if (movieDetails != null) {
                moviePosterIV.setImageResource(movieDetails.getBannerImageResId());
            }
        }

        // Set theater name
        if (selectedTheater != null) {
            theaterNameTV.setText(selectedTheater.getName());
        }

        // Set date and showtime
        setDateAndShowtime();
        // Set seats and combo details
        setSeatsDetails();
        setCombosDetails();

        // Set total price
        TextView totalPriceTV = findViewById(R.id.total_price);
        totalPriceTV.setText(PriceCalculator.formatPrice(totalPrice));
    }

    private void setDateAndShowtime() {
        TextView movieDateTV = findViewById(R.id.movieDate);
        TextView movieShowtimeTV = findViewById(R.id.movieShowtime);

        // Set today's date
        SimpleDateFormat dateFormat = new SimpleDateFormat("d 'tháng' M, yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(new Date());
        movieDateTV.setText(formattedDate);

        // Set showtime (assuming it's passed from previous activity)
        String selectedShowtime = getIntent().getStringExtra("SELECTED_SHOWTIME");
        if (selectedShowtime != null) {
            String[] showtimeParts = selectedShowtime.split(":");
            int startHour = Integer.parseInt(showtimeParts[0]);
            int endHour = startHour + 2;
            movieShowtimeTV.setText(String.format("%02d:00 - %02d:00", startHour, endHour));
        }
    }

    private void setSeatsDetails() {
        TextView noOfSeatsTV = findViewById(R.id.noOfSeats);
        LinearLayout seatDetailsLayout = findViewById(R.id.seat_details_layout);

        if (selectedSeats != null) {
            // Set number of seats
            noOfSeatsTV.setText(String.format("%d vé", selectedSeats.size()));

            // Group seats by type
            Map<SeatType, List<Seat>> seatsByType = groupSeatsByType(selectedSeats);

            // Clear any existing views
            seatDetailsLayout.removeAllViews();

            // Add seat type details
            for (Map.Entry<SeatType, List<Seat>> entry : seatsByType.entrySet()) {
                SeatType seatType = entry.getKey();
                List<Seat> typedSeats = entry.getValue();

                if (!typedSeats.isEmpty() && seatType != SeatType.SOLD) {
                    TextView seatTypeTV = createSeatTypeTextView(typedSeats, seatType);
                    seatDetailsLayout.addView(seatTypeTV);

                    TextView seatPriceTV = createSeatPriceTextView(typedSeats, seatType);
                    seatDetailsLayout.addView(seatPriceTV);
                }
            }
        }
    }

    private Map<SeatType, List<Seat>> groupSeatsByType(List<Seat> seats) {
        return seats.stream()
                .collect(Collectors.groupingBy(Seat::getType));
    }

    private TextView createSeatTypeTextView(List<Seat> seats, SeatType seatType) {
        TextView textView = new TextView(this);
        String seatPositions = seats.stream()
                .map(Seat::getId)
                .collect(Collectors.joining(", "));

        textView.setText(String.format("%d x Adult - %s - 2D: %s",
                seats.size(),
                getSeatTypeDisplay(seatType),
                seatPositions));

        return textView;
    }

    private TextView createSeatPriceTextView(List<Seat> seats, SeatType seatType) {
        TextView textView = new TextView(this);
        int seatPrice = getSeatTypePrice(seatType);
        int totalPrice = seats.size() * seatPrice;

        textView.setText(PriceCalculator.formatPrice(totalPrice));

        return textView;
    }

    private void setCombosDetails() {
        TextView noOfCombosTV = findViewById(R.id.noOfCombos);
        LinearLayout comboDetailsLayout = findViewById(R.id.combo_details_layout);

        if (selectedComboItems != null) {
            // Filter and count combos with quantity > 0
            List<ComboItem> filteredCombos = selectedComboItems.stream()
                    .filter(combo -> combo.getQuantity() > 0)
                    .collect(Collectors.toList());

            // Set number of combos
            noOfCombosTV.setText(String.format("%d combo",
                    filteredCombos.stream().mapToInt(ComboItem::getQuantity).sum()));

            // Clear any existing views
            comboDetailsLayout.removeAllViews();

            // Add combo details
            for (ComboItem combo : filteredCombos) {
                TextView comboNameTV = createComboNameTextView(combo);
                comboDetailsLayout.addView(comboNameTV);

                TextView comboPriceTV = createComboPriceTextView(combo);
                comboDetailsLayout.addView(comboPriceTV);
            }
        }
    }

    private TextView createComboNameTextView(ComboItem combo) {
        TextView textView = new TextView(this);
        textView.setText(String.format("%d x %s",
                combo.getQuantity(),
                combo.getName()));
        return textView;
    }

    private TextView createComboPriceTextView(ComboItem combo) {
        TextView textView = new TextView(this);
        double totalComboPrice = combo.getQuantity() * combo.getPrice();
        textView.setText(PriceCalculator.formatPrice(totalComboPrice));
        return textView;
    }

    // Utility methods
    private String getSeatTypeDisplay(SeatType seatType) {
        switch (seatType) {
            case VIP: return "VIP";
            case COUPLE: return "Couple";
            case STAND: return "Stand";
            default: return "Standard";
        }
    }

    private int getSeatTypePrice(SeatType seatType) {
        switch (seatType) {
            case VIP: return 150000;
            case COUPLE: return 200000;
            case STAND: return 100000;
            default: return 100000;
        }
    }

    private void setupPaymentMethodsRecyclerView() {
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        // Add payment methods
        paymentMethods.add(new PaymentMethod("Cổng VNPAY", R.drawable.ic_vnpay));
        paymentMethods.add(new PaymentMethod("Ví Momo", R.drawable.ic_momo));
        paymentMethods.add(new PaymentMethod("Ví Zalopay", R.drawable.ic_zalopay));
        paymentMethods.add(new PaymentMethod("Thẻ ATM nội địa (Internet Banking)", R.drawable.ic_atm));
        paymentMethods.add(new PaymentMethod("Thẻ quốc tế (Visa, Master, Amex, JCB)", R.drawable.ic_credit_card));
        paymentMethods.add(new PaymentMethod("Ví ShopeePay", R.drawable.ic_shopeepay));
        // Remove duplicates while maintaining order
        Set<String> uniqueNames = new LinkedHashSet<>();
        List<PaymentMethod> uniquePaymentMethods = new ArrayList<>();
        for (PaymentMethod method : paymentMethods) {
            if (uniqueNames.add(method.getName())) {
                uniquePaymentMethods.add(method);
            }
        }
        // Setup adapter with unique payment methods
        paymentMethodAdapter = new PaymentMethodAdapter(uniquePaymentMethods);
        paymentMethodAdapter.setOnPaymentMethodSelectedListener(paymentMethod -> {
            selectedPaymentMethod = paymentMethod;
            // Optionally show a selection toast
            Toast.makeText(this, "Selected: " + paymentMethod.getName(), Toast.LENGTH_SHORT).show();
        });
        paymentMethodsRecyclerView.setHasFixedSize(true);
        paymentMethodsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        paymentMethodsRecyclerView.setAdapter(paymentMethodAdapter);
    }
    private void handlePaymentCompletion() {
        if (selectedPaymentMethod == null) {
            Toast.makeText(this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!termsCheckbox.isChecked()) {
            Toast.makeText(this, "Vui lòng đồng ý với điều khoản", Toast.LENGTH_SHORT).show();
            return;
        }
        // Proceed with payment
        Toast.makeText(this, "Đang xử lý thanh toán với " + selectedPaymentMethod.getName(),
                Toast.LENGTH_SHORT).show();
        // Add your payment processing logic here
    }
}
