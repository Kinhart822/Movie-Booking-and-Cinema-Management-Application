package vn.edu.usth.mcma.frontend.component.Personal;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.GenreResponse;
import vn.edu.usth.mcma.frontend.dto.response.NowShowingResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class Movie_Feedback_Activity extends AppCompatActivity {
    private Movie_Feedback_Adapter adapter;
    private List<Movie_Feedback_Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_feedback);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_movie_list_feedback);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        items = new ArrayList<>();
//        items.add(new Movie_Feedback_Item("Wolverine", "Action", R.drawable.movie4));
//        items.add(new Movie_Feedback_Item("IronMan", "Drama", R.drawable.movie13));
//        items.add(new Movie_Feedback_Item("Wicked", "Comedy", R.drawable.movie12));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Movie_Feedback_Adapter(this, items);
        recyclerView.setAdapter(adapter);

        fetchNowShowingMovies();

        ImageButton backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(view -> onBackPressed());
    }

    private void fetchNowShowingMovies() {
        ApiService
                .getMovieApi(this)
                .getAvailableNowShowingMovies()
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<NowShowingResponse>> call, @NonNull Response<List<NowShowingResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Clear current items
                            items.clear();
                            // Map NowShowingResponse
                            for (NowShowingResponse movie : response.body()) {
                                String genres = TextUtils.join(", ", movie.getGenres().stream().map(GenreResponse::getName).collect(Collectors.toList()));

                                items.add(new Movie_Feedback_Item(
                                        movie.getId(),
                                        movie.getName(),
                                        genres,
                                        movie.getImageBase64()
                                ));
                            }
                            // Notify adapter about data changes
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(Movie_Feedback_Activity.this, "Failed to fetch movies", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<NowShowingResponse>> call, @NonNull Throwable t) {
                        Toast.makeText(Movie_Feedback_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
