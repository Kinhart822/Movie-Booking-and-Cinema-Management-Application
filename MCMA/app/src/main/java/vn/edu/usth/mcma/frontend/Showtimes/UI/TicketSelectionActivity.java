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

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import vn.edu.usth.mcma.R;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_selection);
        initializeViews();
        handleIntentExtras();
        setupBackButton();
        setupTicketList();
        setupCheckoutButton();
    }

    private void initializeViews() {
        ticketRecyclerView = findViewById(R.id.ticket_recycler_view);
        checkoutButton = findViewById(R.id.checkout_button);
        theaterNameTV = findViewById(R.id.theater_name);
        movieNameTV = findViewById(R.id.movie_name);
        releaseDateTV = findViewById(R.id.movie_release_date);
        screenRoomTV = findViewById(R.id.screen_number);
        totalTicketPriceTV = findViewById(R.id.total_price_ticket);
        totalTicketCountTV = findViewById(R.id.total_ticket_count);
    }

    private void handleIntentExtras() {
        guestQuantity = getIntent().getIntExtra(EXTRA_GUEST_QUANTITY, 0);

        // Theater name handling
        Theater selectedTheater = (Theater) getIntent().getSerializableExtra(EXTRA_THEATER);
        if (selectedTheater != null && theaterNameTV != null) {
            theaterNameTV.setText(selectedTheater.getName());
        }

        // Movie name handling
        Movie selectedMovie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
        if (selectedMovie != null && movieNameTV != null) {
            movieNameTV.setText(selectedMovie.getTitle());
        }

        // Release date handling (always today's date)
        if (releaseDateTV != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd'th' MMM, yyyy", Locale.getDefault());
            Date today = new Date();
            String formattedDate = formatDateWithOrdinal(today);
            releaseDateTV.setText(formattedDate);
        }

        // Screen number handling
        String selectedScreenRoom = getIntent().getStringExtra("SELECTED_SCREEN_ROOM");
        if (screenRoomTV != null) {
            screenRoomTV.setText(selectedScreenRoom != null ? selectedScreenRoom : "Screen 1");
        }
    }

    // Helper method to format date with ordinal suffix
    private String formatDateWithOrdinal(Date date) {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.getDefault());

        String month = monthFormat.format(date);
        String year = yearFormat.format(date);
        int day = Integer.parseInt(dayFormat.format(date));

        String dayWithSuffix = getDayWithOrdinal(day);

        return String.format("%s %s, %s", dayWithSuffix, month, year);
    }

    // Helper method to get day with ordinal suffix
    private String getDayWithOrdinal(int day) {
        if (day >= 11 && day <= 13) {
            return day + "th";
        }
        switch (day % 10) {
            case 1:
                return day + "st";
            case 2:
                return day + "nd";
            case 3:
                return day + "rd";
            default:
                return day + "th";
        }
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

    private void updateTicketPriceAndCount(List<TicketItem> items) {
        int totalPrice = items.stream()
                .mapToInt(TicketItem::getTotalPrice)
                .sum();

        int totalCount = items.stream()
                .mapToInt(TicketItem::getQuantity)
                .sum();

        totalTicketPriceTV.setText(formatCurrency(totalPrice));
        totalTicketCountTV.setText(String.format("%d tickets", totalCount));
    }

    private String formatCurrency(int price) {
        return String.format("%,d đ", price);
    }

    private List<TicketItem> createTicketItems() {
        return Arrays.stream(TicketType.values())
                .map(TicketItem::new)
                .collect(Collectors.toList());
    }

    private void setupCheckoutButton() {
        checkoutButton.setOnClickListener(v -> {
            int totalSelectedTickets = ticketAdapter.getSelectedTicketItems().stream()
                    .mapToInt(TicketItem::getQuantity)
                    .sum();
            if (totalSelectedTickets == guestQuantity) {
                Intent intent = new Intent(this, SeatSelectionActivity.class);

                // Crucially, pass the ticket items
                intent.putParcelableArrayListExtra("TICKET_ITEMS", new ArrayList<>(ticketAdapter.getSelectedTicketItems()));

                // Pass other extras as you were doing before
                intent.putExtra("SELECTED_THEATER", getIntent().getSerializableExtra(EXTRA_THEATER));
                intent.putExtra("SELECTED_MOVIE", getIntent().getSerializableExtra(EXTRA_MOVIE));
                intent.putExtra("SELECTED_SHOWTIME", getIntent().getStringExtra("SELECTED_SHOWTIME"));
                intent.putExtra("SELECTED_SCREEN_ROOM", getIntent().getStringExtra("SELECTED_SCREEN_ROOM"));
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

    private void setupBackButton() {
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());
    }
}