package vn.edu.usth.mcma.frontend.Coupon;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;

public class My_Voucher_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_voucher);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_my_voucher_list);

        List<My_Voucher_Item> voucherList = new ArrayList<>();
        voucherList.add(new My_Voucher_Item("Voucher 1", "100 Points", R.drawable.movie1));
        voucherList.add(new My_Voucher_Item("Voucher 2", "200 Points", R.drawable.movie1));
        voucherList.add(new My_Voucher_Item("Voucher 3", "300 Points", R.drawable.movie1));

        My_Voucher_Adapter adapter = new My_Voucher_Adapter(this, voucherList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
