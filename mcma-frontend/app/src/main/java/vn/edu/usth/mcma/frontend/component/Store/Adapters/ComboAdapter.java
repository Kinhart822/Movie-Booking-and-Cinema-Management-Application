package vn.edu.usth.mcma.frontend.component.Store.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.Store.Models.ComboItem;

public class ComboAdapter extends RecyclerView.Adapter<ComboAdapter.ComboViewHolder> {
    private List<ComboItem> comboItems;

    public ComboAdapter(List<ComboItem> comboItems) {
        this.comboItems = comboItems;
    }

    public List<ComboItem> getComboItems() {
        return comboItems;
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

    // ViewHolder class for ComboItem
    class ComboViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private TextView priceText;
        private ImageView comboImage;

        ComboViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.combo_name);
            priceText = itemView.findViewById(R.id.combo_price);
            comboImage = itemView.findViewById(R.id.combo_image);
        }

        // Binds data to the view holder elements
        void bind(ComboItem item) {
            nameText.setText(item.getName());
            priceText.setText(String.format(Locale.getDefault(), "$%,.2f", item.getPrice()));

            if (item.getImageUrl() == null || item.getImageUrl().isEmpty()) {
                comboImage.setImageResource(R.drawable.combo_image_1);
            } else {
                Glide.with(itemView.getContext())
                        .load(item.getImageUrl())
                        .placeholder(R.drawable.combo_image_1)
                        .error(R.drawable.combo_image_1)
                        .into(comboImage);
            }
        }
    }
}

