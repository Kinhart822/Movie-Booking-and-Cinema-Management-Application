package vn.edu.usth.mcma.frontend.component.HomeOld;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

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
    private TextView synopsisTextView;
    private TextView expandCollapseTextView;
    private boolean isSynopsisExpanded = false;
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        id = getIntent().getLongExtra(IntentKey.MOVIE_ID.name(), -1L);
//        appBarLayout = findViewById(R.id.app_bar_layout);
//        toolbar = findViewById(R.id.toolbar);
//        toolbarTitle = findViewById(R.id.toolbar_title);
        synopsisTextView = findViewById(R.id.text_view_overview);
        expandCollapseTextView = findViewById(R.id.text_view_see_more_less);
        setSupportActionBar(toolbar);

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
                        populateMovieDetails(movieDetail);
                    }
                    @Override
                    public void onFailure(@NonNull Call<MovieDetail> call, @NonNull Throwable throwable) {
                        Log.d(TAG, "findMovieDetail onFailure: " + throwable);
                    }
                });
    }
    private void populateMovieDetails(MovieDetail movieDetail) {
        ((TextView) findViewById(R.id.text_view_movie_name)).setText(movieDetail.getName());

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view_movie_trailer);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                youTubePlayer.cueVideo(movieDetail.getTrailerUrl(), 0);
            }
        });

        ((TextView) findViewById(R.id.text_view_movie_genres)).setText(String.join(", ", movieDetail.getGenres()));
        ((TextView) findViewById(R.id.text_view_length)).setText(String.format("%d minutes", movieDetail.getLength()));
        ((TextView) findViewById(R.id.text_view_overview)).setText(movieDetail.getOverview());
        ((TextView) findViewById(R.id.text_view_publish_date)).setText(Instant.parse(movieDetail.getPublishDate()).atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("MMM dd, yyy")));
        ((TextView) findViewById(R.id.text_view_rating)).setText(movieDetail.getRating());
        ((TextView) findViewById(R.id.text_view_director)).setText(String.join(", ", movieDetail.getDirectors()));
        ((TextView) findViewById(R.id.text_view_cast)).setText(String.join(", ", movieDetail.getActors()));
        ((TextView) findViewById(R.id.text_view_review)).setText((movieDetail.getAvgVotes() == null ? "N/A" : movieDetail.getAvgVotes().toString()) + " ⭐");

//        toolbarTitle.setText(movieDetail.getName());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Back button on banner
//                        findViewById(R.id.btn_back_banner).setOnClickListener(v -> finish());

        // Back button in toolbar
//        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        setupSynopsisExpansion();
//        setupToolbarBehavior(appBarLayout, toolbar);
    }

    @SuppressLint("SetTextI18n")
    private void setupSynopsisExpansion() {
        synopsisTextView.post(() -> {
            int maxLines = 3;
            if (synopsisTextView.getLineCount() > maxLines) {
                synopsisTextView.setMaxLines(maxLines);

                expandCollapseTextView.setOnClickListener(v -> {
                    if (!isSynopsisExpanded) {
                        synopsisTextView.setMaxLines(Integer.MAX_VALUE);
                        expandCollapseTextView.setText("See less");
                        isSynopsisExpanded = true;
                    } else {
                        synopsisTextView.setMaxLines(maxLines);
                        expandCollapseTextView.setText("See more");
                        isSynopsisExpanded = false;
                    }
                });
            } else {
                expandCollapseTextView.setVisibility(View.GONE);
            }
        });
    }

    private void setupToolbarBehavior(AppBarLayout appBarLayout, Toolbar toolbar) {
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if (Math.abs(verticalOffset) >= appBarLayout1.getTotalScrollRange()) {
                // Fully collapsed
                toolbar.setVisibility(View.VISIBLE);
            } else {
                // Expanded or partially expanded
                toolbar.setVisibility(View.GONE);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
