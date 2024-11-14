package vn.edu.usth.mcma.frontend.Store.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Store.Utils.PriceCalculator;

import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
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
        List<ComboItem> comboItems = getComboItems(); // Method to get combo items
        adapter = new ComboAdapter(comboItems);
        adapter.setTotalPriceChangedListener(this::updateTotalPrice);
        comboRecyclerView.setAdapter(adapter);
        comboRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void updateTotalPrice(double total) {
        PriceCalculator.PriceResult result = PriceCalculator.calculateTotalPrice(adapter.getComboItems());
        String formattedPrice = PriceCalculator.formatPrice(result.getTotal());
        totalPriceText.setText(String.format("Tổng tiền (đã bao gồm phụ thu): %s", formattedPrice));
        checkoutButton.setEnabled(result.getTotal() > 0);
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

    private List<ComboItem> getComboItems() {
        // Sample data - replace with actual data from your backend
        List<ComboItem> items = new ArrayList<>();
        items.add(new ComboItem("Combo 1", "url1", 80000));
        items.add(new ComboItem("Combo 2", "url2", 120000));
        return items;
    }
}