package vn.edu.usth.mcma.frontend.component.bookingprocess;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lombok.Getter;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.model.AudienceType;

public class AudienceTypeAdapter extends RecyclerView.Adapter<AudienceTypeAdapter.ViewHolder> {
    @Getter
    private final List<AudienceType> items;
    private final IAudienceTypeItemView iAudienceTypeItemView;

    public AudienceTypeAdapter(List<AudienceType> items, IAudienceTypeItemView iAudienceTypeItemView) {
        this.items = items;
        this.iAudienceTypeItemView = iAudienceTypeItemView;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 || position == 2) ? 1 : 0;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_audience_type, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.textView = view.findViewById(R.id.text_view);
        holder.linearLayout = view.findViewById(R.id.constraint_layout);
        if (viewType == 1) {
            holder.textView.setVisibility(View.VISIBLE);
            holder.linearLayout.setVisibility(View.GONE);
        } else {
            holder.textView.setVisibility(View.GONE);
            holder.linearLayout.setVisibility(View.VISIBLE);
        }
        return holder;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemViewType(position) == 1) {
            if (position == 0) {
                holder.textView.setText(String.format("We are having a discount for students. All students will be charged $%d per seat.", items.get(0).getUnitPrice().intValue()));
                return;
            }
            if (position == 2) {
                holder.textView.setText("For non-students, you will be charged the normal rate:");
                return;
            }
        }
        AudienceType item = getItemByPosition(position);
        holder.audienceTypeTextView.setText(item.getId());
        holder.unitPriceTextView.setText(item.getUnitPrice() + " x");
        holder.minusButton.setOnClickListener(v -> updateQuantity(position, -1));
        holder.plusButton.setOnClickListener(v -> updateQuantity(position, 1));
        holder.ticketQuantityTextView.setText(Integer.toString(item.getQuantity()));
        holder.sumPerTypeTextView.setText(Double.toString(item.getUnitPrice() * item.getQuantity()));
    }
    @Override
    public int getItemCount() {
        return items.size() + 2;
    }
    private AudienceType getItemByPosition(int position) {
        return (position == 1) ? items.get(0) : items.get(position - 2);
    }
    private void updateQuantity(int position, int delta) {
        AudienceType item = getItemByPosition(position);
        item.setQuantity(Math.max(0, item.getQuantity() + delta));
        notifyItemChanged(position);
        iAudienceTypeItemView.onQuantityChangeListener(items);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ConstraintLayout linearLayout;
        private final TextView audienceTypeTextView;
        private final TextView unitPriceTextView;
        private final ImageView minusButton;
        private final ImageView plusButton;
        private final TextView ticketQuantityTextView;
        private final TextView sumPerTypeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            audienceTypeTextView = itemView.findViewById(R.id.text_view_audience_type);
            unitPriceTextView = itemView.findViewById(R.id.text_view_unit_price);
            minusButton = itemView.findViewById(R.id.button_minus);
            plusButton = itemView.findViewById(R.id.button_plus);
            ticketQuantityTextView = itemView.findViewById(R.id.text_view_ticket_quantity);
            sumPerTypeTextView = itemView.findViewById(R.id.text_view_sum_per_type);
        }
    }
}
