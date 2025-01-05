package vn.edu.usth.mcma.frontend.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.NotificationResponse;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder>{

    Context context;
    private final List<String> messages;
    private final List<String> dates;

    public NotificationAdapter(Context context) {
        this.context = context;
        this.messages = new ArrayList<>();
        this.dates = new ArrayList<>();
    }
    public void updateData(List<String> newMessages, List<String> newDates) {
        messages.clear();
        messages.addAll(newMessages);
        dates.clear();
        dates.addAll(newDates);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.noti_frame, parent, false);
        return new NotificationViewHolder(view);
//        return new NotificationViewHolder(LayoutInflater.from(context).inflate(R.layout.noti_frame,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
//        NotificationResponse notificationResponse = items.get(position);
//        // Assuming messages and dateCreated are lists, displaying the first item for simplicity
//        if (!notificationResponse.getMessage().isEmpty() && !notificationResponse.getDateCreated().isEmpty()) {
//            holder.nameView.setText(notificationResponse.getMessage().get(0));
//            holder.timeView.setText(notificationResponse.getDateCreated().get(0));
//        } else {
//            holder.nameView.setText("No message available");
//            holder.timeView.setText("No date available");
//        }
        holder.nameView.setText(messages.get(position));
        holder.timeView.setText(dates.get(position));
//        holder.avatarView.setImageResource(items.get(position).getAvatar());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

}
