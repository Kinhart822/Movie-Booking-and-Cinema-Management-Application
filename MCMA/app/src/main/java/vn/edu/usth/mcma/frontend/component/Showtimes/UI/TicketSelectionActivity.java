package vn.edu.usth.mcma.frontend.component.Showtimes.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.TicketResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;
import vn.edu.usth.mcma.frontend.component.Showtimes.Adapters.TicketAdapter;
import vn.edu.usth.mcma.frontend.component.Showtimes.Models.Movie;
import vn.edu.usth.mcma.frontend.component.Showtimes.Models.Theater;
import vn.edu.usth.mcma.frontend.component.Showtimes.Models.TicketItem;
import vn.edu.usth.mcma.frontend.component.Showtimes.Models.TicketType;
import vn.edu.usth.mcma.frontend.constant.IntentKey;

public class TicketSelectionActivity extends AppCompatActivity {
    public static final String EXTRA_GUEST_QUANTITY = "extra_guest_quantity";
    public static final String EXTRA_THEATER = "extra_theater";
    public static final String EXTRA_MOVIE = "extra_movie";

    private int guestQuantity;
    private RecyclerView ticketRecyclerView;
    private TicketAdapter ticketAdapter;
    private TextView totalTicketPriceTV;
    private TextView totalTicketCountTV;
    private Button checkoutButton;
    private TextView movieNameTV;
    private TextView theaterNameTV;
    private TextView releaseDateTV;
    private TextView screenRoomTV;
    private TextView showtime;
    private double totalTicketPrice;
    private int totalCount;
    private Long movieId;
    private Long selectedCityId;
    private Long selectedCinemaId;
    private Long selectedScreenId;
    private Long selectedScheduleId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_selection);

        movieId = getIntent().getLongExtra(IntentKey.MOVIE_ID.name(), -1L);
        selectedCityId = getIntent().getLongExtra(IntentKey.SELECTED_CITY_ID.name(), -1L);
        selectedCinemaId = getIntent().getLongExtra(IntentKey.SELECTED_CINEMA_ID.name(), -1L);
        selectedScreenId = getIntent().getLongExtra(IntentKey.SELECTED_SCREEN_ID.name(), -1L);
        selectedScheduleId = getIntent().getLongExtra(IntentKey.SELECTED_SCHEDULE_ID.name(), -1L);

        initializeViews();
        handleIntentExtras();
        setupBackButton();
        setupTicketList();
        setupCheckoutButton();
        fetchAndDisplayTickets();
    }

    private void fetchAndDisplayTickets() {
        ApiService
                .getBookingApi(this)
                .getAllTickets().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<TicketResponse>> call, @NonNull Response<List<TicketResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<TicketItem> ticketItems = mapResponseToTicketItems(response.body());
                            ticketAdapter.updateItems(ticketItems);
                        } else {
                            showError("Failed to load tickets.");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<TicketResponse>> call, @NonNull Throwable t) {
                        showError("Error: " + t.getMessage());
                    }
                });
    }

    private List<TicketItem> mapResponseToTicketItems(List<TicketResponse> ticketResponses) {
        List<TicketItem> ticketItems = new ArrayList<>();
        for (TicketResponse response : ticketResponses) {
            for (int i = 0; i < response.getTicketIds().size(); i++) {
                try {
                    TicketType ticketType = TicketType.fromName(response.getTicketTypes().get(i));
                    TicketItem item = new TicketItem(
                            ticketType,
                            response.getTicketPriceList().get(i), // Fetch price from API
                            response.getTicketIds().get(i)
                    );
                    item.setQuantity(0); // Default quantity
                    ticketItems.add(item);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace(); // Log and handle unknown ticket types
                }
            }
        }
        return ticketItems;
    }

    private void setupTicketList() {
        List<TicketItem> ticketItems = createTicketItems();
        ticketAdapter = new TicketAdapter(ticketItems, guestQuantity);

        ticketAdapter.setTotalTicketsChangedListener(items -> {
            updateCheckoutButton();
            updateTicketPriceAndCount(items);
        });

        ticketRecyclerView.setAdapter(ticketAdapter);
        ticketRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<TicketItem> createTicketItems() {
        List<TicketItem> ticketItems = new ArrayList<>();
        for (TicketType type : TicketType.values()) {
            int ticketId = type.getId();
            ticketItems.add(new TicketItem(type, type.getPrice(), ticketId));
        }
        return ticketItems;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void updateTicketPriceAndCount(List<TicketItem> items) {
        double totalPrice = items.stream()
                .mapToDouble(TicketItem::getTotalPrice) // Use mapToDouble for double values
                .sum();

        int totalTicketCount = items.stream()
                .mapToInt(TicketItem::getQuantity)
                .sum();

        totalTicketPrice = totalPrice;
        totalCount = totalTicketCount;
        totalTicketPriceTV.setText(formatCurrency(totalPrice));
        totalTicketCountTV.setText(String.format("%d tickets", totalTicketCount));
    }

    private String formatCurrency(double price) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        return format.format(price);
    }


    private void setupCheckoutButton() {
        checkoutButton.setOnClickListener(v -> {
            int totalSelectedTickets = ticketAdapter.getSelectedTicketItems().stream()
                    .mapToInt(TicketItem::getQuantity)
                    .sum();
            if (totalSelectedTickets == guestQuantity) {
                List<Integer> selectedTicketIds = new ArrayList<>();
                for (TicketItem item : ticketAdapter.getSelectedTicketItems()) {
                    for (int i = 0; i < item.getQuantity(); i++) {
                        selectedTicketIds.add(item.getTicketIds()); // Add ticket ID to the list
                    }
                }
                List<TicketItem> ticketItemList = new ArrayList<>(ticketAdapter.getSelectedTicketItems());
                Intent intent = new Intent(this, SeatSelectionActivity.class);
                // Crucially, pass the ticket items
                intent.putParcelableArrayListExtra(IntentKey.TICKET_ITEMS.name(), new ArrayList<>(ticketAdapter.getSelectedTicketItems()));
                intent.putParcelableArrayListExtra(IntentKey.SELECTED_TICKET_ITEMS.name(), new ArrayList<>(ticketItemList));

                // Pass other extras as you were doing before
                intent.putExtra(IntentKey.SELECTED_THEATER.name(), getIntent().getSerializableExtra(EXTRA_THEATER));
                intent.putExtra(IntentKey.THEATER_NAME.name(), getIntent().getStringExtra(IntentKey.THEATER_NAME.name()));
                intent.putExtra(IntentKey.SELECTED_MOVIE.name(), getIntent().getSerializableExtra(EXTRA_MOVIE));
                intent.putExtra(IntentKey.SELECTED_SHOWTIME.name(), getIntent().getStringExtra(IntentKey.SELECTED_SHOWTIME.name()));
                intent.putExtra(IntentKey.SELECTED_SCREEN_ROOM.name(), getIntent().getStringExtra(IntentKey.SELECTED_SCREEN_ROOM.name()));
                int movieBannerResId = getIntent().getIntExtra(IntentKey.MOVIE_BANNER.name(), 0);
                intent.putExtra(IntentKey.MOVIE_BANNER.name(), movieBannerResId);
                intent.putExtra(IntentKey.TOTAL_TICKET_PRICE.name(), totalTicketPrice);
                intent.putExtra(IntentKey.TOTAL_TICKET_COUNT.name(), totalCount);
                intent.putExtra(IntentKey.SELECTED_DATE.name(), getIntent().getStringExtra(IntentKey.SELECTED_DATE.name()));
                intent.putExtra(IntentKey.MOVIE_TITLE.name(), getIntent().getStringExtra(IntentKey.MOVIE_TITLE.name()));

                // Booking
                intent.putExtra(IntentKey.MOVIE_ID.name(), movieId);
                intent.putExtra(IntentKey.SELECTED_CITY_ID.name(), selectedCityId);
                intent.putExtra(IntentKey.SELECTED_CINEMA_ID.name(), selectedCinemaId);
                intent.putExtra(IntentKey.SELECTED_SCREEN_ID.name(), selectedScreenId);
                intent.putExtra(IntentKey.SELECTED_SCHEDULE_ID.name(), selectedScheduleId);
                intent.putIntegerArrayListExtra("SELECTED_TICKET_IDS", new ArrayList<>(selectedTicketIds));

                startActivity(intent);
            } else {
                Toast.makeText(this, "Please order the correct number of seats", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateCheckoutButton() {
        int totalSelectedTickets = ticketAdapter.getSelectedTicketItems().stream()
                .mapToInt(TicketItem::getQuantity)
                .sum();

        checkoutButton.setEnabled(totalSelectedTickets == guestQuantity);
        checkoutButton.setText("Completed ticket type selection");
        checkoutButton.setBackgroundResource(
                totalSelectedTickets == guestQuantity ?
                        R.drawable.rounded_active_background :
                        R.drawable.rounded_dark_background
        );
    }

    private void initializeViews() {
        ticketRecyclerView = findViewById(R.id.ticket_recycler_view);
        checkoutButton = findViewById(R.id.checkout_button);
        theaterNameTV = findViewById(R.id.theater_name);
        releaseDateTV = findViewById(R.id.movie_release_date);
        screenRoomTV = findViewById(R.id.screen_number);
        totalTicketPriceTV = findViewById(R.id.total_price_ticket);
        totalTicketCountTV = findViewById(R.id.total_ticket_count);
        movieNameTV = findViewById(R.id.movie_name2);
        showtime = findViewById(R.id.movie_duration);
    }

    private void handleIntentExtras() {
        guestQuantity = getIntent().getIntExtra(EXTRA_GUEST_QUANTITY, 0);

        // Theater name handling
        Theater selectedTheater = (Theater) getIntent().getSerializableExtra(EXTRA_THEATER);
        String theaterName = getIntent().getStringExtra(IntentKey.THEATER_NAME.name());
        if (selectedTheater != null && theaterNameTV != null) {
            theaterNameTV.setText(theaterName != null ? theaterName : selectedTheater.getName());
        }

        // Movie name handling
        Movie selectedMovie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
        if (selectedMovie != null && movieNameTV != null) {
            movieNameTV.setText(selectedMovie.getTitle());
        }

        // Release date handling (always today's date)
        String selectedDate = getIntent().getStringExtra(IntentKey.SELECTED_DATE.name());
        if (releaseDateTV != null) {
            releaseDateTV.setText(selectedDate);
        }

        // Screen number handling
        String selectedScreenRoom = getIntent().getStringExtra(IntentKey.SELECTED_SCREEN_ROOM.name());
        if (screenRoomTV != null) {
            screenRoomTV.setText(selectedScreenRoom != null ? selectedScreenRoom : "Screen 1");
        }

        String selectedShowtime = getIntent().getStringExtra(IntentKey.SELECTED_SHOWTIME.name());
        if (showtime != null) {
            showtime.setText(selectedShowtime);
        }
    }

    private void setupBackButton() {
        ImageButton backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}