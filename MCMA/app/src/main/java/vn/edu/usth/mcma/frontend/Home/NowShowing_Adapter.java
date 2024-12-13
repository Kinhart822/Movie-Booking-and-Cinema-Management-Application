package vn.edu.usth.mcma.frontend.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.R;

public class NowShowing_Adapter extends RecyclerView.Adapter<NowShowing_ViewHolder> {
    private final FilmViewInterface filmViewInterface;
    private final Context context;
    private final List<NowShowing_Item> items;

    public NowShowing_Adapter(Context context, List<NowShowing_Item> items, FilmViewInterface filmViewInterface) {
        this.context = context;
        this.items = items;
        this.filmViewInterface = filmViewInterface;
    }

    @NonNull
    @Override
    public NowShowing_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NowShowing_ViewHolder(LayoutInflater.from(context).inflate(R.layout.now_showing_film_frame, parent, false), filmViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull NowShowing_ViewHolder holder, int position) {
        NowShowing_Item item = items.get(position);
        holder.nameView.setText(item.getName());
        holder.typeView.setText(item.getCategory());
        holder.timeView.setText(item.getTime());
        holder.age_limitView.setText(item.getAge_limit());
        holder.filmView.setImageResource(item.getFilm_image());

        // Handle film selection
        holder.itemView.setOnClickListener(v -> filmViewInterface.onFilmSelected(position));

        // Handle booking button click
        holder.bookingButton.setOnClickListener(v -> filmViewInterface.onBookingClicked(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

