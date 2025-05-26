package vn.edu.usth.mcma.frontend.component.bookingsession;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.customview.navigate.CustomNavigateButton;
import vn.edu.usth.mcma.frontend.model.item.AudienceTypeItem;
import vn.edu.usth.mcma.frontend.model.response.AudienceTypeResponse;
import vn.edu.usth.mcma.frontend.model.Booking;
import vn.edu.usth.mcma.frontend.network.ApiService;
import vn.edu.usth.mcma.frontend.model.parcelable.AudienceTypeParcelable;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.utils.mapper.AudienceTypeMapper;

public class BookingAudienceTypeSelectionActivity extends AppCompatActivity {
    private static final String TAG = BookingAudienceTypeSelectionActivity.class.getName();
    private static final double priceForStudent = 9;//todo dotenv
    private TextView timeRemainingTextView;
    private TextView totalAudienceCountTextView;
    private TextView totalPriceTextView;
    private CustomNavigateButton nextButton;

    private Booking booking;
    private List<AudienceTypeResponse> audienceDetails;
    private RecyclerView audienceTypeRecyclerView;
    private AudienceTypeAdapter audienceTypeAdapter;
    private int targetAudienceCount;
    private int currentAudienceCount;
    private double totalPrice;
    private Handler timeRemainingHandler;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_audience_type_selection);
        ImageButton backButton = findViewById(R.id.button_back);
        timeRemainingTextView = findViewById(R.id.text_view_time_remaining);
        TextView cinemaNameTextView = findViewById(R.id.text_view_cinema_name);
        TextView screenNameDateDurationTextView = findViewById(R.id.text_view_screen_name_date_duration);
        TextView movieNameTextView = findViewById(R.id.text_view_movie_name);
        TextView ratingTextView = findViewById(R.id.text_view_rating);
        TextView screenTypeTextView = findViewById(R.id.text_view_screen_type);
        totalAudienceCountTextView = findViewById(R.id.text_view_total_audience_count);
        totalPriceTextView = findViewById(R.id.text_view_total_price);
        nextButton = findViewById(R.id.button_next);
        audienceTypeRecyclerView = findViewById(R.id.recycler_view_audience_type);

        backButton
                .setOnClickListener(v -> onBackPressed());

        booking = getIntent().getParcelableExtra(IntentKey.BOOKING.name(), Booking.class);

        assert booking != null;
        cinemaNameTextView.setText(booking.getCinemaName());
        screenNameDateDurationTextView.setText(booking.getScreenNameDateDuration());
        movieNameTextView.setText(booking.getMovieName());
        ratingTextView.setText(booking.getRating());
        screenTypeTextView.setText(booking.getScreenType());

        targetAudienceCount = booking.getTotalAudience();
        currentAudienceCount = 0;
        totalPrice = booking.getTotalPrice();
        totalAudienceCountTextView.setText(String.format("%d / %d audiences", currentAudienceCount, targetAudienceCount));
        totalPriceTextView.setText(String.format("$%.1f", totalPrice));

        findAllAudienceTypeBySchedule();
        prepareTimeRemaining();
        prepareNextButton();
    }
    private void findAllAudienceTypeBySchedule() {
        ApiService
                .getBookingApi(this)
                .findAllAudienceTypeBySchedule(booking.getScheduleId())
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<AudienceTypeResponse>> call, @NonNull Response<List<AudienceTypeResponse>> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "findAllAudienceTypeBySchedule onResponse: code not 200 || body is null");
                            return;
                        }
                        audienceDetails = response.body();
                        postFindAllAudienceTypeByRating();
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<AudienceTypeResponse>> call, @NonNull Throwable throwable) {
                        Log.e(TAG, "findAllAudienceTypeBySchedule onFailure: " + throwable);
                    }
                });
    }
    private void postFindAllAudienceTypeByRating() {
        //todo: after audienceDiscount, do all this in adapter instead
        List<AudienceTypeItem> items = new ArrayList<>(AudienceTypeMapper.fromResponseList(audienceDetails));
        items.add(0, AudienceTypeItem.builder()
                .id("Student")
                .quantity(0)
                .unitPrice(priceForStudent)
                .build());
        audienceTypeAdapter = new AudienceTypeAdapter(
                items,
                this::onQuantityChangeListener,
                targetAudienceCount);
        audienceTypeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        audienceTypeRecyclerView.setAdapter(audienceTypeAdapter);
    }
    @SuppressLint("DefaultLocale")
    private void onQuantityChangeListener() {
        currentAudienceCount = audienceTypeAdapter.getCurrentAudienceCount();
        totalPrice = booking.getTotalPrice() + audienceTypeAdapter.getTotalAudienceTypePrice();
        totalAudienceCountTextView.setText(String.format("%d / %d audiences", currentAudienceCount, targetAudienceCount));
        totalPriceTextView.setText(String.format("$%.1f", totalPrice));
        nextButton.setEnabled(currentAudienceCount == targetAudienceCount);
    }
    private void prepareTimeRemaining() {
        timeRemainingHandler = new Handler(Looper.getMainLooper());
        Runnable timeRemainingRunnable = new Runnable() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                long timeRemaining = booking.getLimitPlusCurrentElapsedBootTime() - SystemClock.elapsedRealtime();
                if (timeRemaining <= 0) {
                    new AlertDialog.Builder(BookingAudienceTypeSelectionActivity.this)
                            .setTitle("Timeout")
                            .setMessage("Your booking session has timed out. Returning to the showtime selection of this movie.")
                            .setPositiveButton("OK", (dialog, which) -> {
                                Intent intent = new Intent(BookingAudienceTypeSelectionActivity.this, BookingShowtimeSelectionActivity.class);
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
        nextButton.setText("Next (2/4)");
        nextButton.setEnabled(false);
        nextButton
                .setOnClickListener(v -> {
                    //todo warning dialog cccd
                    booking = booking
                            .setAudienceTypes(AudienceTypeMapper.fromItemList(audienceTypeAdapter.getItems()));
                    Intent intent = new Intent(this, BookingConcessionSelectionActivity.class);
                    intent.putExtra(IntentKey.BOOKING.name(), booking);
                    startActivity(intent);
                });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeRemainingHandler.removeCallbacksAndMessages(null);
    }
}