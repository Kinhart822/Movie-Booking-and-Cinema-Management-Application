//package vn.edu.usth.mcma.frontend.Showtimes.UI;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//import java.util.stream.Collectors;
//
//import vn.edu.usth.mcma.R;
//import vn.edu.usth.mcma.frontend.Showtimes.Adapters.TicketAdapter;
//import vn.edu.usth.mcma.frontend.Showtimes.Models.Movie;
//import vn.edu.usth.mcma.frontend.Showtimes.Models.Theater;
//import vn.edu.usth.mcma.frontend.Showtimes.Models.TicketItem;
//import vn.edu.usth.mcma.frontend.Showtimes.Models.TicketType;
//
//public class TicketSelectionActivity extends AppCompatActivity {
//    public static final String EXTRA_GUEST_QUANTITY = "extra_guest_quantity";
//    public static final String EXTRA_THEATER = "extra_theater";
//    public static final String EXTRA_MOVIE = "extra_movie";
//
//    private int guestQuantity;
//    private RecyclerView ticketRecyclerView;
//    private TicketAdapter ticketAdapter;
//    private TextView totalTicketPriceTV;
//    private TextView totalTicketCountTV;
//    private Button checkoutButton;
//    private TextView movieNameTV;
//    private TextView theaterNameTV;
//    private TextView releaseDateTV;
//    private TextView screenRoomTV;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ticket_selection);
//        initializeViews();
//        handleIntentExtras();
//        setupBackButton();
//        setupTicketList();
//        setupCheckoutButton();
//    }
//
//    private void initializeViews() {
//        ticketRecyclerView = findViewById(R.id.ticket_recycler_view);
//        checkoutButton = findViewById(R.id.checkout_button);
//        theaterNameTV = findViewById(R.id.theater_name);
//        movieNameTV = findViewById(R.id.movie_name);
//        releaseDateTV = findViewById(R.id.movie_release_date);
//        screenRoomTV = findViewById(R.id.screen_number);
//        totalTicketPriceTV = findViewById(R.id.total_price_ticket);
//        totalTicketCountTV = findViewById(R.id.total_ticket_count);
//    }
//
//    private void handleIntentExtras() {
//        guestQuantity = getIntent().getIntExtra(EXTRA_GUEST_QUANTITY, 0);
//
//        // Theater name handling
//        Theater selectedTheater = (Theater) getIntent().getSerializableExtra(EXTRA_THEATER);
//        if (selectedTheater != null && theaterNameTV != null) {
//            theaterNameTV.setText(selectedTheater.getName());
//        }
//
//        // Movie name handling
//        Movie selectedMovie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
//        if (selectedMovie != null && movieNameTV != null) {
//            movieNameTV.setText(selectedMovie.getTitle());
//        }
//
//        // Release date handling (always today's date)
//        if (releaseDateTV != null) {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd'th' MMM, yyyy", Locale.getDefault());
//            Date today = new Date();
//            String formattedDate = formatDateWithOrdinal(today);
//            releaseDateTV.setText(formattedDate);
//        }
//
//        // Screen number handling
//        String selectedScreenRoom = getIntent().getStringExtra("SELECTED_SCREEN_ROOM");
//        if (screenRoomTV != null) {
//            screenRoomTV.setText(selectedScreenRoom != null ? selectedScreenRoom : "Screen 1");
//        }
//    }
//
//    // Helper method to format date with ordinal suffix
//    private String formatDateWithOrdinal(Date date) {
//        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.getDefault());
//        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
//        SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.getDefault());
//
//        String month = monthFormat.format(date);
//        String year = yearFormat.format(date);
//        int day = Integer.parseInt(dayFormat.format(date));
//
//        String dayWithSuffix = getDayWithOrdinal(day);
//
//        return String.format("%s %s, %s", dayWithSuffix, month, year);
//    }
//
//    // Helper method to get day with ordinal suffix
//    private String getDayWithOrdinal(int day) {
//        if (day >= 11 && day <= 13) {
//            return day + "th";
//        }
//        switch (day % 10) {
//            case 1:
//                return day + "st";
//            case 2:
//                return day + "nd";
//            case 3:
//                return day + "rd";
//            default:
//                return day + "th";
//        }
//    }
//
//    private void setupTicketList() {
//        List<TicketItem> ticketItems = createTicketItems();
//        ticketAdapter = new TicketAdapter(ticketItems, guestQuantity);
//
//        ticketAdapter.setTotalTicketsChangedListener(items -> {
//            updateCheckoutButton();
//            updateTicketPriceAndCount(items);
//        });
//
//        ticketRecyclerView.setAdapter(ticketAdapter);
//        ticketRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//    }
//
//    private void updateTicketPriceAndCount(List<TicketItem> items) {
//        int totalPrice = items.stream()
//                .mapToInt(TicketItem::getTotalPrice)
//                .sum();
//
//        int totalCount = items.stream()
//                .mapToInt(TicketItem::getQuantity)
//                .sum();
//
//        totalTicketPriceTV.setText(formatCurrency(totalPrice));
//        totalTicketCountTV.setText(String.format("%d tickets", totalCount));
//    }
//
//    private String formatCurrency(int price) {
//        return String.format("%,d đ", price);
//    }
//
//    private List<TicketItem> createTicketItems() {
//        return Arrays.stream(TicketType.values())
//                .map(TicketItem::new)
//                .collect(Collectors.toList());
//    }
//
//    private void setupCheckoutButton() {
//        checkoutButton.setOnClickListener(v -> {
//            int totalSelectedTickets = ticketAdapter.getSelectedTicketItems().stream()
//                    .mapToInt(TicketItem::getQuantity)
//                    .sum();
//            if (totalSelectedTickets == guestQuantity) {
//                Intent intent = new Intent(this, SeatSelectionActivity.class);
//
//                // Crucially, pass the ticket items
//                intent.putParcelableArrayListExtra("TICKET_ITEMS", new ArrayList<>(ticketAdapter.getSelectedTicketItems()));
//
//                // Pass other extras as you were doing before
//                intent.putExtra("SELECTED_THEATER", getIntent().getSerializableExtra(EXTRA_THEATER));
//                intent.putExtra("SELECTED_MOVIE", getIntent().getSerializableExtra(EXTRA_MOVIE));
//                intent.putExtra("SELECTED_SHOWTIME", getIntent().getStringExtra("SELECTED_SHOWTIME"));
//                intent.putExtra("SELECTED_SCREEN_ROOM", getIntent().getStringExtra("SELECTED_SCREEN_ROOM"));
//                startActivity(intent);
//            } else {
//                Toast.makeText(this, "Please order the correct number of seats", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void updateCheckoutButton() {
//        int totalSelectedTickets = ticketAdapter.getSelectedTicketItems().stream()
//                .mapToInt(TicketItem::getQuantity)
//                .sum();
//
//        checkoutButton.setEnabled(totalSelectedTickets == guestQuantity);
//        checkoutButton.setText("Completed ticket type selection");
//        checkoutButton.setBackgroundResource(
//                totalSelectedTickets == guestQuantity ?
//                        R.drawable.rounded_active_background :
//                        R.drawable.rounded_dark_background
//        );
//    }
//
//    private void setupBackButton() {
//        ImageButton backButton = findViewById(R.id.back_button);
//        backButton.setOnClickListener(v -> onBackPressed());
//    }
//}

package vn.edu.usth.mcma.frontend.Showtimes.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.TicketResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs.GetAllTicketsAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.TicketAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Movie;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Theater;
import vn.edu.usth.mcma.frontend.Showtimes.Models.TicketItem;
import vn.edu.usth.mcma.frontend.Showtimes.Models.TicketType;

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
    private Movie selectedMovie;
    private TextView showtime;
    private double totalTicketPrice;
    private int totalCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_selection);
        initializeViews();
        handleIntentExtras();
        setupBackButton();
        setupTicketList();
        setupCheckoutButton();
        fetchAndDisplayTickets();
    }

    private void fetchAndDisplayTickets() {
        RetrofitService retrofitService = new RetrofitService(this);
        GetAllTicketsAPI ticketsAPI = retrofitService.getRetrofit().create(GetAllTicketsAPI.class);
        ticketsAPI.getAllTickets().enqueue(new Callback<List<TicketResponse>>() {
            @Override
            public void onResponse(Call<List<TicketResponse>> call, Response<List<TicketResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TicketItem> ticketItems = mapResponseToTicketItems(response.body());
                    ticketAdapter.updateItems(ticketItems);
                } else {
                    showError("Failed to load tickets.");
                }
            }

            @Override
            public void onFailure(Call<List<TicketResponse>> call, Throwable t) {
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
            ticketItems.add(new TicketItem(type,type.getPrice(), ticketId));
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
                // Collect ticket IDs of the selected ticket types
                List<Integer> selectedTicketIds = new ArrayList<>();
                for (TicketItem item : ticketAdapter.getSelectedTicketItems()) {
                    for (int i = 0; i < item.getQuantity(); i++) {
                        selectedTicketIds.add(item.getTicketIds()); // Add ticket ID to the list
                        Log.d("TicketSelection", "Selected Ticket Type ID: " + item.getTicketIds()); // Log the ID of the selected ticket
                    }
                }
                Intent intent = new Intent(this, SeatSelectionActivity.class);
                // Crucially, pass the ticket items
                intent.putParcelableArrayListExtra("TICKET_ITEMS", new ArrayList<>(ticketAdapter.getSelectedTicketItems()));
                // Pass other extras as you were doing before
                intent.putExtra("SELECTED_THEATER", getIntent().getSerializableExtra(EXTRA_THEATER));
                intent.putExtra("THEATER_NAME", getIntent().getStringExtra("THEATER_NAME"));
                intent.putExtra("SELECTED_MOVIE", getIntent().getSerializableExtra(EXTRA_MOVIE));
                intent.putExtra("SELECTED_SHOWTIME", getIntent().getStringExtra("SELECTED_SHOWTIME"));
                intent.putExtra("SELECTED_SCREEN_ROOM", getIntent().getStringExtra("SELECTED_SCREEN_ROOM"));
                int movieBannerResId = getIntent().getIntExtra("MOVIE_BANNER", 0);
                intent.putExtra("MOVIE_BANNER", movieBannerResId);
                intent.putExtra("TOTAL_TICKET_PRICE", totalTicketPrice);
                intent.putExtra("TOTAL_TICKET_COUNT", totalCount);
                intent.putExtra("SELECTED_DATE", getIntent().getStringExtra("SELECTED_DATE"));
                intent.putExtra("MOVIE_TITLE", getIntent().getStringExtra("MOVIE_TITLE"));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please order the correct number of seats", Toast.LENGTH_SHORT).show();
            }
        });
    }

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
        String theaterName = getIntent().getStringExtra("THEATER_NAME");
        if (selectedTheater != null && theaterNameTV != null) {
            theaterNameTV.setText(theaterName != null ? theaterName : selectedTheater.getName());
        }

        // Movie name handling
        Movie selectedMovie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
        if (selectedMovie != null && movieNameTV != null) {
            movieNameTV.setText(selectedMovie.getTitle());
        }

        // Release date handling (always today's date)
        String selectedDate = getIntent().getStringExtra("SELECTED_DATE");
        if (releaseDateTV != null) {
            releaseDateTV.setText(selectedDate);
        }

        // Screen number handling
        String selectedScreenRoom = getIntent().getStringExtra("SELECTED_SCREEN_ROOM");
        if (screenRoomTV != null) {
            screenRoomTV.setText(selectedScreenRoom != null ? selectedScreenRoom : "Screen 1");
        }

        String selectedShowtime = getIntent().getStringExtra("SELECTED_SHOWTIME");
        if(showtime != null){
            showtime.setText(selectedShowtime);
        }
    }

    private void setupBackButton() {
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());
    }
    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}