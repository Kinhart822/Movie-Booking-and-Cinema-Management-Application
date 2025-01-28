package vn.edu.usth.mcma.frontend.component.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.model.ShowtimeOfMovieBySchedule;

public class MovieBookingCinemaAdapter extends RecyclerView.Adapter<MovieBookingCinemaAdapter.ViewHolder> {
    private Map<Long, Map<String, List<ShowtimeOfMovieBySchedule>>> cinemaScreenTypeSchedule;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_movie_booking_cinema, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
