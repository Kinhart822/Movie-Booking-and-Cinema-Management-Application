package vn.edu.usth.mcma.frontend.component.bookingprocess.stepthree;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageButton;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.UI.PaymentBookingActivity;
import vn.edu.usth.mcma.frontend.component.customview.navigate.CustomNavigateButton;
import vn.edu.usth.mcma.frontend.model.response.ConcessionResponse;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.model.Booking;
import vn.edu.usth.mcma.frontend.network.ApiService;
import vn.edu.usth.mcma.frontend.utils.mapper.ConcessionMapper;

public class ConcessionSelectionActivity extends AppCompatActivity {
    private static final String TAG = ConcessionSelectionActivity.class.getName();
    private TextView totalPriceTextView;
    private CustomNavigateButton nextButton;

    private Booking booking;
    private List<ConcessionResponse> concessionResponses;
    private RecyclerView concessionRecyclerView;
    private ConcessionAdapter concessionAdapter;
    private double totalPrice;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concession_selection);
        ImageButton backButton = findViewById(R.id.button_back);
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
        concessionAdapter = new ConcessionAdapter(
                this,
                ConcessionMapper.fromResponseList(concessionResponses),
                this::onConcessionClickListener);
        concessionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        concessionRecyclerView.setAdapter(concessionAdapter);
    }
    @SuppressLint("DefaultLocale")
    private void onConcessionClickListener() {
        totalPrice = booking.getTotalPrice() + concessionAdapter.getTotalConcessionPrice();
        totalPriceTextView.setText(String.format("$%.1f", totalPrice));
    }
    @SuppressLint("SetTextI18n")
    private void prepareNextButton() {
        nextButton.setText("Next (3/4)");
        nextButton
                .setOnClickListener(v -> {
                    booking = booking.toBuilder()
                            .concessions(ConcessionMapper
                                    .fromItemList(concessionAdapter
                                            .getItems()))
                            .build();
                    Intent intent = new Intent(this, PaymentBookingActivity.class);
                    intent.putExtra(IntentKey.BOOKING.name(), booking);
                    startActivity(intent);
                });
    }
}
