package vn.edu.usth.mcma.frontend.Personal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.R;

public class View_Movie_Feedback02_Adapter extends RecyclerView.Adapter<View_Movie_Feedback02_ViewHolder> {
    private List<View_Movie_Feedback02_Item> items;

    // Constructor nhận vào danh sách các mục feedback
    public View_Movie_Feedback02_Adapter(List<View_Movie_Feedback02_Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public View_Movie_Feedback02_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item cho RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_movie_feedback_frame, parent, false);
        return new View_Movie_Feedback02_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull View_Movie_Feedback02_ViewHolder holder, int position) {
        // Gán dữ liệu cho ViewHolder
        View_Movie_Feedback02_Item item = items.get(position);
        holder.ratingBarFeedback.setRating(item.getRating());

        // Cập nhật comment theo rating
        String comment = item.getRatingComment();
        holder.contentFeedback.setText(comment);  // Hiển thị comment dựa trên rating
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng item trong danh sách
        return items.size();
    }
}
