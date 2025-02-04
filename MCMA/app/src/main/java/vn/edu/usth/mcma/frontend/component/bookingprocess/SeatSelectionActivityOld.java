package vn.edu.usth.mcma.frontend.component.bookingprocess;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.customview.navigate.CustomNavigateButton;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.model.Booking;

public class SeatSelectionActivityOld extends AppCompatActivity {
    private static final String TAG = SeatSelectionActivityOld.class.getName();
//    private Booking booking;
//    private Map<Integer, SeatTypeResponse> seatTypes;
//    private RecyclerView seatMatrixRecyclerView;
//    private SeatAdapter seatAdapter;
//    private Integer totalSeatCount;
//    private Double previousTotalPrice;
//    private TextView totalSeatCountTextView;
//    private TextView totalPriceTextView;
//    private CustomNavigateButton nextButton;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);
//        booking = getIntent().getParcelableExtra(IntentKey.BOOKING.name());
//        seatTypes = new HashMap<>();
//        ImageButton backButton = findViewById(R.id.button_back);
//        TextView cinemaNameTextView = findViewById(R.id.text_view_cinema_name);
//        TextView screenNameDateDurationTextView = findViewById(R.id.text_view_screen_name_date_duration);
//        seatMatrixRecyclerView = findViewById(R.id.recycler_view_seat_matrix);
//        totalSeatCount = 0;
//        previousTotalPrice = booking.getAudienceTypes().stream().mapToDouble(a -> a.getUnitPrice() * a.getQuantity()).sum();
//        TextView movieNameTextView = findViewById(R.id.text_view_movie_name);
//        TextView ratingTextView = findViewById(R.id.text_view_rating);
//        TextView screenTypeTextView = findViewById(R.id.text_view_screen_type);
//        totalSeatCountTextView = findViewById(R.id.text_view_total_seat_count);
//        totalPriceTextView = findViewById(R.id.text_view_total_price);
//        nextButton = findViewById(R.id.button_next);

//        backButton
//                .setOnClickListener(v -> onBackPressed());
//
//        cinemaNameTextView.setText(booking.getCinemaName());
//        screenNameDateDurationTextView.setText(booking.getScreenNameDateDuration());
//        movieNameTextView.setText(booking.getMovieName());
//        ratingTextView.setText(booking.getRating());
//        screenTypeTextView.setText(booking.getScreenType());
//        totalSeatCountTextView.setText(String.format("%d / %d seats selected", totalSeatCount, booking.getTotalAudienceCount()));
//        totalPriceTextView.setText("$" + previousTotalPrice);

//        prepareNextButton();
//        findAllSeatTypes();
//        findAllSeatBySchedule();
    }
//    private void findAllSeatTypes() {
//        ApiService
//                .getCinemaApi(this)
//                .findAllSeatTypes()
//                .enqueue(new Callback<>() {
//                    @Override
//                    public void onResponse(@NonNull Call<List<SeatTypeResponse>> call, @NonNull Response<List<SeatTypeResponse>> response) {
//                        if (!response.isSuccessful() || response.body() == null) {
//                            Log.e(TAG, "findAllSeatTypes onResponse: code not 200 || body is null");
//                            return;
//                        }
//                        response.body()
//                                .forEach(st -> seatTypes.put(st.getId(), st));
//                    }
//                    @Override
//                    public void onFailure(@NonNull Call<List<SeatTypeResponse>> call, @NonNull Throwable t) {
//                        Log.e(TAG, "findAllSeatTypes onFailure: " + t);
//                    }
//                });
//    }
//    private void findAllSeatBySchedule() {
//        ApiService
//                .getCinemaApi(this)
//                .findAllSeatBySchedule(booking.getScheduleId())
//                .enqueue(new Callback<>() {
//                    @Override
//                    public void onResponse(@NonNull Call<List<Seat>> call, @NonNull Response<List<Seat>> response) {
//                        if (!response.isSuccessful() || response.body() == null) {
//                            Log.e(TAG, "findAllSeatBySchedule onResponse: code not 200 || body is null");
//                            return;
//                        }
//                        postFindAllSeatBySchedule(response.body());
//                    }
//                    @Override
//                    public void onFailure(@NonNull Call<List<Seat>> call, @NonNull Throwable throwable) {
//                        Log.e(TAG, "findAllSeatBySchedule onFailure: " + throwable);
//                    }
//                });
//    }
//    private void postFindAllSeatBySchedule(List<Seat> seats) {
//        seatAdapter = new SeatAdapter(
//                this,
//                seats,
//                seat -> onSeatClickListener(),
//                booking.getTotalAudienceCount());
//        seatMatrixRecyclerView.setLayoutManager(new GridLayoutManager(this, seatAdapter.getMaxSeatPerRow() + 1));
//        seatMatrixRecyclerView.setAdapter(seatAdapter);
//    }
//    @SuppressLint({"DefaultLocale", "SetTextI18n"})
//    public void onSeatClickListener() {
//        totalSeatCountTextView.setText(String.format("%d / %d seats selected", seatAdapter.getTotalAudienceCount(), booking.getTotalAudienceCount()));
//        totalPriceTextView.setText("$" + (previousTotalPrice + seatAdapter.getTotalSeatPrice(seatTypes)));
//        nextButton.setEnabled(seatAdapter.getTotalAudienceCount() == booking.getTotalAudienceCount());
//    }
//    @SuppressLint("SetTextI18n")
//    private void prepareNextButton() {
//        nextButton.setText("Next");
//        nextButton
//                .setOnClickListener(v -> {
//                    Intent intent = new Intent(this, SeatSelectionActivityOld.class);
//                    intent.putExtra(IntentKey.BOOKING.name(), booking);
//                    startActivity(intent);
//                });
//    }
}