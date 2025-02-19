package vn.edu.usth.mcma.frontend.component.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.movie.MovieDetailShort;
import vn.edu.usth.mcma.frontend.utils.ImageDecoder;

public class NowShowingAdapter extends RecyclerView.Adapter<NowShowingAdapter.ViewHolder> {
    private final Context context;
    private final int inf;
    private final List<MovieDetailShort> items;
    private final IMovieItemView iMovieItemView;

    public NowShowingAdapter(Context context, int inf, List<MovieDetailShort> items, IMovieItemView iMovieItemView) {
        this.context = context;
        this.inf = inf;
        this.items = items;
        this.iMovieItemView = iMovieItemView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.item_home_movie, parent, false)
        );
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (items.isEmpty()) {
            return;
        }
        MovieDetailShort movie = items.get(position % items.size());
        if (movie.getPoster() != null) {
            Glide
                    .with(context)
                    .load(ImageDecoder.decode(movie.getPoster()))
                    .placeholder(R.drawable.placeholder1080x1920)
                    .error(R.drawable.placeholder1080x1920)
                    .into(holder.posterImageView);
        }
        holder.itemView.setOnClickListener(v -> iMovieItemView.onPosterClickListener(position));
        holder.posterImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public int getItemCount() {
        return inf;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.image_view_poster);
        }
    }
}