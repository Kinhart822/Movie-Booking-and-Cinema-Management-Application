package vn.edu.usth.mcma.frontend.Showtimes.UI;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.SeatAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Movie;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Seat;
import vn.edu.usth.mcma.frontend.Showtimes.Models.SeatType;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Theater;
import vn.edu.usth.mcma.frontend.Showtimes.Models.TicketItem;

public class SeatSelectionActivity extends AppCompatActivity {
    private int totalTicketPrice; // store total ticket price from previous activity
    private RecyclerView seatRecyclerView;
    private SeatAdapter seatAdapter;
    private List<List<Seat>> seatLayout;
    private Theater selectedTheater;
    private String selectedShowtime;
    private Movie selectedMovie;
    private int totalSeatsPerRow;
    private int guestQuantity; //variable to track kid ticket quantity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);
        // Retrieve guest quantity from previous activity
        List<TicketItem> ticketItems = getIntent().getParcelableArrayListExtra("TICKET_ITEMS");
        guestQuantity = calculateTotalTicketQuantity(ticketItems);
        // Calculate total ticket price
        totalTicketPrice = calculateTotalTicketPrice(ticketItems);
        // Determine seats per row based on theater and movie duration
        totalSeatsPerRow = determineTotalSeatsPerRow();
        selectedTheater = (Theater) getIntent().getSerializableExtra("SELECTED_THEATER");
        selectedShowtime = getIntent().getStringExtra("SELECTED_SHOWTIME");
        selectedMovie = (Movie) getIntent().getSerializableExtra("SELECTED_MOVIE");
        setupTheaterInfo();
        setupSeatLayout();
        setupRecyclerView();
        setupCheckoutButton();
        setupBackButton();
        updateSelectedSeatsDisplay();
    }

    private int calculateTotalTicketQuantity(List<TicketItem> ticketItems) {
        return ticketItems.stream()
                .mapToInt(TicketItem::getQuantity)
                .sum();
    }
    // New method to calculate total ticket price
    private int calculateTotalTicketPrice(List<TicketItem> ticketItems) {
        return ticketItems.stream()
                .mapToInt(TicketItem::getTotalPrice)
                .sum();
    }
    private void setupTheaterInfo() {
        TextView theaterNameTV = findViewById(R.id.theater_name);
        TextView movieNameTV = findViewById(R.id.movie_name2);
        TextView screenNumberTV = findViewById(R.id.screen_number);
        TextView releaseDateTV = findViewById(R.id.movie_release_date);
        TextView durationTV = findViewById(R.id.movie_duration);

        // Get selected screen room from intent
        String selectedScreenRoom = getIntent().getStringExtra("SELECTED_SCREEN_ROOM");
        if (selectedScreenRoom != null) {
            screenNumberTV.setText(selectedScreenRoom);
        } else {
            screenNumberTV.setText("Screen 1"); // Default fallback
        }
        theaterNameTV.setText(selectedTheater.getName());
        movieNameTV.setText(selectedMovie.getTitle());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd'th' MMM, yyyy", Locale.getDefault());
        releaseDateTV.setText(dateFormat.format(new Date()));
        String[] showtimeParts = selectedShowtime.split(":");
        int startHour = Integer.parseInt(showtimeParts[0]);
        int endHour = startHour + 2;
        durationTV.setText(String.format("%02d:00 - %02d:00", startHour, endHour));
    }

    private int determineTotalSeatsPerRow() {
        // Randomly choose between 12 and 17 seats
        return new Random().nextInt(6) + 12;
    }

    private void setupSeatLayout() {
        seatLayout = new ArrayList<>();
        int rowCount = 8;

        // Rows A to G with equal number of seats
        for (int row = 0; row < rowCount - 1; row++) {
            List<Seat> currentRow = createRowWithSeats(row);
            seatLayout.add(currentRow);
        }

        // Last row (H) - Couple seats
        List<Seat> coupleRow = createCoupleRow();
        seatLayout.add(coupleRow);
    }

    private List<Seat> createRowWithSeats(int row) {
        List<Seat> currentRow = new ArrayList<>();
        int vipStartIndex = calculateVIPStartIndex();
        int vipEndIndex = vipStartIndex + 4;

        for (int seat = 0; seat < totalSeatsPerRow; seat++) {
            SeatType seatType = determineSeatType(row, seat, vipStartIndex, vipEndIndex);
            currentRow.add(new Seat(
                    ((char)('A' + row)) + String.valueOf(seat + 1),
                    seatType,
                    seatType != SeatType.SOLD
            ));
        }
        return currentRow;
    }

    private List<Seat> createCoupleRow() {
        List<Seat> coupleRow = new ArrayList<>();
        int coupleSeats = totalSeatsPerRow / 2;

        for (int i = 0; i < coupleSeats; i++) {
            coupleRow.add(new Seat(
                    "H" + (i + 1),
                    SeatType.COUPLE,
                    true
            ));
        }

        return coupleRow;
    }

    private int calculateVIPStartIndex() {
        return (totalSeatsPerRow - 4) / 2;
    }

    private SeatType determineSeatType(int row, int seatIndex, int vipStartIndex, int vipEndIndex) {
        // First 3 rows are standard seats
        if (row < 3) {
            return SeatType.STAND;
        }

        // VIP seats in middle rows (D, E, F, G)
        if (row >= 3 && row <= 6) {
            if (seatIndex >= vipStartIndex && seatIndex < vipStartIndex + 4) {
                return SeatType.VIP;
            }
        }

        // Randomly assign some seats as sold
        return new Random().nextDouble() < 0.1 ? SeatType.SOLD : SeatType.STAND;
    }

    private void setupCheckoutButton() {
        Button checkoutButton = findViewById(R.id.checkout_button);
        checkoutButton.setOnClickListener(v -> {
            Set<Seat> selectedSeats = seatAdapter.getSelectedSeats();
            if (selectedSeats.size() != guestQuantity) {
                Toast.makeText(this, "Please choose the correct number of seats", Toast.LENGTH_SHORT).show();
                return;
            }

            // Calculate total seat price
            int seatPriceAdditional = calculateTotalPrice(selectedSeats);
            int totalPrice = totalTicketPrice + seatPriceAdditional;
            ArrayList<Seat> seatsList = new ArrayList<>(selectedSeats);

            // Prepare intent for ComboSelectionActivity
            List<TicketItem> ticketItems = getIntent().getParcelableArrayListExtra("TICKET_ITEMS");
            Intent intent = new Intent(this, ComboSelectionActivity.class);

            intent.putParcelableArrayListExtra("TICKET_ITEMS", new ArrayList<>(ticketItems));
            // Pass all necessary extras
            intent.putExtra(ComboSelectionActivity.EXTRA_SEAT_PRICE, totalPrice);
            intent.putExtra(ComboSelectionActivity.EXTRA_SEAT_COUNT, guestQuantity);
            intent.putParcelableArrayListExtra(ComboSelectionActivity.EXTRA_SELECTED_SEATS, new ArrayList<>(selectedSeats));
            // Pass through extras from previous activities
            intent.putExtra(ComboSelectionActivity.EXTRA_THEATER,
                    getIntent().getSerializableExtra("SELECTED_THEATER"));
            intent.putExtra(ComboSelectionActivity.EXTRA_MOVIE,
                    getIntent().getSerializableExtra("SELECTED_MOVIE"));
            intent.putExtra("SELECTED_SHOWTIME",
                    getIntent().getStringExtra("SELECTED_SHOWTIME"));
            intent.putExtra("SELECTED_SCREEN_ROOM",
                    getIntent().getStringExtra("SELECTED_SCREEN_ROOM"));

            startActivity(intent);
        });
    }

    // Price calculation methods
    private void updateSelectedSeatsDisplay() {
        TextView noOfSeatsTV = findViewById(R.id.no_of_seats);
        TextView seatPriceTV = findViewById(R.id.seat_price_total);
        Button checkoutButton = findViewById(R.id.checkout_button);

        // Get selected seats from adapter
        Set<Seat> selectedSeats = seatAdapter.getSelectedSeats();

        // Update number of seats
        int seatCount = selectedSeats.size();
        noOfSeatsTV.setText(seatCount + " ghế");

        // Calculate seat price
        int seatPriceAdditional = calculateTotalPrice(selectedSeats);
        int totalPrice = totalTicketPrice + seatPriceAdditional;
        seatPriceTV.setText(formatCurrency(totalPrice));

        // Enable/disable checkout based on selected seats matching ticket quantity
        boolean isCorrectSeatCount = seatCount == guestQuantity;
        checkoutButton.setEnabled(isCorrectSeatCount);
        checkoutButton.setBackgroundResource(
                isCorrectSeatCount ?
                        R.drawable.rounded_active_background :
                        R.drawable.rounded_dark_background
        );
    }

    private int calculateTotalPrice(Set<Seat> selectedSeats) {
        int basePrice = 0;
        for (Seat seat : selectedSeats) {
            switch (seat.getType()) {
                case VIP:
                    basePrice += 150000; // VIP seat price
                    break;
                case COUPLE:
                    basePrice += 200000; // Couple seat price
                    break;
                case STAND:
                    basePrice += 100000; // Standard seat price
                    break;
            }
        }
        return basePrice;
    }

    private String formatCurrency(int price) {
        return String.format("%,d đ", price);
    }


    private void setupBackButton() {
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void setupRecyclerView() {
        seatRecyclerView = findViewById(R.id.seatRecyclerView);
        int maxRowSeats = seatLayout.stream().mapToInt(List::size).max().orElse(17);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, maxRowSeats);
        seatRecyclerView.setLayoutManager(gridLayoutManager);
        seatAdapter = new SeatAdapter(seatLayout, seat -> {
            updateSelectedSeatsDisplay();
        }, guestQuantity); // Pass total guest quantity to adapter
        seatRecyclerView.setAdapter(seatAdapter);
    }
}