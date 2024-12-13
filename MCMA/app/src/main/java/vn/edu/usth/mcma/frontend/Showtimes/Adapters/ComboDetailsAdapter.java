package vn.edu.usth.mcma.frontend.Showtimes.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.R;

public class ComboDetailsAdapter extends RecyclerView.Adapter<ComboDetailsAdapter.ComboDetailsViewHolder> {
    private List<ComboDetailItem> comboDetails;

    public static class ComboDetailItem {
        private String name;
        private String price;

        public ComboDetailItem(String name, String price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }
    }

    public ComboDetailsAdapter(List<ComboDetailItem> comboDetails) {
        this.comboDetails = comboDetails;
    }

    @NonNull
    @Override
    public ComboDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_combo_details, parent, false);
        return new ComboDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComboDetailsViewHolder holder, int position) {
        ComboDetailItem item = comboDetails.get(position);
        holder.nameTV.setText(item.getName());
        holder.priceTV.setText(item.getPrice());
    }

    @Override
    public int getItemCount() {
        return comboDetails.size();
    }

    public static class ComboDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV;
        TextView priceTV;

        public ComboDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.numberComboName);
            priceTV = itemView.findViewById(R.id.comboPricePerQuantity);
        }
    }
}
