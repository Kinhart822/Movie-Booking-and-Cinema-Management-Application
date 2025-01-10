package vn.edu.usth.mcma.frontend.component.Showtimes.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.Showtimes.Models.MovieDetails;
import vn.edu.usth.mcma.frontend.component.Showtimes.Utils.MovieDataProvider;
import vn.edu.usth.mcma.frontend.constant.IntentKey;

public class MovieDetailsActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private TextView synopsisTextView;
    private TextView expandCollapseTextView;
    private boolean isSynopsisExpanded = false;
    private Button bookingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        // Find views
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        synopsisTextView = findViewById(R.id.tv_synopsis);
        expandCollapseTextView = findViewById(R.id.tv_concise);
        setSupportActionBar(toolbar);

        // Get movie title from intent
        String movieTitle = getIntent().getStringExtra(IntentKey.MOVIE_TITLE.name());
        MovieDetails movieDetails = MovieDataProvider.getMovieDetails(movieTitle);

        // Set toolbar and collapsing toolbar title
        toolbarTitle.setText(movieTitle);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Back button on banner
        findViewById(R.id.btn_back_banner).setOnClickListener(v -> finish());
        // Back button in toolbar
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Setup booking buttons based on theater type
//        setupBookingButtons(movieTitle);
        // Populate movie details
        populateMovieDetails(movieDetails);
        setupSynopsisExpansion();
        setupToolbarBehavior(appBarLayout, toolbar);
    }

    @SuppressLint("SetTextI18n")
    private void populateMovieDetails(MovieDetails movie) {
        // Populate all TextViews with movie details
        ((TextView)findViewById(R.id.tv_movie_title)).setText(movie.getTitle());
        ((ImageView)findViewById(R.id.tv_movie_banner)).setImageResource(movie.getBannerImageResId());
        ((TextView)findViewById(R.id.tv_movie_genres)).setText(String.join(", ", movie.getGenres()));
        ((TextView)findViewById(R.id.tv_duration)).setText(movie.getDuration() + " minutes");
        ((TextView)findViewById(R.id.tv_release_date)).setText(movie.getReleaseDate());
        ((TextView)findViewById(R.id.tv_synopsis)).setText(movie.getSynopsis());
        ((TextView)findViewById(R.id.tv_director)).setText(movie.getDirector());
        ((TextView)findViewById(R.id.tv_cast)).setText(String.join(", ", movie.getCast()));
        ((TextView)findViewById(R.id.tv_classification)).setText(movie.getClassification());
    }

    private void setupSynopsisExpansion() {
        synopsisTextView.post(() -> {
            int maxLines = 3;
            if (synopsisTextView.getLineCount() > maxLines) {
                synopsisTextView.setMaxLines(maxLines);
                expandCollapseTextView.setVisibility(View.VISIBLE);

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
}