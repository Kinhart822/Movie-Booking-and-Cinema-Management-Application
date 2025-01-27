package vn.edu.usth.mcma.frontend.component.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.UI.MovieBookingActivity;
import vn.edu.usth.mcma.frontend.component.common.GenreButton;
import vn.edu.usth.mcma.frontend.dto.movie.GenreShort;
import vn.edu.usth.mcma.frontend.dto.movie.MovieDetailShort;
import vn.edu.usth.mcma.frontend.component.common.MovieDetailActivity;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.helper.diff.MovieDiffCallback;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class SearchMovieActivity extends AppCompatActivity {
    private static final String TAG = SearchMovieActivity.class.getName();
    private SearchView searchView;
    private String name;
    private SearchMovieAdapter adapter;
    private LinearLayout genreButtonsLinearLayout;
    private RecyclerView searchResultRecyclerView;
    private Set<Long> selectedGenres;
    private List<MovieDetailShort> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        ImageButton backButton = findViewById(R.id.button_back);
        searchView = findViewById(R.id.search_view);
        name = "";
        genreButtonsLinearLayout = findViewById(R.id.linear_layout_genre_buttons);
        searchResultRecyclerView = findViewById(R.id.recycler_view_search_result);
        selectedGenres = new HashSet<>();
        items = new ArrayList<>();

        backButton
                .setOnClickListener(view -> onBackPressed());

        prepareSearchView();
        findAllGenre();
        prepareSearchResultRecyclerView();
        findAllMovieByGenre();
    }
    private void prepareSearchView() {
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                name = query;
                findAllMovieByGenre();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                name = newText;
                findAllMovieByGenre();
                return false;
            }
        });
    }
    private void prepareSearchResultRecyclerView() {
        adapter = new SearchMovieAdapter(this, items, new ISearchItemView() {
            @Override
            public void onPosterClickListener(int position) {
                navigateToMovieDetailActivity(position);
            }
            @Override
            public void onBookTicketsClickListener(int position) {
                openMovieBookingActivity(position);
            }
        });
        searchResultRecyclerView.setAdapter(adapter);
        searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
    private void navigateToMovieDetailActivity(int position) {
        MovieDetailShort selectedFilm = items.get(position);
        Intent intent = new Intent(SearchMovieActivity.this, MovieDetailActivity.class);
        intent.putExtra(IntentKey.MOVIE_ID.name(), selectedFilm.getId());
        startActivity(intent);
    }
    private void openMovieBookingActivity(int position) {
        MovieDetailShort movie = items.get(position);
        Intent intent = new Intent(SearchMovieActivity.this, MovieBookingActivity.class);
        intent.putExtra(IntentKey.MOVIE_TITLE.name(), movie.getName());//todo
        intent.putExtra(IntentKey.MOVIE_ID.name(), movie.getId());
        startActivity(intent);
    }
    private void findAllGenre() {
        ApiService
                .getMovieApi(this)
                .findAllGenre()
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<GenreShort>> call, @NonNull Response<List<GenreShort>> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "findAllGenre onResponse: code not 200 || body is null");
                            return;
                        }
                        response.body()
                                .forEach(g -> addGenreButton(g));
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<GenreShort>> call, @NonNull Throwable t) {
                        Log.e(TAG, "findAllGenre onFailure: " + t);
                    }
                });
    }
    private void addGenreButton(GenreShort genre) {
        GenreButton genreButton = new GenreButton(this);
        genreButton.setGenreId(genre.getId());
        genreButton.setText(genre.getName());
        genreButton.setTransformationMethod(null);
        genreButton.setBackgroundColor(0xFFD0D0D0);
        genreButton.setTextColor(ContextCompat.getColor(this, R.color.black));
        genreButton.setPadding(20, 10, 20, 10);
        genreButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17.5f);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8, 0, 8, 0);
        genreButton.setLayoutParams(params);
        genreButton.setOnClickListener(v -> {
            if (genreButton.isSelected()) {
                genreButton.setSelected(false);
                genreButton.setBackgroundColor(0xFFD0D0D0);
                selectedGenres.remove(genreButton.getGenreId());
                findAllMovieByGenre();
                return;
            }
            genreButton.setSelected(true);
            genreButton.setBackgroundColor(0xFF6098EB);
            selectedGenres.add(genreButton.getGenreId());
            findAllMovieByGenre();
        });
        genreButtonsLinearLayout.addView(genreButton);
    }
    private void findAllMovieByGenre() {
        ApiService
                .getMovieApi(this)
                .findAllMovieByGenre(name.trim(), selectedGenres)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<MovieDetailShort>> call, @NonNull Response<List<MovieDetailShort>> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "findAllMovieByGenre onResponse: code not 200 || body is null");
                            return;
                        }
                        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MovieDiffCallback(items, response.body()));
                        items.clear();
                        items.addAll(response.body());
                        diffResult.dispatchUpdatesTo(adapter);
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<MovieDetailShort>> call, @NonNull Throwable t) {
                        Log.e(TAG, "findAllMovieByGenre onFailure: " + t);
                    }
                });
    }
}
