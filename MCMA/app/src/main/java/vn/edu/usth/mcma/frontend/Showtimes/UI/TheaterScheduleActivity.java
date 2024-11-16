package vn.edu.usth.mcma.frontend.Showtimes.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.MovieScheduleAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Movie;
import vn.edu.usth.mcma.frontend.Showtimes.Models.TheaterType;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.TheaterDataProvider;

public class TheaterScheduleActivity extends AppCompatActivity
        implements MovieScheduleAdapter.    OnShowtimeClickListener {
    private RecyclerView movieRecyclerView;
    private MovieScheduleAdapter movieAdapter;
    private TheaterType currentType;
    private String selectedDate;
    private Button selectedDateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater_schedule);

        currentType = TheaterType.valueOf(
                getIntent().getStringExtra("THEATER_TYPE"));

        setupViews();
        setupDateButtons();
        loadMovieSchedule();
    }

    private void setupViews() {
        movieRecyclerView = findViewById(R.id.movie_recycler_view);
        movieAdapter = new MovieScheduleAdapter(this);
        movieRecyclerView.setAdapter(movieAdapter);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupDateButtons() {
        // Similar to original implementation but with selected state handling
        LinearLayout daysContainer = findViewById(R.id.days_container);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("E\ndd/MM", Locale.getDefault());

        for (int i = 0; i < 7; i++) {
            Button dayButton = new Button(this);
            dayButton.setText(dayFormat.format(calendar.getTime()));

            if (i == 0) {
                dayButton.setSelected(true);
                selectedDateButton = dayButton;
                selectedDate = dayFormat.format(calendar.getTime());
            }

            dayButton.setOnClickListener(v -> {
                if (selectedDateButton != null) {
                    selectedDateButton.setSelected(false);
                }
                dayButton.setSelected(true);
                selectedDateButton = dayButton;
                selectedDate = dayButton.getText().toString();
                loadMovieSchedule();
            });

            daysContainer.addView(dayButton);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
    }

    private void loadMovieSchedule() {
        // Load movies and showtimes based on selectedDate and currentType
        List<Movie> movies = getMoviesForDate(selectedDate);
        movieAdapter.setMovies(movies);
        movieAdapter.setTheaterType(currentType);
    }

    private List<Movie> getMoviesForDate(String date) {
        return TheaterDataProvider.getMoviesForTheater(currentType, date);
    }

    @Override
    public void onShowtimeClick(Movie movie, String showtime) {
        // Handle showtime selection
        Toast.makeText(this,
                "Selected: " + movie.getTitle() + " at " + showtime,
                Toast.LENGTH_SHORT).show();
    }
}