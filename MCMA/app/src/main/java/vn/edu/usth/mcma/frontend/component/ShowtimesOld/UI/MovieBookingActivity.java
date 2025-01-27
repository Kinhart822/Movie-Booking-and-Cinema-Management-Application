package vn.edu.usth.mcma.frontend.component.ShowtimesOld.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.movie.MovieDetailShort2;
import vn.edu.usth.mcma.frontend.dto.movie.ShowtimeOfMovieByCity;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.CinemaResponse;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.CityResponse;
import vn.edu.usth.mcma.frontend.helper.ImageDecoder;
import vn.edu.usth.mcma.frontend.network.ApiService;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Adapters.TheaterShowtimesAdapter;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models.Movie;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models.Theater;

import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Utils.TheaterDataProvider;
import vn.edu.usth.mcma.frontend.constant.IntentKey;

public class MovieBookingActivity extends AppCompatActivity {
    private static final String TAG = MovieBookingActivity.class.getName();
    private Long id;
    private ImageButton backButton;
    private MovieDetailShort2 movie;
    private ImageView bannerImageView;
    private TextView nameTextView;
    private TextView lengthTextView;
    private TextView ratingTextView;
    private LinearLayout showtimeDateButtonsLinearLayout;
    private LinearLayout showtimeCityButtonsLinearLayout;
    private Map<LocalDate, Map<Long, ShowtimeOfMovieByCity>> dateCityMap;
    private Button selectedDate;
    private Button selectedCity;
    private RecyclerView cinemaButtonsRecyclerView;

    private TheaterShowtimesAdapter theaterAdapter;
    private String movieTitle;
    private long selectedCityId;
    private Long selectedCinemaId;
    private Long selectedScreenId;
    private Long selectedScheduleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_booking);
        id = getIntent().getLongExtra(IntentKey.MOVIE_ID.name(), -1L);
        backButton = findViewById(R.id.button_back);
        bannerImageView = findViewById(R.id.image_view_banner);
        nameTextView = findViewById(R.id.text_view_name);
        lengthTextView = findViewById(R.id.text_view_length);
        ratingTextView = findViewById(R.id.text_view_rating);
        showtimeDateButtonsLinearLayout = findViewById(R.id.linear_layout_showtime_date_buttons);
        showtimeCityButtonsLinearLayout = findViewById(R.id.linear_layout_showtime_city_buttons);
        dateCityMap = new HashMap<>();
        cinemaButtonsRecyclerView = findViewById(R.id.recycler_view_cinema_buttons);

        backButton
                .setOnClickListener(v -> onBackPressed());

        findMovieDetailShort2();
        findAllShowtimeByMovie();


        setupTheatersList();
    }
    private void findMovieDetailShort2() {
        ApiService
                .getMovieApi(this)
                .findMovieDetailShort2(id)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieDetailShort2> call, @NonNull Response<MovieDetailShort2> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "findMovieDetailShort2 onResponse: code not 200 || body is null");
                            return;
                        }
                        movie = response.body();
                        postFindMovieDetailShort2();
                    }
                    @Override
                    public void onFailure(@NonNull Call<MovieDetailShort2> call, @NonNull Throwable throwable) {
                        Log.e(TAG, "findMovieDetailShort2 onFailure: " + throwable);
                    }
                });
    }
    @SuppressLint("DefaultLocale")
    private void postFindMovieDetailShort2() {
        if (movie.getBanner() != null) {
            Glide
                    .with(this)
                    .load(ImageDecoder.decode(movie.getBanner()))
                    .placeholder(R.drawable.placeholder1080x1920)
                    .error(R.drawable.placeholder1080x1920)
                    .into(bannerImageView);
        }
        nameTextView.setText(movie.getName());
        lengthTextView.setText(String.format("%d min", movie.getLength()));
        ratingTextView.setText(movie.getRating());
    }
    private void findAllShowtimeByMovie() {
        ApiService
                .getMovieApi(this)
                .findAllShowtimeByMovie(id)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ShowtimeOfMovieByCity>> call, @NonNull Response<List<ShowtimeOfMovieByCity>> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "findAllShowtimeByMovie onResponse: code not 200 || body is null");
                            return;
                        }
                        postFindAllShowtimeByMovie(response.body());
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<ShowtimeOfMovieByCity>> call, @NonNull Throwable throwable) {
                        Log.e(TAG, "findAllShowtimeByMovie onFailure: " + throwable);
                    }
                });
    }
    private void postFindAllShowtimeByMovie(List<ShowtimeOfMovieByCity> showtimeOfMovieByCity) {
        showtimeOfMovieByCity
                .forEach(city -> city
                        .getShowtimeOfMovieByCinemas()
                        .forEach(cinema -> cinema
                                .getShowtimeOfMovieByScreen()
                                .forEach(screen -> screen
                                        .getShowtimeOfMovieBySchedule()
                                        .forEach(showtime -> dateCityMap
                                                .computeIfAbsent(
                                                        Instant
                                                                .parse(showtime.getStartTime())
                                                                .atZone(ZoneId.systemDefault())
                                                                .toLocalDate(),
                                                        showtimeDate -> new HashMap<>())
                                                .computeIfAbsent(city.getCityId(), cityId -> city)))));
        List<LocalDate> showtimes = new ArrayList<>();
        dateCityMap
                .forEach((showtime, cityMap) -> showtimes.add(showtime));

        addDateButtons(showtimes);
        addCityButtons(showtimeOfMovieByCity);
        selectedDate = (Button) showtimeDateButtonsLinearLayout.getChildAt(0);
        selectedCity = (Button) showtimeCityButtonsLinearLayout.getChildAt(0);
        prepareCinemaButtonsRecyclerView();
    }
    private void addDateButtons(List<LocalDate> showtimes) {
        showtimes
                .forEach(showtime -> {
                    Button showtimeDateButton = new Button(this);
                    showtimeDateButton.setText(showtime.toString());
                    showtimeDateButton.setTransformationMethod(null);
                    showtimeDateButton.setBackgroundResource(R.drawable.button_selector_showtime);
                    showtimeDateButton.setTextColor(ContextCompat.getColor(this, R.color.black));
                    showtimeDateButton.setPadding(20, 10, 20, 10);
                    showtimeDateButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17.5f);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(8, 0, 8, 0);
                    showtimeDateButton.setLayoutParams(params);
                    showtimeDateButton.setOnClickListener(v -> {
                        selectedDate.setSelected(false);
                        selectedDate = showtimeDateButton;
                    });
                    showtimeDateButtonsLinearLayout.addView(showtimeDateButton);
                });
    }
    private void addCityButtons(List<ShowtimeOfMovieByCity> cities) {
        cities
                .forEach(city -> {
                    Button showtimeCityButton = new Button(this);
                    showtimeCityButton.setText(city.getCityName());
                    showtimeCityButton.setTransformationMethod(null);
                    showtimeCityButton.setBackgroundResource(R.drawable.button_selector_showtime);
                    showtimeCityButton.setTextColor(ContextCompat.getColor(this, R.color.black));
                    showtimeCityButton.setPadding(20, 10, 20, 10);
                    showtimeCityButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17.5f);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(8, 0, 8, 0);
                    showtimeCityButton.setLayoutParams(params);
                    showtimeCityButton.setOnClickListener(v -> {
                        selectedCity.setSelected(false);
                        selectedCity = showtimeCityButton;
                    });
                    showtimeCityButtonsLinearLayout.addView(showtimeCityButton);
                });
    }
    private void prepareCinemaButtonsRecyclerView() {

    }



    private void setupTheatersList() {
//        RecyclerView theatersRecyclerView = findViewById(R.id.theaters_recycler_view);
        theaterAdapter = new TheaterShowtimesAdapter((theater, date, showtime, screenId, screenRoom, scheduleId) -> {
            selectedCinemaId = theater.getId(); // Lấy theaterId
            selectedScreenId = screenId;
            selectedScheduleId = scheduleId;
            showQuantityTicketDialog(theater,date, showtime, screenId, screenRoom);
        }, id);
//        theatersRecyclerView.setAdapter(theaterAdapter);
//        theatersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    // Add new method to MovieBookingActivity.java
    @Deprecated
    private void showQuantityTicketDialog(Theater theater,String date, String showtime, Long screenId, String screenRoom) {
        QuantityTicketDialog dialog = new QuantityTicketDialog(this, new QuantityTicketDialog.OnDialogActionListener() {
            @Override
            public void onContinueClicked(int guestQuantity) {
                List<String> showtimes = TheaterDataProvider.generateShowtimes();
                Movie selectedMovie = new Movie(
                        "movie_" + movieTitle.toLowerCase().replace(" ", "_"),
                        movieTitle,
                        showtimes // You might want to populate this with actual showtimes
                );

                Intent intent = new Intent(MovieBookingActivity.this, TicketSelectionActivity.class);
                intent.putExtra(TicketSelectionActivity.EXTRA_GUEST_QUANTITY, guestQuantity);
                intent.putExtra(TicketSelectionActivity.EXTRA_THEATER, theater);
                intent.putExtra(TicketSelectionActivity.EXTRA_MOVIE, selectedMovie);
                intent.putExtra(IntentKey.SELECTED_SHOWTIME.name(), showtime);
                intent.putExtra(IntentKey.SELECTED_DATE.name(), date);
                intent.putExtra(IntentKey.SELECTED_SCREEN.name(), screenId);
                intent.putExtra(IntentKey.SELECTED_SCREEN_ROOM.name(), screenRoom);
                int movieBannerResId = getIntent().getIntExtra(IntentKey.MOVIE_BANNER.name(), 0);
                intent.putExtra(IntentKey.MOVIE_BANNER.name(), movieBannerResId);

                // Booking
                intent.putExtra(IntentKey.MOVIE_ID.name(), id);
                intent.putExtra(IntentKey.SELECTED_CITY_ID.name(), selectedCityId);
                intent.putExtra(IntentKey.SELECTED_CINEMA_ID.name(), selectedCinemaId);
                intent.putExtra(IntentKey.SELECTED_SCREEN_ID.name(), selectedScreenId);
                intent.putExtra(IntentKey.SELECTED_SCHEDULE_ID.name(), selectedScheduleId);
                startActivity(intent);
            }

            @Override
            public void onCloseClicked() {
            }
        });
        dialog.show();
    }
}