package vn.edu.usth.mcma.frontend.component.ShowtimesOld.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.GenreResponse;
import vn.edu.usth.mcma.frontend.dto.response.PerformerResponse;
import vn.edu.usth.mcma.frontend.dto.response.Review;
import vn.edu.usth.mcma.frontend.component.common.MovieDetailActivity;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models.Movie;
import vn.edu.usth.mcma.frontend.constant.IntentKey;

public class MovieScheduleAdapter extends RecyclerView.Adapter<MovieScheduleAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private OnShowtimeClickListener listener;

    public MovieScheduleAdapter(OnShowtimeClickListener listener) {
        this.movies = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item_layout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    public void setOnShowtimeClickListener(OnShowtimeClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView movieTitle;
        private TextView viewDetails;
        private LinearLayout timeContainer;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movie_title);
            viewDetails = itemView.findViewById(R.id.view_details);
            timeContainer = itemView.findViewById(R.id.time_container);
        }

        void bind(Movie movie) {
            movieTitle.setText(movie.getTitle());
            timeContainer.removeAllViews();
            List<String> sortedSchedules = movie
                    .getSchedules()
                    .stream()
                    .map(s -> Instant
                            .parse(s.getStartTime()))
                    .sorted()
                    .map(s -> s
                            .toString()
                            .substring(11,16))
                    .collect(Collectors.toList());

            // 4. Tạo nút (Button) cho mỗi thời gian đã sắp xếp
            for (String schedule : sortedSchedules) {
                Button timeButton = new Button(itemView.getContext());
                timeButton.setText(schedule);
                timeButton.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onShowtimeClick(movie, schedule);
                    }
                });

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        itemView.getContext().getResources().getDimensionPixelSize(R.dimen.time_button_width),
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(8, 0, 8, 0);
                timeButton.setLayoutParams(params);

                timeContainer.addView(timeButton);
            }

            viewDetails.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), MovieDetailActivity.class);
                intent.putExtra(IntentKey.MOVIE_NAME.name(), movie.getTitle());
                intent.putExtra(IntentKey.MOVIE_GENRES.name(), new ArrayList<>(movie.getGenreResponses().stream().map(GenreResponse::getName).collect(Collectors.toList())));
                intent.putExtra(IntentKey.MOVIE_LENGTH.name(), movie.getMovieLength());
                intent.putExtra(IntentKey.MOVIE_DESCRIPTION.name(), movie.getDescription());
                intent.putExtra(IntentKey.PUBLISHED_DATE.name(), movie.getPublishDate());
                intent.putExtra(IntentKey.IMAGE_URL.name(), movie.getImageUrl());
                intent.putExtra(IntentKey.BACKGROUND_IMAGE_URL.name(), movie.getBackgroundImageUrl());
                intent.putExtra(IntentKey.TRAILER.name(), movie.getTrailerUrl());
                intent.putExtra(IntentKey.MOVIE_RATING.name(), movie.getRatingResponse().getName());
                intent.putExtra(IntentKey.MOVIE_PERFORMER_NAME.name(), new ArrayList<>(movie.getPerformerResponses().stream().map(PerformerResponse::getName).collect(Collectors.toList())));
                intent.putStringArrayListExtra(IntentKey.MOVIE_PERFORMER_TYPE.name(), new ArrayList<>(movie.getPerformerResponses().stream().map(p->p.getTypeId().toString()).collect(Collectors.toList())));
                intent.putExtra(IntentKey.MOVIE_COMMENT.name(), new ArrayList<>(movie.getReviews().stream().map(Review::getUserComment).collect(Collectors.toList())));
                intent.putExtra(IntentKey.AVERAGE_STAR.name(), movie.getReviews().stream().mapToInt(Review::getUserVote).average().orElse(0.0));

                itemView.getContext().startActivity(intent);
            });
        }


    }

    public interface OnShowtimeClickListener {
        void onShowtimeClick(Movie movie, String showtime);
    }
}