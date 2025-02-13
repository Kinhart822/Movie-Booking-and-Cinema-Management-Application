package vn.edu.usth.mcma.frontend.component.bookingsession;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.customview.item.PaymentMethodLayout;
import vn.edu.usth.mcma.frontend.component.customview.navigate.CustomNavigateButton;
import vn.edu.usth.mcma.frontend.dto.movie.MovieDetailShort2;
import vn.edu.usth.mcma.frontend.dto.response.ApiResponse;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.CouponResponse;
import vn.edu.usth.mcma.frontend.model.Booking;
import vn.edu.usth.mcma.frontend.model.item.PaymentMethodItem;
import vn.edu.usth.mcma.frontend.model.response.BankTransferForm;
import vn.edu.usth.mcma.frontend.model.response.PaymentMethodResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Adapters.CouponAdapter;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models.Coupon;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.network.apis.BookingApi;
import vn.edu.usth.mcma.frontend.utils.ImageDecoder;
import vn.edu.usth.mcma.frontend.utils.mapper.BookingMapper;
import vn.edu.usth.mcma.frontend.utils.mapper.ItemsOrderedMapper;
import vn.edu.usth.mcma.frontend.utils.mapper.PaymentMethodMapper;
import vn.edu.usth.mcma.frontend.utils.mapper.SeatMapper;
import vn.edu.usth.mcma.frontend.utils.mapper.SeatTypeMapper;

public class BookingCheckoutActivity extends AppCompatActivity {
    private static final String TAG = BookingCheckoutActivity.class.getName();
    private TextView timeRemainingTextView;
    private ImageView movieBannerImageView;
    private TextView totalPriceTextView;
    private LinearLayout paymentMethodsLinearLayout;
    private CheckBox termsAgreementCheckBox;
    private CustomNavigateButton nextButton;

    private Booking booking;
    private Handler timeRemainingHandler;
    private RecyclerView itemsOrderedRecyclerView;
    private List<PaymentMethodResponse> paymentMethodResponses;
    private PaymentMethodLayout selectedPaymentMethod;

    private Button buttonCoupon;
    private Coupon selectedCoupon;
    private TextView totalPriceCouponTV;
    private List<Coupon> couponList = new ArrayList<>();
    private int selectedMovieCouponId;
    private int selectedUserCouponId;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_checkout);
        ImageButton backButton = findViewById(R.id.button_back);
        timeRemainingTextView = findViewById(R.id.text_view_time_remaining);
        movieBannerImageView = findViewById(R.id.image_view_movie_banner);
        TextView movieNameTextView = findViewById(R.id.text_view_movie_name);
        TextView ratingDescriptionTextView = findViewById(R.id.text_view_movie_rating_description);
        TextView cinemaScreenTextView = findViewById(R.id.text_view_cinema_screen);
        TextView dateDurationTextView = findViewById(R.id.text_view_date_duration);
        TextView itemsOrderedSummaryTextView = findViewById(R.id.text_view_items_ordered_summary);
        totalPriceTextView = findViewById(R.id.text_view_total_price);
        itemsOrderedRecyclerView = findViewById(R.id.recycler_view_items_ordered);
        nextButton.findViewById(R.id.button_next);
        paymentMethodsLinearLayout = findViewById(R.id.linear_layout_payment_methods);

        backButton.setOnClickListener(view -> onBackPressed());

        booking = getIntent().getParcelableExtra(IntentKey.BOOKING.name(), Booking.class);

        assert booking != null;
        movieNameTextView.setText(booking.getMovieName());
        ratingDescriptionTextView.setText(booking.getRatingDescription());
        String[] screenNameDateDurationArray = booking.getScreenNameDateDuration().split("-");
        cinemaScreenTextView.setText(String.format("%s - %s", booking.getCinemaName(), screenNameDateDurationArray[0].trim()));
        dateDurationTextView.setText(screenNameDateDurationArray[1].trim());
        itemsOrderedSummaryTextView.setText(String.format("%d tickets + %d concessions", booking.getTotalAudience(), booking.getConcessions().size()));
        totalPriceTextView.setText(String.format("$%.1f", booking.getTotalPrice()));

        findMovieDetailShort2();
        prepareItemsOrderedRecyclerView();
        prepareTimeRemaining();
        findAllPaymentMethod();

//        couponList = fetchCoupons(movieId);todo
        setupCouponButton();
        updateCouponButton();
        updateTotalPriceWithCoupon();
        setupCheckoutButton();
    }
    private void findMovieDetailShort2() {
        ApiService
                .getMovieApi(this)
                .findMovieDetailShort2(booking.getMovieId())
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieDetailShort2> call, @NonNull Response<MovieDetailShort2> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "findMovieDetailShort2 onResponse: code not 200 || body is null");
                            return;
                        }
                        postFindMovieDetailShort2(response.body());
                    }
                    @Override
                    public void onFailure(@NonNull Call<MovieDetailShort2> call, @NonNull Throwable throwable) {
                        Log.e(TAG, "findMovieDetailShort2 onFailure: " + throwable);
                    }
                });
    }
    private void postFindMovieDetailShort2(MovieDetailShort2 movie) {
        if (movie.getBanner() != null) {
            Glide
                    .with(this)
                    .load(ImageDecoder.decode(movie.getBanner()))
                    .placeholder(R.drawable.placeholder1080x1920)
                    .error(R.drawable.placeholder1080x1920)
                    .into(movieBannerImageView);
        }
    }
    private void prepareItemsOrderedRecyclerView() {
        itemsOrderedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsOrderedRecyclerView.setAdapter(new ItemsOrderedAdapter(ItemsOrderedMapper.fromParcelableList(booking.getItemsOrdered())));
    }
    private void findAllPaymentMethod() {
        ApiService
                .getBookingApi(this)
                .findAllPaymentMethod()
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<PaymentMethodResponse>> call, @NonNull Response<List<PaymentMethodResponse>> response) {
                        if (!response.isSuccessful() || response.body() == null){
                            Log.e(TAG, "findAllPaymentMethod onResponse: code not 200 || body is null");
                            return;
                        }
                        paymentMethodResponses = response.body();
                        postFindAllPaymentMethod();
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<PaymentMethodResponse>> call, @NonNull Throwable throwable) {
                        Log.e(TAG, "findAllPaymentMethod onFailure: " + throwable);
                    }
                });
    }
    private void postFindAllPaymentMethod() {
        preparePaymentMethod();
    }
    private void preparePaymentMethod() {
        PaymentMethodMapper.fromResponseList(paymentMethodResponses)
                .forEach(item -> {
                    PaymentMethodLayout paymentMethodLayout = new PaymentMethodLayout(this);
                    paymentMethodLayout.init(item);
                    paymentMethodLayout.getRadioButton().setOnClickListener(v -> {
                        selectedPaymentMethod.getRadioButton().setChecked(false);
                        paymentMethodLayout.getRadioButton().setChecked(true);
                        selectedPaymentMethod = paymentMethodLayout;
                    });
                    paymentMethodsLinearLayout.addView(paymentMethodLayout);
                });
        selectedPaymentMethod = (PaymentMethodLayout) paymentMethodsLinearLayout.getChildAt(0);
    }

    private void setupCouponButton() {
//        buttonCoupon.setOnClickListener(v -> showCouponSelectionDialog());todo
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
//            buttonCoupon.setText("Select coupon");
        }
    }

    @SuppressLint("DefaultLocale")
    private void updateTotalPriceWithCoupon() {
        if (selectedCoupon != null) {
            // Calculate discounted price
            double discountPercentage = selectedCoupon.getDiscountPercentage();
//            double discountAmount = originalTotalPrice * discountPercentage;
//            double discountedPrice = originalTotalPrice - discountAmount;

            // Update total price with coupon TextView
//            totalPriceCouponTV.setText(String.format("$%.2f", discountedPrice));
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

    private void setupCheckoutButton() {
        Button checkoutButton = findViewById(R.id.checkout_button);
//        checkoutButton.setOnClickListener(v -> {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("You have 5 minutes to complete the payment.\n")
//                    .setCancelable(false)
//                    .setPositiveButton("OK", (dialog, id) -> {
//                        // Chuyển sang PayingMethodActivity
//                        Intent intent = new Intent(getApplicationContext(), PayingMethodActivity.class);
//                        intent.putExtra(IntentKey.MOVIE_NAME.name(), getIntent().getStringExtra(IntentKey.MOVIE_NAME.name()));
//                        intent.putExtra(IntentKey.CINEMA_NAME.name(), getIntent().getStringExtra(IntentKey.CINEMA_NAME.name()));
//                        intent.putExtra(IntentKey.SELECTED_DATE.name(), getIntent().getStringExtra(IntentKey.SELECTED_DATE.name()));
//                        intent.putExtra(IntentKey.SELECTED_SHOWTIME.name(), getIntent().getStringExtra(IntentKey.SELECTED_SHOWTIME.name()));
//                        intent.putExtra(IntentKey.SELECTED_SCREEN_ROOM.name(), getIntent().getStringExtra(IntentKey.SELECTED_SCREEN_ROOM.name()));
//
//                        if (selectedMovieCouponId != 0 && selectedMovieCouponId > 0) {
//                            intent.putExtra(IntentKey.SELECTED_MOVIE_COUPON_ID.name(), selectedMovieCouponId);
//                        }
//                        if (selectedUserCouponId != 0 && selectedUserCouponId > 0) {
//                            intent.putExtra(IntentKey.SELECTED_USER_COUPON_ID.name(), selectedUserCouponId);
//                        }
//
//                        startActivity(intent);
//                    });
//            builder.show();
//        });
    }
    private void prepareTimeRemaining() {
        timeRemainingHandler = new Handler(Looper.getMainLooper());
        Runnable timeRemainingRunnable = new Runnable() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                long timeRemaining = booking.getLimitPlusCurrentElapsedBootTime() - SystemClock.elapsedRealtime();
                if (timeRemaining <= 0) {
                    new androidx.appcompat.app.AlertDialog.Builder(BookingCheckoutActivity.this)
                            .setTitle("Timeout")
                            .setMessage("Your booking session has timed out. Returning to the showtime selection of this movie.")
                            .setPositiveButton("OK", (dialog, which) -> {
                                Intent intent = new Intent(BookingCheckoutActivity.this, BookingShowtimeSelectionActivity.class);
                                intent.putExtra(IntentKey.MOVIE_ID.name(), booking.getMovieId());
                                startActivity(intent);
                            })
                            .setCancelable(false)
                            .show();
                    return;
                }
                timeRemainingTextView.setText(String.format("%02d:%02d", timeRemaining / (60 * 1000), (timeRemaining / 1000) % 60));
                timeRemainingHandler.postDelayed(this, 1000);
            }
        };
        timeRemainingHandler.post(timeRemainingRunnable);
    }
    @SuppressLint("SetTextI18n")
    private void prepareNextButton() {
        nextButton.setText("Finish (4/4)");
        nextButton.setEnabled(selectedPaymentMethod.getRadioButton().isChecked() && termsAgreementCheckBox.isChecked());
        nextButton
                .setOnClickListener(v -> pendingPayment(() -> {
//                    booking = booking.toBuilder()
//                            .ratingDescription(scheduleDetail.getRatingDescription())
//                            .limitPlusCurrentElapsedBootTime(limitPlusCurrentElapsedBootTime)
//                            .seatTypes(SeatTypeMapper.fromItemMap(seatAdapter.getSeatTypes())).build()
//                            .setSeats(SeatMapper.fromItemList(seatAdapter.getSelectedSeats()));
//                    Intent intent = new Intent(this, BookingAudienceTypeSelectionActivity.class);
//                    intent.putExtra(IntentKey.BOOKING.name(), booking);
//                    startActivity(intent);
                }));
    }
    private void pendingPayment(BookingApi.PendingPaymentCallback callback) {
        ApiService
                .getBookingApi(this)
                .pendingPayment(
                        booking.getBookingId(),
                        BookingMapper.fromBooking(booking))
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<BankTransferForm> call, @NonNull Response<BankTransferForm> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "onResponse: code not 200 || body is null");
                            return;
                        }
//                        booking = booking.toBuilder()
//                                .bankTransferContent(response.body()).build();
                        callback.onWaitForPayment();
                    }
                    @Override
                    public void onFailure(@NonNull Call<BankTransferForm> call, @NonNull Throwable throwable) {
                        Log.e(TAG, "pendingPayment onFailure: " + throwable);
                    }
                });
    }
}
