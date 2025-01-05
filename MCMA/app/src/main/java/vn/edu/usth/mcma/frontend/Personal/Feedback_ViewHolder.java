package vn.edu.usth.mcma.frontend.Personal;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.mcma.R;

public class Feedback_ViewHolder extends RecyclerView.ViewHolder {
    TextView nameView;
    TextView ratingView;
    TextView commentView;

    public Feedback_ViewHolder(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.movie_name_feedback);
        ratingView = itemView.findViewById(R.id.rating_feedback);
        commentView = itemView.findViewById(R.id.comment_feedback);
    }
}
