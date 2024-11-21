package vn.edu.usth.mcma.frontend.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Home.FilmViewInterface;

public class Search_Adapter extends RecyclerView.Adapter<Search_ViewHolder> {
    private SearchViewInterface searchViewInterface;
    private final Context context;
    private final List<Search_Item> items;

    public Search_Adapter(Context context, List<Search_Item> items, SearchViewInterface searchViewInterface) {
        this.context = context;
        this.items = items;
        this.searchViewInterface = searchViewInterface;
    }

    @NonNull
    @Override
    public Search_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Search_ViewHolder(LayoutInflater.from(context).inflate(R.layout.coming_soon_film_frame, parent, false), searchViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Search_ViewHolder holder, int position) {
        Search_Item item = items.get(position);
        holder.nameView.setText(item.getName());
        holder.typeView.setText(item.getCategory());
        holder.timeView.setText(item.getTime());
        holder.age_limitView.setText(item.getAge_limit());
        holder.filmView.setImageResource(item.getFilm_image());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
