package vn.edu.usth.mcma.frontend.Store.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.mcma.R;

import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

public class ComboMenuActivity extends AppCompatActivity {
    private RecyclerView comboRecyclerView;
    private TextView totalPriceText;
    private Button checkoutButton;
    private ComboAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_menu);

        initializeViews();
        setupComboList();
        setupCheckoutButton();

        String theaterName = getIntent().getStringExtra("theater_name");
        setTitle(theaterName);
    }

    private void setupComboList() {
        adapter = new ComboAdapter();
        adapter.setTotalPriceChangedListener(this::updateTotalPrice);
        comboRecyclerView.setAdapter(adapter);
        comboRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void updateTotalPrice(double total) {
        totalPriceText.setText(String.format(Locale.getDefault(), "%,.0fđ", total));
        checkoutButton.setEnabled(total > 0);
    }

    private void initializeViews() {
        comboRecyclerView = findViewById(R.id.combo_recycler_view);
        totalPriceText = findViewById(R.id.total_price_text);
        checkoutButton = findViewById(R.id.checkout_button);
    }

    private void setupCheckoutButton() {
        checkoutButton.setOnClickListener(v -> {
            // Handle checkout logic here
        });
    }
}