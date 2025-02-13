package vn.edu.usth.mcma.frontend.component.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.model.item.NotificationItem;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    private final List<NotificationItem> items;

    public NotificationAdapter(List<NotificationItem> items) {
        this.items = items;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationItem item = items.get(position);
        holder.titleTextView.setText(item.getTitle());
        holder.timeTextView.setText(Instant.parse(item.getCreatedDate()).atZone(ZoneId.systemDefault()).toLocalTime().toString());
        holder.contentTextView.setText(item.getContent());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;
        TextView timeTextView;
        TextView contentTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_view_title);
            timeTextView = itemView.findViewById(R.id.text_view_time);
            contentTextView = itemView.findViewById(R.id.text_view_content);
        }
    }
}
