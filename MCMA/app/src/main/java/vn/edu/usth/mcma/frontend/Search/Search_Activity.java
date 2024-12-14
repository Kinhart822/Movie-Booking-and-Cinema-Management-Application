package vn.edu.usth.mcma.frontend.Search;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.MovieGenreResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.SearchMovieByNameResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.GetAllMovieGenres;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.SearchMovieByName;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;

public class Search_Activity extends AppCompatActivity implements SearchViewInterface {
    private SearchView searchView;
    private RecyclerView recyclerView;
    private Search_Adapter adapter;
    private List<SearchMovieByNameResponse> items;
    private List<SearchMovieByNameResponse> filteredItems;
    private List<View> buttons; // Danh sách lưu các button
    private List<MovieGenreResponse> genres; // Store fetched genres
    private LinearLayout genreButtonContainer; // Container for dynamic buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();

        recyclerView = findViewById(R.id.recyclersearch_activity);

        items = new ArrayList<>();
        filteredItems = new ArrayList<>();
        buttons = new ArrayList<>(); // Khởi tạo danh sách button

        genreButtonContainer = findViewById(R.id.genre_button_container);

//        filteredItems.addAll(items);

        adapter = new Search_Adapter(this, filteredItems, this);
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

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> onBackPressed());

//        setupCategoryButton(findViewById(R.id.btn_all), "All");
//        setupCategoryButton(findViewById(R.id.btn_action), "Action");
//        setupCategoryButton(findViewById(R.id.btn_adventure), "Adventure");
//        setupCategoryButton(findViewById(R.id.btn_comedy), "Comedy");
//        setupCategoryButton(findViewById(R.id.btn_horror), "Horror");
//        setupCategoryButton(findViewById(R.id.btn_drama), "Drama");
    }

    private void fetchAndDisplayGenres() {
        RetrofitService retrofitService = new RetrofitService(this);
        GetAllMovieGenres getAllMovieGenres = retrofitService.getRetrofit().create(GetAllMovieGenres.class);
        getAllMovieGenres.getAllMovieGenres().enqueue(new Callback<List<MovieGenreResponse>>() {
            @Override
            public void onResponse(Call<List<MovieGenreResponse>> call, Response<List<MovieGenreResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MovieGenreResponse> genres = response.body();

                    // Add "All" button first
                    addGenreButton("All");

                    // Add buttons for each genre dynamically
                    for (MovieGenreResponse genre : genres) {
                        addGenreButton(genre.getGenreName());
                    }
                } else {
                    Toast.makeText(Search_Activity.this, "Failed to fetch genres", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MovieGenreResponse>> call, Throwable t) {
                Toast.makeText(Search_Activity.this, "Failed to fetch genres", Toast.LENGTH_SHORT).show();
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
        int limit = 5;
        int offset = 0;

        RetrofitService retrofitService = new RetrofitService(this);
        SearchMovieByName searchMovieByName = retrofitService.getRetrofit().create(SearchMovieByName.class);
        searchMovieByName.searchMovies(title, limit, offset).enqueue(new Callback<List<SearchMovieByNameResponse>>() {
            @Override
            public void onResponse(Call<List<SearchMovieByNameResponse>> call, Response<List<SearchMovieByNameResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    items.clear();
                    items.addAll(response.body());
                    filteredItems.clear();
                    filteredItems.addAll(items); // Initially show all movies
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(Search_Activity.this, "Failed to fetch movies", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SearchMovieByNameResponse>> call, Throwable t) {
                Toast.makeText(Search_Activity.this, "Failed to fetch movies", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupCategoryButton(View button, String category) {
        buttons.add(button);
        button.setOnClickListener(v -> {
            resetButtonColors();
            button.setBackgroundColor(Color.LTGRAY);
            filterByCategory(category);
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
                List<String> genres = item.getGenreNameList();
                if (genres != null && genres.contains(category)) {
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
            if (item.getGenreNameList() != null) {
                for (String genre : item.getGenreNameList()) {
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
    public void onItemClick(int position) {
        SearchMovieByNameResponse clickedItem = filteredItems.get(position);
        Toast.makeText(this, "Film: " + clickedItem.getName(), Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
