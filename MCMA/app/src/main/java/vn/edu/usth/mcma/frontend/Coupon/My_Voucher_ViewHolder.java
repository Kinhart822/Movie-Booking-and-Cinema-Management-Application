package vn.edu.usth.mcma.frontend.Coupon;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.mcma.R;

public class My_Voucher_ViewHolder extends RecyclerView.ViewHolder {
    public final ImageView couponImage;
    public final TextView couponName;
    public final TextView couponPoint;

    public My_Voucher_ViewHolder(@NonNull View itemView) {
        super(itemView);
        couponImage = itemView.findViewById(R.id.coupon_image);
        couponName = itemView.findViewById(R.id.coupon_name);
        couponPoint = itemView.findViewById(R.id.coupon_point);
    }
}
