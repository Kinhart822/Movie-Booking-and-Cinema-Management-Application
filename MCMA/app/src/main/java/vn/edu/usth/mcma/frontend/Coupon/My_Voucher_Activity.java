package vn.edu.usth.mcma.frontend.Coupon;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.response.ViewCouponResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class My_Voucher_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private My_Voucher_Adapter adapter;
    private final List<My_Voucher_Item> voucherList = new ArrayList<>();
    private FrameLayout noDataContainer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_voucher);

        recyclerView = findViewById(R.id.recyclerview_my_voucher_list);
        noDataContainer = findViewById(R.id.my_coupon_no_data_container);

        adapter = new My_Voucher_Adapter(this, voucherList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

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
        ApiService
                .getBookingApi(this)
                .getAllCouponByUser()
                .enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ViewCouponResponse> call, @NonNull Response<ViewCouponResponse> response) {
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
            public void onFailure(@NonNull Call<ViewCouponResponse> call, @NonNull Throwable t) {
                showNoDataView();
                Toast.makeText(My_Voucher_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
