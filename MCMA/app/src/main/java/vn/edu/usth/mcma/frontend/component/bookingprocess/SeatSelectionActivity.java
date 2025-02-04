package vn.edu.usth.mcma.frontend.component.bookingprocess;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.customview.navigate.CustomNavigateButton;
import vn.edu.usth.mcma.frontend.dto.bookingprocess.ScheduleDetail;
import vn.edu.usth.mcma.frontend.dto.response.SeatTypeResponse;
import vn.edu.usth.mcma.frontend.model.Seat;
import vn.edu.usth.mcma.frontend.model.Booking;
import vn.edu.usth.mcma.frontend.network.ApiService;
import vn.edu.usth.mcma.frontend.constant.IntentKey;

public class SeatSelectionActivity extends AppCompatActivity {
    private static final String TAG = SeatSelectionActivity.class.getName();
    private TextView cinemaNameTextView;
    private TextView screenNameDateDurationTextView;
    private TextView movieNameTextView;
    private TextView ratingTextView;
    private TextView screenTypeTextView;
    private CustomNavigateButton nextButton;
    private TextView totalSeatCountTextView;
    private TextView totalPriceTextView;

    private Long scheduleId;
    private ScheduleDetail scheduleDetail;
    private Booking booking;
    private List<SeatTypeResponse> seatTypeResponses;
    private List<Seat> seatResponses;
    private RecyclerView seatMatrixRecyclerView;
    private SeatAdapter seatAdapter;
    private int totalAudienceCount;
    private double totalPrice;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);
        ImageButton backButton = findViewById(R.id.button_back);
        cinemaNameTextView = findViewById(R.id.text_view_cinema_name);
        screenNameDateDurationTextView = findViewById(R.id.text_view_screen_name_date_duration);
        movieNameTextView = findViewById(R.id.text_view_movie_name);
        ratingTextView = findViewById(R.id.text_view_rating);
        screenTypeTextView = findViewById(R.id.text_view_screen_type);
        totalSeatCountTextView = findViewById(R.id.text_view_total_seat_count);
        totalPriceTextView = findViewById(R.id.text_view_total_price);
        nextButton = findViewById(R.id.button_next);
        seatMatrixRecyclerView = findViewById(R.id.recycler_view_seat_matrix);

        scheduleId = getIntent().getLongExtra(IntentKey.SCHEDULE_ID.name(), -1L);
        booking = new Booking();
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
                .getMovieApi(this)
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
                .getCinemaApi(this)
                .findAllSeatTypes()
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<SeatTypeResponse>> call, @NonNull Response<List<SeatTypeResponse>> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "findAllSeatTypes onResponse: code not 200 || body is null");
                            return;
                        }
                        seatTypeResponses = response.body();
                        postFindAllSeatTypes();
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<SeatTypeResponse>> call, @NonNull Throwable t) {
                        Log.e(TAG, "findAllSeatTypes onFailure: " + t);
                    }
                });
    }
    private void postFindAllSeatTypes() {
        findAllSeatBySchedule();
    }
    private void findAllSeatBySchedule() {
        ApiService
                .getCinemaApi(this)
                .findAllSeatBySchedule(booking.getScheduleId())
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Seat>> call, @NonNull Response<List<Seat>> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "findAllSeatBySchedule onResponse: code not 200 || body is null");
                            return;
                        }
                        seatResponses = response.body();
                        postFindAllSeatBySchedule();
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<Seat>> call, @NonNull Throwable throwable) {
                        Log.e(TAG, "findAllSeatBySchedule onFailure: " + throwable);
                    }
                });
    }
    private void postFindAllSeatBySchedule() {
        seatAdapter = new SeatAdapter(
                this,
                seatTypeResponses,
                seatResponses,
                seat -> onSeatClickListener());
        seatMatrixRecyclerView.setLayoutManager(new GridLayoutManager(this, seatAdapter.getMaxSeatPerRow() + 1));
        seatMatrixRecyclerView.setAdapter(seatAdapter);
    }
    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    public void onSeatClickListener() {
        totalAudienceCount = seatAdapter.getTotalAudienceCount();
        totalPrice = seatAdapter.getTotalSeatPrice();
        totalSeatCountTextView.setText(String.format("%d seats selected", totalAudienceCount));
        totalPriceTextView.setText(String.format("$%.1f", totalPrice));
    }
    @SuppressLint("SetTextI18n")
    private void prepareNextButton() {
        nextButton.setText("Next");
        nextButton
                .setOnClickListener(v -> {
                    booking = booking.toBuilder()
                            .scheduleId(scheduleId)
                            .rootSeats(seatAdapter.getSelectedRootSeats())
                            .totalAudienceCount(totalAudienceCount)
                            .totalPrice(totalPrice)
                            .build();
                    Intent intent = new Intent(this, AudienceTypeSelectionActivity.class);
                    intent.putExtra(IntentKey.BOOKING.name(), booking);
                    startActivity(intent);
                });
    }
}