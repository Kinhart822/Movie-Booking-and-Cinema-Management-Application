package vn.edu.usth.mcma.frontend.components.Search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.stream.Collectors;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.Response.BookingProcess.Genre;
import vn.edu.usth.mcma.frontend.dto.Response.SearchMovieByNameResponse;

public class Search_Adapter extends RecyclerView.Adapter<Search_ViewHolder> {
    private SearchViewInterface searchViewInterface;
    private final Context context;
    private final List<SearchMovieByNameResponse> items;

    public Search_Adapter(Context context, List<SearchMovieByNameResponse> items, SearchViewInterface searchViewInterface) {
        this.context = context;
        this.items = items;
        this.searchViewInterface = searchViewInterface;
    }

    @NonNull
    @Override
    public Search_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Search_ViewHolder(LayoutInflater.from(context).inflate(R.layout.search_movie_frame, parent, false), searchViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Search_ViewHolder holder, int position) {
        SearchMovieByNameResponse item = items.get(position);
        holder.nameView.setText(item.getName() != null ? item.getName() : "Unknown");

        List<String> genres = item.getGenres().stream().map(Genre::getName).collect(Collectors.toList());
        if (!genres.isEmpty()) {
            holder.typeView.setText(genres.get(0)); // Use a valid index, e.g., 0 or a relevant value
        } else {
            holder.typeView.setText(R.string.unknown_genre); // Fallback text
        }
        holder.timeView.setText(item.getLength() != null ? item.getLength() + " min" : "Unknown");

        String rating = item.getRating().getName();
        if (!genres.isEmpty()) {
            holder.age_limitView.setText(genres.get(0)); // Use a valid index, e.g., 0 or a relevant value
        } else {
            holder.age_limitView.setText(R.string.unknown_rating); // Fallback text
        }
        Glide.with(context)
                .load(item.getImageUrl())
                .into(holder.filmView);

        holder.itemView.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            Log.d("Search_Adapter", "Item clicked at position: " + currentPosition);
            searchViewInterface.onItemClick(currentPosition);
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
