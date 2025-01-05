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
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.ComingSoonResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.NowShowingResponse;

public class ComingSoon_Adapter extends RecyclerView.Adapter<ComingSoon_ViewHolder> {
    private final FilmViewInterface filmViewInterface;
    private final Context context;
    private final List<ComingSoonResponse> items;

    public ComingSoon_Adapter(Context context, List<ComingSoonResponse> items, FilmViewInterface filmViewInterface) {
        this.context = context;
        this.items = items;
        this.filmViewInterface = filmViewInterface;
    }

    @NonNull
    @Override
    public ComingSoon_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ComingSoon_ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.coming_soon_film_frame, parent, false),
                filmViewInterface
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ComingSoon_ViewHolder holder, int position) {
        ComingSoonResponse comingSoonResponse = items.get(position);

        holder.nameView.setText(comingSoonResponse.getMovieName());
        holder.timeView.setText(String.format("%d min", comingSoonResponse.getMovieLength()));

        List<String> genres = comingSoonResponse.getMovieGenreNameList();
        if (genres != null && !genres.isEmpty()) {
            holder.typeView.setText(genres.get(0)); // Use a valid index, e.g., 0 or a relevant value
        } else {
            holder.typeView.setText(R.string.unknown_genre); // Fallback text
        }

        List<String> rating = comingSoonResponse.getMovieRatingDetailNameList();
        if (rating != null && !rating.isEmpty()) {
            holder.age_limitView.setText(rating.get(0)); // Use a valid index, e.g., 0 or a relevant value
        } else {
            holder.age_limitView.setText(R.string.unknown_rating); // Fallback text
        }

        Glide.with(context)
                .load(comingSoonResponse.getImageUrl())
                .into(holder.filmView);

        holder.itemView.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            Log.d("ComingSoon_Adapter", "Item clicked at position: " + currentPosition);
            filmViewInterface.onFilmSelected(currentPosition);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
