package vn.edu.usth.mcma.frontend.Coupon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.CouponResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;

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
        String imageUrl = item.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            // Dùng Glide để tải ảnh từ URL
            Glide.with(context)
                    .load(imageUrl)
                    .apply(new RequestOptions().placeholder(R.drawable.voucher))
                    .into(holder.couponImage);
        } else {
            Glide.with(context)
                    .load(R.drawable.voucher)
                    .into(holder.couponImage);
        }

        holder.itemView.setOnClickListener(v -> {
            int couponId = item.getId();
            fetchCouponDetails(couponId);
        });
    }

    private void fetchCouponDetails(int couponId) {
        ApiService
                .getBookingApi(context)
                .getCouponDetails(couponId)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<CouponResponse> call, @NonNull Response<CouponResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            CouponResponse couponDetails = response.body();

                            Intent intent = new Intent(context, User_Coupon_Details_Activity.class);
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
                    public void onFailure(@NonNull Call<CouponResponse> call, @NonNull Throwable t) {
                        Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }
}
