package vn.edu.usth.mcma.frontend.Showtimes.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Request.BookingRequest;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.SendBookingResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs.BookingAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.PaymentMethodAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Booking.PaymentMethod;

public class PayingMethodActivity extends AppCompatActivity {
    private RecyclerView paymentMethodsRecyclerView;
    private PaymentMethodAdapter paymentMethodAdapter;
    private CheckBox termsCheckbox;
    private Button completePaymentButton;
    private PaymentMethod selectedPaymentMethodEnum;
    private vn.edu.usth.mcma.frontend.Showtimes.Models.PaymentMethod selectedPaymentMethod;
    private TextView movieTitleTV, theaterNameTV, screenNumberTV, movieDateTV, movieShowtimeTV;
    private int movieId;
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
    private TextView countdownTimer;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paying_method);

        movieId = getIntent().getIntExtra("MOVIE_ID", -1);
        selectedCityId = getIntent().getIntExtra("SELECTED_CITY_ID", -1);
        selectedCinemaId = getIntent().getIntExtra("SELECTED_CINEMA_ID", -1);
        selectedScreenId = getIntent().getIntExtra("SELECTED_SCREEN_ID", -1);
        selectedScheduleId = getIntent().getIntExtra("SELECTED_SCHEDULE_ID", -1);
        selectedTicketIds = getIntent().getIntegerArrayListExtra("SELECTED_TICKET_IDS");
        selectedSeatIds = getIntent().getIntegerArrayListExtra("SELECTED_SEAT_IDS");
        selectedFoodIds = getIntent().getIntegerArrayListExtra("SELECTED_FOOD_IDS");
        selectedDrinkIds = getIntent().getIntegerArrayListExtra("SELECTED_DRINK_IDS");
        selectedMovieCouponId = getIntent().getIntExtra("SELECTED_MOVIE_COUPON_ID", -1);
        selectedUserCouponId = getIntent().getIntExtra("SELECTED_USER_COUPON_ID", -1);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> onBackPressed());

        countdownTimer = findViewById(R.id.countdown_timer);
        startCountdownTimer();

        initializeViews();
        setupPaymentMethodsRecyclerView();
        handlePaymentCompletion();
    }

    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(5 * 60 * 1000, 1000) { // 5 phút
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 1000 / 60;
                long seconds = (millisUntilFinished / 1000) % 60;

                countdownTimer.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                Toast.makeText(PayingMethodActivity.this, "Time's up!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        };

        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
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
    }

//    private void setupPaymentMethodsRecyclerView() {
//        List<vn.edu.usth.mcma.frontend.Showtimes.Models.Booking.PaymentMethod> paymentMethods = Arrays.asList(vn.edu.usth.mcma.frontend.Showtimes.Models.Booking.PaymentMethod.Cash, vn.edu.usth.mcma.frontend.Showtimes.Models.Booking.PaymentMethod.Bank_Transfer);
//
//        paymentMethodAdapter = new PaymentMethodAdapter(paymentMethods);
//        paymentMethodAdapter.setOnPaymentMethodSelectedListener(paymentMethod -> {
//            selectedPaymentMethod = paymentMethod;
//            Toast.makeText(this, "Selected: " + paymentMethod.name(), Toast.LENGTH_SHORT).show();
//        });
//
//        paymentMethodsRecyclerView.setHasFixedSize(true);
//        paymentMethodsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        paymentMethodsRecyclerView.setAdapter(paymentMethodAdapter);
//    }

    private void setupPaymentMethodsRecyclerView() {
        List<vn.edu.usth.mcma.frontend.Showtimes.Models.PaymentMethod> paymentMethods = new ArrayList<>();
        paymentMethods.add(new vn.edu.usth.mcma.frontend.Showtimes.Models.PaymentMethod("Cổng VNPAY", R.drawable.ic_vnpay));
        paymentMethods.add(new vn.edu.usth.mcma.frontend.Showtimes.Models.PaymentMethod("Ví Momo", R.drawable.ic_momo));
        paymentMethods.add(new vn.edu.usth.mcma.frontend.Showtimes.Models.PaymentMethod("Ví Zalopay", R.drawable.ic_zalopay));
        paymentMethods.add(new vn.edu.usth.mcma.frontend.Showtimes.Models.PaymentMethod("Thẻ ATM nội địa (Internet Banking)", R.drawable.ic_atm));
        paymentMethods.add(new vn.edu.usth.mcma.frontend.Showtimes.Models.PaymentMethod("Thẻ quốc tế (Visa, Master, Amex, JCB)", R.drawable.ic_credit_card));
        paymentMethods.add(new vn.edu.usth.mcma.frontend.Showtimes.Models.PaymentMethod("Ví ShopeePay", R.drawable.ic_shopeepay));

        Set<String> uniqueNames = new LinkedHashSet<>();
        List<vn.edu.usth.mcma.frontend.Showtimes.Models.PaymentMethod> uniquePaymentMethods = new ArrayList<>();
        for (vn.edu.usth.mcma.frontend.Showtimes.Models.PaymentMethod method : paymentMethods) {
            if (uniqueNames.add(method.getName())) {
                uniquePaymentMethods.add(method);
            }
        }

        paymentMethodAdapter = new PaymentMethodAdapter(uniquePaymentMethods);
        paymentMethodAdapter.setOnPaymentMethodSelectedListener(paymentMethod -> {
            selectedPaymentMethod = paymentMethod;
            selectedPaymentMethodEnum = PaymentMethod.Bank_Transfer;
            Toast.makeText(this, "Selected: " + paymentMethod.getName(), Toast.LENGTH_SHORT).show();
        });

        paymentMethodsRecyclerView.setHasFixedSize(true);
        paymentMethodsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        paymentMethodsRecyclerView.setAdapter(paymentMethodAdapter);
    }


    private void handlePaymentCompletion() {
        completePaymentButton.setOnClickListener(v -> {
            if (selectedPaymentMethod == null) {
                Toast.makeText(this, "Please select payment method!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!termsCheckbox.isChecked()) {
                Toast.makeText(this, "Please agree to the terms!", Toast.LENGTH_SHORT).show();
                return;
            }

            sendBookingRequest();

            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

            Intent intent = new Intent(this, vn.edu.usth.mcma.frontend.MainActivity.class);
            intent.putExtra("navigate_to", "HomeFragment");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void sendBookingRequest() {
        BookingRequest.Builder builder = new BookingRequest.Builder(
                movieId, selectedCityId, selectedCinemaId, selectedScreenId, selectedScheduleId,
                selectedTicketIds, selectedSeatIds);

        if (!selectedFoodIds.isEmpty()) {
            builder.setFoodIds(selectedFoodIds);
        }
        if (!selectedDrinkIds.isEmpty()) {
            builder.setDrinkIds(selectedDrinkIds);
        }
        if (selectedMovieCouponId != 0 && selectedMovieCouponId > 0) {
            builder.setMovieCouponId(selectedMovieCouponId);
        }
        if (selectedUserCouponId != 0 && selectedUserCouponId > 0) {
            builder.setUserCouponId(selectedUserCouponId);
        }

        // Tạo đối tượng BookingRequest
        BookingRequest bookingRequest = builder.build();

        // Tạo đối tượng API
        RetrofitService retrofitService = new RetrofitService(this);
        BookingAPI bookingAPI = retrofitService.getRetrofit().create(BookingAPI.class);
        Call<SendBookingResponse> call = bookingAPI.processBooking(bookingRequest);

        // Gửi yêu cầu API
        call.enqueue(new Callback<SendBookingResponse>() {
            @Override
            public void onResponse(Call<SendBookingResponse> call, Response<SendBookingResponse> response) {
                if (response.isSuccessful()) {
                    SendBookingResponse sendBookingResponse = response.body();
                    if (sendBookingResponse != null) {
                        Toast.makeText(getApplicationContext(), "Process Booking successfully!", Toast.LENGTH_SHORT).show();
                        int bookingId = sendBookingResponse.getBookingId();
                        completeBooking(bookingId, selectedPaymentMethodEnum);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to Process Booking.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SendBookingResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void completeBooking(int bookingId, PaymentMethod paymentMethod) {
        BookingRequest bookingRequest = new BookingRequest(bookingId, paymentMethod);

        RetrofitService retrofitService = new RetrofitService(this);
        BookingAPI bookingAPI = retrofitService.getRetrofit().create(BookingAPI.class);
        Call<BookingResponse> call = bookingAPI.completeBooking(bookingRequest);

        // Gửi yêu cầu API
        call.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                if (response.isSuccessful()) {
                    BookingResponse bookingResponse = response.body();
                    if (bookingResponse != null) {
                        if (paymentMethod.equals(PaymentMethod.Bank_Transfer)) {
                            Toast.makeText(getApplicationContext(), "Complete Booking successfully!", Toast.LENGTH_SHORT).show();
                        } else if (paymentMethod.equals(PaymentMethod.Cash)) {
                            Toast.makeText(getApplicationContext(), "Your Booking Status will be Pending, and your seat(s) will be held.\n" +
                                    "Please go to your selected cinema to pay  for your booking before the start date of the movie!\n" +
                                    "Or else your booking will be canceled!!\"", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to Complete Booking.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}