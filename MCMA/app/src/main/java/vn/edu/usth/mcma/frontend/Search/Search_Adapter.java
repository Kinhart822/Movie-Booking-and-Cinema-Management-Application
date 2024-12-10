package vn.edu.usth.mcma.frontend.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.SearchMovieByGenreResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.SearchMovieByNameResponse;
import vn.edu.usth.mcma.frontend.Home.FilmViewInterface;

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
        holder.typeView.setText(item.getGenreName() != null ? item.getGenreName() : "N/A");
        holder.timeView.setText(item.getLength() != null ? item.getLength() + " min" : "Unknown");
        holder.age_limitView.setText(item.getRatingName() != null ? item.getRatingName() : "No Rating");

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
