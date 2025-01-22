package vn.edu.usth.mcma.frontend.component.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.movie.MovieDetailShort;
import vn.edu.usth.mcma.frontend.helper.ImageDecoder;

public class NowShowing_Adapter extends RecyclerView.Adapter<NowShowing_ViewHolder> {
    private final FilmViewInterface filmViewInterface;
    private final Context context;
    private final List<MovieDetailShort> items;

    public NowShowing_Adapter(Context context, List<MovieDetailShort> items, FilmViewInterface filmViewInterface) {
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

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull NowShowing_ViewHolder holder, int position) {
        MovieDetailShort nowShowing = items.get(position);
        System.out.println(nowShowing);

        holder.nameView.setText(nowShowing.getName());
        holder.timeView.setText(String.format("%d min", nowShowing.getLength()));

        holder.age_limitView.setText(nowShowing.getRating());

        Glide
                .with(context)
                .load(ImageDecoder
                        .decode(nowShowing
                                .getImageBase64()))
                .into(holder.filmView);

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

