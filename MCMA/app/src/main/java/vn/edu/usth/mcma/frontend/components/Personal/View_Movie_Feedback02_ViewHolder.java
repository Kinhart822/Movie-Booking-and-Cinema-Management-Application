package vn.edu.usth.mcma.frontend.components.Personal;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.mcma.R;

public class View_Movie_Feedback02_ViewHolder extends RecyclerView.ViewHolder {
    public TextView contentFeedback;
    public RatingBar ratingBarFeedback;

    public View_Movie_Feedback02_ViewHolder(View itemView) {
        super(itemView);
        contentFeedback = itemView.findViewById(R.id.content_feedback);
        ratingBarFeedback = itemView.findViewById(R.id.ratingBar_feedback);
    }
}
