package vn.edu.usth.mcma.frontend.component.bookingsession.steptwo;

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

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.bookingsession.MovieBookingActivity;
import vn.edu.usth.mcma.frontend.component.bookingsession.stepthree.ConcessionSelectionActivity;
import vn.edu.usth.mcma.frontend.component.customview.navigate.CustomNavigateButton;
import vn.edu.usth.mcma.frontend.dto.bookingsession.AudienceDetail;
import vn.edu.usth.mcma.frontend.model.Booking;
import vn.edu.usth.mcma.frontend.network.ApiService;
import vn.edu.usth.mcma.frontend.model.AudienceType;
import vn.edu.usth.mcma.frontend.constant.IntentKey;

public class AudienceTypeSelectionActivity extends AppCompatActivity {
    private static final String TAG = AudienceTypeSelectionActivity.class.getName();
    private static final double priceForStudent = 9;//todo dotenv
    private TextView timeRemainingTextView;
    private TextView totalAudienceCountTextView;
    private TextView totalPriceTextView;
    private CustomNavigateButton nextButton;

    private Booking booking;
    private List<AudienceDetail> audienceDetails;
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
        setContentView(R.layout.activity_audience_type_selection);
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

        booking = getIntent().getParcelableExtra(IntentKey.BOOKING.name());

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

        findAllAudienceTypeByRating();
        prepareTimeRemaining();
        prepareNextButton();
    }
    private void findAllAudienceTypeByRating() {
        ApiService
                .getBookingApi(this)
                .findAllAudienceTypeByRating(booking.getRating())
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<AudienceDetail>> call, @NonNull Response<List<AudienceDetail>> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "findAllAudienceTypeByRating onResponse: code not 200 || body is null");
                            return;
                        }
                        audienceDetails = response.body();
                        postFindAllAudienceTypeByRating();
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<AudienceDetail>> call, @NonNull Throwable throwable) {
                        Log.e(TAG, "findAllAudienceTypeByRating onFailure: " + throwable);
                    }
                });
    }
    private void postFindAllAudienceTypeByRating() {
        //todo: after audienceDiscount, do all this in adapter instead
        List<AudienceType> items = audienceDetails
                .stream()
                .map(a -> AudienceType.builder()
                        .id(a.getId())
                        .unitPrice(a.getUnitPrice())
                        .quantity(0)
                        .build())
                .collect(Collectors.toList());
        items.add(0, AudienceType.builder()
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
                long timeRemaining = getIntent().getLongExtra(IntentKey.BOOKING_TIME_LIMIT_PLUS_CURRENT_ELAPSED_BOOT_TIME.name(), -1L) - SystemClock.elapsedRealtime();
                if (timeRemaining <= 0) {
                    new AlertDialog.Builder(AudienceTypeSelectionActivity.this)
                            .setTitle("Timeout")
                            .setMessage("Your booking session has timed out. Returning to the showtime selection of this movie.")
                            .setPositiveButton("OK", (dialog, which) -> {
                                Intent intent = new Intent(AudienceTypeSelectionActivity.this, MovieBookingActivity.class);
                                intent.putExtra(IntentKey.MOVIE_ID.name(), getIntent().getLongExtra(IntentKey.MOVIE_ID.name(), -1L));
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
                    booking = booking.toBuilder()
                            .audienceTypes(audienceTypeAdapter.getItems())
                            .build();
                    Intent intent = new Intent(this, ConcessionSelectionActivity.class);
                    intent.putExtra(IntentKey.BOOKING.name(), booking);
                    intent.putExtra(IntentKey.BOOKING_TIME_LIMIT_PLUS_CURRENT_ELAPSED_BOOT_TIME.name(), getIntent().getLongExtra(IntentKey.BOOKING_TIME_LIMIT_PLUS_CURRENT_ELAPSED_BOOT_TIME.name(), -1L));
                    intent.putExtra(IntentKey.MOVIE_ID.name(), getIntent().getLongExtra(IntentKey.MOVIE_ID.name(), -1L));
                    startActivity(intent);
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeRemainingHandler.removeCallbacksAndMessages(null);
    }
}