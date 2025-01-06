package vn.edu.usth.mcma.frontend.Showtimes.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Enum.PerformerType;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Genre;
import vn.edu.usth.mcma.frontend.Home.OnlyDetailsActivity;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Movie;

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

            // 1. Lấy danh sách thời gian
            List<String> showtimes = movie.getTime();

            // 2. Sắp xếp thời gian theo định dạng HH:mm (hoặc HH)
            @SuppressLint({"NewApi", "LocalSuppress"}) List<LocalTime> sortedTimes = showtimes.stream()
                    .map(time -> {
                        try {
                            // Giả sử định dạng là HH:mm, nếu chỉ có HH thì đổi thành time + ":00"
                            return LocalTime.parse(time.length() == 2 ? time + ":00" : time);
                        } catch (Exception  e) {
                            Log.e("TimeParseError", "Lỗi khi phân tích thời gian: " + time);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .sorted()
                    .collect(Collectors.toList());

            // 3. Chuyển đổi LocalTime về định dạng chuỗi HH:mm để hiển thị
            @SuppressLint({"NewApi", "LocalSuppress"}) List<String> formattedTimes = sortedTimes.stream()
                    .map(time -> time.format(DateTimeFormatter.ofPattern("HH:mm")))
                    .collect(Collectors.toList());

            // 4. Tạo nút (Button) cho mỗi thời gian đã sắp xếp
            for (String time : formattedTimes) {
                Button timeButton = new Button(itemView.getContext());
                timeButton.setText(time);
                timeButton.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onShowtimeClick(movie, time);
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
                Intent intent = new Intent(itemView.getContext(), OnlyDetailsActivity.class);
                intent.putExtra("MOVIE_NAME", movie.getTitle());
                intent.putExtra("MOVIE_GENRES", new ArrayList<>(movie.getGenres().stream().map(Genre::getName).collect(Collectors.toList())));
                intent.putExtra("MOVIE_LENGTH", movie.getMovieLength());
                intent.putExtra("MOVIE_DESCRIPTION", movie.getDescription());
                intent.putExtra("PUBLISHED_DATE", movie.getPublishDate());
                intent.putExtra("IMAGE_URL", movie.getImageUrl());
                intent.putExtra("BACKGROUND_IMAGE_URL", movie.getBackgroundImageUrl());
                intent.putExtra("TRAILER", movie.getTrailerUrl());
                intent.putExtra("MOVIE_RATING", new ArrayList<>(movie.getMovieRatingDetailNameList()));
                intent.putExtra("MOVIE_PERFORMER_NAME", new ArrayList<>(movie.getMoviePerformerNameList()));

                List<String> performerTypeStrings = new ArrayList<>();
                for (PerformerType performerType : movie.getMoviePerformerType()) {
                    performerTypeStrings.add(performerType.toString());
                }
                intent.putStringArrayListExtra("MOVIE_PERFORMER_TYPE", new ArrayList<>(performerTypeStrings));

                intent.putExtra("MOVIE_COMMENT", new ArrayList<>(movie.getComments()));
                intent.putExtra("AVERAGE_STAR", movie.getAverageRating());

                itemView.getContext().startActivity(intent);
            });
        }


    }

    public interface OnShowtimeClickListener {
        void onShowtimeClick(Movie movie, String showtime);
    }
}