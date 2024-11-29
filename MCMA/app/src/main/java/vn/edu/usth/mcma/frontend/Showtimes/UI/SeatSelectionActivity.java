package vn.edu.usth.mcma.frontend.Showtimes.UI;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.SeatAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Movie;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Seat;
import vn.edu.usth.mcma.frontend.Showtimes.Models.SeatType;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Theater;

public class SeatSelectionActivity extends AppCompatActivity {
    private RecyclerView seatRecyclerView;
    private SeatAdapter seatAdapter;
    private List<List<Seat>> seatLayout;
    private Theater selectedTheater;
    private String selectedShowtime;
    private Movie selectedMovie;
    private int totalSeatsPerRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);
        // Determine seats per row based on theater and movie duration
        totalSeatsPerRow = determineTotalSeatsPerRow();
        selectedTheater = (Theater) getIntent().getSerializableExtra("SELECTED_THEATER");
        selectedShowtime = getIntent().getStringExtra("SELECTED_SHOWTIME");
        selectedMovie = (Movie) getIntent().getSerializableExtra("SELECTED_MOVIE");
        setupTheaterInfo();
        setupSeatLayout();
        setupRecyclerView();
        setupCheckoutButton();
    }

    private void setupTheaterInfo() {
        TextView theaterNameTV = findViewById(R.id.theater_name);
        TextView movieNameTV = findViewById(R.id.movie_name);
        TextView screenNumberTV = findViewById(R.id.screen_number);
        TextView releaseDateTV = findViewById(R.id.movie_release_date);
        TextView durationTV = findViewById(R.id.movie_duration);

        theaterNameTV.setText(selectedTheater.getName());
        movieNameTV.setText(selectedMovie.getTitle());
        screenNumberTV.setText("Screen 1");
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
            // Get selected seats
            Set<Seat> selectedSeats = seatAdapter.getSelectedSeats();

            // Ensure at least one seat is selected
            if (selectedSeats.isEmpty()) {
                // Show an error message or dialog
                Toast.makeText(this, "Please select at least one seat", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get the current total price
            String currentPrice = ((TextView)findViewById(R.id.text_price_)).getText().toString();

            // Convert Set to ArrayList for Parcelable
            ArrayList<Seat> seatsList = new ArrayList<>(selectedSeats);

            // Navigate to ComboSelectionActivity
            Intent intent = new Intent(this, ComboSelectionActivity.class);
            intent.putExtra(ComboSelectionActivity.EXTRA_SEAT_PRICE, currentPrice);
            intent.putParcelableArrayListExtra(ComboSelectionActivity.EXTRA_SELECTED_SEATS, seatsList);
            startActivity(intent);
        });
    }

    // Price calculation methods
    private void updateSelectedSeatsDisplay() {
        TextView noOfSeatsTV = findViewById(R.id.no_of_seats);
        TextView priceTotalTV = findViewById(R.id.text_price_);
        Button checkoutButton = findViewById(R.id.checkout_button);

        // Get selected seats from adapter
        Set<Seat> selectedSeats = seatAdapter.getSelectedSeats();

        // Update number of seats
        int seatCount = selectedSeats.size();
        noOfSeatsTV.setText(seatCount + " ghế");

        // Calculate price
        int totalPrice = calculateTotalPrice(selectedSeats);
        priceTotalTV.setText(formatCurrency(totalPrice));

        // Update checkout button color based on selection
        if (seatCount > 0 && totalPrice > 0) {
            checkoutButton.setBackgroundResource(R.drawable.rounded_active_background);
            checkoutButton.setEnabled(true);
        } else {
            checkoutButton.setBackgroundResource(R.drawable.rounded_dark_background);
            checkoutButton.setEnabled(true);
        }
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

    private void setupRecyclerView() {
        seatRecyclerView = findViewById(R.id.seatRecyclerView);

        // Dynamically set column count based on max row seats
        int maxRowSeats = seatLayout.stream()
                .mapToInt(List::size)
                .max()
                .orElse(17);

        // Use GridLayoutManager with fixed column count
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, maxRowSeats);
        seatRecyclerView.setLayoutManager(gridLayoutManager);

        seatAdapter = new SeatAdapter(seatLayout, seat -> {
            updateSelectedSeatsDisplay();
        });
        seatRecyclerView.setAdapter(seatAdapter);
    }
}



