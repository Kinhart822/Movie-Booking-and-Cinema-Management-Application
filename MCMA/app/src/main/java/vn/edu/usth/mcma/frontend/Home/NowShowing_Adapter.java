package vn.edu.usth.mcma.frontend.Home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.NowShowingResponse;

public class NowShowing_Adapter extends RecyclerView.Adapter<NowShowing_ViewHolder> {
    private final FilmViewInterface filmViewInterface;
    private final Context context;
    private final List<NowShowingResponse> items;

    public NowShowing_Adapter(Context context, List<NowShowingResponse> items, FilmViewInterface filmViewInterface) {
        this.context = context;
        this.items = items;
        this.filmViewInterface = filmViewInterface;
    }

    @NonNull
    @Override
    public NowShowing_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NowShowing_ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.now_showing_film_frame, parent, false),
                filmViewInterface
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NowShowing_ViewHolder holder, int position) {
        NowShowingResponse nowShowingItem = items.get(position);

        holder.nameView.setText(nowShowingItem.getMovieName());
        holder.timeView.setText(String.format("%d min", nowShowingItem.getMovieLength()));

        List<String> genres = nowShowingItem.getMovieGenreNameList();
        if (genres != null && !genres.isEmpty()) {
            holder.typeView.setText(genres.get(0)); // Use first genre as default
        } else {
            holder.typeView.setText(R.string.unknown_genre);
        }

        List<String> ratings = nowShowingItem.getMovieRatingDetailNameList();
        if (ratings != null && !ratings.isEmpty()) {
            holder.age_limitView.setText(ratings.get(0)); // Use first rating as default
        } else {
            holder.age_limitView.setText(R.string.unknown_rating);
        }

        Glide.with(context).load(nowShowingItem.getImageUrl()).into(holder.filmView);

        holder.itemView.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            Log.d("NowShowing_Adapter", "Item clicked at position: " + currentPosition);
            filmViewInterface.onFilmSelected(currentPosition);
        });

        holder.bookingButton.setOnClickListener(v -> filmViewInterface.onBookingClicked(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

