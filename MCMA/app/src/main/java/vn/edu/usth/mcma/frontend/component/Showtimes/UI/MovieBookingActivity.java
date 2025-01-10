package vn.edu.usth.mcma.frontend.component.Showtimes.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.Response.BookingProcess.CinemaResponse;
import vn.edu.usth.mcma.frontend.dto.Response.BookingProcess.CityResponse;
import vn.edu.usth.mcma.frontend.network.apis.BookingProcessAPIs.GetAllCitiesAPI;
import vn.edu.usth.mcma.frontend.network.apis.BookingProcessAPIs.GetCinemaByCityIdAPI;
import vn.edu.usth.mcma.frontend.network.RetrofitService;
import vn.edu.usth.mcma.frontend.component.Showtimes.Adapters.TheaterShowtimesAdapter;
import vn.edu.usth.mcma.frontend.component.Showtimes.Models.Movie;
import vn.edu.usth.mcma.frontend.component.Showtimes.Models.MovieDetails;
import vn.edu.usth.mcma.frontend.component.Showtimes.Models.Theater;

import vn.edu.usth.mcma.frontend.component.Showtimes.Utils.MovieDataProvider;
import vn.edu.usth.mcma.frontend.component.Showtimes.Utils.TheaterDataProvider;
import vn.edu.usth.mcma.frontend.constant.IntentKey;

public class MovieBookingActivity extends AppCompatActivity {
    private RecyclerView theatersRecyclerView;
    private TheaterShowtimesAdapter theaterAdapter;
    private String selectedDate;
    private Button selectedDateButton;
    private Button selectedCityButton;
    private String selectedCity;
    private LinearLayout citiesContainer;
    private View citiesSection;
    private View theatersSection;
    private String movieTitle;
    private Long movieId;
    private long selectedCityId;
    private Long selectedCinemaId;
    private Long selectedScreenId;
    private Long selectedScheduleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_booking);

        movieTitle = getIntent().getStringExtra(IntentKey.MOVIE_TITLE.name());
        movieId = getIntent().getLongExtra(IntentKey.MOVIE_ID.name(), -1L);
        citiesSection = findViewById(R.id.cities_section);
        theatersSection = findViewById(R.id.theaters_section);
        citiesContainer = findViewById(R.id.cities_container);

        movieTitle = getIntent().getStringExtra(IntentKey.MOVIE_TITLE.name());

        selectedCity = TheaterDataProvider.getCities().get(0);

        setupToolbarAndBanner();
        setupMovieInfo();
        fetchCitiesByMovie(movieId);
        setupTheatersList();

        citiesSection.setVisibility(View.VISIBLE);
        theatersSection.setVisibility(View.VISIBLE);
    }

    private void fetchCitiesByMovie(Long movieId) {
        RetrofitService retrofitService = new RetrofitService(this);
        GetAllCitiesAPI getAllCitiesAPI = retrofitService.getRetrofit().create(GetAllCitiesAPI.class);

        getAllCitiesAPI.getCitiesByMovieId(movieId).enqueue(new Callback<List<CityResponse>>() {
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
        RetrofitService retrofitService = new RetrofitService(this);
        GetCinemaByCityIdAPI apiService = retrofitService.getRetrofit().create(GetCinemaByCityIdAPI.class);

        Call<List<CinemaResponse>> call = apiService.getCinemasByMovieIdAndCityId(movieId, cityId);
        call.enqueue(new Callback<List<CinemaResponse>>() {
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
            public void onFailure(Call<List<CinemaResponse>> call, Throwable t) {
                Toast.makeText(MovieBookingActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @SuppressLint("UseCompatLoadingForDrawables")
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
            cityButton.setBackground(getDrawable(R.drawable.date_button_selector));
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(movieTitle);

        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);

        // Get movie details
        MovieDetails movieDetails = MovieDataProvider.getMovieDetails(movieTitle);

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
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());
    }

    @SuppressLint("SetTextI18n")
    private void setupMovieInfo() {
        TextView titleTextView = findViewById(R.id.movie_title);
        titleTextView.setText(movieTitle);
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

        theaterAdapter.setTheaters(cityTheaters, selectedDate, movieTitle);
    }

    private void setupTheatersList() {
        theatersRecyclerView = findViewById(R.id.theaters_recycler_view);
        theaterAdapter = new TheaterShowtimesAdapter(new TheaterShowtimesAdapter.OnShowtimeClickListener() {
            @Override
            public void onShowtimeClick(Theater theater, String date, String showtime, Long screenId, String screenRoom, Long scheduleId) {
                selectedCinemaId = theater.getId(); // Lấy theaterId
                selectedScreenId = screenId;
                selectedScheduleId = scheduleId;
                showQuantityTicketDialog(theater,date, showtime, screenId, screenRoom);
            }
        },movieId);
        theatersRecyclerView.setAdapter(theaterAdapter);
        theatersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    // Add new method to MovieBookingActivity.java
    private void showQuantityTicketDialog(Theater theater,String date, String showtime, Long screenId, String screenRoom) {
        QuantityTicketDialog dialog = new QuantityTicketDialog(this, new QuantityTicketDialog.OnDialogActionListener() {
            @Override
            public void onContinueClicked(int guestQuantity) {
                MovieDetails movieDetails = MovieDataProvider.getMovieDetails(movieTitle);
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
                intent.putExtra(IntentKey.MOVIE_ID.name(), movieId);
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