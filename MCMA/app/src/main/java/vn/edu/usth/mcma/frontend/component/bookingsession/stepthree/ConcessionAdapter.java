package vn.edu.usth.mcma.frontend.component.bookingsession.stepthree;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.stream.Collectors;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.model.item.ConcessionItem;
import vn.edu.usth.mcma.frontend.utils.ImageDecoder;

public class ConcessionAdapter extends RecyclerView.Adapter<ConcessionAdapter.ViewHolder> {
    private final Context context;
    private final List<ConcessionItem> items;
    private final IConcessionItemView iConcessionItemView;

    public ConcessionAdapter(
            Context context,
            List<ConcessionItem> items,
            IConcessionItemView iConcessionItemView) {
        this.context = context;
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

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ConcessionItem item = items.get(position);
        if (item.getImageBase64() != null) {
            Glide
                    .with(context)
                    .load(ImageDecoder.decode(item.getImageBase64()))
                    .placeholder(R.drawable.placeholder1080x1920)
                    .error(R.drawable.placeholder1080x1920)
                    .into(holder.concessionImageView);
        }
        holder.concessionNameTextView.setText(item.getName());
        holder.concessionDescriptionTextView.setText(item.getDescription());
        holder.concessionComboPrice.setText(String.format("Price: $%.1f", item.getComboPrice()));
        holder.minusButton.setOnClickListener(v -> updateQuantity(position, -1));
        holder.plusButton.setOnClickListener(v -> updateQuantity(position, 1));
        holder.concessionQuantityTextView.setText(Integer.toString(item.getQuantity()));
        holder.sumPerTypeTextView.setText("$" + item.getComboPrice() * item.getQuantity());
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    private void updateQuantity(int position, int delta) {
        ConcessionItem item = items.get(position);
        item.setQuantity(Math.max(0, item.getQuantity() + delta));
        notifyItemChanged(position);
        iConcessionItemView.onConcessionClickListener();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView concessionImageView;
        private final TextView concessionNameTextView;
        private final TextView concessionDescriptionTextView;
        private final TextView concessionComboPrice;
        private final TextView concessionQuantityTextView;
        private final TextView sumPerTypeTextView;
        private final ImageButton minusButton;
        private final ImageButton plusButton;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            concessionImageView = itemView.findViewById(R.id.image_view_concession);
            concessionNameTextView = itemView.findViewById(R.id.text_view_concession_name);
            concessionDescriptionTextView = itemView.findViewById(R.id.text_view_concession_description);
            concessionComboPrice = itemView.findViewById(R.id.text_view_concession_combo_price);
            concessionQuantityTextView = itemView.findViewById(R.id.text_view_concession_quantity);
            sumPerTypeTextView = itemView.findViewById(R.id.text_view_sum_per_type);
            minusButton = itemView.findViewById(R.id.button_minus);
            plusButton = itemView.findViewById(R.id.button_plus);
        }
    }
    public List<ConcessionItem> getItems() {
        return items
                .stream()
                .filter(c -> c.getQuantity() > 0)
                .collect(Collectors.toList());
    }
    public double getTotalConcessionPrice() {
        return this
                .items
                .stream()
                .mapToDouble(a -> a.getComboPrice() * a.getQuantity())
                .sum();
    }
}