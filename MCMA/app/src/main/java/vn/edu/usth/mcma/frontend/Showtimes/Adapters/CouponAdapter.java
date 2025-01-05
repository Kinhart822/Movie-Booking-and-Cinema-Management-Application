package vn.edu.usth.mcma.frontend.Showtimes.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Coupon;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponViewHolder> {
    private final List<Coupon> coupons;
    private OnCouponClickListener listener;
    private Coupon currentSelection;

    public interface OnCouponClickListener {
        void onCouponClick(Coupon coupon);
    }

    public CouponAdapter(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public void setOnCouponClickListener(OnCouponClickListener listener) {
        this.listener = listener;
    }

    public Coupon getCurrentSelection() {
        return currentSelection;
    }

    public void setCurrentSelection(Coupon coupon) {
        // Deselect previous selection
        if (currentSelection != null) {
            currentSelection.setSelected(false);
        }
        // Update new selection
        if (coupon != null) {
            coupon.setSelected(true);
            currentSelection = coupon;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coupon_item_selection, parent, false);
        return new CouponViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder holder, int position) {
        Coupon coupon = coupons.get(position);
        holder.bind(coupon);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                setCurrentSelection(coupon);
                listener.onCouponClick(coupon);
            }
        });
    }

    @Override
    public int getItemCount() {
        return coupons.size();
    }

    static class CouponViewHolder extends RecyclerView.ViewHolder {
        private final TextView couponName;
        private final View checkMark;

        CouponViewHolder(@NonNull View itemView) {
            super(itemView);
            couponName = itemView.findViewById(R.id.coupon_name);
            checkMark = itemView.findViewById(R.id.selected_check);
        }

        void bind(Coupon coupon) {
            couponName.setText(coupon.getName());
            checkMark.setVisibility(coupon.isSelected() ? View.VISIBLE : View.INVISIBLE);
        }
    }
}