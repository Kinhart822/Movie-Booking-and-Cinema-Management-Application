package vn.edu.usth.mcma.frontend.Showtimes.UI;

import static vn.edu.usth.mcma.frontend.Showtimes.Models.SeatType.COUPLE;
import static vn.edu.usth.mcma.frontend.Showtimes.Models.SeatType.STAND;
import static vn.edu.usth.mcma.frontend.Showtimes.Models.SeatType.VIP;

import android.annotation.SuppressLint;
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
    public static final String EXTRA_THEATER = "extra_theater";
    public static final String EXTRA_MOVIE = "extra_movie";

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
    private int totalTicketCount;
    private int totalComboCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_booking);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> {
            onBackPressed();
        });

        initializeViews();
        retrieveIntentExtras();
        setupCouponButton();
        selectedCoupon = getCouponList().get(0);
        updateCouponButton();
        originalTotalPrice = totalPrice;
        updateTotalPriceWithCoupon();
        setupCheckoutButton();
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
//        selectedMovie = (Movie) getIntent().getSerializableExtra("SELECTED_MOVIE");
//        selectedTheater = (Theater) getIntent().getSerializableExtra("SELECTED_THEATER");
//        String theaterName = getIntent().getStringExtra("CINEMA_NAME");
//        // Use the passed theater name if available
//        if (selectedTheater != null) {
//            selectedTheater.setName(theaterName != null ? theaterName : selectedTheater.getName());
//        }
        selectedSeats = getIntent().getParcelableArrayListExtra("SELECTED_SEATS");
        selectedComboItems = getIntent().getParcelableArrayListExtra("SELECTED_COMBO_ITEMS");
        totalPrice = getIntent().getDoubleExtra("TOTAL_PRICE", 0);
    }

    private void initializeViews() {
        // Movie Details
        TextView screenNumberTV = findViewById(R.id.screen_number);
        TextView movieTitleTV = findViewById(R.id.movieTitle);
        TextView theaterNameTV = findViewById(R.id.theaterName);
        TextView movieDateTV = findViewById(R.id.movieDate);
        TextView movieShowtimeTV = findViewById(R.id.movieDuration);
        buttonCoupon = findViewById(R.id.coupon_button);
        totalPriceTV = findViewById(R.id.total_price);
        totalPriceCouponTV = findViewById(R.id.total_price_coupon);

        String selectedMovie = getIntent().getStringExtra("MOVIE_NAME");
        if (selectedMovie != null) {
            movieTitleTV.setText(selectedMovie);
        }

        String selectedTheater = getIntent().getStringExtra("CINEMA_NAME");
        if (selectedTheater != null) {
            theaterNameTV.setText(selectedTheater);
        }

        String selectedDate = getIntent().getStringExtra("SELECTED_DATE");
        if (movieDateTV != null) {
            movieDateTV.setText(selectedDate);
        }

        // Screen number handling
        String selectedScreenRoom = getIntent().getStringExtra("SELECTED_SCREEN_ROOM");
        if (screenNumberTV != null) {
            screenNumberTV.setText(selectedScreenRoom != null ? selectedScreenRoom : "Screen 1");
        }

        String selectedShowtime = getIntent().getStringExtra("SELECTED_SHOWTIME");
        if (movieShowtimeTV != null) {
            movieShowtimeTV.setText(selectedShowtime);
        }

        // Set seats and combo details
        //        setTicketDetails();
        setSeatsDetails();
//        setCombosDetails();

        // Set total price
        TextView totalPriceTV = findViewById(R.id.total_price);
        totalPriceTV.setText(PriceCalculator.formatPrice(totalPrice));
    }

//    private void setTicketDetails() {
//        ticketDetailsRecyclerView = findViewById(R.id.ticket_details_recycler_view);
//        List<TicketItem> ticketItems = getIntent().getParcelableArrayListExtra("TICKET_ITEMS");
//
//        if (ticketItems != null) {
//            // Filter out ticket types with zero quantity and create TicketDetailsItems
//            List<TicketDetailsAdapter.TicketDetailsItem> ticketDetailItems = ticketItems.stream()
//                    .filter(item -> item.getQuantity() > 0)
//                    .map(item -> new TicketDetailsAdapter.TicketDetailsItem(
//                            item.getQuantity(),
//                            item.getType().getName(),
//                            (int) item.getTotalPrice()
//                    ))
//                    .collect(Collectors.toList());
//
//            // Only set up the adapter if there are ticket items
//            if (!ticketDetailItems.isEmpty()) {
//                TicketDetailsAdapter ticketDetailsAdapter = new TicketDetailsAdapter(ticketDetailItems);
//                ticketDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//                ticketDetailsRecyclerView.setAdapter(ticketDetailsAdapter);
//            }
//        }
//    }

    @SuppressLint("DefaultLocale")
    private void setSeatsDetails() {
        TextView noOfSeatsTV = findViewById(R.id.noOfSeats);
        totalTicketCount = getIntent().getIntExtra("TOTAL_TICKET_COUNT", 0);
        seatDetailsRecyclerView = findViewById(R.id.seat_details_recycler_view);

        if (selectedSeats != null) {
            noOfSeatsTV.setText(String.format("%d ticket(s)", totalTicketCount));

//            // Prepare seat details for the adapter
//            List<SeatDetailsAdapter.SeatDetailItem> seatDetailItems = new ArrayList<>();
//            Map<SeatType, List<Seat>> seatsByType = groupSeatsByType(selectedSeats);
//
//            for (Map.Entry<SeatType, List<Seat>> entry : seatsByType.entrySet()) {
//                SeatType seatType = entry.getKey();
//                List<Seat> typedSeats = entry.getValue();
//
//                if (!typedSeats.isEmpty() && seatType != SeatType.SOLD) {
//                    String seatPositions = typedSeats.stream()
//                            .map(Seat::getId)
//                            .collect(Collectors.joining(", "));
//
//                    String seatTypeDisplay = getSeatTypeDisplay(seatType);
//                    int seatPrice = getSeatTypePrice(seatType);
//
//                    seatDetailItems.add(new SeatDetailsAdapter.SeatDetailItem(
//                            String.format("%d x %s - 2D: %s",
//                                    typedSeats.size(), seatTypeDisplay, seatPositions),
//                            PriceCalculator.formatPrice(typedSeats.size() * seatPrice)
//                    ));
//                }
//            }
//
//            SeatDetailsAdapter seatDetailsAdapter = new SeatDetailsAdapter(seatDetailItems);
//            seatDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//            seatDetailsRecyclerView.setAdapter(seatDetailsAdapter);
        }
    }
//
//    private Map<SeatType, List<Seat>> groupSeatsByType(List<Seat> seats) {
//        return seats.stream()
//                .collect(Collectors.groupingBy(Seat::getType));
//    }
//
    @SuppressLint("DefaultLocale")
    private void setCombosDetails() {
        TextView noOfCombosTV = findViewById(R.id.noOfCombos);
        totalComboCount = getIntent().getIntExtra("TOTAL_COMBO_COUNT", 0);
        comboDetailsRecyclerView = findViewById(R.id.combo_details_recycler_view);

        if (selectedComboItems != null) {
//            // Filter and count combos with quantity > 0
//            List<ComboItem> filteredCombos = selectedComboItems.stream()
//                    .filter(combo -> combo.getQuantity() > 0)
//                    .collect(Collectors.toList());
//
//            int totalComboCount = filteredCombos.stream().mapToInt(ComboItem::getQuantity).sum();
            noOfCombosTV.setText(String.format("%d combo(s)", totalComboCount));

//            // Prepare combo details for the adapter
//            List<ComboDetailsAdapter.ComboDetailItem> comboDetailItems = filteredCombos.stream()
//                    .map(combo -> new ComboDetailsAdapter.ComboDetailItem(
//                            String.format("%d x %s", combo.getQuantity(), combo.getName()),
//                            PriceCalculator.formatPrice(combo.getQuantity() * combo.getPrice())
//                    ))
//                    .collect(Collectors.toList());
//
//            ComboDetailsAdapter comboDetailsAdapter = new ComboDetailsAdapter(comboDetailItems);
//            comboDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//            comboDetailsRecyclerView.setAdapter(comboDetailsAdapter);
        }
    }

    // Utility methods
//    private String getSeatTypeDisplay(SeatType seatType) {
//        switch (seatType) {
//            case VIP:
//                return "VIP";
//            case COUPLE:
//                return "Couple";
//            case STAND:
//                return "Stand";
//            default:
//                return "Standard";
//        }
//    }
//
//    private int getSeatTypePrice(SeatType seatType) {
//        switch (seatType) {
//            case VIP:
//                return 150000;
//            case COUPLE:
//                return 200000;
//            case STAND:
//                return 100000;
//            default:
//                return 100000;
//        }
//    }

    private void setupCheckoutButton() {
        Button checkoutButton = findViewById(R.id.checkout_button);
        checkoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, PayingMethodActivity.class);

            // Pass necessary data to PayingMethodActivity
            int movieBannerResId = getIntent().getIntExtra("MOVIE_BANNER", 0);
            intent.putExtra("MOVIE_BANNER", movieBannerResId);
            intent.putExtra("MOVIE_TITLE", getIntent().getStringExtra("MOVIE_TITLE"));
            intent.putExtra("THEATER_NAME", getIntent().getStringExtra("THEATER_NAME"));
            intent.putExtra("SELECTED_SHOWTIME", getIntent().getStringExtra("SELECTED_SHOWTIME"));
            intent.putExtra("SELECTED_SCREEN_ROOM", getIntent().getStringExtra("SELECTED_SCREEN_ROOM"));

            startActivity(intent);
        });
    }

}
