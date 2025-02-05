package vn.edu.usth.mcma.frontend.component.bookingsession;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.TextView;
import android.widget.ImageButton;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.UI.BookingCheckoutActivity;
import vn.edu.usth.mcma.frontend.component.customview.navigate.CustomNavigateButton;
import vn.edu.usth.mcma.frontend.model.response.ConcessionResponse;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.model.Booking;
import vn.edu.usth.mcma.frontend.network.ApiService;
import vn.edu.usth.mcma.frontend.utils.mapper.ConcessionMapper;

public class BookingConcessionSelectionActivity extends AppCompatActivity {
    private static final String TAG = BookingConcessionSelectionActivity.class.getName();
    private TextView timeRemainingTextView;
    private TextView totalPriceTextView;
    private CustomNavigateButton nextButton;

    private Booking booking;
    private List<ConcessionResponse> concessionResponses;
    private RecyclerView concessionRecyclerView;
    private BookingConcessionAdapter bookingConcessionAdapter;
    private double totalPrice;
    private Handler timeRemainingHandler;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_concession_selection);
        ImageButton backButton = findViewById(R.id.button_back);
        timeRemainingTextView = findViewById(R.id.text_view_time_remaining);
        TextView cinemaNameTextView = findViewById(R.id.text_view_cinema_name);
        TextView screenNameDateDurationTextView = findViewById(R.id.text_view_screen_name_date_duration);
        TextView movieNameTextView = findViewById(R.id.text_view_movie_name);
        TextView ratingTextView = findViewById(R.id.text_view_rating);
        TextView screenTypeTextView = findViewById(R.id.text_view_screen_type);
        TextView totalAudienceTextView = findViewById(R.id.text_view_total_audience);
        totalPriceTextView = findViewById(R.id.text_view_total_price);
        nextButton = findViewById(R.id.button_next);
        concessionRecyclerView = findViewById(R.id.recycler_view_concession);

        backButton
                .setOnClickListener(v -> onBackPressed());

        booking = getIntent().getParcelableExtra(IntentKey.BOOKING.name());

        assert booking != null;
        cinemaNameTextView.setText(booking.getCinemaName());
        screenNameDateDurationTextView.setText(booking.getScreenNameDateDuration());
        movieNameTextView.setText(booking.getMovieName());
        ratingTextView.setText(booking.getRating());
        screenTypeTextView.setText(booking.getScreenType());
        totalAudienceTextView.setText(String.format("%d audiences", booking.getTotalAudience()));

        totalPrice = booking.getTotalPrice();
        totalPriceTextView.setText(String.format("$%.1f", totalPrice));

        findAllConcessionBySchedule();
        prepareTimeRemaining();
        prepareNextButton();
    }
    private void findAllConcessionBySchedule() {
        ApiService
                .getBookingApi(this)
                .findAllConcessionBySchedule(booking.getScheduleId())
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ConcessionResponse>> call, @NonNull Response<List<ConcessionResponse>> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "findAllConcessionBySchedule onResponse: code not 200 || body is null");
                            return;
                        }
                        concessionResponses = response.body();
                        postFindAllConcessionBySchedule();
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<ConcessionResponse>> call, @NonNull Throwable throwable) {
                        Log.e(TAG, "findAllConcessionBySchedule onFailure: " + throwable);
                    }
                });
    }
    private void postFindAllConcessionBySchedule() {
        bookingConcessionAdapter = new BookingConcessionAdapter(
                this,
                ConcessionMapper.fromResponseList(concessionResponses),
                this::onConcessionClickListener);
        concessionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        concessionRecyclerView.setAdapter(bookingConcessionAdapter);
    }
    @SuppressLint("DefaultLocale")
    private void onConcessionClickListener() {
        totalPrice = booking.getTotalPrice() + bookingConcessionAdapter.getTotalConcessionPrice();
        totalPriceTextView.setText(String.format("$%.1f", totalPrice));
    }
    private void prepareTimeRemaining() {

        timeRemainingHandler = new Handler(Looper.getMainLooper());
        Runnable timeRemainingRunnable = new Runnable() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                long timeRemaining = booking.getLimitPlusCurrentElapsedBootTime() - SystemClock.elapsedRealtime();
                if (timeRemaining <= 0) {
                    new AlertDialog.Builder(BookingConcessionSelectionActivity.this)
                            .setTitle("Timeout")
                            .setMessage("Your booking session has timed out. Returning to the showtime selection of this movie.")
                            .setPositiveButton("OK", (dialog, which) -> {
                                Intent intent = new Intent(BookingConcessionSelectionActivity.this, BookingShowtimeSelectionActivity.class);
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
        nextButton.setText("Next (3/4)");
        nextButton
                .setOnClickListener(v -> {
                    booking = booking.toBuilder()
                            .concessions(ConcessionMapper
                                    .fromItemList(bookingConcessionAdapter
                                            .getItems()))
                            .build();
                    Intent intent = new Intent(this, BookingCheckoutActivity.class);
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
