package vn.edu.usth.mcma.frontend.Feedback;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView; // Thêm import này
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Request.MovieRespondRequest;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.AddMovieRespondAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;

public class RatingMovie_Activity extends AppCompatActivity {
    private EditText editFeedback;
    private Button buttonSubmit;
    private RatingBar ratingBar;
    private TextView ratingScale;
    private Integer movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_movie);

        TextView movieName = findViewById(R.id.tvMovieName);
        TextView movieType = findViewById(R.id.tvMovieType);
        ImageView movieImage = findViewById(R.id.tvMovieImage); // Thêm ImageView
        ImageButton mImageView = findViewById(R.id.back_button);
        ratingBar = findViewById(R.id.ratingBar);
        ratingScale = findViewById(R.id.tvRatingScale);
        editFeedback = findViewById(R.id.etComment);
        buttonSubmit = findViewById(R.id.btnSubmit);

//        // Lấy dữ liệu từ intent trong adapter
//        String name = getIntent().getStringExtra("movie_name");
//        String type = getIntent().getStringExtra("movie_type");
//        int imageResId = getIntent().getIntExtra("movie_image", R.drawable.movie5); // Lấy hình ảnh, với giá trị mặc định nếu không có

        // Retrieve data passed from FeedbackFragment
        Intent intent = getIntent();
        String name = intent.getStringExtra("movie_name");
        String type = intent.getStringExtra("movie_type");
        String imageUrl = intent.getStringExtra("movie_image");
        movieId = intent.getIntExtra("movieId", -1);


        // Gán dữ liệu cho các view
        movieName.setText(name);
        movieType.setText(type);
//        movieImage.setImageResource(imageResId); // Hiển thị hình ảnh
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

//                Intent resultIntent = new Intent();
//                resultIntent.putExtra("rating", rating);
//                resultIntent.putExtra("feedback", feedback);
//                setResult(RESULT_OK, resultIntent);
//                finish();

                // Reset the feedback and rating sections
                editFeedback.setText(""); // Clear the feedback text
                ratingBar.setRating(5);  // Reset the RatingBar to 0
                ratingScale.setText(R.string.rating_excellent); // Clear the rating description text
                submitFeedback(movieId, feedback, (double) rating);
            }
        });
    }

    private void submitFeedback(Integer movieId, String feedback, Double rating) {
        RetrofitService retrofitService = new RetrofitService(this);
        AddMovieRespondAPI addMovieRespondAPI = retrofitService.getRetrofit().create(AddMovieRespondAPI.class);

        MovieRespondRequest movieRespondRequest = new MovieRespondRequest();
        movieRespondRequest.setMovieId(movieId);
        movieRespondRequest.setComment(feedback);
        movieRespondRequest.setSelectedRatingStar(rating);

        addMovieRespondAPI.addRespond(movieRespondRequest).enqueue(new Callback<MovieRespondRequest>() {
            @Override
            public void onResponse(Call<MovieRespondRequest> call, Response<MovieRespondRequest> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RatingMovie_Activity.this, "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                } else {
                    Toast.makeText(RatingMovie_Activity.this, "Failed to submit feedback. Try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieRespondRequest> call, Throwable t) {
                Toast.makeText(RatingMovie_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
