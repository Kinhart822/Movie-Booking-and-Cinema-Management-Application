package vn.edu.usth.mcma.frontend.Personal;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.Toast;

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
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Genre;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.NowShowingResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.NowShowingMovieAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;

public class Movie_Feedback_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Movie_Feedback_Adapter adapter;
    private List<Movie_Feedback_Item> items;
    private NowShowingMovieAPI nowShowingMovieAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_feedback);

        recyclerView = findViewById(R.id.recyclerview_movie_list_feedback);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        items = new ArrayList<>();
//        items.add(new Movie_Feedback_Item("Wolverine", "Action", R.drawable.movie4));
//        items.add(new Movie_Feedback_Item("IronMan", "Drama", R.drawable.movie13));
//        items.add(new Movie_Feedback_Item("Wicked", "Comedy", R.drawable.movie12));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Movie_Feedback_Adapter(this, items);
        recyclerView.setAdapter(adapter);

        RetrofitService retrofitService = new RetrofitService(this);
        nowShowingMovieAPI = retrofitService.getRetrofit().create(NowShowingMovieAPI.class);

        fetchNowShowingMovies();

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> onBackPressed());
    }

    private void fetchNowShowingMovies() {
        nowShowingMovieAPI.getAvailableNowShowingMovies().enqueue(new Callback<List<NowShowingResponse>>() {
            @Override
            public void onResponse(Call<List<NowShowingResponse>> call, Response<List<NowShowingResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Clear current items
                    items.clear();
                    // Map NowShowingResponse
                    for (NowShowingResponse movie : response.body()) {
                        String genres = TextUtils.join(", ", movie.getGenres().stream().map(Genre::getName).collect(Collectors.toList()));

                        items.add(new Movie_Feedback_Item(
                                movie.getId(),
                                movie.getName(),
                                genres,
                                movie.getImageUrl()
                        ));
                    }
                    // Notify adapter about data changes
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(Movie_Feedback_Activity.this, "Failed to fetch movies", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NowShowingResponse>> call, Throwable t) {
                Toast.makeText(Movie_Feedback_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
