package vn.edu.usth.mcma.frontend.component.bookingsession;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.model.item.ItemsOrderedItem;

public class ItemsOrderedAdapter extends RecyclerView.Adapter<ItemsOrderedAdapter.ViewHolder> {
    private final List<ItemsOrderedItem> items;

    public ItemsOrderedAdapter(List<ItemsOrderedItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_items_ordered, parent, false));
    }
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemsOrderedItem item = items.get(position);
        holder.quantityTextView.setText(String.format("%d x", item.getQuantity()));
        holder.nameTextView.setText(item.getName());
        holder.totalPriceTextView.setText(String.format("$%.1f", item.getTotalPrice()));
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView quantityTextView;
        TextView nameTextView;
        TextView totalPriceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.quantityTextView = itemView.findViewById(R.id.text_view_quantity);
            this.nameTextView = itemView.findViewById(R.id.text_view_name);
            this.totalPriceTextView = itemView.findViewById(R.id.text_view_total_price);
        }
    }
}
