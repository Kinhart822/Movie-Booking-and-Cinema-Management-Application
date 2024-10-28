package vn.edu.usth.mcma.frontend;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import vn.edu.usth.mcma.R;

public class TheaterScheduleActivity extends AppCompatActivity {
    private LinearLayout daysContainer;
    private LinearLayout movieScheduleContainer;
    private Button selectedDateButton;
    private HorizontalScrollView daysScrollView;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater_schedule);

        // Initialize views
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        TextView theaterNameView = findViewById(R.id.theater_name);
        // Get theater name from intent
        String theaterName = getIntent().getStringExtra("THEATER_NAME");
        theaterNameView.setText(theaterName != null ? theaterName : "");

        daysContainer = findViewById(R.id.days_container);
        movieScheduleContainer = findViewById(R.id.movie_schedule_container);
        daysScrollView = findViewById(R.id.days_scroll_view);

        addDays();
        loadMovieSchedule(selectedDate);
    }

    private void addDays() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("E\ndd/MM", Locale.getDefault());
        SimpleDateFormat dateKey = new SimpleDateFormat("dd/MM", Locale.getDefault());

        // Create 7 date buttons
        for (int i = 0; i < 7; i++) {
            Button dayButton = new Button(this);
            dayButton.setText(dayFormat.format(calendar.getTime()));
            String dateStr = dateKey.format(calendar.getTime());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.date_button_width),
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            dayButton.setLayoutParams(params);

            // Set initial selected state for first button
            if (i == 0) {
                dayButton.setSelected(true);
                selectedDateButton = dayButton;
                selectedDate = dateStr;
            }

            dayButton.setOnClickListener(v -> {
                if (selectedDateButton != null) {
                    selectedDateButton.setSelected(false);
                }
                dayButton.setSelected(true);
                selectedDateButton = dayButton;
                selectedDate = dateStr;
                loadMovieSchedule(dateStr);
            });

            daysContainer.addView(dayButton);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
    }

    private void loadMovieSchedule(String date) {
        movieScheduleContainer.removeAllViews();

        // Example: Different movies for different dates
        if (date.equals(selectedDate)) {
            addMovieItem("NGÀY XƯA CÓ MỘT CHUYỆN TÌNH",
                    new String[]{"11:35", "14:05", "16:35", "19:05", "21:40", "22:45", "23:50"});
            addMovieItem("THE WILD ROBOT",
                    new String[]{"10:30", "13:00", "15:30", "18:00", "20:30", "22:00", "23:30"});
            addMovieItem("CÔ DÂU HÀO MÔN",
                    new String[]{"09:45", "12:15", "14:45", "17:15", "19:45", "21:15", "23:15"});
        } else {
            // Different schedule for other dates
            addMovieItem("MOVIE 1 FOR " + date,
                    new String[]{"10:00", "12:30", "15:00", "17:30", "20:00", "22:30", "23:45"});
            // Add morte movies as needed
        }
    }

    private void addMovieItem(String title, String[] showTimes) {
        View movieItemView = getLayoutInflater().inflate(R.layout.movie_item_layout, movieScheduleContainer, false);

        TextView titleView = movieItemView.findViewById(R.id.movie_title);
        titleView.setText(title);

        TextView moreDetailsView = movieItemView.findViewById(R.id.view_details);
        moreDetailsView.setText("More details");

        LinearLayout timeContainer = movieItemView.findViewById(R.id.time_container);
        HorizontalScrollView timeScrollView = movieItemView.findViewById(R.id.time_scroll_view);

        for (String time : showTimes) {
            Button timeButton = new Button(this);
            timeButton.setText(time);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.time_button_width),
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            timeButton.setLayoutParams(params);
            timeContainer.addView(timeButton);
        }

        movieScheduleContainer.addView(movieItemView);
    }
}