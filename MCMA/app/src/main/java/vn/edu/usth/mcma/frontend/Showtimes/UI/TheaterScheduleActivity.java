package vn.edu.usth.mcma.frontend.Showtimes.UI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Enum.PerformerType;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.MovieResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.ScheduleResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.GetAllInformationOfSelectedMovie;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.GetScheduleAPI;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Movie;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.MovieScheduleAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.TheaterDataProvider;

public class TheaterScheduleActivity extends AppCompatActivity
        implements MovieScheduleAdapter.OnShowtimeClickListener {
    private MovieScheduleAdapter movieAdapter;
    private String selectedDate;
    private Button selectedDateButton;
    private int theaterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater_schedule);

        // Get theater details from intent
        theaterId = getIntent().getIntExtra("CINEMA_ID", -1);
        String theaterName = getIntent().getStringExtra("THEATER_NAME");
        String theaterAddress = getIntent().getStringExtra("THEATER_ADDRESS");

        // Set theater details in toolbar
        TextView nameTextView = findViewById(R.id.theater_name);
        TextView addressTextView = findViewById(R.id.theater_address);
        nameTextView.setText(theaterName);
        addressTextView.setText(theaterAddress);

        // Setup back button
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        setupViews();
        setupDateButtons(theaterId);
    }

    private void setupViews() {
        RecyclerView movieRecyclerView = findViewById(R.id.movie_recycler_view);
        movieAdapter = new MovieScheduleAdapter(this);
        movieRecyclerView.setAdapter(movieAdapter);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupDateButtons(int theaterId) {
        LinearLayout daysContainer = findViewById(R.id.days_container);

        GetScheduleAPI apiService = new Retrofit.Builder()
                .baseUrl("http://192.168.1.103:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GetScheduleAPI.class);

        Call<List<ScheduleResponse>> call = apiService.getScheduleByCinema(theaterId);
        call.enqueue(new Callback<List<ScheduleResponse>>() {
            @Override
            public void onResponse(Call<List<ScheduleResponse>> call, Response<List<ScheduleResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ScheduleResponse> schedules = response.body();
                    List<String> uniqueDates = new ArrayList<>();

                    // Lấy danh sách ngày duy nhất từ API
                    for (ScheduleResponse schedule : schedules) {
                        String uniqueDateKey = schedule.getDayOfWeek() + schedule.getDate();
                        if (!uniqueDates.contains(uniqueDateKey)) {
                            uniqueDates.add(uniqueDateKey);
                        }
                    }

                    // Khởi tạo các nút cho từng ngày
                    for (int i = 0; i < uniqueDates.size(); i++) {
                        ScheduleResponse schedule = schedules.get(i);
                        String formattedDate = schedule.getDate();
                        String dayOfWeek = schedule.getDayOfWeek();
                        String buttonText = dayOfWeek + "\n" + formattedDate; // Hiển thị dạng "Thứ Ba\n17/12/2024"

                        Button dayButton = new Button(TheaterScheduleActivity.this);
                        dayButton.setText(buttonText); // Hiển thị cả ngày và thứ
                        dayButton.setAllCaps(false); // Tắt chữ in hoa của text
                        dayButton.setGravity(Gravity.CENTER); // Căn giữa cả hai dòng

                        // Thiết lập giao diện cho button
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(8, 0, 8, 0);
                        dayButton.setLayoutParams(params);
                        dayButton.setBackground(getDrawable(R.drawable.date_button_selector));
                        dayButton.setTextColor(Color.BLACK);

                        // Đặt ngày đầu tiên được chọn mặc định
                        if (i == 0) {
                            dayButton.setSelected(true);
                            selectedDateButton = dayButton;
                            selectedDate = formattedDate;
                            loadMovieSchedule(formattedDate); // Tải lịch chiếu cho ngày đầu tiên
                        }

                        // Xử lý khi người dùng chọn ngày
                        dayButton.setOnClickListener(v -> {
                            if (selectedDateButton != null) {
                                selectedDateButton.setSelected(false);
                            }
                            dayButton.setSelected(true);
                            selectedDateButton = dayButton;
                            selectedDate = formattedDate;
                            loadMovieSchedule(formattedDate); // Tải lịch chiếu theo ngày đã chọn
                        });

                        daysContainer.addView(dayButton);
                    }
                } else {
                    Toast.makeText(TheaterScheduleActivity.this, "No schedule found for this cinema.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ScheduleResponse>> call, Throwable t) {
                Log.e("TheaterScheduleError", "Error fetching schedule", t);
                Toast.makeText(TheaterScheduleActivity.this, "Failed to load schedule. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMovieSchedule(String date) {
        GetScheduleAPI apiService = new Retrofit.Builder()
                .baseUrl("http://192.168.1.103:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GetScheduleAPI.class);

        Call<List<ScheduleResponse>> call = apiService.getScheduleByCinema(theaterId);
        call.enqueue(new Callback<List<ScheduleResponse>>() {
            @Override
            public void onResponse(Call<List<ScheduleResponse>> call, Response<List<ScheduleResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ScheduleResponse> schedules = response.body();

                    // Lọc lịch chiếu theo ngày đã chọn
                    List<Movie> movies = new ArrayList<>();
                    Map<Integer, Integer> movieIdMap = new HashMap<>();

                    for (ScheduleResponse schedule : schedules) {
                        if (date.equals(schedule.getDate())) {
                            Movie movie = new Movie(
                                    schedule.getMovieId(),
                                    schedule.getMovieName(),
                                    schedule.getMovieName(),
                                    schedule.getMovieLength(),
                                    schedule.getDescription(),
                                    schedule.getPublishedDate(),
                                    schedule.getTrailerLink(),
                                    schedule.getImageUrl(),
                                    schedule.getBackgroundImAageUrl(),
                                    schedule.getMovieGenreNameList(),
                                    schedule.getImageUrlList(),
                                    schedule.getMovieGenreDescriptions(),
                                    schedule.getMoviePerformerNameList(),
                                    schedule.getMoviePerformerType(),
                                    schedule.getMovieRatingDetailNameList(),
                                    schedule.getMovieRatingDetailDescriptions(),
                                    schedule.getComments(),
                                    schedule.getAverageRating()
                            );
                            movies.add(movie);

                            movieIdMap.put(movies.size() - 1, schedule.getMovieId());
                        }
                    }

                    movieAdapter.setMovies(movies); // Cập nhật adapter với danh sách phim

                    movieAdapter.setOnShowtimeClickListener((movie, showtime) -> {
                        int position = movies.indexOf(movie);
                        if (position != -1 && movieIdMap.containsKey(position)) {
                            int movieId = movieIdMap.get(position);
                            fetchMovieDetails(movieId); // Fetch additional details
                        }
                    });
                } else {
                    Toast.makeText(TheaterScheduleActivity.this, "No schedule found for this date.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ScheduleResponse>> call, Throwable t) {
                Log.e("TheaterScheduleError", "Error fetching schedule", t);
                Toast.makeText(TheaterScheduleActivity.this, "Failed to load schedule. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchMovieDetails(int movieId) {
        GetAllInformationOfSelectedMovie apiService = new Retrofit.Builder()
                .baseUrl("http://192.168.1.103:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GetAllInformationOfSelectedMovie.class);

        Call<MovieResponse> call = apiService.getAllInformationOfSelectedMovie(movieId);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MovieResponse movieResponse = response.body();
                    // Handle movie details (e.g., display in a new activity)
                    Intent intent = new Intent(TheaterScheduleActivity.this, MovieDetailsActivity.class);
                    intent.putExtra("MOVIE_TITLE", movieResponse.getMovieName());
                    intent.putExtra("MOVIE_NAME", movieResponse.getMovieName());
                    intent.putExtra("MOVIE_GENRES", new ArrayList<>(movieResponse.getMovieGenreNameList()));
                    intent.putExtra("MOVIE_LENGTH", movieResponse.getMovieLength());
                    intent.putExtra("MOVIE_DESCRIPTION", movieResponse.getDescription());
                    intent.putExtra("PUBLISHED_DATE", movieResponse.getPublishedDate());
                    intent.putExtra("IMAGE_URL", movieResponse.getImageUrl());
                    intent.putExtra("BACKGROUND_IMAGE_URL", movieResponse.getBackgroundImageUrl());
                    intent.putExtra("TRAILER", movieResponse.getTrailerLink());
                    intent.putExtra("MOVIE_RATING", new ArrayList<>(movieResponse.getMovieRatingDetailNameList()));
                    intent.putExtra("MOVIE_PERFORMER_NAME", new ArrayList<>(movieResponse.getMoviePerformerNameList()));

                    List<String> performerTypeStrings = new ArrayList<>();
                    for (PerformerType performerType : movieResponse.getMoviePerformerType()) {
                        performerTypeStrings.add(performerType.toString());
                    }
                    intent.putStringArrayListExtra("MOVIE_PERFORMER_TYPE", new ArrayList<>(performerTypeStrings));

                    intent.putExtra("MOVIE_COMMENT", new ArrayList<>(movieResponse.getComments()));
                    intent.putExtra("AVERAGE_STAR", movieResponse.getAverageRating());
                } else {
                    Toast.makeText(TheaterScheduleActivity.this, "Failed to fetch movie details.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("TheaterScheduleError", "Error fetching movie details", t);
                Toast.makeText(TheaterScheduleActivity.this, "Failed to load movie details. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Movie> getMoviesForDate(String date) {
        Activity activity = this;

        List<Movie> movies = TheaterDataProvider.getMoviesForTheater(date, activity);

        // Make sure movie titles match those in MovieDataProvider
        // This ensures MovieDetailsActivity can find the correct movie details
        return movies;
    }

    @Override
    public void onShowtimeClick(Movie movie, String showtime) {
        // Handle showtime selection
        Toast.makeText(this,
                "Selected: " + movie.getTitle() + " at " + showtime,
                Toast.LENGTH_SHORT).show();
    }
}