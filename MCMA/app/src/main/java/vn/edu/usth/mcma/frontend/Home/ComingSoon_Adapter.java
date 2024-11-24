package vn.edu.usth.mcma.frontend.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.R;

public class ComingSoon_Adapter extends RecyclerView.Adapter<ComingSoon_ViewHolder> {
    private final FilmViewInterface filmViewInterface;
    private final Context context;
    private final List<ComingSoon_Item> items;

    public ComingSoon_Adapter(Context context, List<ComingSoon_Item> items, FilmViewInterface filmViewInterface) {
        this.context = context;
        this.items = items;
        this.filmViewInterface = filmViewInterface;
    }

    @NonNull
    @Override
    public ComingSoon_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ComingSoon_ViewHolder(LayoutInflater.from(context).inflate(R.layout.coming_soon_film_frame, parent, false), filmViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ComingSoon_ViewHolder holder, int position) {
        ComingSoon_Item item = items.get(position);
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
