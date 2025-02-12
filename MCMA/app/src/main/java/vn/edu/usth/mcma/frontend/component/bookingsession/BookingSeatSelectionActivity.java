package vn.edu.usth.mcma.frontend.component.bookingsession;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.customview.navigate.CustomNavigateButton;
import vn.edu.usth.mcma.frontend.dto.bookingsession.ScheduleDetail;
import vn.edu.usth.mcma.frontend.dto.response.ApiResponse;
import vn.edu.usth.mcma.frontend.model.response.SeatTypeResponse;
import vn.edu.usth.mcma.frontend.model.Booking;
import vn.edu.usth.mcma.frontend.model.request.HoldRootSeat;
import vn.edu.usth.mcma.frontend.model.request.HoldSeatRequest;
import vn.edu.usth.mcma.frontend.model.response.SeatResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.network.apis.BookingApi;
import vn.edu.usth.mcma.frontend.utils.mapper.SeatMapper;
import vn.edu.usth.mcma.frontend.utils.mapper.SeatTypeMapper;

public class BookingSeatSelectionActivity extends AppCompatActivity {
    private static final String TAG = BookingSeatSelectionActivity.class.getName();
    private static final double BOOKING_TIME_LIMIT = 1; //todo dotenv
    private TextView timeRemainingTextView;
    private TextView cinemaNameTextView;
    private TextView screenNameDateDurationTextView;
    private TextView movieNameTextView;
    private TextView ratingTextView;
    private TextView screenTypeTextView;
    private CustomNavigateButton nextButton;
    private TextView totalSeatCountTextView;
    private TextView totalPriceTextView;
    private TextView availableRowsTextView;

    private Long scheduleId;
    private ScheduleDetail scheduleDetail;
    private Booking booking;
    private List<SeatTypeResponse> seatTypeResponses;
    private List<SeatResponse> seatResponses;
    private RecyclerView seatMatrixRecyclerView;
    private SeatAdapter seatAdapter;
    private int totalAudienceCount;
    private double totalPrice;
    private Handler timeRemainingHandler;
    private long limitPlusCurrentElapsedBootTime;
    private long timeRemaining;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_seat_selection);
        ImageButton backButton = findViewById(R.id.button_back);
        timeRemainingTextView = findViewById(R.id.text_view_time_remaining);
        cinemaNameTextView = findViewById(R.id.text_view_cinema_name);
        screenNameDateDurationTextView = findViewById(R.id.text_view_screen_name_date_duration);
        movieNameTextView = findViewById(R.id.text_view_movie_name);
        ratingTextView = findViewById(R.id.text_view_rating);
        screenTypeTextView = findViewById(R.id.text_view_screen_type);
        totalSeatCountTextView = findViewById(R.id.text_view_total_seat_count);
        totalPriceTextView = findViewById(R.id.text_view_total_price);
        availableRowsTextView = findViewById(R.id.text_view_available_rows);
        nextButton = findViewById(R.id.button_next);
        seatMatrixRecyclerView = findViewById(R.id.recycler_view_seat_matrix);

        booking = getIntent().getParcelableExtra(IntentKey.BOOKING.name(), Booking.class);
        assert booking != null;
        scheduleId = booking.getScheduleId();
        totalAudienceCount = 0;
        totalPrice = 0.0;
        totalSeatCountTextView.setText(String.format("%d seats selected", totalAudienceCount));
        totalPriceTextView.setText(String.format("$%.1f", totalPrice));

        backButton
                .setOnClickListener(v -> onBackPressed());

        prepareNextButton();
        findScheduleDetail();
    }
    private void findScheduleDetail() {
        ApiService
                .getBookingApi(this)
                .findScheduleDetail(scheduleId)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<ScheduleDetail> call, @NonNull Response<ScheduleDetail> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "findScheduleDetail onResponse: code not 200 || body is null");
                            return;
                        }
                        scheduleDetail = response.body();
                        postFindScheduleDetail();
                    }
                    @Override
                    public void onFailure(@NonNull Call<ScheduleDetail> call, @NonNull Throwable throwable) {
                        Log.e(TAG, "findScheduleDetail onFailure: " + throwable);
                    }
                });
    }
    private void postFindScheduleDetail() {
        String cinemaName = scheduleDetail.getCinemaName();
        String screenNameDateDuration = String.format(
                "%s - %s %s ~ %s",
                scheduleDetail.getScreenName(),
                Instant.parse(scheduleDetail.getStartDateTime()).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MMM dd, uuuu")),
                Instant.parse(scheduleDetail.getStartDateTime()).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("HH:mm")),
                Instant.parse(scheduleDetail.getEndDateTime()).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("HH:mm")));
        String movieName = scheduleDetail.getMovieName();
        String rating = scheduleDetail.getRating();
        String screenType = scheduleDetail.getScreenType();

        booking = booking.toBuilder()
                .cinemaName(cinemaName)
                .screenNameDateDuration(screenNameDateDuration)
                .movieName(movieName)
                .rating(rating)
                .screenType(screenType).build();

        cinemaNameTextView.setText(cinemaName);
        screenNameDateDurationTextView.setText(screenNameDateDuration);
        movieNameTextView.setText(movieName);
        ratingTextView.setText(rating);
        screenTypeTextView.setText(screenType);

        //if you implement findAllSeatTypesBySchedule, it should goes here
        findAllSeatTypes();
    }
    private void findAllSeatTypes() {
        ApiService
                .getBookingApi(this)
                .findAllSeatTypeBySchedule(booking.getScheduleId())
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<SeatTypeResponse>> call, @NonNull Response<List<SeatTypeResponse>> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "findAllSeatTypeBySchedule onResponse: code not 200 || body is null");
                            return;
                        }
                        seatTypeResponses = response.body();
                        postFindAllSeatTypes();
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<SeatTypeResponse>> call, @NonNull Throwable t) {
                        Log.e(TAG, "findAllSeatTypeBySchedule onFailure: " + t);
                    }
                });
    }
    private void postFindAllSeatTypes() {
        findAllSeatBySchedule();
    }
    private void findAllSeatBySchedule() {
        ApiService
                .getBookingApi(this)
                .findAllSeatBySchedule(scheduleId)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<SeatResponse>> call, @NonNull Response<List<SeatResponse>> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "findAllSeatBySchedule onResponse: code not 200 || body is null");
                            return;
                        }
                        seatResponses = response.body();
                        postFindAllSeatBySchedule();
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<SeatResponse>> call, @NonNull Throwable throwable) {
                        Log.e(TAG, "findAllSeatBySchedule onFailure: " + throwable);
                    }
                });
    }
    private void postFindAllSeatBySchedule() {
        seatAdapter = new SeatAdapter(
                this,
                SeatTypeMapper.fromResponseList(seatTypeResponses),
                SeatMapper.fromResponseList(seatResponses),
                seat -> onSeatClickListener());
        seatMatrixRecyclerView.setLayoutManager(new GridLayoutManager(this, seatAdapter.getMaxSeatPerRow() + 1));
        seatMatrixRecyclerView.setAdapter(seatAdapter);

        availableRowsTextView.setText(String.format("Nearest row:  %s\nFarthest row: %s", seatAdapter.getNearestRow(), seatAdapter.getFarthestRow()));

        prepareTimeRemaining();
    }
    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void onSeatClickListener() {
        totalAudienceCount = seatAdapter.getTotalAudienceCount();
        totalPrice = seatAdapter.getTotalSeatPrice();
        totalSeatCountTextView.setText(String.format("%d seats selected", totalAudienceCount));
        totalPriceTextView.setText(String.format("$%.1f", totalPrice));
        nextButton.setEnabled(totalAudienceCount != 0);
    }
    private void prepareTimeRemaining() {
        limitPlusCurrentElapsedBootTime = SystemClock.elapsedRealtime() + (long) (BOOKING_TIME_LIMIT * 60 * 1000);

        timeRemainingHandler = new Handler(Looper.getMainLooper());
        Runnable timeRemainingRunnable = new Runnable() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                timeRemaining = limitPlusCurrentElapsedBootTime - SystemClock.elapsedRealtime();
                if (timeRemaining <= 0) {
                    new AlertDialog.Builder(BookingSeatSelectionActivity.this)
                            .setTitle("Timeout")
                            .setMessage("Your booking session has timed out. Returning to the showtime selection of this movie.")
                            .setPositiveButton("OK", (dialog, which) -> {
                                Intent intent = new Intent(BookingSeatSelectionActivity.this, BookingShowtimeSelectionActivity.class);
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
        nextButton.setText("Next (1/4)");
        nextButton.setEnabled(false);
        nextButton
                .setOnClickListener(v -> holdSeatRequest(() -> {
                    booking = booking.toBuilder()
                            .ratingDescription(scheduleDetail.getRatingDescription())
                            .limitPlusCurrentElapsedBootTime(limitPlusCurrentElapsedBootTime)
                            .seatTypes(SeatTypeMapper.fromItemMap(seatAdapter.getSeatTypes())).build()
                            .setSeats(SeatMapper.fromItemList(seatAdapter.getSelectedSeats()));
                    Intent intent = new Intent(this, BookingAudienceTypeSelectionActivity.class);
                    intent.putExtra(IntentKey.BOOKING.name(), booking);
                    startActivity(intent);
                }));
    }
    private void holdSeatRequest(BookingApi.HoldSeatRequestCallback callback) {
        ApiService
                .getBookingApi(this)
                .holdSeatRequest(
                        scheduleId,
                        HoldSeatRequest.builder()
                                .sessionId(booking.getSessionId())
                                .rootSeats(seatAdapter
                                        .getSelectedRootSeats().stream()
                                        .map(r -> HoldRootSeat.builder()
                                                .rootRow(r.getRootRow())
                                                .rootCol(r.getRootCol())
                                                .build())
                                        .toList())
                                .timeRemaining(timeRemaining).build())
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "holdSeatRequest onResponse: code not 200 || body is null");
                            return;
                        }
                        callback.onSeatHoldSuccessfully();
                    }
                    @Override
                    public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable throwable) {
                        Log.e(TAG, "holdSeatRequest onFailure: " + throwable);
                    }
                });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeRemainingHandler.removeCallbacksAndMessages(null);
    }
    //todo when back no more held
}