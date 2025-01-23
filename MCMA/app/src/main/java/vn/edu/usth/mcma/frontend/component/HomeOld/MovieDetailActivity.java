package vn.edu.usth.mcma.frontend.component.HomeOld;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

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
    private ExoPlayer exoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        id = getIntent().getLongExtra(IntentKey.MOVIE_ID.name(), -1L);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        appBarLayout = findViewById(R.id.app_bar_layout);
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        synopsisTextView = findViewById(R.id.tv_synopsis);
        expandCollapseTextView = findViewById(R.id.tv_concise);
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

                        // Set toolbar and collapsing toolbar title
                        toolbarTitle.setText(movieDetail.getName());
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            getSupportActionBar().setDisplayShowHomeEnabled(true);
                        }

                        // Back button on banner
                        findViewById(R.id.btn_back_banner).setOnClickListener(v -> finish());

                        // Back button in toolbar
                        toolbar.setNavigationOnClickListener(v -> onBackPressed());
                        setupSynopsisExpansion();
                        setupToolbarBehavior(appBarLayout, toolbar);
                    }
                    @Override
                    public void onFailure(@NonNull Call<MovieDetail> call, @NonNull Throwable throwable) {
                        Log.d(TAG, "findMovieDetail onFailure: " + throwable);
                    }
                });
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void populateMovieDetails(MovieDetail movieDetail) {
        ((TextView) findViewById(R.id.text_view_movie_name)).setText(movieDetail.getName());
        // Setup video playback for banner
        PlayerView playerView = findViewById(R.id.tv_movie_banner);
        exoPlayer = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(exoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(movieDetail.getTrailerUrl());
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.play();
        ((TextView) findViewById(R.id.tv_movie_genres)).setText(String.join(", ", movieDetail.getGenres()));
        ((TextView) findViewById(R.id.tv_duration)).setText(String.format("%d minutes", movieDetail.getLength()));
        ((TextView) findViewById(R.id.tv_synopsis)).setText(movieDetail.getOverview());
        ((TextView) findViewById(R.id.tv_release_date)).setText(Instant.parse(movieDetail.getPublishDate()).atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("MMM dd, yyy")));
        ((TextView) findViewById(R.id.tv_classification)).setText(movieDetail.getRating());
        ((TextView) findViewById(R.id.tv_director)).setText(String.join(", ", movieDetail.getDirectors()));
        ((TextView) findViewById(R.id.tv_cast)).setText(String.join(", ", movieDetail.getActors()));
        ((TextView) findViewById(R.id.tv_star)).setText((movieDetail.getAvgVotes() == null ? "N/A" : movieDetail.getAvgVotes().toString()) + " ⭐");
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
                        expandCollapseTextView.setText("Reduce");
                        isSynopsisExpanded = true;
                    } else {
                        synopsisTextView.setMaxLines(maxLines);
                        expandCollapseTextView.setText("Expand");
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
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
