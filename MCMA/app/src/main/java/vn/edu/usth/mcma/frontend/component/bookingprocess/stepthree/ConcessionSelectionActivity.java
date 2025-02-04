package vn.edu.usth.mcma.frontend.component.bookingprocess.stepthree;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.UI.PaymentBookingActivity;
import vn.edu.usth.mcma.frontend.component.customview.navigate.CustomNavigateButton;
import vn.edu.usth.mcma.frontend.model.response.ConcessionResponse;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models.Seat;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models.ComboItem;
import vn.edu.usth.mcma.frontend.model.AudienceType;
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



    private List<ComboItem> comboItemList = new ArrayList<>();
    private int totalComboCount;
    private double totalPriceOfSelectedChoice;
    private List<Integer> selectedTicketIds = new ArrayList<>();
    private List<Integer> selectedSeatIds = new ArrayList<>();

    @SuppressLint("SetTextI18n")
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
        nextButton = findViewById(R.id.button_next);

        backButton
                .setOnClickListener(v -> onBackPressed());

        booking = getIntent().getParcelableExtra(IntentKey.BOOKING.name());

        assert booking != null;
        cinemaNameTextView.setText(booking.getCinemaName());
        screenNameDateDurationTextView.setText(booking.getScreenNameDateDuration());
        movieNameTextView.setText(booking.getMovieName());
        ratingTextView.setText(booking.getRating());
        screenTypeTextView.setText(booking.getScreenType());
        totalAudienceTextView.setText(Integer.toString(booking.getTotalAudience()));

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
                ConcessionMapper.fromResponseList(concessionResponses),
                this::onConcessionClickListener);
        concessionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        concessionRecyclerView.setAdapter(concessionAdapter);
    }
    private void onConcessionClickListener() {

    }


    private void setupCheckoutButton() {
        Button checkoutButton = findViewById(R.id.checkout_button);
        checkoutButton.setOnClickListener(v -> {
            List<AudienceType> ticketItems = getIntent().getParcelableArrayListExtra(IntentKey.TICKET_ITEMS.name());
            List<Seat> selectedSeats = getIntent().getParcelableArrayListExtra(EXTRA_SELECTED_SEATS);
//            List<ComboItem> selectedComboItems = comboAdapter.getSelectedComboItems();

            List<Integer> selectedFoodIds = new ArrayList<>();
            List<Integer> selectedDrinkIds = new ArrayList<>();
            comboItemList.stream()
                    .filter(item -> item.getType() == 0 && item.getQuantity() > 0)
                    .forEach(item -> {
                        for (int i = 0; i < item.getQuantity(); i++) {
                            selectedFoodIds.add(item.getComboIds());
                        }
                    });
            comboItemList.stream()
                    .filter(item -> item.getType() == 1 && item.getQuantity() > 0)
                    .forEach(item -> {
                        for (int i = 0; i < item.getQuantity(); i++) {
                            selectedDrinkIds.add(item.getComboIds());
                        }
                    });

            selectedTicketIds =  getIntent().getIntegerArrayListExtra(IntentKey.SELECTED_TICKET_IDS.name());
            selectedSeatIds = getIntent().getIntegerArrayListExtra(IntentKey.SELECTED_SEAT_IDS.name());


            // Create intent to PaymentBookingActivity
            Intent intent = new Intent(this, PaymentBookingActivity.class);
            intent.putParcelableArrayListExtra(IntentKey.TICKET_ITEMS.name(), new ArrayList<>(ticketItems));
            intent.putExtra(IntentKey.SELECTED_MOVIE.name(), getIntent().getSerializableExtra(IntentKey.SELECTED_MOVIE.name()));
            intent.putExtra(IntentKey.SELECTED_THEATER.name(), getIntent().getSerializableExtra(IntentKey.SELECTED_THEATER.name()));
            intent.putExtra(IntentKey.THEATER_NAME.name(), getIntent().getStringExtra(IntentKey.THEATER_NAME.name()));
            intent.putExtra(IntentKey.SELECTED_SHOWTIME.name(), getIntent().getStringExtra(IntentKey.SELECTED_SHOWTIME.name()));
            intent.putExtra(IntentKey.SELECTED_SCREEN_ROOM.name(), getIntent().getStringExtra(IntentKey.SELECTED_SCREEN_ROOM.name()));
            intent.putExtra(IntentKey.SELECTED_DATE.name(), getIntent().getStringExtra(IntentKey.SELECTED_DATE.name()));
            int movieBannerResId = getIntent().getIntExtra(IntentKey.MOVIE_BANNER.name(), 0);
            intent.putExtra(IntentKey.MOVIE_BANNER.name(), movieBannerResId);
            intent.putExtra(IntentKey.MOVIE_NAME.name(), movieName);
            intent.putExtra(IntentKey.CINEMA_NAME.name(), cinemaName);
            intent.putExtra(IntentKey.TOTAL_TICKET_COUNT.name(), totalTicketCount);
            intent.putExtra(IntentKey.TOTAL_COMBO_COUNT.name(), totalComboCount);
            intent.putParcelableArrayListExtra(IntentKey.SELECTED_COMBO_ITEMS.name(), new ArrayList<>(comboItemList));
            intent.putExtra(IntentKey.TOTAL_PRICE_OF_SELECTED_CHOICE.name(), totalPriceOfSelectedChoice);
            intent.putParcelableArrayListExtra(IntentKey.SELECTED_TICKET_ITEMS.name(), new ArrayList<>(items));
            intent.putExtra(IntentKey.TOTAL_TICKET_PRICE.name(), totalTicketPrice);
            intent.putParcelableArrayListExtra(IntentKey.SELECTED_SEAT_ITEMS.name(), new ArrayList<>(seatItems));

            // Booking
            intent.putExtra(IntentKey.MOVIE_ID.name(), movieId);
            intent.putExtra(IntentKey.SELECTED_CITY_ID.name(), selectedCityId);
            intent.putExtra(IntentKey.SELECTED_CINEMA_ID.name(), cinemaId);
            intent.putExtra(IntentKey.SELECTED_SCREEN_ID.name(), selectedScreenId);
            intent.putExtra(IntentKey.SELECTED_SCHEDULE_ID.name(), selectedScheduleId);
            intent.putIntegerArrayListExtra(IntentKey.SELECTED_TICKET_IDS.name(), new ArrayList<>(selectedTicketIds));
            intent.putIntegerArrayListExtra(IntentKey.SELECTED_SEAT_IDS.name(), new ArrayList<>(selectedSeatIds));
            intent.putIntegerArrayListExtra(IntentKey.SELECTED_FOOD_IDS.name(), new ArrayList<>(selectedFoodIds));
            intent.putIntegerArrayListExtra(IntentKey.SELECTED_DRINK_IDS.name(), new ArrayList<>(selectedDrinkIds));
            startActivity(intent);
        });
    }

    @SuppressLint("SetTextI18n")
    private void prepareNextButton() {
        nextButton.setText("Next (3/4)");
        nextButton
                .setOnClickListener(v -> {
                    booking = booking.toBuilder()
                            .build();
                    Intent intent = new Intent(this, PaymentBookingActivity.class);
                    intent.putExtra(IntentKey.BOOKING.name(), booking);
                    startActivity(intent);
                });
    }
}
