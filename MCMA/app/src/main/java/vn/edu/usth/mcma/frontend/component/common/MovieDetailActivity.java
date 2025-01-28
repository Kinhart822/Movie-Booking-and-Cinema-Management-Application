package vn.edu.usth.mcma.frontend.component.common;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.willy.ratingbar.ScaleRatingBar;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.dto.movie.MovieDetail;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String TAG = MovieDetailActivity.class.getName();
    private Long id;
    private MovieDetail movieDetail;
    private YouTubePlayerView youTubePlayerView;
    private TextView nameTextView;
    private TextView genreTextView;
    private ScaleRatingBar scaleRatingBar;
    private TextView lengthTextView;
    private TextView overviewTextView;
    private TextView seeMoreLessTextView;
    private boolean isOverviewExpanded;
    private TextView publishDateTextView;
    private TextView ratingTextView;
    private TextView directorTextView;
    private TextView castTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        id = getIntent().getLongExtra(IntentKey.MOVIE_ID.name(), -1L);
        ImageButton backButton = findViewById(R.id.button_back);
        youTubePlayerView = findViewById(R.id.youtube_player_view_movie_trailer);
        getLifecycle().addObserver(youTubePlayerView);
        nameTextView = findViewById(R.id.text_view_movie_name);
        genreTextView = findViewById(R.id.text_view_movie_genres);
        scaleRatingBar = findViewById(R.id.scale_rating_bar_review);
        lengthTextView = findViewById(R.id.text_view_length);
        overviewTextView = findViewById(R.id.text_view_overview);
        seeMoreLessTextView = findViewById(R.id.text_view_see_more_less);
        isOverviewExpanded = false;
        publishDateTextView = findViewById(R.id.text_view_publish_date);
        ratingTextView = findViewById(R.id.text_view_rating);
        directorTextView = findViewById(R.id.text_view_director);
        castTextView = findViewById(R.id.text_view_cast);
        Button bookTicketsButton = findViewById(R.id.button_book_tickets);

        backButton
                .setOnClickListener(v -> onBackPressed());
        bookTicketsButton.setOnClickListener(v -> openMovieBookingActivity());

        findMovieDetail();
    }
    private void findMovieDetail() {
        ApiService
                .getMovieApi(this)
                .findMovieDetail(id)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieDetail> call, @NonNull Response<MovieDetail> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "findMovieDetail onResponse: code not ok || body is null");
                            return;
                        }
                        movieDetail = response.body();
                        postFindMovieDetail(movieDetail);
                    }
                    @Override
                    public void onFailure(@NonNull Call<MovieDetail> call, @NonNull Throwable throwable) {
                        Log.d(TAG, "findMovieDetail onFailure: " + throwable);
                    }
                });
    }
    @SuppressLint("DefaultLocale")
    private void postFindMovieDetail(MovieDetail movieDetail) {
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                youTubePlayer.cueVideo(movieDetail.getTrailerUrl(), 0);
            }
        });
        nameTextView.setText(movieDetail.getName());
        genreTextView.setText(String.join(", ", movieDetail.getGenres()));
        scaleRatingBar.setRating(movieDetail.getAvgVotes() != null ? movieDetail.getAvgVotes().floatValue() : 0);
        lengthTextView.setText(String.format("%d minutes", movieDetail.getLength()));
        overviewTextView.setText(movieDetail.getOverview());
        publishDateTextView.setText(Instant.parse(movieDetail.getPublishDate()).atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("MMM dd, yyy")));
        ratingTextView.setText(movieDetail.getRating());
        directorTextView.setText(String.join(", ", movieDetail.getDirectors()));
        castTextView.setText(String.join(", ", movieDetail.getActors()));
        prepareOverview();
    }
    @SuppressLint("SetTextI18n")
    private void prepareOverview() {
        seeMoreLessTextView.setVisibility(View.GONE);
        overviewTextView.post(() -> {
            int maxLines = 3;
            if (overviewTextView.getLineCount() <= maxLines) {
                seeMoreLessTextView.setVisibility(View.GONE);
                return;
            }
            overviewTextView.setMaxLines(maxLines);
            seeMoreLessTextView.setVisibility(View.VISIBLE);
            seeMoreLessTextView.setText("See more");
            seeMoreLessTextView.setOnClickListener(v -> {
                if (isOverviewExpanded) {
                    overviewTextView.setMaxLines(maxLines);
                    seeMoreLessTextView.setText("See more");
                    isOverviewExpanded = false;
                    return;
                }
                overviewTextView.setMaxLines(Integer.MAX_VALUE);
                seeMoreLessTextView.setText("See less");
                isOverviewExpanded = true;
            });
        });
    }
    private void openMovieBookingActivity() {
        Intent intent = new Intent(MovieDetailActivity.this, MovieBookingActivity.class);
        intent.putExtra(IntentKey.MOVIE_TITLE.name(), movieDetail.getName());//todo
        intent.putExtra(IntentKey.MOVIE_ID.name(), movieDetail.getId());
        startActivity(intent);
    }
}
