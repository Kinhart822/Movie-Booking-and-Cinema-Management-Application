package vn.edu.usth.mcma.frontend.Showtimes.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Movie;
import vn.edu.usth.mcma.frontend.Showtimes.UI.MovieDetailsActivity;

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

//            List<String> showtimes = movie.getShowtimes();
//            for (String time : showtimes) {
//                Button timeButton = new Button(itemView.getContext());
//                timeButton.setText(time);
//                timeButton.setOnClickListener(v -> listener.onShowtimeClick(movie, time));
//
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                        itemView.getContext().getResources().getDimensionPixelSize(R.dimen.time_button_width),
//                        LinearLayout.LayoutParams.WRAP_CONTENT
//                );
//                params.setMargins(8, 0, 8, 0);
//                timeButton.setLayoutParams(params);
//
//                timeContainer.addView(timeButton);
//            }

            viewDetails.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), MovieDetailsActivity.class);
                intent.putExtra("MOVIE_TITLE", movie.getTitle());
                itemView.getContext().startActivity(intent);
            });
        }
    }

    public interface OnShowtimeClickListener {
        void onShowtimeClick(Movie movie, String showtime);
    }
}