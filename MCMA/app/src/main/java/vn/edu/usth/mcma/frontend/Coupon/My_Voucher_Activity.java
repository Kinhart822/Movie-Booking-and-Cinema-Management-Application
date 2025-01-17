package vn.edu.usth.mcma.frontend.Coupon;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.CouponResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.ViewCouponResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.CouponAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;

public class My_Voucher_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private My_Voucher_Adapter adapter;
    private List<My_Voucher_Item> voucherList = new ArrayList<>();
    private FrameLayout noDataContainer;
    private CouponAPI couponAPI;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_voucher);

        recyclerView = findViewById(R.id.recyclerview_my_voucher_list);
        noDataContainer = findViewById(R.id.my_coupon_no_data_container);

//        voucherList.add(new My_Voucher_Item("Voucher 1", "100 Points", R.drawable.movie1));
//        voucherList.add(new My_Voucher_Item("Voucher 2", "200 Points", R.drawable.movie1));
//        voucherList.add(new My_Voucher_Item("Voucher 3", "300 Points", R.drawable.movie1));

        adapter = new My_Voucher_Adapter(this, voucherList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        RetrofitService retrofitService = new RetrofitService(this);
        couponAPI = retrofitService.getRetrofit().create(CouponAPI.class);

        showNoDataView();
        fetchUserVouchers();

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> onBackPressed());
    }

    void showNoDataView() {
        recyclerView.setVisibility(View.GONE);
        noDataContainer.setVisibility(View.VISIBLE);
    }

    void hideNoDataView() {
        recyclerView.setVisibility(View.VISIBLE);
        noDataContainer.setVisibility(View.GONE);
    }

    private void fetchUserVouchers() {
        Call<ViewCouponResponse> call = couponAPI.getAllCouponByUser();
        call.enqueue(new Callback<ViewCouponResponse>() {
            @Override
            public void onResponse(Call<ViewCouponResponse> call, Response<ViewCouponResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    voucherList.clear();
                    ViewCouponResponse viewCouponResponse = response.body();

                    if (viewCouponResponse.getNameCoupons().isEmpty()) {
                        showNoDataView();
                    } else {
                        int size = viewCouponResponse.getNameCoupons().size();
                        for (int i = 0; i < size; i++) {
                            voucherList.add(new My_Voucher_Item(
                                    viewCouponResponse.getCouponIds().get(i),
                                    viewCouponResponse.getNameCoupons().get(i),
                                    viewCouponResponse.getPointToExchangeList().get(i) + " Points",
                                    viewCouponResponse.getDateAvailableList().get(i),
                                    viewCouponResponse.getExpirationDates().get(i),
                                    viewCouponResponse.getImageUrlCoupons().get(i)
                            ));
                        }
                        hideNoDataView();
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    showNoDataView();
                    Toast.makeText(My_Voucher_Activity.this, "Failed to fetch vouchers", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ViewCouponResponse> call, Throwable t) {
                showNoDataView();
                Toast.makeText(My_Voucher_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
