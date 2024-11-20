package vn.edu.usth.mcma.frontend.Personal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Notification.NotificationItem;
import vn.edu.usth.mcma.frontend.Notification.NotificationViewHolder;

public class BookingHistory_Adapter extends RecyclerView.Adapter<BookingHistory_ViewHolder> {

    Context context;
    List<BookingHistory_Item> items;

    public BookingHistory_Adapter(Context context, List<BookingHistory_Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public BookingHistory_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingHistory_ViewHolder(LayoutInflater.from(context).inflate(R.layout.booking_history_frame,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookingHistory_ViewHolder holder, int position) {
        holder.timeView.setText(items.get(position).getTime());
        holder.siteView.setText(items.get(position).getSite());
        holder.item_nameView.setText(items.get(position).getItem_name());
        holder.quantityView.setText(items.get(position).getQuantity());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
