package vn.edu.usth.mcma.frontend.component.Notification;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.mcma.R;

public class NotificationViewHolder extends RecyclerView.ViewHolder{

//    ImageView avatarView;
    TextView nameView;
    TextView timeView;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
//        avatarView = itemView.findViewById(R.id.user_image);
        nameView = itemView.findViewById(R.id.content);
        timeView = itemView.findViewById(R.id.time);
    }

}
