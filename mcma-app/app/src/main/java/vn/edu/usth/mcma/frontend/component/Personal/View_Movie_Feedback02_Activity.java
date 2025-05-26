package vn.edu.usth.mcma.frontend.component.Personal;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.response.MovieRespondResponse;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class View_Movie_Feedback02_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private View_Movie_Feedback02_Adapter adapter;
    private List<View_Movie_Feedback02_Item> feedbackItems;
    private int movieId;
    private FrameLayout noDataContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie_feedback);

        recyclerView = findViewById(R.id.recyclerview_view_movie_feedback);
        noDataContainer = findViewById(R.id.movie_feedback_no_data_container);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        feedbackItems = getSampleFeedbacks();
        feedbackItems = new ArrayList<>();
        adapter = new View_Movie_Feedback02_Adapter(feedbackItems);
        recyclerView.setAdapter(adapter);

        // Lấy tên phim từ Intent (nếu cần)
        String movieName = getIntent().getStringExtra(IntentKey.movie_feedback_name.name());
        if (movieName != null) {
            // Xử lý tên phim, ví dụ hiển thị trong UI
        }

        movieId = getIntent().getIntExtra(IntentKey.movie_feedback_id.name(), 0);
        showNoDataView();
        // Fetch movie feedback data from API
        fetchMovieFeedbacks();

        ImageButton backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(view -> onBackPressed());
    }

    void showNoDataView() {
        recyclerView.setVisibility(View.GONE);
        noDataContainer.setVisibility(View.VISIBLE);
    }
    void hideNoDataView() {
        recyclerView.setVisibility(View.VISIBLE);
        noDataContainer.setVisibility(View.GONE);
    }


    private void fetchMovieFeedbacks() {
        ApiService
                .getMovieApi(this)
                .viewMovieRespondByMovie(movieId).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<MovieRespondResponse>> call, @NonNull Response<List<MovieRespondResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Clear the existing list
                            feedbackItems.clear();

                            // Map the API response to your View_Movie_Feedback02_Item model
                            if (response.body().isEmpty()) {
                                showNoDataView();
                            } else {
                                for (MovieRespondResponse movieFeedback : response.body()) {
                                    feedbackItems.add(new View_Movie_Feedback02_Item(
                                            movieFeedback.getMovieName(),
                                            movieFeedback.getContent(),
                                            movieFeedback.getRatingStar()
                                    ));
                                }
                                // Notify the adapter that the data has changed
                                hideNoDataView();
                                adapter.notifyDataSetChanged();
                            }
                        } else {
//                    Toast.makeText(View_Movie_Feedback02_Activity.this, "Failed to fetch movie feedbacks", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<MovieRespondResponse>> call, @NonNull Throwable t) {
                        showNoDataView();
//                Toast.makeText(View_Movie_Feedback02_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    private List<View_Movie_Feedback02_Item> getSampleFeedbacks() {
//        List<View_Movie_Feedback02_Item> feedbacks = new ArrayList<>();
//        feedbacks.add(new View_Movie_Feedback02_Item(4.5f, "Loved the action scenes!"));
//        feedbacks.add(new View_Movie_Feedback02_Item(3.0f, "Could be better."));
//        feedbacks.add(new View_Movie_Feedback02_Item(5.0f, "One of the best movies ever!"));
//        feedbacks.add(new View_Movie_Feedback02_Item(2.5f, "Storyline was weak."));
//        return feedbacks;
//    }
}

