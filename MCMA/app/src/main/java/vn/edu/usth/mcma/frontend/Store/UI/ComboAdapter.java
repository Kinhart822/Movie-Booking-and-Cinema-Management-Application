package vn.edu.usth.mcma.frontend.Store.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;

import vn.edu.usth.mcma.R;

public class ComboAdapter extends RecyclerView.Adapter<ComboAdapter.ComboViewHolder> {
    private List<ComboItem> comboItems;
    private OnTotalPriceChangedListener listener;

    @Override
    public ComboViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_item_combo, parent, false);
        return new ComboViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComboViewHolder holder, int position) {
        ComboItem item = comboItems.get(position);
        holder.bind(item);
        holder.plusButton.setOnClickListener(v -> updateQuantity(position, 1));
        holder.minusButton.setOnClickListener(v -> updateQuantity(position, -1));
    }

    private void updateQuantity(int position, int delta) {
        ComboItem item = comboItems.get(position);
        int newQuantity = Math.max(0, item.getQuantity() + delta);
        item.setQuantity(newQuantity);
        notifyItemChanged(position);
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        double total = comboItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        if (listener != null) {
            listener.onTotalPriceChanged(total);
        }
    }

    public static class ComboViewHolder extends RecyclerView.ViewHolder {
        TextView comboName, comboPrice, comboQuantity;
        ImageView comboImage;
        Button plusButton, minusButton;

        public ComboViewHolder(View itemView) {
            super(itemView);
            comboName = itemView.findViewById(R.id.combo_name);
            comboPrice = itemView.findViewById(R.id.combo_price);
            comboQuantity = itemView.findViewById(R.id.combo_quantity);
            comboImage = itemView.findViewById(R.id.combo_image);
            plusButton = itemView.findViewById(R.id.plus_button);
            minusButton = itemView.findViewById(R.id.minus_button);
        }

        public void bind(ComboItem item) {
            comboName.setText(item.getName());
            comboPrice.setText(String.format(Locale.getDefault(), "%,.0fđ", item.getPrice()));
            comboQuantity.setText(String.valueOf(item.getQuantity()));
            // Load image using a library like Glide or Picasso if needed
        }
    }

    public interface OnTotalPriceChangedListener {
        void onTotalPriceChanged(double totalPrice);
    }

    public void setTotalPriceChangedListener(OnTotalPriceChangedListener listener) {
        this.listener = listener;
    }

}
