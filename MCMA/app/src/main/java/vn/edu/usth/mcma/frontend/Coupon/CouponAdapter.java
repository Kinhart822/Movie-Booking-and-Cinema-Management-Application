package vn.edu.usth.mcma.frontend.Coupon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.usth.mcma.R;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponViewHolder> {

    private Context context;
    private List<CouponItem> couponList;

    public CouponAdapter(Context context, List<CouponItem> couponList) {
        this.context = context;
        this.couponList = couponList;
    }

    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.coupon_frame, parent, false);
        return new CouponViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder holder, int position) {
        CouponItem coupon = couponList.get(position);
        holder.couponName.setText(coupon.getName());
        holder.couponPoints.setText("Points: " + coupon.getPoints());
        Glide.with(context).load(coupon.getImageUrl()).into(holder.couponImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Coupon_Details_Activity.class);
            intent.putExtra("imageUrl", coupon.getImageUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return couponList.size();
    }

    public static class CouponViewHolder extends RecyclerView.ViewHolder {
        ImageView couponImage;
        TextView couponName, couponPoints;

        public CouponViewHolder(@NonNull View itemView) {
            super(itemView);
            couponImage = itemView.findViewById(R.id.coupon_image);
            couponName = itemView.findViewById(R.id.coupon_name);
            couponPoints = itemView.findViewById(R.id.coupon_point);
        }
    }
}
