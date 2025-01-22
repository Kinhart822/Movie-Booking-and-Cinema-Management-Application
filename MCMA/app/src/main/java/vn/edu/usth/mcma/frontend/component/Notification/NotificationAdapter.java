package vn.edu.usth.mcma.frontend.component.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;

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
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.nameView.setText(messages.get(position));
        holder.timeView.setText(dates.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

}
