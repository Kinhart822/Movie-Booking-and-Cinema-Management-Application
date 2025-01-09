package vn.edu.usth.mcma.frontend.Home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.constants.IntentKey;

public class OnlyDetailsActivity extends AppCompatActivity {
    private TextView synopsisTextView;
    private TextView expandCollapseTextView;
    private boolean isSynopsisExpanded = false;

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

        // Hide the booking button
//        Button bookingButton = findViewById(R.id.bookingButton);
//        bookingButton.setVisibility(View.GONE);

        // Get movie title from intent
        Intent intent = getIntent();
        String movieName = intent.getStringExtra(IntentKey.MOVIE_NAME.name());
        List<String> movieGenres = intent.getStringArrayListExtra(IntentKey.MOVIE_GENRES.name());
        int movieLength = intent.getIntExtra(IntentKey.MOVIE_LENGTH.name(), 0);
        String movieDescription = intent.getStringExtra(IntentKey.MOVIE_DESCRIPTION.name());
        String publishedDate = intent.getStringExtra(IntentKey.PUBLISHED_DATE.name());
        String backgroundImageUrL = intent.getStringExtra(IntentKey.BACKGROUND_IMAGE_URL.name());
        List<String> movieRatings = intent.getStringArrayListExtra(IntentKey.MOVIE_RATING.name());
        List<String> moviePerformerNameList = intent.getStringArrayListExtra(IntentKey.MOVIE_PERFORMER_NAME.name());
        List<String> moviePerformerTypeList = intent.getStringArrayListExtra(IntentKey.MOVIE_PERFORMER_TYPE.name());
        List<String> movieCommentList = intent.getStringArrayListExtra(IntentKey.MOVIE_COMMENT.name());
        double averageStar = intent.getDoubleExtra(IntentKey.AVERAGE_STAR.name(), 0.0);

        // Set toolbar and collapsing toolbar title
        toolbarTitle.setText(movieName);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Back button on banner
        findViewById(R.id.btn_back_banner).setOnClickListener(v -> finish());

        // Back button in toolbar
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Populate movie details
        populateMovieDetails(movieName, movieGenres, movieLength, movieDescription, publishedDate, backgroundImageUrL, movieRatings, moviePerformerNameList, moviePerformerTypeList, movieCommentList, averageStar);
        setupSynopsisExpansion();
        setupToolbarBehavior(appBarLayout, toolbar);
    }

    private void populateMovieDetails(String movieName, List<String> movieGenres, Integer movieLength, String movieDescription, String publishedDate, String backgroundImageUrL, List<String> movieRatings, List<String> moviePerformerNameList, List<String> moviePerformerTypeList, List<String> movieCommentList, Double averageStar) {
        // Populate all TextViews with movie details
        ((TextView) findViewById(R.id.tv_movie_title)).setText(movieName);
        ImageView bannerView = findViewById(R.id.tv_movie_banner);
        Glide.with(this).load(backgroundImageUrL).into(bannerView);

        ((TextView) findViewById(R.id.tv_movie_genres)).setText(String.join(", ", movieGenres));
        ((TextView) findViewById(R.id.tv_duration)).setText(String.format("%d minutes", movieLength));
        ((TextView) findViewById(R.id.tv_synopsis)).setText(movieDescription);
        ((TextView) findViewById(R.id.tv_release_date)).setText(publishedDate);
        ((TextView) findViewById(R.id.tv_classification)).setText(String.join(", ", movieRatings));

        List<String> directors = new ArrayList<>();
        List<String> cast = new ArrayList<>();

        if (moviePerformerTypeList != null && moviePerformerNameList != null && moviePerformerTypeList.size() == moviePerformerNameList.size()) {
            for (int i = 0; i < moviePerformerTypeList.size(); i++) {
                String type = moviePerformerTypeList.get(i).toLowerCase();
                String name = moviePerformerNameList.get(i);

                if ("director".equals(type) || "0".equals(type)) {
                    directors.add(name);
                } else if ("actor".equals(type) || "1".equals(type)) {
                    cast.add(name);
                }
            }
        }

        ((TextView) findViewById(R.id.tv_director)).setText(directors.isEmpty() ? "N/A" : String.join(", ", directors));
        ((TextView) findViewById(R.id.tv_cast)).setText(cast.isEmpty() ? "N/A" : String.join(", ", cast));
        ((TextView) findViewById(R.id.tv_star)).setText(String.valueOf(averageStar));
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
