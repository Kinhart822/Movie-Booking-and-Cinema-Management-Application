package vn.edu.usth.mcma.frontend.component.ShowtimesOld.UI;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.common.MovieDetailActivity;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.GenreResponse;
import vn.edu.usth.mcma.frontend.dto.response.MovieResponse;
import vn.edu.usth.mcma.frontend.dto.response.PerformerResponse;
import vn.edu.usth.mcma.frontend.dto.response.Review;
import vn.edu.usth.mcma.frontend.dto.response.ScheduleSelectedByCinemaResponse;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models.Movie;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Adapters.MovieScheduleAdapter;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class TheaterScheduleActivity extends AppCompatActivity implements MovieScheduleAdapter.OnShowtimeClickListener {
    private MovieScheduleAdapter movieAdapter;
    private String selectedDate;
    private Button selectedDateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater_schedule);

        // Get theater details from intent
        Long theaterId = getIntent().getLongExtra(IntentKey.CINEMA_ID.name(), -1L);
        String theaterName = getIntent().getStringExtra(IntentKey.THEATER_NAME.name());
        String theaterAddress = getIntent().getStringExtra(IntentKey.THEATER_ADDRESS.name());

        // Set theater details in toolbar
        TextView nameTextView = findViewById(R.id.theater_name);
        TextView addressTextView = findViewById(R.id.theater_address);
        LinearLayout nav_container = findViewById(R.id.nav_container);
        nameTextView.setText(theaterName);
        addressTextView.setText(theaterAddress);

        // Setup back button
        ImageButton backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> finish());

        nav_container.setOnClickListener(v -> openMapWithAddress(theaterAddress));

        setupViews();
        setupDateButtons(theaterId);
    }
    private void openMapWithAddress(String theaterAddress) {
        if (theaterAddress != null && !theaterAddress.isEmpty()) {
            // Trường hợp muốn design lại theo ý mình
//            Intent intent = new Intent(TheaterScheduleActivity.this, GoogleMapActivity.class);
//            intent.putExtra("THEATER_ADDRESS", theaterAddress);
//            startActivity(intent);
            // Mở app lên
            try {
                String geoUri = "geo:0,0?q=" + Uri.encode(theaterAddress);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to open map.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Address not available.", Toast.LENGTH_SHORT).show();
        }
    }


    private void setupViews() {
        RecyclerView movieRecyclerView = findViewById(R.id.movie_recycler_view);
        movieAdapter = new MovieScheduleAdapter(this);
        movieRecyclerView.setAdapter(movieAdapter);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupDateButtons(Long theaterId) {
        LinearLayout daysContainer = findViewById(R.id.days_container);
        daysContainer.removeAllViews();
        ApiService
                .getCinemaApi(this)
                .getScheduleByCinema(theaterId)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<ScheduleSelectedByCinemaResponse> call, @NonNull Response<ScheduleSelectedByCinemaResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ScheduleSelectedByCinemaResponse scheduleResponse = response.body();

                            List<String> uniqueDates = new ArrayList<>();
                            List<String> uniqueDateEntries = new ArrayList<>();

                            if (scheduleResponse.getDate() != null && scheduleResponse.getDayOfWeek() != null
                                    && scheduleResponse.getDate().size() == scheduleResponse.getDayOfWeek().size()) {

                                for (int i = 0; i < scheduleResponse.getDate().size(); i++) {
                                    String date = scheduleResponse.getDate().get(i);
                                    String dayOfWeek = scheduleResponse.getDayOfWeek().get(i);
                                    String uniqueKey = dayOfWeek + "\n" + date;

                                    if (!uniqueDateEntries.contains(uniqueKey)) {
                                        uniqueDateEntries.add(uniqueKey);
                                        uniqueDates.add(date);
                                    }
                                }
                            }

                            if (uniqueDates.isEmpty()) {
                                Toast.makeText(TheaterScheduleActivity.this, "No schedule found for this cinema.", Toast.LENGTH_SHORT).show();
                            }

                            for (int i = 0; i < uniqueDates.size(); i++) {
                                final String originalDate = uniqueDates.get(i); // Dạng yyyy-MM-dd
                                final String formattedDate = convertDateFormat(originalDate); // Chuyển thành dd/MM/yyyy
                                final String buttonText = uniqueDateEntries.get(i);

                                Button dayButton = new Button(TheaterScheduleActivity.this);
                                dayButton.setText(buttonText);
                                dayButton.setAllCaps(false);
                                dayButton.setGravity(Gravity.CENTER);

                                // Thiết lập giao diện cho button
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                params.setMargins(8, 0, 8, 0);
                                int paddingInDp = 10;
                                int paddingInPx = (int) TypedValue.applyDimension(
                                        TypedValue.COMPLEX_UNIT_DIP,
                                        paddingInDp,
                                        getResources().getDisplayMetrics()
                                );
                                dayButton.setPadding(paddingInPx, paddingInPx, paddingInPx, paddingInPx);
                                dayButton.setLayoutParams(params);
                                dayButton.setBackground(getDrawable(R.drawable.button_selector_showtime));
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
                    public void onFailure(@NonNull Call<ScheduleSelectedByCinemaResponse> call, @NonNull Throwable t) {
                        Log.e("TheaterScheduleError", "Error fetching schedule", t);
                        Toast.makeText(TheaterScheduleActivity.this, "Failed to load schedule. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadMovieSchedule(String date) {
        ApiService
                .getMovieApi(this)
                .getAllMovieBySelectedDate(date)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<MovieResponse>> call, @NonNull Response<List<MovieResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Map<Integer, Long> movieIdMap = new HashMap<>();

                            // Lọc lịch chiếu theo ngày đã chọn
                            List<Movie> movies = response
                                    .body()
                                    .stream()
                                    .map(m -> Movie
                                            .builder()
                                            .movieId(m.getId())
                                            .movieName(m.getName())
                                            .title(m.getName())
                                            .movieLength(m.getLength())
                                            .description(m.getDescription())
                                            .publishDate(m.getPublishDate())
                                            .trailerUrl(m.getTrailerUrl())
                                            .imageUrl(m.getPoster())
                                            .backgroundImageUrl(m.getBanner())
                                            .schedules(m.getSchedules())
                                            .genreResponses(m.getGenreResponses())
                                            .performerResponses(m.getPerformerResponses())
                                            .ratingResponse(m.getRatingResponse())
                                            .reviews(m.getReviews())
                                            .build())
                                    .collect(Collectors.toList());
                            AtomicInteger i = new AtomicInteger(0);
                            movies.forEach(m -> movieIdMap.put(i.getAndIncrement(), m.getMovieId()));

                            movieAdapter.setMovies(movies); // Cập nhật adapter với danh sách phim
                            movieAdapter.setOnShowtimeClickListener((movie, showtime) -> {
                                int position = movies.indexOf(movie);
                                if (position != -1 && movieIdMap.containsKey(position)) {
                                    Long movieId = movieIdMap.get(position);
                                    assert movieId != null;
                                    fetchMovieDetails(movieId); // Fetch additional details
                                }
                            });


                        } else {
                            Toast.makeText(TheaterScheduleActivity.this, "No schedule found for this date.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<MovieResponse>> call, @NonNull Throwable t) {
                        Log.e("TheaterScheduleError", "Error fetching schedule", t);
                        Toast.makeText(TheaterScheduleActivity.this, "Failed to load schedule. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchMovieDetails(Long movieId) {
        ApiService
                .getMovieApi(this)
                .getAllInformationOfSelectedMovie(movieId)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            MovieResponse movieResponse = response.body();
                            // Handle movie details (e.g., display in a new activity)
                            Intent intent = new Intent(TheaterScheduleActivity.this, MovieDetailActivity.class);
                            //todo
                            intent.putExtra(IntentKey.MOVIE_TITLE.name(), movieResponse.getName());
                            intent.putExtra(IntentKey.MOVIE_NAME.name(), movieResponse.getName());
                            intent.putExtra("MOVIE_TRAILER_LINK", movieResponse.getTrailerUrl());
                            intent.putExtra(IntentKey.MOVIE_GENRES.name(), new ArrayList<>(movieResponse.getGenreResponses().stream().map(GenreResponse::getName).collect(Collectors.toList())));
                            intent.putExtra(IntentKey.MOVIE_LENGTH.name(), movieResponse.getLength());
                            intent.putExtra(IntentKey.MOVIE_DESCRIPTION.name(), movieResponse.getDescription());
                            intent.putExtra(IntentKey.PUBLISHED_DATE.name(), movieResponse.getPublishDate());
                            intent.putExtra(IntentKey.IMAGE_URL.name(), movieResponse.getPoster());
                            intent.putExtra(IntentKey.BACKGROUND_IMAGE_URL.name(), movieResponse.getBanner());
                            intent.putExtra(IntentKey.TRAILER.name(), movieResponse.getTrailerUrl());
                            intent.putExtra(IntentKey.MOVIE_RATING.name(), movieResponse.getRatingResponse().getName());
                            intent.putExtra(IntentKey.MOVIE_PERFORMER_NAME.name(), new ArrayList<>(movieResponse.getPerformerResponses().stream().map(PerformerResponse::getName).collect(Collectors.toList())));
                            intent.putStringArrayListExtra(IntentKey.MOVIE_PERFORMER_TYPE.name(), new ArrayList<>(movieResponse.getPerformerResponses().stream().map(p->p.getTypeId().toString()).collect(Collectors.toList())));
                            intent.putExtra(IntentKey.MOVIE_COMMENT.name(), new ArrayList<>(movieResponse.getReviews().stream().map(Review::getUserComment).collect(Collectors.toList())));
                            intent.putExtra(IntentKey.AVERAGE_STAR.name(), movieResponse.getReviews().stream().mapToInt(Review::getUserVote).average().orElse(0.0));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                        Log.e("TheaterScheduleError", "Error fetching movie details", t);
                        Toast.makeText(TheaterScheduleActivity.this, "Failed to load movie details. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onShowtimeClick(Movie movie, String showtime) {
        // Handle showtime selection
        Toast.makeText(this,
                "Selected: " + movie.getMovieName() + " at " + showtime,
                Toast.LENGTH_SHORT).show();
    }

    private String convertDateFormat(String originalDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date date = inputFormat.parse(originalDate);
            assert date != null;
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return originalDate; // Nếu có lỗi, trả về ngày gốc không thay đổi
        }
    }
}