package vn.edu.usth.mcma.frontend.Feedback;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.R;

public class RatingMovie_Adapter extends RecyclerView.Adapter<RatingMovie_ViewHolder> {
    private final Context context;
    private final List<RatingMovie_Item> items;
    private final OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(String movieName, String movieType, int movieImage);
    }

    public RatingMovie_Adapter(Context context, List<RatingMovie_Item> items, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.items = items;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RatingMovie_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RatingMovie_ViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_feedback_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RatingMovie_ViewHolder holder, int position) {
        RatingMovie_Item item = items.get(position);

        // Gán dữ liệu cho các view
        holder.nameView.setText(item.getMovie_name());
        holder.typeView.setText(item.getMovie_type());
        holder.imageView.setImageResource(item.getMovie_image());

        // Xử lý sự kiện click item
        holder.itemView.setOnClickListener(v ->
                onItemClickListener.onItemClick(item.getMovie_name(), item.getMovie_type(), item.getMovie_image()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
