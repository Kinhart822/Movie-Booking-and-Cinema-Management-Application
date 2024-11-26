package vn.edu.usth.mcma.frontend.Personal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.R;

public class Feedback_Adapter extends RecyclerView.Adapter<Feedback_ViewHolder> {

    Context context;
    List<Feedback_Item> items;

    public Feedback_Adapter(Context context, List<Feedback_Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Feedback_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Feedback_ViewHolder(LayoutInflater.from(context).inflate(R.layout.feedback_frame,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Feedback_ViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getMovie_name());
        holder.ratingView.setText(items.get(position).getRating_star());
        holder.commentView.setText(items.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
