package vn.edu.usth.mcma.frontend.Showtimes.UI;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);
        selectedTheater = (Theater) getIntent().getSerializableExtra("SELECTED_THEATER");
        selectedShowtime = getIntent().getStringExtra("SELECTED_SHOWTIME");
        selectedMovie = (Movie) getIntent().getSerializableExtra("SELECTED_MOVIE");
        setupTheaterInfo();
        setupSeatLayout();
        setupRecyclerView();
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

    private void setupSeatLayout() {
        seatLayout = new ArrayList<>();
        int rowCount = 8;
        int minSeats = 12;
        int maxSeats = 17;

        for (int row = 0; row < rowCount - 1; row++) {
            List<Seat> currentRow = new ArrayList<>();
            int seatsInRow = generateSeatsInRow(minSeats, maxSeats);

            for (int seat = 0; seat < seatsInRow; seat++) {
                SeatType seatType = determineSeatType(row, seat, seatsInRow);
                currentRow.add(new Seat(
                        ((char)('A' + row)) + String.valueOf(seat + 1),
                        seatType,
                        seatType != SeatType.SOLD
                ));
            }
            seatLayout.add(currentRow);
        }

        // Last row (H) - Couple seats
        List<Seat> coupleRow = new ArrayList<>();
        int coupleSeats = Math.min(maxSeats / 2, 8);
        for (int i = 0; i < coupleSeats; i++) {
            coupleRow.add(new Seat(
                    "H" + (i + 1),
                    SeatType.COUPLE,
                    true
            ));
        }
        seatLayout.add(coupleRow);
    }

    private int generateSeatsInRow(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    private SeatType determineSeatType(int row, int seatIndex, int totalSeatsInRow) {
        // First 3-4 rows are stand seats
        if (row < 3) {
            return SeatType.STAND;
        }

        // VIP seats in middle rows
        if (row >= 3 && row <= 6) {
            int vipStartIndex = (totalSeatsInRow - 4) / 2;
            int vipEndIndex = vipStartIndex + 4;

            if (seatIndex >= vipStartIndex && seatIndex < vipEndIndex) {
                return SeatType.VIP;
            }
        }

        // Randomly assign some seats as sold
        return new Random().nextDouble() < 0.1 ? SeatType.SOLD : SeatType.STAND;
    }


    private void setupRecyclerView() {
        seatRecyclerView = findViewById(R.id.seatRecyclerView);
        seatRecyclerView.setLayoutManager(new GridLayoutManager(this, 17));

        seatAdapter = new SeatAdapter(seatLayout, seat -> {
            Toast.makeText(this, "Selected seat: " + seat.getId(), Toast.LENGTH_SHORT).show();
        });
        seatRecyclerView.setAdapter(seatAdapter);
    }
}


