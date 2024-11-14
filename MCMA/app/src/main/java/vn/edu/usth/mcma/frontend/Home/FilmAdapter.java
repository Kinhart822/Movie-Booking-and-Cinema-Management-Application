package vn.edu.usth.mcma.frontend.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.R;

public class FilmAdapter extends RecyclerView.Adapter<FilmViewHolder> {
    private final FilmViewInterface filmViewInterface;

    Context context;
    List<FilmItem> items;

    public FilmAdapter(Context context, List<FilmItem> items, FilmViewInterface filmViewInterface) {
        this.filmViewInterface = filmViewInterface;
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilmViewHolder(LayoutInflater.from(context).inflate(R.layout.film_frame, parent, false), filmViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.typeView.setText(items.get(position).getCategory());
        holder.filmView.setImageResource(items.get(position).getFilm_image());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
