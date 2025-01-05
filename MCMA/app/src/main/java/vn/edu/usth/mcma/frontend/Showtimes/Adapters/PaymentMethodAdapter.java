package vn.edu.usth.mcma.frontend.Showtimes.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Showtimes.Models.PaymentMethod;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.PaymentMethodViewHolder> {
    private List<PaymentMethod> paymentMethods;

//    private List<vn.edu.usth.mcma.frontend.Showtimes.Models.Booking.PaymentMethod> paymentMethods;

    private int selectedPosition = -1;
    private OnPaymentMethodSelectedListener listener;
    public PaymentMethodAdapter(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

//    public PaymentMethodAdapter(List<vn.edu.usth.mcma.frontend.Showtimes.Models.Booking.PaymentMethod> paymentMethods) {
//        this.paymentMethods = paymentMethods;
//    }

    public interface OnPaymentMethodSelectedListener {
        void onPaymentMethodSelected(PaymentMethod paymentMethod);
    }

//    public interface OnPaymentMethodSelectedListener {
//        void onPaymentMethodSelected(PaymentMethod paymentMethod);
//    }

    public void setOnPaymentMethodSelectedListener(OnPaymentMethodSelectedListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public PaymentMethodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payment_method, parent, false);
        return new PaymentMethodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentMethodViewHolder holder, int position) {
        PaymentMethod paymentMethod = paymentMethods.get(position);
        holder.paymentMethodIcon.setImageResource(paymentMethod.getIconResource());
        holder.paymentMethodName.setText(paymentMethod.getName());

        // Set text color to black
        holder.paymentMethodName.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.black));

        holder.paymentMethodRadio.setChecked(position == selectedPosition);

        // Handle both the entire item click and radio button click
        View.OnClickListener clickListener = v -> {
            int previousSelected = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            // Update previous and new selections
            if (previousSelected != -1) {
                notifyItemChanged(previousSelected);
            }
            notifyItemChanged(selectedPosition);
            if (listener != null) {
                listener.onPaymentMethodSelected(paymentMethod);
            }
        };
        // Set click listener for both the item view and radio button
        holder.itemView.setOnClickListener(clickListener);
        holder.paymentMethodRadio.setOnClickListener(clickListener);
        // Prevent radio button from handling clicks separately
        holder.paymentMethodRadio.setClickable(false);

//        vn.edu.usth.mcma.frontend.Showtimes.Models.Booking.PaymentMethod paymentMethod = paymentMethods.get(position);
//        holder.paymentMethodIcon.setImageResource(paymentMethod.getIconResource());
//        holder.paymentMethodName.setText(paymentMethod.getDisplayName());
//
//        holder.paymentMethodName.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.black));
//        holder.paymentMethodRadio.setChecked(position == selectedPosition);
//
//        View.OnClickListener clickListener = v -> {
//            int previousSelected = selectedPosition;
//            selectedPosition = holder.getAdapterPosition();
//            if (previousSelected != -1) {
//                notifyItemChanged(previousSelected);
//            }
//            notifyItemChanged(selectedPosition);
//            if (listener != null) {
//                listener.onPaymentMethodSelected(paymentMethod);
//            }
//        };
//        holder.itemView.setOnClickListener(clickListener);
//        holder.paymentMethodRadio.setOnClickListener(clickListener);
//        holder.paymentMethodRadio.setClickable(false);
    }

    @Override
    public int getItemCount() {
        return paymentMethods.size();
    }
    static class PaymentMethodViewHolder extends RecyclerView.ViewHolder {
        ImageView paymentMethodIcon;
        TextView paymentMethodName;
        RadioButton paymentMethodRadio;
        PaymentMethodViewHolder(View itemView) {
            super(itemView);
            paymentMethodIcon = itemView.findViewById(R.id.paymentMethodIcon);
            paymentMethodName = itemView.findViewById(R.id.paymentMethodName);
            paymentMethodRadio = itemView.findViewById(R.id.paymentMethodRadio);
        }
    }
}