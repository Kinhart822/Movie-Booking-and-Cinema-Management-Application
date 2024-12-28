package vn.edu.usth.mcma.frontend.Showtimes.UI;

import static vn.edu.usth.mcma.frontend.Showtimes.Models.SeatType.COUPLE;
import static vn.edu.usth.mcma.frontend.Showtimes.Models.SeatType.STAND;
import static vn.edu.usth.mcma.frontend.Showtimes.Models.SeatType.VIP;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
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
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.ComboDetailsAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.CouponAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.PaymentMethodAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.SeatDetailsAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.TicketDetailsAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Models.ComboItem;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Coupon;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Movie;
import vn.edu.usth.mcma.frontend.Showtimes.Models.MovieDetails;
import vn.edu.usth.mcma.frontend.Showtimes.Models.PaymentMethod;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Seat;
import vn.edu.usth.mcma.frontend.Showtimes.Models.SeatType;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Theater;
import vn.edu.usth.mcma.frontend.Showtimes.Models.TicketItem;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.PriceCalculator;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.MovieDataProvider;

public class PaymentBookingActivity extends AppCompatActivity {
    private RecyclerView ticketDetailsRecyclerView;
    private RecyclerView seatDetailsRecyclerView;
    private RecyclerView comboDetailsRecyclerView;
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
    private Button buttonCoupon;
    private Coupon selectedCoupon;
    private TextView totalPriceTV;
    private TextView totalPriceCouponTV;
    private double originalTotalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_booking);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> {
            onBackPressed();
        });

        // Retrieve intent extras
        retrieveIntentExtras();
        // Initialize and set up views
        initializeViews();
        setupCouponButton();
        // Set initial coupon to "Not use coupon"
        selectedCoupon = getCouponList().get(0);
        updateCouponButton();
        // Store the original total price
        originalTotalPrice = totalPrice;
        // Update total price with coupon
        updateTotalPriceWithCoupon();
        // Initialize views
        paymentMethodsRecyclerView = findViewById(R.id.paymentMethodsRecyclerView);
        termsCheckbox = findViewById(R.id.termsCheckbox);
        completePaymentButton = findViewById(R.id.completePaymentButton);
        // Setup RecyclerView
        setupPaymentMethodsRecyclerView();
        // Setup complete payment button
        completePaymentButton.setOnClickListener(v -> handlePaymentCompletion());
    }
    private void setupCouponButton() {
        buttonCoupon.setOnClickListener(v -> showCouponSelectionDialog());
    }

    private void showCouponSelectionDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.activity_coupon_booking, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        TextView cancelBtn = dialogView.findViewById(R.id.cancel_button);
        Button confirmBtn = dialogView.findViewById(R.id.confirm_button);
        RecyclerView couponDialogRecyclerView = dialogView.findViewById(R.id.coupon_recycler_view);

        List<Coupon> couponList = getCouponList();
        CouponAdapter dialogAdapter = new CouponAdapter(couponList);
        dialogAdapter.setOnCouponClickListener(coupon -> {
            selectedCoupon = coupon;
            dialogAdapter.setCurrentSelection(coupon);
        });

        // Set current selection in dialog
        dialogAdapter.setCurrentSelection(selectedCoupon);

        couponDialogRecyclerView.setAdapter(dialogAdapter);
        couponDialogRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        AlertDialog dialog = builder.create();

        cancelBtn.setOnClickListener(v -> dialog.dismiss());
        confirmBtn.setOnClickListener(v -> {
            updateCouponButton();
            updateTotalPriceWithCoupon();
            dialog.dismiss();
        });
        dialog.show();
    }

    private void updateCouponButton() {
        buttonCoupon.setText(selectedCoupon.getName());
    }

    private void updateTotalPriceWithCoupon() {
        // Calculate discounted price
        double discountPercentage = selectedCoupon.getDiscountPercentage();
        double discountAmount = originalTotalPrice * discountPercentage;
        double discountedPrice = originalTotalPrice - discountAmount;

        // Update total price with coupon TextView
        totalPriceCouponTV.setText(PriceCalculator.formatPrice(discountedPrice));
    }

    private List<Coupon> getCouponList() {
        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon("1", "Not use coupon"));
        coupons.add(new Coupon("2", "10%"));
        coupons.add(new Coupon("3", "20%"));
        coupons.add(new Coupon("4", "30%"));
        coupons.add(new Coupon("5", "40%"));
        coupons.add(new Coupon("6", "50%"));
        coupons.add(new Coupon("7", "60%"));
        coupons.add(new Coupon("8", "70%"));
        coupons.add(new Coupon("9", "80%"));
        coupons.add(new Coupon("10", "90%"));
        return coupons;
    }

    private void retrieveIntentExtras() {
        selectedMovie = (Movie) getIntent().getSerializableExtra("SELECTED_MOVIE");
        selectedTheater = (Theater) getIntent().getSerializableExtra("SELECTED_THEATER");
        String theaterName = getIntent().getStringExtra("THEATER_NAME");
        // Use the passed theater name if available
        if (selectedTheater != null) {
            selectedTheater.setName(theaterName != null ? theaterName : selectedTheater.getName());
        }
        selectedSeats = getIntent().getParcelableArrayListExtra("SELECTED_SEATS");
        selectedComboItems = getIntent().getParcelableArrayListExtra("SELECTED_COMBO_ITEMS");
        totalPrice = getIntent().getDoubleExtra("TOTAL_PRICE", 0);
    }
    private void initializeViews()   {
        // Movie Details
        TextView screenNumberTV = findViewById(R.id.screen_number);
        TextView movieTitleTV = findViewById(R.id.movieTitle);
        ImageView moviePosterIV = findViewById(R.id.moviePoster);
        TextView theaterNameTV = findViewById(R.id.theaterName);
        buttonCoupon = findViewById(R.id.coupon_button);
        totalPriceTV = findViewById(R.id.total_price);
        totalPriceCouponTV = findViewById(R.id.total_price_coupon);
        String selectedScreenRoom = getIntent().getStringExtra("SELECTED_SCREEN_ROOM");
        if (selectedScreenRoom != null) {
            screenNumberTV.setText(selectedScreenRoom);
        } else {
            screenNumberTV.setText("Screen 1"); // Default fallback
        }
        // Retrieve movie title
        String movieTitle = getIntent().getStringExtra("MOVIE_TITLE");
        movieTitleTV.setText(movieTitle);

        // Retrieve and set movie poster
        int movieBannerResId = getIntent().getIntExtra("MOVIE_BANNER", 0);
        if (movieBannerResId != 0) {
            moviePosterIV.setImageResource(movieBannerResId);
        }

        // Retrieve and set theater name
        if (selectedTheater != null) {
            theaterNameTV.setText(selectedTheater.getName());
        } else {
            // Fallback to intent extra
            String theaterName = getIntent().getStringExtra("THEATER_NAME");
            theaterNameTV.setText(theaterName != null ? theaterName : "Unknown Theater");
        }
        // Set date and showtime
        setDateAndShowtime();
        // Set seats and combo details
        setSeatsDetails();
        setCombosDetails();
        setTicketDetails();

        // Set total price
        TextView totalPriceTV = findViewById(R.id.total_price);
        totalPriceTV.setText(PriceCalculator.formatPrice(totalPrice));
    }

    private void setTicketDetails() {
        ticketDetailsRecyclerView = findViewById(R.id.ticket_details_recycler_view);
        List<TicketItem> ticketItems = getIntent().getParcelableArrayListExtra("TICKET_ITEMS");

        if (ticketItems != null) {
            // Filter out ticket types with zero quantity and create TicketDetailsItems
            List<TicketDetailsAdapter.TicketDetailsItem> ticketDetailItems = ticketItems.stream()
                    .filter(item -> item.getQuantity() > 0)
                    .map(item -> new TicketDetailsAdapter.TicketDetailsItem(
                            item.getQuantity(),
                            item.getType().getName(),
                            (int) item.getTotalPrice()
                    ))
                    .collect(Collectors.toList());

            // Only set up the adapter if there are ticket items
            if (!ticketDetailItems.isEmpty()) {
                TicketDetailsAdapter ticketDetailsAdapter = new TicketDetailsAdapter(ticketDetailItems);
                ticketDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                ticketDetailsRecyclerView.setAdapter(ticketDetailsAdapter);
            }
        }
    }

    private void setDateAndShowtime() {
        TextView movieDateTV = findViewById(R.id.movieDate);
        TextView movieShowtimeTV = findViewById(R.id.movieDuration);

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
        seatDetailsRecyclerView = findViewById(R.id.seat_details_recycler_view);

        if (selectedSeats != null) {
            noOfSeatsTV.setText(String.format("%d vé", selectedSeats.size()));

            // Prepare seat details for the adapter
            List<SeatDetailsAdapter.SeatDetailItem> seatDetailItems = new ArrayList<>();
            Map<SeatType, List<Seat>> seatsByType = groupSeatsByType(selectedSeats);

            for (Map.Entry<SeatType, List<Seat>> entry : seatsByType.entrySet()) {
                SeatType seatType = entry.getKey();
                List<Seat> typedSeats = entry.getValue();

                if (!typedSeats.isEmpty() && seatType != SeatType.SOLD) {
                    String seatPositions = typedSeats.stream()
                            .map(Seat::getId)
                            .collect(Collectors.joining(", "));

                    String seatTypeDisplay = getSeatTypeDisplay(seatType);
                    int seatPrice = getSeatTypePrice(seatType);

                    seatDetailItems.add(new SeatDetailsAdapter.SeatDetailItem(
                            String.format("%d x %s - 2D: %s",
                                    typedSeats.size(), seatTypeDisplay, seatPositions),
                            PriceCalculator.formatPrice(typedSeats.size() * seatPrice)
                    ));
                }
            }

            SeatDetailsAdapter seatDetailsAdapter = new SeatDetailsAdapter(seatDetailItems);
            seatDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            seatDetailsRecyclerView.setAdapter(seatDetailsAdapter);
        }
    }

    private Map<SeatType, List<Seat>> groupSeatsByType(List<Seat> seats) {
        return seats.stream()
                .collect(Collectors.groupingBy(Seat::getType));
    }

    private void setCombosDetails() {
        TextView noOfCombosTV = findViewById(R.id.noOfCombos);
        comboDetailsRecyclerView = findViewById(R.id.combo_details_recycler_view);

        if (selectedComboItems != null) {
            // Filter and count combos with quantity > 0
            List<ComboItem> filteredCombos = selectedComboItems.stream()
                    .filter(combo -> combo.getQuantity() > 0)
                    .collect(Collectors.toList());

            int totalComboCount = filteredCombos.stream().mapToInt(ComboItem::getQuantity).sum();
            noOfCombosTV.setText(String.format("%d combo", totalComboCount));

            // Prepare combo details for the adapter
            List<ComboDetailsAdapter.ComboDetailItem> comboDetailItems = filteredCombos.stream()
                    .map(combo -> new ComboDetailsAdapter.ComboDetailItem(
                            String.format("%d x %s", combo.getQuantity(), combo.getName()),
                            PriceCalculator.formatPrice(combo.getQuantity() * combo.getPrice())
                    ))
                    .collect(Collectors.toList());

            ComboDetailsAdapter comboDetailsAdapter = new ComboDetailsAdapter(comboDetailItems);
            comboDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            comboDetailsRecyclerView.setAdapter(comboDetailsAdapter);
        }
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

        // Hiển thị thông báo hoàn tất thanh toán
        Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();

        // Chuyển về HomeFragment
        Intent intent = new Intent(this, vn.edu.usth.mcma.frontend.MainActivity.class); // Thay `MainActivity` bằng activity chứa HomeFragment
        intent.putExtra("navigate_to", "HomeFragment"); // Gửi thông tin để chuyển đến HomeFragment
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        // Kết thúc PaymentBookingActivity
        finish();
    }

}
