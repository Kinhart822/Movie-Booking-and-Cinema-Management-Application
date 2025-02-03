package vn.edu.usth.mcma.frontend.component.bookingprocess;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.UI.SeatSelectionActivity;
import vn.edu.usth.mcma.frontend.component.customview.navigate.CustomNavigateButton;
import vn.edu.usth.mcma.frontend.dto.bookingprocess.AudienceDetail;
import vn.edu.usth.mcma.frontend.dto.bookingprocess.ScheduleDetail;
import vn.edu.usth.mcma.frontend.model.Booking;
import vn.edu.usth.mcma.frontend.network.ApiService;
import vn.edu.usth.mcma.frontend.model.AudienceType;
import vn.edu.usth.mcma.frontend.constant.IntentKey;

public class AudienceTypeSelectionActivity extends AppCompatActivity {
    private static final String TAG = AudienceTypeSelectionActivity.class.getName();
    private Long scheduleId;
    private ScheduleDetail scheduleDetail;
    private Booking booking;
    private List<AudienceDetail> audienceDetails;
    private TextView cinemaNameTextView;
    private TextView screenNameDateDurationTextView;
    private LinearLayout audienceTypeLinearLayout;
    private double priceForStudent;
    private RecyclerView audienceTypeRecyclerView;
    private AudienceTypeAdapter audienceTypeAdapter;
    private TextView movieNameTextView;
    private TextView ratingTextView;
    private TextView screenTypeTextView;
    private TextView totalAudienceCountTextView;
    private Integer totalAudienceCount;
    private TextView totalPriceTextView;
    private Double totalPrice;
    private CustomNavigateButton nextButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audience_type_selection);
        scheduleId = getIntent().getLongExtra(IntentKey.SCHEDULE_ID.name(), -1L);
        booking = new Booking();
        ImageButton backButton = findViewById(R.id.button_back);
        cinemaNameTextView = findViewById(R.id.text_view_cinema_name);
        screenNameDateDurationTextView = findViewById(R.id.text_view_screen_name_date_duration);
        audienceTypeLinearLayout = findViewById(R.id.linear_layout_audience_type);
        priceForStudent = 9;
        audienceTypeRecyclerView = findViewById(R.id.recycler_view_audience_type);
        movieNameTextView = findViewById(R.id.text_view_movie_name);
        ratingTextView = findViewById(R.id.text_view_rating);
        screenTypeTextView = findViewById(R.id.text_view_screen_type);
        totalAudienceCountTextView = findViewById(R.id.text_view_total_audience_count);
        totalPriceTextView = findViewById(R.id.text_view_total_price);
        nextButton = findViewById(R.id.button_next);

        backButton
                .setOnClickListener(v -> onBackPressed());

        findScheduleDetail();
        prepareNextButton();
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
        totalAudienceCountTextView.setText("0 audiences");
        totalPriceTextView.setText("$0.0");

        findAllAudienceTypeByRating();
    }
    private void findAllAudienceTypeByRating() {
        ApiService
                .getMovieApi(this)
                .findAllAudienceTypeByRating(scheduleDetail.getRating())
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
        List<AudienceType> items = audienceDetails
                .stream()
                .map(a -> AudienceType.builder()
                        .id(a.getId())
                        .quantity(0)
                        .unitPrice(a.getUnitPrice())
                        .build())
                .collect(Collectors.toList());
        items.add(0, AudienceType.builder()
                .id("Student")
                .quantity(0)
                .unitPrice(priceForStudent)
                .build());
        audienceTypeAdapter = new AudienceTypeAdapter(items, this::onQuantityChangeListener);
        audienceTypeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        audienceTypeRecyclerView.setAdapter(audienceTypeAdapter);
    }
    @SuppressLint("SetTextI18n")
    private void onQuantityChangeListener(List<AudienceType> items) {
        totalAudienceCount = items
                .stream()
                .mapToInt(AudienceType::getQuantity)
                .sum();
        totalPrice = items
                .stream()
                .mapToDouble(a -> a.getUnitPrice() * a.getQuantity())
                .sum();
        totalAudienceCountTextView.setText(totalAudienceCount + " audiences");
        totalPriceTextView.setText("$" + totalPrice);
    }
    //todo
    private String formatCurrency(double price) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        return format.format(price);
    }

    @SuppressLint("SetTextI18n")
    private void prepareNextButton() {
        nextButton.setText("Next");
        nextButton
                .setOnClickListener(v -> {
                    //todo warning dialog
                    Intent intent = new Intent(this, SeatSelectionActivity.class);
                    // todo
                    startActivity(intent);
                });
    }
}