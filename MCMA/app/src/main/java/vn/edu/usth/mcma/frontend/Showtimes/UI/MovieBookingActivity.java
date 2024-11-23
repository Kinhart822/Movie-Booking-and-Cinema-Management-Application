package vn.edu.usth.mcma.frontend.Showtimes.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.TheaterShowtimesAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Models.MovieDetails;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Theater;
import vn.edu.usth.mcma.frontend.Showtimes.Models.TheaterType;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.MovieDataProvider;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.TheaterDataProvider;

public class MovieBookingActivity extends AppCompatActivity {
    private RecyclerView theatersRecyclerView;
    private TheaterShowtimesAdapter theaterAdapter;
    private String selectedDate;
    private Button selectedDateButton;
    private String movieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_booking);

        movieTitle = getIntent().getStringExtra("MOVIE_TITLE");

        setupToolbarAndBanner();
        setupMovieInfo();
        setupDateButtons();
        setupTheatersList();
    }

    private void setupToolbarAndBanner() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(movieTitle);

        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);

        // Get movie details
        MovieDetails movieDetails = MovieDataProvider.getMovieDetails(movieTitle);

        // Set banner image
        ImageView bannerImage = findViewById(R.id.movie_banner);
        bannerImage.setImageResource(movieDetails.getBannerImageResId());

        // Setup collapsing toolbar behavior
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if (Math.abs(verticalOffset) >= appBarLayout1.getTotalScrollRange()) {
                // Fully collapsed
                toolbar.setVisibility(View.VISIBLE);
            } else {
                // Expanded or partially expanded
                toolbar.setVisibility(View.GONE);
            }
        });

        // Setup back button
        findViewById(R.id.back_button).setOnClickListener(v -> finish());
    }

    private void setupMovieInfo() {
        MovieDetails movieDetails = MovieDataProvider.getMovieDetails(movieTitle);

        TextView titleTextView = findViewById(R.id.movie_title);
        TextView durationTextView = findViewById(R.id.movie_duration);

        titleTextView.setText(movieTitle);
        durationTextView.setText(movieDetails.getDuration() + " minutes");
    }

    private void setupDateButtons() {
        LinearLayout datesContainer = findViewById(R.id.dates_container);
        SimpleDateFormat dayFormat = new SimpleDateFormat("E\ndd/MM", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < 7; i++) {
            Button dateButton = new Button(this);
            dateButton.setText(dayFormat.format(calendar.getTime()));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            dateButton.setLayoutParams(params);

            dateButton.setBackground(getDrawable(R.drawable.date_button_selector));

            if (i == 0) {
                dateButton.setSelected(true);
                selectedDateButton = dateButton;
                selectedDate = dayFormat.format(calendar.getTime());
            }

            dateButton.setOnClickListener(v -> {
                if (selectedDateButton != null) {
                    selectedDateButton.setSelected(false);
                }
                dateButton.setSelected(true);
                selectedDateButton = dateButton;
                selectedDate = dateButton.getText().toString();
                updateTheatersList();
            });

            datesContainer.addView(dateButton);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
    }

    private void setupTheatersList() {
        theatersRecyclerView = findViewById(R.id.theaters_recycler_view);
        theaterAdapter = new TheaterShowtimesAdapter(new TheaterShowtimesAdapter.OnShowtimeClickListener() {
            @Override
            public void onShowtimeClick(Theater theater, String showtime) {
                // Handle showtime selection
                Toast.makeText(MovieBookingActivity.this,
                        "Selected: " + theater.getName() + " at " + showtime,
                        Toast.LENGTH_SHORT).show();
                // Start booking confirmation activity
            }
        });
        theatersRecyclerView.setAdapter(theaterAdapter);
        theatersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateTheatersList();
    }

    private void updateTheatersList() {
        // Get all theaters across all cities that show this movie
        List<Theater> allTheaters = new ArrayList<>();
        for (String city : TheaterDataProvider.getCities()) {
            allTheaters.addAll(TheaterDataProvider.getTheatersForCity(city, TheaterType.REGULAR));
        }
        theaterAdapter.setTheaters(allTheaters, selectedDate);
    }
}