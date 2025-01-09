package vn.edu.usth.mcma.frontend.Personal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.constants.IntentKey;

import com.bumptech.glide.Glide;

import java.util.List;

public class Movie_Feedback_Adapter extends RecyclerView.Adapter<Movie_Feedback_ViewHolder> {

    Context context;
    List<Movie_Feedback_Item> items;

    public Movie_Feedback_Adapter(Context context, List<Movie_Feedback_Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Movie_Feedback_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Movie_Feedback_ViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_feedback_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Movie_Feedback_ViewHolder holder, int position) {
        Movie_Feedback_Item item = items.get(position);

        holder.nameView.setText(item.getMovie_name());
        holder.typeView.setText(item.getMovie_type());
//        holder.imageView.setImageResource(item.getMovie_image());
        Glide.with(context)
                .load(item.getImageUrl())
                .placeholder(R.drawable.usthlogo)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, View_Movie_Feedback02_Activity.class);
            intent.putExtra(IntentKey.movie_feedback_id.name(), item.getMovieId());
            intent.putExtra(IntentKey.movie_feedback_name.name(), item.getMovie_name());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
