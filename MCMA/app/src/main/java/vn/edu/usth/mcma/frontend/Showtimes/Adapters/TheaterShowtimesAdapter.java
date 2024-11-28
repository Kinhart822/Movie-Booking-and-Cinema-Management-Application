package vn.edu.usth.mcma.frontend.Showtimes.Adapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.flexbox.FlexboxLayout;


import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Movie;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Theater;
import vn.edu.usth.mcma.frontend.Showtimes.Models.TheaterType;
import vn.edu.usth.mcma.frontend.Showtimes.UI.MovieBookingActivity;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.TheaterDataProvider;

public class TheaterShowtimesAdapter extends RecyclerView.Adapter<TheaterShowtimesAdapter.TheaterViewHolder> {
    private List<Theater> theaters = new ArrayList<>();
    private String selectedDate;
    private String movieTitle;
    private TheaterType theaterType;
    private OnShowtimeClickListener listener;
    private SparseBooleanArray expandedStates = new SparseBooleanArray();

    public interface OnShowtimeClickListener {
        void onShowtimeClick(Theater theater, String showtime);
    }

    public TheaterShowtimesAdapter(OnShowtimeClickListener listener) {
        this.listener = listener;
    }

    public void setTheaters(List<Theater> theaters, String selectedDate, String movieTitle, TheaterType theaterType) {
        this.theaters = theaters;
        this.selectedDate = selectedDate;
        this.movieTitle = movieTitle;
        this.theaterType = theaterType;
        expandedStates.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TheaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.theater_showtimes_item, parent, false);
        return new TheaterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheaterViewHolder holder, int position) {
        holder.bind(theaters.get(position), position);
    }

    @Override
    public int getItemCount() {
        return theaters.size();
    }

    class TheaterViewHolder extends RecyclerView.ViewHolder {
        private TextView theaterName;
        private TextView theaterAddress;
        private FlexboxLayout showtimesContainer;
        private ImageView arrowIcon;
        private View divider;
        private ConstraintLayout headerLayout;

        TheaterViewHolder(@NonNull View itemView) {
            super(itemView);
            theaterName = itemView.findViewById(R.id.theater_name);
            theaterAddress = itemView.findViewById(R.id.theater_address);
            showtimesContainer = itemView.findViewById(R.id.showtimes_container);
            arrowIcon = itemView.findViewById(R.id.arrow_icon);
            divider = itemView.findViewById(R.id.divider);
            headerLayout = itemView.findViewById(R.id.header_layout);
        }

        void bind(Theater theater, int position) {
            theaterName.setText(theater.getName());
            theaterAddress.setText(theater.getAddress());

            boolean isExpanded = expandedStates.get(position, false);
            showtimesContainer.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            arrowIcon.setRotation(isExpanded ? 180f : 0f);

            // Show divider for all items except the last one
            divider.setVisibility(position < theaters.size() - 1 ? View.VISIBLE : View.GONE);

            headerLayout.setOnClickListener(v -> {
                boolean newState = !expandedStates.get(position, false);
                expandedStates.put(position, newState);

                // Animate arrow rotation
                arrowIcon.animate()
                        .rotation(newState ? 180f : 0f)
                        .setDuration(200)
                        .start();

                // Show/hide showtimes container
                if (newState) {
                    showtimesContainer.setVisibility(View.VISIBLE);
                    populateShowtimes(theater);
                } else {
                    showtimesContainer.setVisibility(View.GONE);
                }
            });

            // Populate showtimes only if expanded
            if (isExpanded) {
                populateShowtimes(theater);
            }
        }

        private void populateShowtimes(Theater theater) {
            showtimesContainer.removeAllViews();
            // Get showtimes based on theater type
            List<String> showtimes = new ArrayList<>();
            List<Movie> movies = TheaterDataProvider.getMoviesForTheater(theaterType, selectedDate);

            for (Movie movie : movies) {
                if (movie.getTitle().equals(movieTitle)) {
                    showtimes.addAll(movie.getShowtimesForType(theaterType));
                    break;
                }
            }

            // Create showtime buttons with appropriate styling
            Context context = itemView.getContext();
            for (String time : showtimes) {
                Button timeButton = new Button(context);
                timeButton.setText(time);
                timeButton.setOnClickListener(v -> listener.onShowtimeClick(theater, time));

                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                        FlexboxLayout.LayoutParams.WRAP_CONTENT,
                        FlexboxLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(8, 8, 8, 8);
                timeButton.setLayoutParams(params);

                showtimesContainer.addView(timeButton);
            }
        }
    }
}
