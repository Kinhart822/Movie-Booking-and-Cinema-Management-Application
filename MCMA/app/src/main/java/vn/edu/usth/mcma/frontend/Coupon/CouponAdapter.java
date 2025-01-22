package vn.edu.usth.mcma.frontend.Coupon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.CouponResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.CouponAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder holder, int position) {
        CouponItem coupon = couponList.get(position);
        holder.couponName.setText(coupon.getName());
        holder.couponPoints.setText("Points: " + coupon.getPoints() + " " + "points");

        String imageUrl = coupon.getImageUrl();
        if (imageUrl == null || imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(R.drawable.voucher)
                    .into(holder.couponImage);
        } else {
            Glide.with(context)
                    .load(imageUrl)
                    .error(R.drawable.voucher)
                    .into(holder.couponImage);
        }

        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, Coupon_Details_Activity.class);
//            context.startActivity(intent);
            int couponId = coupon.getId();
            fetchCouponDetails(couponId);
        });
    }

    private void fetchCouponDetails(int couponId) {
        RetrofitService retrofitService = new RetrofitService(context);
        CouponAPI couponAPI = retrofitService.getRetrofit().create(CouponAPI.class);

        Call<CouponResponse> call = couponAPI.getCouponDetails(couponId);
        call.enqueue(new Callback<CouponResponse>() {
            @Override
            public void onResponse(Call<CouponResponse> call, Response<CouponResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CouponResponse couponDetails = response.body();

                    Intent intent = new Intent(context, Coupon_Details_Activity.class);
                    intent.putExtra("couponId", couponDetails.getCouponIds().get(0));
                    intent.putExtra("couponName", couponDetails.getCouponNameList().get(0));
                    intent.putExtra("imageUrl", couponDetails.getImageUrlList().get(0));
                    intent.putExtra("backgroundImageUrl", couponDetails.getBackgroundImageUrlList().get(0));
                    intent.putExtra("description", couponDetails.getCouponDescriptionList().get(0));
                    intent.putExtra("dateAvailable", couponDetails.getDateAvailableList().get(0));
                    intent.putExtra("dateExpired", couponDetails.getExpirationDates().get(0));
                    intent.putExtra("points", couponDetails.getPointToExchangeList().get(0));
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Failed to fetch coupon details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CouponResponse> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
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
