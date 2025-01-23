package vn.edu.usth.mcma.frontend.component.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.Search.Search_Adapter;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.GenreResponse;
import vn.edu.usth.mcma.frontend.dto.response.MovieGenreResponse;
import vn.edu.usth.mcma.frontend.dto.response.PerformerResponse;
import vn.edu.usth.mcma.frontend.dto.response.SearchMovieByNameResponse;
import vn.edu.usth.mcma.frontend.component.HomeOld.MovieDetailActivity;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class SearchMovieActivity extends AppCompatActivity {
    private SearchView searchView;
    private Search_Adapter adapter;
    private final List<SearchMovieByNameResponse> items = new ArrayList<>();
    private List<SearchMovieByNameResponse> filteredItems;
    private List<View> buttons;
    private LinearLayout genreButtonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();

        RecyclerView recyclerView = findViewById(R.id.recyclersearch_activity);

        filteredItems = new ArrayList<>();
        buttons = new ArrayList<>(); // Khởi tạo danh sách button

        genreButtonContainer = findViewById(R.id.genre_button_container);

//        filteredItems.addAll(items);

        adapter = new Search_Adapter(this, filteredItems, this::openMovieDetailsActivity);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        fetchMoviesFromApi();
        fetchAndDisplayGenres();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchMoviesFromApi(); // Fetch movies based on the query
                return true; // Indicate that the query submission is handled
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Optional: Update the UI or filter local data here if needed
                filterList(newText);
                return false;
//                return false;
            }

        });

        ImageButton backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(view -> onBackPressed());
    }

    private void openMovieDetailsActivity(int position) {
        SearchMovieByNameResponse clickedItem = filteredItems.get(position);
        Intent intent = new Intent(SearchMovieActivity.this, MovieDetailActivity.class);

        intent.putExtra(IntentKey.MOVIE_NAME.name(), clickedItem.getName());
        intent.putExtra(IntentKey.MOVIE_GENRES.name(), new ArrayList<>(clickedItem.getGenreResponses().stream().map(GenreResponse::getName).collect(Collectors.toList())));
        intent.putExtra(IntentKey.MOVIE_LENGTH.name(), clickedItem.getLength());
        intent.putExtra(IntentKey.MOVIE_DESCRIPTION.name(), clickedItem.getDescription());
        intent.putExtra(IntentKey.PUBLISHED_DATE.name(), clickedItem.getPublishDate());
        intent.putExtra(IntentKey.IMAGE_URL.name(), clickedItem.getImageUrl());
        intent.putExtra(IntentKey.BACKGROUND_IMAGE_URL.name(), clickedItem.getBackgroundImageUrl());
        intent.putExtra(IntentKey.TRAILER.name(), clickedItem.getTrailerUrl());
        intent.putExtra(IntentKey.MOVIE_RATING.name(), clickedItem.getRatingResponse().getName());
        intent.putExtra(IntentKey.MOVIE_PERFORMER_NAME.name(), new ArrayList<>(clickedItem.getPerformerResponses().stream().map(PerformerResponse::getName).collect(Collectors.toList())));
        intent.putStringArrayListExtra(IntentKey.MOVIE_PERFORMER_TYPE.name(), new ArrayList<>(clickedItem.getPerformerResponses().stream().map(p->p.getTypeId().toString()).collect(Collectors.toList())));

        startActivity(intent);
    }

    private void fetchAndDisplayGenres() {
        ApiService
                .getMovieApi(this)
                .getAllMovieGenres().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<MovieGenreResponse>> call, @NonNull Response<List<MovieGenreResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<MovieGenreResponse> genres = response.body();

                            // Add "All" button first
                            addGenreButton("All");

                            // Add buttons for each genre dynamically
                            for (MovieGenreResponse genre : genres) {
                                addGenreButton(genre.getGenreName());
                            }
                        } else {
                            Toast.makeText(SearchMovieActivity.this, "Failed to fetch genres", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<MovieGenreResponse>> call, @NonNull Throwable t) {
                        Toast.makeText(SearchMovieActivity.this, "Failed to fetch genres", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addGenreButton(String genreName) {
        Button button = new Button(this);
        button.setText(genreName);
        button.setTransformationMethod(null);
        button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorLightBlue));
        button.setTextColor(ContextCompat.getColor(this, R.color.black));
        button.setPadding(20, 10, 20, 10);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17.5f);

        // Set button margins programmatically
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8, 0, 8, 0);
        button.setLayoutParams(params);

        // Add button to container
        genreButtonContainer.addView(button);

        // Set click listener
        button.setOnClickListener(v -> {
            resetButtonColors();
            button.setBackgroundColor(Color.LTGRAY);
            filterByCategory(genreName);
        });

        buttons.add(button); // Add button to list for reset
    }


    private void fetchMoviesFromApi() {
        String title = searchView.getQuery().toString().trim();

        ApiService
                .getMovieApi(this)
                .searchMovies(title)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<SearchMovieByNameResponse>> call, @NonNull Response<List<SearchMovieByNameResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            items.clear();
                            items.addAll(response.body());
                            filteredItems.clear();
                            filteredItems.addAll(items); // Initially show all movies
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(SearchMovieActivity.this, "Failed to fetch movies", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<SearchMovieByNameResponse>> call, @NonNull Throwable t) {
                        Toast.makeText(SearchMovieActivity.this, "Failed to fetch movies", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void resetButtonColors() {
        for (View button : buttons) {
            button.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void filterByCategory(String category) {
        filteredItems.clear();

        if (category.equalsIgnoreCase("All")) {
            filteredItems.addAll(items);
        } else {
            for (SearchMovieByNameResponse item : items) {
                List<String> genres = item.getGenreResponses().stream().map(GenreResponse::getName).collect(Collectors.toList());
                if (genres.contains(category)) {
                    filteredItems.add(item);
                }
//                if (item.getGenreName().equalsIgnoreCase(category)) {
//                    filteredItems.add(item);
//                }
            }

            if (filteredItems.isEmpty()) {
                Toast.makeText(this, "No films found in category: " + category, Toast.LENGTH_SHORT).show();
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void filterList(String text) {
        filteredItems.clear();
        for (SearchMovieByNameResponse item : items) {
            // Check if the name matches
            boolean matchesName = item.getName().toLowerCase().contains(text.toLowerCase());

            // Check if any genre in the genreNameList matches
            boolean matchesGenre = false;
            if (item.getGenreResponses() != null) {
                for (String genre : item.getGenreResponses().stream().map(GenreResponse::getName).collect(Collectors.toList())) {
                    if (genre.toLowerCase().contains(text.toLowerCase())) {
                        matchesGenre = true;
                        break;
                    }
                }
            }

            // Add item if it matches either name or genre
            if (matchesName || matchesGenre) {
                filteredItems.add(item);
            }
        }
//        for (SearchMovieByNameResponse item : items) {
//            if (item.getName().toLowerCase().contains(text.toLowerCase()) ||
//                    item.getGenreName().toLowerCase().contains(text.toLowerCase())) {
//                filteredItems.add(item);
//            }
//        }

        if (filteredItems.isEmpty()) {
            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
