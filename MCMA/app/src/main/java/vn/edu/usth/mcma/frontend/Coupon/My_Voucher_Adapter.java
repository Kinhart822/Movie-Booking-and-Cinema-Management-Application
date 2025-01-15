package vn.edu.usth.mcma.frontend.Coupon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.R;

public class My_Voucher_Adapter extends RecyclerView.Adapter<My_Voucher_ViewHolder> {
    private final List<My_Voucher_Item> voucherList;
    private final Context context;

    public My_Voucher_Adapter(Context context, List<My_Voucher_Item> voucherList) {
        this.context = context;
        this.voucherList = voucherList;
    }

    @NonNull
    @Override
    public My_Voucher_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.coupon_frame, parent, false);
        return new My_Voucher_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull My_Voucher_ViewHolder holder, int position) {
        My_Voucher_Item item = voucherList.get(position);
        holder.couponName.setText(item.getName());
        holder.couponPoint.setText(item.getPoint());
        holder.couponImage.setImageResource(item.getImageResource());
    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }
}
