package vn.edu.usth.mcma.frontend.Showtimes.UI;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Request.BookingRequest;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.CouponResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat.AvailableSeatResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.SendBookingResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs.BookingAPI;
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
    private String movieName;
    private String cinemaName;
    private int selectedCityId;
    private int selectedCinemaId;
    private int selectedScreenId;
    private int selectedScheduleId;
    private List<Integer> selectedTicketIds = new ArrayList<>();
    private List<Integer> selectedSeatIds = new ArrayList<>();
    private List<Integer> selectedFoodIds = new ArrayList<>();
    private List<Integer> selectedDrinkIds = new ArrayList<>();
    private int selectedMovieCouponId;
    private int selectedUserCouponId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_booking);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> {
            onBackPressed();
        });

        movieId = getIntent().getIntExtra("MOVIE_ID", -1);
        selectedCityId = getIntent().getIntExtra("SELECTED_CITY_ID", -1);
        selectedCinemaId = getIntent().getIntExtra("SELECTED_CINEMA_ID", -1);
        selectedScreenId = getIntent().getIntExtra("SELECTED_SCREEN_ID", -1);
        selectedScheduleId = getIntent().getIntExtra("SELECTED_SCHEDULE_ID", -1);
        selectedTicketIds =  getIntent().getIntegerArrayListExtra("SELECTED_TICKET_IDS");
        selectedSeatIds = getIntent().getIntegerArrayListExtra("SELECTED_SEAT_IDS");
        selectedFoodIds = getIntent().getIntegerArrayListExtra("SELECTED_FOOD_IDS");
        selectedDrinkIds = getIntent().getIntegerArrayListExtra("SELECTED_DRINK_IDS");

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

        CouponAdapter dialogAdapter = new CouponAdapter(couponList);
        dialogAdapter.setOnCouponClickListener(coupon -> {
            selectedCoupon = coupon;
            dialogAdapter.setCurrentSelection(coupon);
            if (coupon.getType() == 0) {
                selectedUserCouponId = coupon.getId();  // Lưu ID user coupon
                selectedMovieCouponId = -1;  // Reset ID movie coupon
            } else {
                selectedMovieCouponId = coupon.getId();  // Lưu ID movie coupon
                selectedUserCouponId = -1;  // Reset ID user coupon
            }
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

    @SuppressLint("SetTextI18n")
    private void updateCouponButton() {
        if (selectedCoupon != null) {
            buttonCoupon.setText(selectedCoupon.getName());
        } else {
            buttonCoupon.setText("Select coupon");
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
                    for (CouponResponse couponResponse : userCouponResponses) {
                        List<String> names = couponResponse.getCouponNameList();
                        List<String> descriptions = couponResponse.getCouponDescriptionList();
                        List<BigDecimal> discountRates = couponResponse.getDiscountRateList();

                        for (int i = 0; i < names.size(); i++) {
                            String name = names.get(i);
                            String description = descriptions.get(i);
                            double discountRate = discountRates.get(i).doubleValue();
                            int couponId = couponResponse.getCouponIds().get(i);

                            String couponName = String.format("%s - %s - %s%%", name, description, discountRate * 100);
                            coupons.add(new Coupon(couponName, 0, couponId));
                        }
                    }
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
                    for (CouponResponse couponResponse : movieCouponResponses) {
                        List<String> names = couponResponse.getCouponNameList();
                        List<String> descriptions = couponResponse.getCouponDescriptionList();
                        List<BigDecimal> discountRates = couponResponse.getDiscountRateList();

                        for (int i = 0; i < names.size(); i++) {
                            String name = names.get(i);
                            String description = descriptions.get(i);
                            double discountRate = discountRates.get(i).doubleValue();
                            int couponId = couponResponse.getCouponIds().get(i); // Lấy ID từ response

                            String couponName = String.format("%s - %s - %s%%", name, description, discountRate * 100);
                            coupons.add(new Coupon(couponName, 1, couponId));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CouponResponse>> call, Throwable t) {
                Log.e("CouponFetch", "Error fetching movie coupons", t);
            }
        });

        return coupons;
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
            movieName = selectedMovie;
        }

        String selectedTheater = getIntent().getStringExtra("CINEMA_NAME");
        if (selectedTheater != null) {
            theaterNameTV.setText(selectedTheater);
            cinemaName = selectedTheater;
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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You have 5 minutes to complete the payment.\n")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, id) -> {
                        // Chuyển sang PayingMethodActivity
                        Intent intent = new Intent(getApplicationContext(), PayingMethodActivity.class);
                        intent.putExtra("MOVIE_NAME", getIntent().getStringExtra("MOVIE_NAME"));
                        intent.putExtra("CINEMA_NAME", getIntent().getStringExtra("CINEMA_NAME"));
                        intent.putExtra("SELECTED_DATE", getIntent().getStringExtra("SELECTED_DATE"));
                        intent.putExtra("SELECTED_SHOWTIME", getIntent().getStringExtra("SELECTED_SHOWTIME"));
                        intent.putExtra("SELECTED_SCREEN_ROOM", getIntent().getStringExtra("SELECTED_SCREEN_ROOM"));

                        // Booking
                        intent.putExtra("MOVIE_ID", movieId);
                        intent.putExtra("SELECTED_CITY_ID", selectedCityId);
                        intent.putExtra("SELECTED_CINEMA_ID", selectedCinemaId);
                        intent.putExtra("SELECTED_SCREEN_ID", selectedScreenId);
                        intent.putExtra("SELECTED_SCHEDULE_ID", selectedScheduleId);
                        intent.putIntegerArrayListExtra("SELECTED_TICKET_IDS", new ArrayList<>(selectedTicketIds));
                        intent.putIntegerArrayListExtra("SELECTED_SEAT_IDS", new ArrayList<>(selectedSeatIds));
                        intent.putIntegerArrayListExtra("SELECTED_FOOD_IDS", new ArrayList<>(selectedFoodIds));
                        intent.putIntegerArrayListExtra("SELECTED_DRINK_IDS", new ArrayList<>(selectedDrinkIds));
                        if (selectedMovieCouponId != 0 && selectedMovieCouponId > 0) {
                            intent.putExtra("SELECTED_MOVIE_COUPON_ID", selectedMovieCouponId);
                        }
                        if (selectedUserCouponId != 0 && selectedUserCouponId > 0) {
                            intent.putExtra("SELECTED_USER_COUPON_ID", selectedUserCouponId);
                        }

                        startActivity(intent);
                    });
            builder.show();
        });
    }
}
