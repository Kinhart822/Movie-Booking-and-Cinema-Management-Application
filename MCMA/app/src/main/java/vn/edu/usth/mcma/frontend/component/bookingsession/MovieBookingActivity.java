package vn.edu.usth.mcma.frontend.component.bookingsession;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.customview.filter.CityButton;
import vn.edu.usth.mcma.frontend.component.customview.filter.DateButton;
import vn.edu.usth.mcma.frontend.dto.movie.MovieDetailShort2;
import vn.edu.usth.mcma.frontend.dto.movie.ShowtimeOfMovieByCityResponse;
import vn.edu.usth.mcma.frontend.utils.ImageDecoder;
import vn.edu.usth.mcma.frontend.model.ShowtimeOfMovieBySchedule;
import vn.edu.usth.mcma.frontend.network.ApiService;

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
    private Map<LocalDate, Map<Long, Map<String, Map<String, List<ShowtimeOfMovieBySchedule>>>>> dateCityCinemaNameScreenTypeScheduleMap;
    private DateButton selectedDate;
    private CityButton selectedCity;
    private TextView noScheduleTextView;
    private RecyclerView cinemaButtonsRecyclerView;
    private MovieBookingCinemaAdapter movieBookingCinemaAdapter;

    @SuppressLint("SetTextI18n")
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
        dateCityCinemaNameScreenTypeScheduleMap = new TreeMap<>();
        noScheduleTextView = findViewById(R.id.text_view_no_schedule);
        cinemaButtonsRecyclerView = findViewById(R.id.recycler_view_cinema_buttons);

        backButton
                .setOnClickListener(v -> onBackPressed());
        noScheduleTextView.setText("There are no movie sessions available");

        findMovieDetailShort2();
        findAllShowtimeByMovie();
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
                    public void onResponse(@NonNull Call<List<ShowtimeOfMovieByCityResponse>> call, @NonNull Response<List<ShowtimeOfMovieByCityResponse>> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "findAllShowtimeByMovie onResponse: code not 200 || body is null");
                            return;
                        }
                        postFindAllShowtimeByMovie(response.body());
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<ShowtimeOfMovieByCityResponse>> call, @NonNull Throwable throwable) {
                        Log.e(TAG, "findAllShowtimeByMovie onFailure: " + throwable);
                    }
                });
    }
    private void postFindAllShowtimeByMovie(List<ShowtimeOfMovieByCityResponse> response) {
        response
                .forEach(city -> city
                        .getShowtimeOfMovieByCinema()
                        .forEach(cinema -> cinema
                                .getShowtimeOfMovieByScreen()
                                .forEach(screen -> screen
                                        .getShowtimeOfMovieBySchedule()
                                        .forEach(showtime -> dateCityCinemaNameScreenTypeScheduleMap
                                                .computeIfAbsent(
                                                        Instant
                                                                .parse(showtime.getStartTime())
                                                                .atZone(ZoneId.systemDefault())
                                                                .toLocalDate(),
                                                        showtimeDate -> new TreeMap<>())
                                                .computeIfAbsent(
                                                        city.getCityId(),
                                                        cityId -> new TreeMap<>())
                                                .computeIfAbsent(
                                                        cinema.getCinemaName(),
                                                        cinemaId -> new TreeMap<>())
                                                .computeIfAbsent(
                                                        screen.getScreenType(),
                                                        screenType -> new ArrayList<>())
                                                .add(ShowtimeOfMovieBySchedule.builder()
                                                        .scheduleId(showtime.getScheduleId())
                                                        .time(Instant
                                                                .parse(showtime.getStartTime())
                                                                .atZone(ZoneId.systemDefault())
                                                                .toLocalTime()).build())))));
        List<LocalDate> showtimes = new ArrayList<>();
        dateCityCinemaNameScreenTypeScheduleMap
                .forEach((showtime, cityMap) -> showtimes.add(showtime));
        addDateButtons(showtimes);
        addCityButtons(response);
        selectedDate = (DateButton) showtimeDateButtonsLinearLayout.getChildAt(0);
        selectedDate.setSelected(true);
        selectedCity = (CityButton) showtimeCityButtonsLinearLayout.getChildAt(0);
        selectedCity.setSelected(true);
        prepareCinemaButtonsRecyclerView();
    }
    private void addDateButtons(List<LocalDate> showtimes) {
        showtimes
                .forEach(showtime -> {
                    DateButton showtimeDateButton = new DateButton(this);
                    showtimeDateButton.setDate(showtime);
                    showtimeDateButton.setText(showtime.toString());
                    showtimeDateButton.setOnClickListener(v -> {
                        selectedDate.setSelected(false);
                        selectedDate.invalidate();
                        selectedDate = showtimeDateButton;
                        selectedDate.setSelected(true);
                        feedAdapter();
                    });
                    showtimeDateButtonsLinearLayout.addView(showtimeDateButton);
                });
    }
    private void addCityButtons(List<ShowtimeOfMovieByCityResponse> cities) {
        cities
                .forEach(city -> {
                    CityButton showtimeCityButton = new CityButton(this);
                    showtimeCityButton.setCityId(city.getCityId());
                    showtimeCityButton.setText(city.getCityName());
                    showtimeCityButton.setOnClickListener(v -> {
                        selectedCity.setSelected(false);
                        selectedCity.invalidate();
                        selectedCity = showtimeCityButton;
                        selectedCity.setSelected(true);
                        feedAdapter();
                    });
                    showtimeCityButtonsLinearLayout.addView(showtimeCityButton);
                });
    }
    private void prepareCinemaButtonsRecyclerView() {
        cinemaButtonsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieBookingCinemaAdapter = new MovieBookingCinemaAdapter(this, id);
        feedAdapter();
        cinemaButtonsRecyclerView.setAdapter(movieBookingCinemaAdapter);
    }
    /*
     * used after initialize adapter or want to update data
     */
    private void feedAdapter() {
        Optional<Map<String, Map<String, List<ShowtimeOfMovieBySchedule>>>> food = Optional.ofNullable(dateCityCinemaNameScreenTypeScheduleMap)
                .map(map -> map.get(selectedDate.getDate()))
                .map(map -> map.get(selectedCity.getCityId()));
        movieBookingCinemaAdapter.updateData(food.orElse(null));
        if (food.isEmpty()) {
            cinemaButtonsRecyclerView.setVisibility(View.GONE);
            noScheduleTextView.setVisibility(View.VISIBLE);
            return;
        }
        cinemaButtonsRecyclerView.setVisibility(View.VISIBLE);
        noScheduleTextView.setVisibility(View.GONE);
    }
}