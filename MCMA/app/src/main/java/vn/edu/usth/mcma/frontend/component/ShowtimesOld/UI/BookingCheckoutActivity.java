package vn.edu.usth.mcma.frontend.component.ShowtimesOld.UI;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.CouponResponse;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.Seat.AvailableSeatResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Adapters.ComboDetailsAdapter;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Adapters.CouponAdapter;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Adapters.SeatDetailsAdapter;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Adapters.TicketDetailsAdapter;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models.Coupon;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Utils.PriceCalculator;
import vn.edu.usth.mcma.frontend.constant.IntentKey;

public class BookingCheckoutActivity extends AppCompatActivity {
    private Button buttonCoupon;
    private Coupon selectedCoupon;
    private TextView totalPriceCouponTV;
    private List<Coupon> couponList = new ArrayList<>();
    private int selectedMovieCouponId;
    private int selectedUserCouponId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_checkout);

        ImageButton backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(view -> onBackPressed());
        initializeViews();
        retrieveIntentExtras();
        setupCouponButton();
        updateCouponButton();
        updateTotalPriceWithCoupon();
        setupCheckoutButton();
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
        ApiService
                .getAccountApi(this)
                .getAllCouponByUser()
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<CouponResponse>> call, @NonNull Response<List<CouponResponse>> response) {
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
                    public void onFailure(@NonNull Call<List<CouponResponse>> call, @NonNull Throwable t) {
                        Log.e("CouponFetch", "Error fetching user coupons", t);
                    }
                });

        // Fetch coupons by movie
        ApiService
                .getMovieApi(this)
                .getAllCouponsByMovie(movieId)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<CouponResponse>> call, @NonNull Response<List<CouponResponse>> response) {
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
                    public void onFailure(@NonNull Call<List<CouponResponse>> call, @NonNull Throwable t) {
                        Log.e("CouponFetch", "Error fetching movie coupons", t);
                    }
                });

        return coupons;
    }

    @SuppressLint("DefaultLocale")
    private void retrieveIntentExtras() {
        totalPrice = getIntent().getDoubleExtra(IntentKey.TOTAL_PRICE_OF_SELECTED_CHOICE.name(), 0.0);
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

        String selectedMovie = getIntent().getStringExtra(IntentKey.MOVIE_NAME.name());
        if (selectedMovie != null) {
            movieTitleTV.setText(selectedMovie);
        }

        String selectedTheater = getIntent().getStringExtra(IntentKey.CINEMA_NAME.name());
        if (selectedTheater != null) {
            theaterNameTV.setText(selectedTheater);
        }

        String selectedDate = getIntent().getStringExtra(IntentKey.SELECTED_DATE.name());
        if (movieDateTV != null) {
            movieDateTV.setText(selectedDate);
        }

        // Screen number handling
        String selectedScreenRoom = getIntent().getStringExtra(IntentKey.SELECTED_SCREEN_ROOM.name());
        if (screenNumberTV != null) {
            screenNumberTV.setText(selectedScreenRoom != null ? selectedScreenRoom : "Screen 1");
        }

        String selectedShowtime = getIntent().getStringExtra(IntentKey.SELECTED_SHOWTIME.name());
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
        RecyclerView ticketDetailsRecyclerView = findViewById(R.id.ticket_details_recycler_view);
        if (ticketItems != null) {
            // Filter out ticket types with zero quantity and create TicketDetailsItems
            List<TicketDetailsAdapter.TicketDetailsItem> ticketDetailItems = ticketItems.stream()
                    .filter(item -> item.getQuantity() > 0)
                    .map(item -> new TicketDetailsAdapter.TicketDetailsItem(
                            item.getQuantity(),
                            item.getId(),
                            item.getUnitPrice() * item.getQuantity()
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
        RecyclerView seatDetailsRecyclerView = findViewById(R.id.seat_details_recycler_view);

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

    @SuppressLint("DefaultLocale")
    private void setCombosDetails() {
        TextView noOfCombosTV = findViewById(R.id.noOfCombos);
        noOfCombosTV.setText(String.format("%d combo(s)", totalComboCount));
        RecyclerView comboDetailsRecyclerView = findViewById(R.id.combo_details_recycler_view);

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
                        intent.putExtra(IntentKey.MOVIE_NAME.name(), getIntent().getStringExtra(IntentKey.MOVIE_NAME.name()));
                        intent.putExtra(IntentKey.CINEMA_NAME.name(), getIntent().getStringExtra(IntentKey.CINEMA_NAME.name()));
                        intent.putExtra(IntentKey.SELECTED_DATE.name(), getIntent().getStringExtra(IntentKey.SELECTED_DATE.name()));
                        intent.putExtra(IntentKey.SELECTED_SHOWTIME.name(), getIntent().getStringExtra(IntentKey.SELECTED_SHOWTIME.name()));
                        intent.putExtra(IntentKey.SELECTED_SCREEN_ROOM.name(), getIntent().getStringExtra(IntentKey.SELECTED_SCREEN_ROOM.name()));

                        // Booking
                        intent.putExtra(IntentKey.MOVIE_ID.name(), movieId);
                        intent.putExtra(IntentKey.SELECTED_CITY_ID.name(), selectedCityId);
                        intent.putExtra(IntentKey.SELECTED_CINEMA_ID.name(), selectedCinemaId);
                        intent.putExtra(IntentKey.SELECTED_SCREEN_ID.name(), selectedScreenId);
                        intent.putExtra(IntentKey.SELECTED_SCHEDULE_ID.name(), selectedScheduleId);
                        intent.putIntegerArrayListExtra("SELECTED_TICKET_IDS", new ArrayList<>(selectedTicketIds));
                        intent.putIntegerArrayListExtra("SELECTED_SEAT_IDS", new ArrayList<>(selectedSeatIds));
                        intent.putIntegerArrayListExtra("SELECTED_FOOD_IDS", new ArrayList<>(selectedFoodIds));
                        intent.putIntegerArrayListExtra("SELECTED_DRINK_IDS", new ArrayList<>(selectedDrinkIds));
                        if (selectedMovieCouponId != 0 && selectedMovieCouponId > 0) {
                            intent.putExtra(IntentKey.SELECTED_MOVIE_COUPON_ID.name(), selectedMovieCouponId);
                        }
                        if (selectedUserCouponId != 0 && selectedUserCouponId > 0) {
                            intent.putExtra(IntentKey.SELECTED_USER_COUPON_ID.name(), selectedUserCouponId);
                        }

                        startActivity(intent);
                    });
            builder.show();
        });
    }
}
