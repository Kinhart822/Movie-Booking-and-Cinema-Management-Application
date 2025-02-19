package vn.edu.usth.mcma.frontend.component.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.model.item.NotificationItem;
import vn.edu.usth.mcma.frontend.utils.ImageDecoder;
import vn.edu.usth.mcma.frontend.utils.QRCodeGenerator;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    private final Context context;
    private final List<NotificationItem> items;

    public NotificationAdapter(Context context, List<NotificationItem> items) {
        this.context = context;
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
        String time;
        ZonedDateTime dateTime = Instant.parse(item.getCreatedDate()).atZone(ZoneId.systemDefault());
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        if (dateTime.toLocalDate().isEqual(now.toLocalDate())) {
            time = "Today " + dateTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
        } else {
            time = dateTime.format(DateTimeFormatter.ofPattern("EEE hh:mm a")); // Day of week and time
        }
        holder.timeTextView.setText(time);
        holder.contentTextView.setText(item.getContent());
        Glide
                .with(context)
                .load(QRCodeGenerator.generateQRCode(item.getTitle(), 500, 500))
                .placeholder(R.drawable.placeholder1080x1920)
                .error(R.drawable.placeholder1080x1920)
                .into(holder.qrImageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;
        TextView timeTextView;
        TextView contentTextView;
        ImageView qrImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_view_title);
            timeTextView = itemView.findViewById(R.id.text_view_time);
            contentTextView = itemView.findViewById(R.id.text_view_content);
            qrImageView = itemView.findViewById(R.id.image_view_qr);
        }
    }
}
