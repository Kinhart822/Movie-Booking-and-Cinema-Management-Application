package vn.edu.usth.mcma.frontend.Showtimes.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Showtimes.Models.MovieDetails;
import vn.edu.usth.mcma.frontend.Showtimes.Models.TheaterType;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.MovieDataProvider;

public class MovieDetailsActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private TextView synopsisTextView;
    private TextView expandCollapseTextView;
    private boolean isSynopsisExpanded = false;
    private TheaterType currentTheaterType;
    private Button bookingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Get theater type from intent if available
        currentTheaterType = (TheaterType) getIntent().getSerializableExtra("THEATER_TYPE");
        if (currentTheaterType == null) {
            currentTheaterType = TheaterType.REGULAR; // Default fallback
        }
        // Find views
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        synopsisTextView = findViewById(R.id.tv_synopsis);
        expandCollapseTextView = findViewById(R.id.tv_concise);
        setSupportActionBar(toolbar);

        // Get movie title from intent
        String movieTitle = getIntent().getStringExtra("MOVIE_TITLE");
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
        setupBookingButtons(movieTitle);
        // Populate movie details
        populateMovieDetails(movieDetails);
        setupSynopsisExpansion();
        setupToolbarBehavior(appBarLayout, toolbar);
    }

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
        ((TextView)findViewById(R.id.tv_language)).setText(movie.getLanguage());
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
                        expandCollapseTextView.setText("Thu gọn");
                        isSynopsisExpanded = true;
                    } else {
                        synopsisTextView.setMaxLines(maxLines);
                        expandCollapseTextView.setText("Xem thêm");
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

    private void setupBookingButtons(String movieTitle) {
        LinearLayout bookingButtonsContainer = findViewById(R.id.booking_buttons_container);
        bookingButtonsContainer.removeAllViews(); // Clear any existing buttons

        bookingButton = new Button(this);
        String buttonText = getString(R.string.book_ticket) + " - " + currentTheaterType.getDisplayName();
        bookingButton.setText(buttonText);

        // Set button styling if needed
        if (currentTheaterType == TheaterType.FIRST_CLASS) {
            // Apply first class styling
            bookingButton.setBackgroundResource(R.drawable.first_class_button_bg);
        } else {
            // Apply regular styling
            bookingButton.setBackgroundResource(R.drawable.regular_button_bg);
        }

        bookingButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MovieBookingActivity.class);
            intent.putExtra("MOVIE_TITLE", movieTitle);
            intent.putExtra("THEATER_TYPE", currentTheaterType);
            startActivity(intent);
        });

        // Add button to container
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonParams.setMargins(0, 8, 0, 8);
        bookingButton.setLayoutParams(buttonParams);
        bookingButtonsContainer.addView(bookingButton);
    }
}