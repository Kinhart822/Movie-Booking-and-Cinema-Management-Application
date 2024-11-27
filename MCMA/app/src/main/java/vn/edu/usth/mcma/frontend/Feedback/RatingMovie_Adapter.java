package vn.edu.usth.mcma.frontend.Feedback;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.R;

public class RatingMovie_Adapter extends RecyclerView.Adapter<RatingMovie_ViewHolder> {

    Context context;
    List<RatingMovie_Item> items;

    public RatingMovie_Adapter(Context context, List<RatingMovie_Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RatingMovie_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RatingMovie_ViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_feedback_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RatingMovie_ViewHolder holder, int position) {
        RatingMovie_Item item = items.get(position);

        holder.nameView.setText(item.getMovie_name());
        holder.typeView.setText(item.getMovie_type());
        holder.imageView.setImageResource(item.getMovie_image());


        // Chỗ này sẽ click vô được item trong recycle view
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RatingMovie_Activity.class);
            intent.putExtra("movie_name", item.getMovie_name());
            intent.putExtra("movie_type", item.getMovie_type());
            intent.putExtra("movie_image", item.getMovie_image());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
