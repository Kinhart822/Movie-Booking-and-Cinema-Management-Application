package vn.edu.usth.mcma.frontend.component.ShowtimesOld.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.response.SeatTypeResponse;
import vn.edu.usth.mcma.frontend.dto.response.Seat;
import vn.edu.usth.mcma.frontend.network.ApiService;
import vn.edu.usth.mcma.frontend.helper.SeatMapHelper;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Adapters.SeatAdapter;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models.Movie;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models.Theater;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models.TicketItem;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
//todo see commit 5675655 of branch AdditionalFunctionForUser
public class SeatSelectionActivity extends AppCompatActivity {
    private double totalTicketPrice;
    private int totalTicketCount;
    private RecyclerView seatRecyclerView;
    private SeatAdapter seatAdapter;
    private Theater selectedTheater;
    private Movie selectedMovie;
    private int desiredSeatCount;
    private double totalTicketAndSeatPrice;
    private int totalSeatCount;
    private List<TicketItem> tickets = new ArrayList<>();
    private Long movieId;
    private Long selectedCityId;
    private Long selectedCinemaId;
    private Long selectedScreenId;
    private Long selectedScheduleId;
    private final List<Long> selectedTicketIds = new ArrayList<>();
    private Map<Integer, SeatTypeResponse> seatTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        movieId = getIntent().getLongExtra(IntentKey.MOVIE_ID.name(), -1L);
        selectedCityId = getIntent().getLongExtra(IntentKey.SELECTED_CITY_ID.name(), -1L);
        selectedCinemaId = getIntent().getLongExtra(IntentKey.SELECTED_CINEMA_ID.name(), -1L);
        selectedScheduleId = getIntent().getLongExtra(IntentKey.SELECTED_SCHEDULE_ID.name(), -1L);

        totalTicketCount = getIntent().getIntExtra(IntentKey.TOTAL_TICKET_COUNT.name(), 0);
        Log.d("SeatSelection", IntentKey.TOTAL_TICKET_COUNT.name()+" received: " + totalTicketCount);
        tickets = getIntent().getParcelableArrayListExtra(IntentKey.SELECTED_TICKET_ITEMS.name());
        Log.d("SeatSelection", IntentKey.SELECTED_TICKET_ITEMS.name()+" received: " + tickets);
//      todo  selectedTicketIds = Arrays.stream(Objects.requireNonNull(getIntent().getLongArrayExtra(IntentKey.SELECTED_TICKET_IDS.name()))).boxed().collect(Collectors.toList());
        Log.d("SeatSelection", IntentKey.SELECTED_TICKET_ITEMS.name()+" received: " + selectedTicketIds);

        totalTicketPrice = getIntent().getDoubleExtra(IntentKey.TOTAL_TICKET_PRICE.name(), 0.0);
        selectedTheater = (Theater) getIntent().getSerializableExtra(IntentKey.SELECTED_THEATER.name());
        selectedMovie = (Movie) getIntent().getSerializableExtra(IntentKey.SELECTED_MOVIE.name());
        desiredSeatCount = totalTicketCount;

        setupTheaterInfo();
        setupCheckoutButton();
        setupBackButton();
        fetchAllSeatTypes();
        fetchAllSeats();
    }
    private void fetchAllSeatTypes() {
        seatTypes = new HashMap<>();
        ApiService
                .getCinemaApi(this)
                .getAllSeatTypes()
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<SeatTypeResponse>> call, @NonNull Response<List<SeatTypeResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            response.body()
                                    .forEach(st -> seatTypes.put(st.getId(), st));
                        } else {
                            Toast.makeText(SeatSelectionActivity.this, "Failed to fetch seatType list", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<SeatTypeResponse>> call, @NonNull Throwable t) {
                        Toast.makeText(SeatSelectionActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void fetchAllSeats() {
        selectedScreenId = getIntent().getLongExtra(IntentKey.SELECTED_SCREEN_ID.name(), -1L);
        ApiService
                .getCinemaApi(this)
                .getAllSeatsByScreenId(selectedScreenId)
                .enqueue(new Callback<>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(@NonNull Call<List<Seat>> call, @NonNull Response<List<Seat>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            SeatMapHelper seatMapHelper = new SeatMapHelper(response.body());
                            seatAdapter = new SeatAdapter(
                                    seatMapHelper,
                                    seatTypes,
                                    SeatSelectionActivity.this,
                                    seat -> updateSelectedSeatsDisplay(),
                                    desiredSeatCount);
                            seatRecyclerView = findViewById(R.id.seatRecyclerView);
                            seatRecyclerView.setLayoutManager(new GridLayoutManager(SeatSelectionActivity.this, seatMapHelper.getMaxSeatPerRow() + 1));
                            seatRecyclerView.setAdapter(seatAdapter);
                            seatAdapter.notifyDataSetChanged();
                        } else {
                            String cinemaName = getIntent().getStringExtra(IntentKey.THEATER_NAME.name());
                            Toast.makeText(SeatSelectionActivity.this, "Failed to fetch seats of " + cinemaName, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Seat>> call, @NonNull Throwable t) {
                        Toast.makeText(SeatSelectionActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @SuppressLint("SetTextI18n")
    public void updateSelectedSeatsDisplay() {
        TextView noOfSeatsTV = findViewById(R.id.no_of_seats);
        TextView seatPriceTV = findViewById(R.id.seat_price_total);
        Button checkoutButton = findViewById(R.id.checkout_button);

        List<Seat> selectedSeats = seatAdapter.getSelectedRootSeats();
        int seatCount = selectedSeats.size();
        noOfSeatsTV.setText(seatCount + " seat(s)");
        // Tính giá tiền ghế
        double totalSeatPrice = calculateTotalSeatPrice(selectedSeats);
        double finalPrice = totalTicketPrice + totalSeatPrice;
        totalTicketAndSeatPrice = finalPrice;
        totalSeatCount = seatCount;
        seatPriceTV.setText(formatCurrency(finalPrice));
        // Kích hoạt nút "Checkout" nếu số ghế chọn đúng
        boolean isCorrectSeatCount = seatCount == desiredSeatCount;
        checkoutButton.setEnabled(isCorrectSeatCount);
        checkoutButton.setBackgroundResource(isCorrectSeatCount
                ? R.drawable.rounded_active_background
                : R.drawable.rounded_dark_background
        );
    }
    @SuppressLint("SetTextI18n")
    private void setupTheaterInfo() {
        TextView theaterNameTV = findViewById(R.id.theater_name);
        TextView movieNameTV = findViewById(R.id.movie_name2);
        TextView screenNumberTV = findViewById(R.id.screen_number);
        TextView releaseDateTV = findViewById(R.id.movie_release_date);
        TextView showtime = findViewById(R.id.movie_duration);
        // Get selected screen room from intent
        String selectedScreenRoom = getIntent().getStringExtra(IntentKey.SELECTED_SCREEN_ROOM.name());
        screenNumberTV.setText(Objects.requireNonNullElse(selectedScreenRoom, "Default name"));
        theaterNameTV.setText(selectedTheater.getName());
        movieNameTV.setText(selectedMovie.getTitle());

        String selectedDate = getIntent().getStringExtra(IntentKey.SELECTED_DATE.name());
        if (releaseDateTV != null) {
            releaseDateTV.setText(selectedDate);
        }

        String selectedShowtime = getIntent().getStringExtra(IntentKey.SELECTED_SHOWTIME.name());
        if (showtime != null) {
            showtime.setText(selectedShowtime);
        }
    }

    private double calculateTotalSeatPrice(List<Seat> selectedSeats) {
        return selectedSeats.stream()
                .mapToDouble(seat -> Objects
                        .requireNonNull(seatTypes.get(seat.getTypeId()))
                        .getPrice())
                .sum();
    }

    private String formatCurrency(double price) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        return format.format(price);
    }

    private void setupCheckoutButton() {
        Button checkoutButton = findViewById(R.id.checkout_button);
        checkoutButton.setOnClickListener(v -> {
            List<Seat> selectedSeats = seatAdapter.getSelectedRootSeats();
            int seatCount = selectedSeats.size();
            if (seatCount != desiredSeatCount) {
                Toast.makeText(this, "Please choose the correct number of seats", Toast.LENGTH_SHORT).show();
                return;
            }
            // Prepare intent for ComboSelectionActivity
            List<TicketItem> ticketItems = getIntent().getParcelableArrayListExtra(IntentKey.TICKET_ITEMS.name());
            Intent intent = new Intent(this, ComboSelectionActivity.class);
            assert ticketItems != null;
            intent.putParcelableArrayListExtra(IntentKey.TICKET_ITEMS.name(), new ArrayList<>(ticketItems));
            // Pass through extras from previous activities
            intent.putExtra(ComboSelectionActivity.EXTRA_THEATER, getIntent().getSerializableExtra(IntentKey.SELECTED_THEATER.name()));
            intent.putExtra(IntentKey.THEATER_NAME.name(), getIntent().getStringExtra(IntentKey.THEATER_NAME.name()));
            intent.putExtra(ComboSelectionActivity.EXTRA_MOVIE, getIntent().getSerializableExtra(IntentKey.SELECTED_MOVIE.name()));
            intent.putExtra(IntentKey.SELECTED_SHOWTIME.name(), getIntent().getStringExtra(IntentKey.SELECTED_SHOWTIME.name()));
            intent.putExtra(IntentKey.SELECTED_SCREEN_ROOM.name(), getIntent().getStringExtra(IntentKey.SELECTED_SCREEN_ROOM.name()));
            int movieBannerResId = getIntent().getIntExtra(IntentKey.MOVIE_BANNER.name(), 0);
            intent.putExtra(IntentKey.MOVIE_BANNER.name(), movieBannerResId);
            intent.putExtra(IntentKey.SELECTED_DATE.name(), getIntent().getStringExtra(IntentKey.SELECTED_DATE.name()));
            intent.putExtra(IntentKey.MOVIE_TITLE.name(), getIntent().getStringExtra(IntentKey.MOVIE_TITLE.name()));
            intent.putExtra(IntentKey.TOTAL_TICKET_AND_SEAT_PRICE.name(), totalTicketAndSeatPrice);
            intent.putExtra(IntentKey.TOTAL_SEAT_COUNT.name(), totalSeatCount);
            intent.putExtra(IntentKey.TOTAL_TICKET_COUNT.name(), totalTicketCount);
            intent.putParcelableArrayListExtra(IntentKey.SELECTED_TICKET_ITEMS.name(), new ArrayList<>(tickets));
            intent.putExtra(IntentKey.TOTAL_TICKET_PRICE.name(), totalTicketPrice);
            intent.putParcelableArrayListExtra(IntentKey.SELECTED_SEAT_ITEMS.name(), new ArrayList<>(selectedSeats));
            // Booking
            intent.putExtra(IntentKey.MOVIE_ID.name(), movieId);
            intent.putExtra(IntentKey.SELECTED_CITY_ID.name(), selectedCityId);
            intent.putExtra(IntentKey.SELECTED_CINEMA_ID.name(), selectedCinemaId);
            intent.putExtra(IntentKey.SELECTED_SCREEN_ID.name(), selectedScreenId);
            intent.putExtra(IntentKey.SELECTED_SCHEDULE_ID.name(), selectedScheduleId);
            intent.putExtra(IntentKey.SELECTED_TICKET_IDS.name(), selectedTicketIds.stream().mapToLong(Long::longValue).toArray());

            //todo
//            intent.putIntegerArrayListExtra(IntentKey.SELECTED_SEAT_IDS.name(), new ArrayList<>(selectedSeatIds));
            // start
            startActivity(intent);
        });
    }
    private void setupBackButton() {
        ImageButton backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> onBackPressed());
    }
}