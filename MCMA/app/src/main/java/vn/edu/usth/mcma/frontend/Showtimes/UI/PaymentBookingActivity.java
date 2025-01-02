package vn.edu.usth.mcma.frontend.Showtimes.UI;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.CouponResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat.AvailableSeatResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs.GetAllCouponAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.ComboDetailsAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.CouponAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.SeatDetailsAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.TicketDetailsAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Models.ComboItem;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Coupon;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Seat;
import vn.edu.usth.mcma.frontend.Showtimes.Models.SeatType;
import vn.edu.usth.mcma.frontend.Showtimes.Models.TicketItem;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.PriceCalculator;

public class PaymentBookingActivity extends AppCompatActivity {
    private RecyclerView ticketDetailsRecyclerView;
    private RecyclerView seatDetailsRecyclerView;
    private RecyclerView comboDetailsRecyclerView;
    private List<AvailableSeatResponse> selectedSeats = new ArrayList<>();
    private List<ComboItem> selectedComboItems;
    private double totalPrice;
    private Button buttonCoupon;
    private Coupon selectedCoupon;
    private TextView totalPriceTV;
    private TextView totalPriceCouponTV;
    private double originalTotalPrice;
    private int totalTicketCount;
    private int totalComboCount;
    private List<TicketItem> ticketItems = new ArrayList<>();
    private double totalTicketPrice;
    private List<ComboItem> comboItems = new ArrayList<>();
    private int movieId;
    private List<Coupon> couponList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_booking);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> {
            onBackPressed();
        });

        movieId = getIntent().getIntExtra("MOVIE_ID", -1);

        totalTicketCount = getIntent().getIntExtra("TOTAL_TICKET_COUNT", 0);
        Log.d("PaymentBookingActivity", "Total Ticket Count: " + totalTicketCount);

        ticketItems = getIntent().getParcelableArrayListExtra("SELECTED_TICKET_ITEMS");
        Log.d("PaymentBookingActivity", "SELECTED_TICKET_ITEMS received: " + ticketItems);

        totalTicketPrice = getIntent().getDoubleExtra("TOTAL_TICKET_PRICE", 0.0);
        Log.d("PaymentBookingActivity", "TOTAL_TICKET_PRICE received: " + totalTicketPrice);

        totalComboCount = getIntent().getIntExtra("TOTAL_COMBO_COUNT", 0);
        Log.d("PaymentBookingActivity", "Total Combo Count: " + totalComboCount);

        comboItems = getIntent().getParcelableArrayListExtra("SELECTED_COMBO_ITEMS");
        Log.d("PaymentBookingActivity", "SELECTED_COMBO_ITEMS received: " + comboItems);

        selectedSeats = getIntent().getParcelableArrayListExtra("SELECTED_SEAT_ITEMS");
        Log.d("PaymentBookingActivity", "SELECTED_SEAT_ITEMS received: " + selectedSeats);

        initializeViews();
        retrieveIntentExtras();
        couponList = fetchCoupons(movieId);
        setupCouponButton();
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

        // Wait until coupons are fetched before showing the dialog
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
        if (selectedCoupon != null) {
            buttonCoupon.setText(selectedCoupon.getName());
        }
    }

    @SuppressLint("DefaultLocale")
    private void updateTotalPriceWithCoupon() {
        if (selectedCoupon != null) {
            // Calculate discounted price
            double discountPercentage = selectedCoupon.getDiscountPercentage();
            double discountAmount = originalTotalPrice * discountPercentage;
            double discountedPrice = originalTotalPrice - discountAmount;

            // Update total price with coupon TextView
            totalPriceCouponTV.setText(String.format("$%.2f", discountedPrice));
        }
    }

    private List<Coupon> fetchCoupons(int movieId) {
        List<Coupon> coupons = new ArrayList<>();
        RetrofitService retrofitService = new RetrofitService(this);
        GetAllCouponAPI apiService = retrofitService.getRetrofit().create(GetAllCouponAPI.class);

        // Fetch coupons by user
        Call<List<CouponResponse>> userCouponsCall = apiService.getAllCouponByUser();
        userCouponsCall.enqueue(new Callback<List<CouponResponse>>() {
            @Override
            public void onResponse(Call<List<CouponResponse>> call, Response<List<CouponResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CouponResponse> userCouponResponses = response.body();
                    processCoupons(coupons, userCouponResponses);
                }
            }

            @Override
            public void onFailure(Call<List<CouponResponse>> call, Throwable t) {
                Log.e("CouponFetch", "Error fetching user coupons", t);
            }
        });

        // Fetch coupons by movie
        Call<List<CouponResponse>> movieCouponsCall = apiService.getAllCouponsByMovie(movieId);
        movieCouponsCall.enqueue(new Callback<List<CouponResponse>>() {
            @Override
            public void onResponse(Call<List<CouponResponse>> call, Response<List<CouponResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CouponResponse> movieCouponResponses = response.body();
                    processCoupons(coupons, movieCouponResponses);
                }
            }

            @Override
            public void onFailure(Call<List<CouponResponse>> call, Throwable t) {
                Log.e("CouponFetch", "Error fetching movie coupons", t);
            }
        });

        return coupons;
    }

    private void processCoupons(List<Coupon> coupons, List<CouponResponse> couponResponses) {
        for (CouponResponse response : couponResponses) {
            List<String> names = response.getCouponNameList();
            List<String> descriptions = response.getCouponDescriptionList();
            List<BigDecimal> discountRates = response.getDiscountRateList();

            for (int i = 0; i < names.size(); i++) {
                String name = names.get(i);
                String description = descriptions.get(i);
                double discountRate = discountRates.get(i).doubleValue();

                String couponName = String.format("%s - %s - %s%%", name, description, discountRate * 100);
                coupons.add(new Coupon(couponName));
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private void retrieveIntentExtras() {
        totalPrice = getIntent().getDoubleExtra("TOTAL_PRICE_OF_SELECTED_CHOICE", 0.0);
        totalPriceTV.setText(String.format("$%.2f", totalPrice));
        totalPriceCouponTV.setText(String.format("$%.2f", totalPrice));
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
        setTicketDetails();
        setSeatsDetails();
        setCombosDetails();

        // Set total price
        TextView totalPriceTV = findViewById(R.id.total_price);
        totalPriceTV.setText(PriceCalculator.formatPrice(totalPrice));
    }

    private void setTicketDetails() {
        ticketDetailsRecyclerView = findViewById(R.id.ticket_details_recycler_view);
        if (ticketItems != null) {
            // Filter out ticket types with zero quantity and create TicketDetailsItems
            List<TicketDetailsAdapter.TicketDetailsItem> ticketDetailItems = ticketItems.stream()
                    .filter(item -> item.getQuantity() > 0)
                    .map(item -> new TicketDetailsAdapter.TicketDetailsItem(
                            item.getQuantity(),
                            item.getType().getName(),
                            item.getTotalPrice()
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

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void setSeatsDetails() {
        TextView noOfSeatsTV = findViewById(R.id.noOfSeats);
        noOfSeatsTV.setText(String.format("%d ticket(s)", totalTicketCount));
        seatDetailsRecyclerView = findViewById(R.id.seat_details_recycler_view);

        if (selectedSeats != null) {
            List<SeatDetailsAdapter.SeatDetailItem> seatDetailItems = new ArrayList<>();

            Map<String, List<AvailableSeatResponse>> seatsByType = selectedSeats.stream()
                    .filter(seat -> !"Unavailable".equalsIgnoreCase(seat.getSeatStatus()) && !"Held".equalsIgnoreCase(seat.getSeatStatus()))
                    .collect(Collectors.groupingBy(AvailableSeatResponse::getAvailableSeatsType));

            for (Map.Entry<String, List<AvailableSeatResponse>> entry : seatsByType.entrySet()) {
                String seatType = entry.getKey();
                List<AvailableSeatResponse> typedSeats = entry.getValue();

                if (!typedSeats.isEmpty()) {
                    String seatPositions = typedSeats.stream()
                            .map(seat -> String.valueOf(seat.getAvailableSeat()))
                            .collect(Collectors.joining(", "));

                    double seatPrice = typedSeats.get(0).getSeatPrice();

                    seatDetailItems.add(new SeatDetailsAdapter.SeatDetailItem(
                            String.format("%d x %s - %s",
                                    typedSeats.size(), seatType, seatPositions),
                            PriceCalculator.formatPrice(typedSeats.size() * seatPrice)
                    ));
                }
            }

            // Gắn adapter và RecyclerView
            SeatDetailsAdapter seatDetailsAdapter = new SeatDetailsAdapter(seatDetailItems);
            seatDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            seatDetailsRecyclerView.setAdapter(seatDetailsAdapter);
        }
    }

    private Map<SeatType, List<Seat>> groupSeatsByType(List<Seat> seats) {
        return seats.stream()
                .collect(Collectors.groupingBy(Seat::getType));
    }

    @SuppressLint("DefaultLocale")
    private void setCombosDetails() {
        TextView noOfCombosTV = findViewById(R.id.noOfCombos);
        noOfCombosTV.setText(String.format("%d combo(s)", totalComboCount));
        comboDetailsRecyclerView = findViewById(R.id.combo_details_recycler_view);

        if (comboItems != null) {
            List<ComboDetailsAdapter.ComboDetailItem> comboDetailItems = comboItems.stream()
                    .filter(combo -> combo.getQuantity() > 0)
                    .map(combo -> new ComboDetailsAdapter.ComboDetailItem(
                            String.format("%d x %s", combo.getQuantity(), combo.getName()),
                            combo.getQuantity() * combo.getPrice()
                    ))
                    .collect(Collectors.toList());

            ComboDetailsAdapter comboDetailsAdapter = new ComboDetailsAdapter(comboDetailItems);
            comboDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            comboDetailsRecyclerView.setAdapter(comboDetailsAdapter);
        }
    }

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
