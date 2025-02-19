package vn.edu.usth.mcma.frontend.component.bookingsession;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.model.item.PaymentMethodItem;
import vn.edu.usth.mcma.frontend.utils.ImageDecoder;

@Deprecated
public class PaymentMethodsAdapter extends RecyclerView.Adapter<PaymentMethodsAdapter.ViewHolder> {
    private final Context context;
    private final List<PaymentMethodItem> items;
    private final IPaymentMethodItemView iPaymentMethodItemView;
    private int selectedPosition;

    public PaymentMethodsAdapter(Context context, List<PaymentMethodItem> items, IPaymentMethodItemView iPaymentMethodItemView) {
        this.context = context;
        this.items = items;
        this.iPaymentMethodItemView = iPaymentMethodItemView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payment_methods, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PaymentMethodItem item = items.get(position);
        holder.descriptionTextView.setText(item.getDescription());
        holder.radioButton.setOnClickListener(v -> {
            selectedPosition = holder.getAbsoluteAdapterPosition();
            iPaymentMethodItemView.onPaymentMethodClickListener();
        });
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImageView;
        TextView descriptionTextView;
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iconImageView = itemView.findViewById(R.id.image_view_icon);
            this.descriptionTextView = itemView.findViewById(R.id.text_view_description);
            this.radioButton = itemView.findViewById(R.id.radio_button);
        }
    }
    public PaymentMethodItem getSelectedPaymentMethod() {
        return items.get(selectedPosition);
    }
}
