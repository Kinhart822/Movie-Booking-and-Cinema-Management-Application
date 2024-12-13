package vn.edu.usth.mcma.frontend.Store.Adapters;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Store.Models.ComboItem;

public class ComboAdapter extends RecyclerView.Adapter<ComboAdapter.ComboViewHolder> {
    private List<ComboItem> comboItems;
    private OnTotalPriceChangedListener listener;

    public ComboAdapter(List<ComboItem> comboItems) {
        this.comboItems = comboItems;
    }

    // Custom listener interface for price updates
    public interface OnTotalPriceChangedListener {
        void onTotalPriceChanged(double total);
    }

    public List<ComboItem> getComboItems() {
        return comboItems;
    }

    public void setTotalPriceChangedListener(OnTotalPriceChangedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ComboViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_item_combo, parent, false);
        return new ComboViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComboViewHolder holder, int position) {
        ComboItem item = comboItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return comboItems != null ? comboItems.size() : 0;
    }

    // Updates the quantity of a ComboItem and recalculates the total price
    private void updateQuantity(int position, int delta) {
        ComboItem item = comboItems.get(position);
        int newQuantity = Math.max(0, item.getQuantity() + delta);
        item.setQuantity(newQuantity);
        notifyItemChanged(position);
        calculateTotalPrice();
    }

    // Calculates the total price based on the quantities of ComboItem
    private void calculateTotalPrice() {
        double total = 0;
        for (ComboItem item : comboItems) {
            total += item.getPrice() * item.getQuantity();
        }
        if (listener != null) {
            listener.onTotalPriceChanged(total);
        }
    }

    // ViewHolder class for ComboItem
    class ComboViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private TextView priceText;
        private TextView quantityText;
        private ImageView plusButton;
        private ImageView minusButton;
        private ImageView comboImage;

        ComboViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.combo_name);
            priceText = itemView.findViewById(R.id.combo_price);
            quantityText = itemView.findViewById(R.id.combo_quantity);
            plusButton = itemView.findViewById(R.id.plus_button);
            minusButton = itemView.findViewById(R.id.minus_button);
            comboImage = itemView.findViewById(R.id.combo_image);

            // Setting up button click listeners to adjust quantity
            plusButton.setOnClickListener(v -> updateQuantity(getAdapterPosition(), 1));
            minusButton.setOnClickListener(v -> updateQuantity(getAdapterPosition(), -1));
        }

        // Binds data to the view holder elements
        void bind(ComboItem item) {
            nameText.setText(item.getName());
            priceText.setText(String.format(Locale.getDefault(), "%,.0fđ", item.getPrice()));
            quantityText.setText(String.valueOf(item.getQuantity()));

            // Load image from drawable resource
            try {
                int resourceId = Integer.parseInt(item.getImageUrl());
                comboImage.setImageResource(resourceId);
            } catch (NumberFormatException | Resources.NotFoundException e) {
                comboImage.setImageResource(R.drawable.default_combo_image);
            }
        }
    }
}

