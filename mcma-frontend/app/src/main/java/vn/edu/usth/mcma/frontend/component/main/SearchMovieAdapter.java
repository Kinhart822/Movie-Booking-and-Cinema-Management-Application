package vn.edu.usth.mcma.frontend.component.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.movie.MovieDetailShort;
import vn.edu.usth.mcma.frontend.utils.ImageDecoder;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.ViewHolder> {
    private final Context context;
    private final List<MovieDetailShort> items;
    private final ISearchItemView iSearchItemView;

    public SearchMovieAdapter(Context context, List<MovieDetailShort> items, ISearchItemView iSearchItemView) {
        this.context = context;
        this.items = items;
        this.iSearchItemView = iSearchItemView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.item_search_movie, parent, false));
    }
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieDetailShort movie = items.get(position);
        if (movie.getPoster() != null) {
            Glide
                    .with(context)
                    .load(ImageDecoder.decode(movie.getPoster()))
                    .placeholder(R.drawable.placeholder1080x1920)
                    .error(R.drawable.placeholder1080x1920)
                    .into(holder.posterImageView);
        }
        holder.posterImageView.setOnClickListener(v -> iSearchItemView.onPosterClickListener(position));
        holder.nameTextView.setText(movie.getName());
        holder.lengthTextView.setText(String.format("%d min", movie.getLength()));
        holder.ratingTextView.setText(movie.getRating());
        holder.bookTicketsButton.setOnClickListener(v -> iSearchItemView.onBookTicketsClickListener(position));
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        Glide
                .with(context)
                .clear(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;
        TextView nameTextView;
        TextView lengthTextView;
        TextView ratingTextView;
        Button bookTicketsButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.image_view_poster);
            nameTextView = itemView.findViewById(R.id.text_view_name);
            lengthTextView = itemView.findViewById(R.id.text_view_length);
            ratingTextView = itemView.findViewById(R.id.text_view_rating);
            bookTicketsButton = itemView.findViewById(R.id.button_book_tickets);
        }
    }
}
