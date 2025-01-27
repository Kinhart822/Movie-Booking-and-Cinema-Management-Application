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

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> showtimeDates;

    private TheaterShowtimesAdapter theaterAdapter;
    private Button selectedCityButton;
    private String selectedCity;
    private LinearLayout citiesContainer;
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
        showtimeDates = new ArrayList<>();

        backButton
                .setOnClickListener(v -> onBackPressed());

        findMovieDetailShort2();
        findAllShowtimeByMovie();

//        View citiesSection = findViewById(R.id.cities_section);
//        View theatersSection = findViewById(R.id.theaters_section);
//        citiesContainer = findViewById(R.id.cities_container);
        selectedCity = TheaterDataProvider.getCities().get(0);
        setupToolbarAndBanner();
//        setupMovieInfo();
        fetchCitiesByMovie(id);
        setupTheatersList();
//        citiesSection.setVisibility(View.VISIBLE);
//        theatersSection.setVisibility(View.VISIBLE);
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

    }

    private void addShowtimeDateButton(String showtimeDate) {
        Button showtimeDateButton = new Button(this);
        showtimeDateButton.setText(showtimeDate);
        showtimeDateButton.setTransformationMethod(null);
        showtimeDateButton.setBackgroundResource(R.drawable.button_selector_showtime_date);
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
            if (showtimeDateButton.isSelected()) {
                return;
            }
        });
    }


    private void fetchCitiesByMovie(Long movieId) {
        ApiService
                .getMovieApi(this)
                .getCitiesByMovieId(movieId).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<CityResponse>> call, @NonNull Response<List<CityResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<CityResponse> cities = response.body();

                            if (!cities.isEmpty()) {
                                // Chọn thành phố đầu tiên làm mặc định
                                CityResponse defaultCity = cities.get(0);
                                selectedCity = defaultCity.getCityName();
                                long defaultCityId = defaultCity.getCityId();

                                fetchCinemasByCity(movieId, defaultCityId);
                            }

                            setupCityButtons(movieId, cities);

                        } else {
                            Toast.makeText(MovieBookingActivity.this, "Failed to fetch cities", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<CityResponse>> call, @NonNull Throwable t) {
                        Toast.makeText(MovieBookingActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void fetchCinemasByCity(Long movieId, Long cityId) {
        ApiService
                .getCinemaApi(this)
                .getCinemasByMovieIdAndCityId(movieId, cityId).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<CinemaResponse>> call, @NonNull Response<List<CinemaResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<CinemaResponse> cinemas = response.body();
                            updateTheatersList(cinemas); // Populate theaters dynamically
                        } else {
                            Toast.makeText(MovieBookingActivity.this, "Failed to fetch cinemas", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<CinemaResponse>> call, @NonNull Throwable t) {
                        Toast.makeText(MovieBookingActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void setupCityButtons(Long movieId, List<CityResponse> cityResponses) {
        citiesContainer.removeAllViews();

        ColorStateList textColorStateList = ContextCompat.getColorStateList(this, R.color.button_text_selector);

        for (CityResponse cityResponse : cityResponses) {
            Long cityId = cityResponse.getCityId();
            String city = cityResponse.getCityName();

            Button cityButton = new Button(this);
            cityButton.setText(city);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            cityButton.setLayoutParams(params);
            cityButton.setBackground(getDrawable(R.drawable.button_selector_showtime_date));
            cityButton.setTextColor(textColorStateList);
            cityButton.setAllCaps(false);
            cityButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

            // Đặt trạng thái `selected` cho nút mặc định
            if (selectedCity == null || city.equals(selectedCity)) {
                cityButton.setSelected(true);
                selectedCityButton = cityButton;
                selectedCity = city;
                selectedCityId = cityId;
            }

            cityButton.setOnClickListener(v -> {
                if (selectedCityButton != null) {
                    selectedCityButton.setSelected(false); // Bỏ chọn nút trước đó
                }
                cityButton.setSelected(true); // Chọn nút hiện tại
                selectedCityButton = cityButton;
                selectedCity = city;

                if (cityId != -1) {
                    fetchCinemasByCity(movieId, cityId);
                    selectedCityId = cityId;
                }
            });

            citiesContainer.addView(cityButton);
        }
    }
    private void setupToolbarAndBanner() {
        ImageButton backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> finish());
    }
    private void updateTheatersList(List<CinemaResponse> cinemas) {
        List<Theater> cityTheaters = new ArrayList<>();

        for (CinemaResponse cinema : cinemas) {
            cityTheaters.add(Theater
                    .builder()
                    .id(cinema.getId())
                    .name(cinema.getName())
                    .address(cinema.getAddress()).build());
        }

        theaterAdapter.setTheaters(cityTheaters);
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