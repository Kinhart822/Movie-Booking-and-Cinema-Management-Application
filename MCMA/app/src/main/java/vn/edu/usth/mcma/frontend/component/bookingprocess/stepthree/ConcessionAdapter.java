package vn.edu.usth.mcma.frontend.component.bookingprocess.stepthree;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.stream.Collectors;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models.ComboItem;
import vn.edu.usth.mcma.frontend.model.item.ConcessionItem;

public class ConcessionAdapter extends RecyclerView.Adapter<ConcessionAdapter.ViewHolder> {
    private final List<ConcessionItem> items;
    private final IConcessionItemView iConcessionItemView;

    public ConcessionAdapter(List<ConcessionItem> items, IConcessionItemView iConcessionItemView) {
        this.items = items;
        this.iConcessionItemView = iConcessionItemView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_concession, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ComboItem item = items.get(position);
        holder.bind(item);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<ComboItem> getSelectedComboItems() {
        return items.stream()
                .filter(item -> item.getQuantity() > 0)
                .collect(Collectors.toList());
    }
    // Updates the quantity of a ComboItem and recalculates the total price
    private void updateQuantity(int position, int delta) {
        ComboItem item = items.get(position);
        int newQuantity = Math.max(0, item.getQuantity() + delta);

        if (newQuantity != item.getQuantity()) {
            item.setQuantity(newQuantity);
            notifyItemChanged(position); // Update only the modified item
            if (listener != null) {
                listener.onTotalPriceChanged(items); // Notify the listener to update the total price
            }
        }
    }
    // ViewHolder class for ComboItem
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView concessionImageView;
        private TextView concessionNameTextView;
        private TextView concessionDescriptionTextView;
        private TextView concessionQuantityTextView;


        ViewHolder(@NonNull View itemView) {
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
        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        void bind(ComboItem item) {
            nameText.setText(item.getName());
            priceText.setText(String.format("$%.2f", item.getPrice()));
            quantityText.setText(String.valueOf(item.getQuantity()));

            // Check if image URL is null or empty, then load default image, otherwise load from URL
            if (item.getImageUrl() == null || item.getImageUrl().isEmpty()) {
                comboImage.setImageResource(R.drawable.combo_image_1);
            } else {
                Glide.with(itemView.getContext())
                        .load(item.getImageUrl())
                        .placeholder(R.drawable.combo_image_1) // Placeholder while loading image
                        .error(R.drawable.combo_image_1) // Fallback image if image URL fails to load
                        .into(comboImage);
            }
        }

    }
}