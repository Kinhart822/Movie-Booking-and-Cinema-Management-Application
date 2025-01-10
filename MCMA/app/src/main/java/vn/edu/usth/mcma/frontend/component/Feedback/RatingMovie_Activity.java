package vn.edu.usth.mcma.frontend.component.Feedback;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView; // Thêm import này
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.Request.MovieRespondRequest;
import vn.edu.usth.mcma.frontend.network.ApiService;
import vn.edu.usth.mcma.frontend.constant.IntentKey;

public class RatingMovie_Activity extends AppCompatActivity {
    private EditText editFeedback;
    private RatingBar ratingBar;
    private TextView ratingScale;
    private Integer movieId;
    private static final String KEY_MOVIE_ID = "movieId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_movie);

        TextView movieName = findViewById(R.id.tvMovieName);
        TextView movieType = findViewById(R.id.tvMovieType);
        ImageView movieImage = findViewById(R.id.tvMovieImage);
        ImageButton mImageView = findViewById(R.id.back_button);
        ratingBar = findViewById(R.id.ratingBar);
        ratingScale = findViewById(R.id.tvRatingScale);
        editFeedback = findViewById(R.id.etComment);
        Button buttonSubmit = findViewById(R.id.btnSubmit);
        Intent intent = getIntent();
        String name = intent.getStringExtra(IntentKey.movie_name.name());
        String type = intent.getStringExtra(IntentKey.movie_type.name());
        String imageUrl = intent.getStringExtra(IntentKey.movie_image.name());

        movieId = intent.getIntExtra(KEY_MOVIE_ID, -1);

        if (movieId == -1) {
            Toast.makeText(this, "Movie ID not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Gán dữ liệu cho các view
        movieName.setText(name);
        movieType.setText(type);
        Glide.with(this).load(imageUrl).into(movieImage);

        // Xử lý nút quay lại
        mImageView.setOnClickListener(v -> finish());

        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            String ratingText;
            if (rating <= 1) {
                ratingText = "Terrible";
            } else if (rating <= 2) {
                ratingText = "Bad";
            } else if (rating <= 3) {
                ratingText = "Okay";
            } else if (rating <= 4) {
                ratingText = "Good";
            } else {
                ratingText = "Excellent!";
            }
            ratingScale.setText(ratingText);
        });

        buttonSubmit.setOnClickListener(v -> {
            String feedback = editFeedback.getText().toString().trim();
            float rating = ratingBar.getRating();

            if (feedback.isEmpty()) {
                Toast.makeText(this, "Please leave a comment", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Thank you for rating: " + rating + " stars\nFeedback: " + feedback, Toast.LENGTH_SHORT).show();
                // Reset the feedback and rating sections
                editFeedback.setText(""); // Clear the feedback text
                ratingBar.setRating(5);  // Reset the RatingBar to 0
                ratingScale.setText(R.string.rating_excellent); // Clear the rating description text
                submitFeedback(movieId, feedback, (double) rating);
            }
        });
    }

    private void submitFeedback(Integer movieId, String feedback, Double rating) {

        MovieRespondRequest movieRespondRequest = new MovieRespondRequest();
        movieRespondRequest.setMovieId(movieId);
        movieRespondRequest.setComment(feedback);
        movieRespondRequest.setSelectedRatingStar(rating);

        ApiService
                .getMovieApi(this)
                .addRespond(movieRespondRequest).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieRespondRequest> call, @NonNull Response<MovieRespondRequest> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RatingMovie_Activity.this, "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();
                            finish(); // Close the activity
                        } else {
                            Toast.makeText(RatingMovie_Activity.this, "Failed to submit feedback. Try again!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieRespondRequest> call, @NonNull Throwable t) {
                        Toast.makeText(RatingMovie_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
