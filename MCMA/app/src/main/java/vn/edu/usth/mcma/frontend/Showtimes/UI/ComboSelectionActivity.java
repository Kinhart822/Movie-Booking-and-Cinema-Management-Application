package vn.edu.usth.mcma.frontend.Showtimes.UI;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Movie;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Seat;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.ComboAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.TheaterAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Models.ComboItem;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Theater;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.PriceCalculator;

public class ComboSelectionActivity extends AppCompatActivity {
    public static final String EXTRA_SEAT_PRICE = "extra_seat_price";
    public static final String EXTRA_SELECTED_SEATS = "extra_selected_seats";

    private RecyclerView comboRecyclerView;
    private TextView totalPriceText;
    private TextView seatPriceText;
    private Button checkoutButton;
    private ComboAdapter comboAdapter;
    private double seatPrice = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_selection);
        initializeViews();

        // Get seat selection data from previous activity
        String initialSeatPrice = getIntent().getStringExtra(EXTRA_SEAT_PRICE);
        ArrayList<Seat> selectedSeatsList = getIntent().getParcelableArrayListExtra(EXTRA_SELECTED_SEATS);
        Set<Seat> selectedSeats = selectedSeatsList != null ? new HashSet<>(selectedSeatsList) : new HashSet<>();
        if (initialSeatPrice != null) {
            try {
                // Remove non-numeric characters and parse
                seatPrice = Double.parseDouble(initialSeatPrice.replaceAll("[^0-9]", ""));
            } catch (NumberFormatException e) {
                seatPrice = 0;
            }
        }
        // Update number of seats
        if (seatPriceText != null) {
            String seatsText = selectedSeats.size() + " ghế";
            seatPriceText.setText(seatsText);
        }
        setupBackButton();
        setupComboList();
        setupCheckoutButton();
    }

    @SuppressLint("WrongViewCast")
    private void initializeViews() {
        comboRecyclerView = findViewById(R.id.combo_recycler_view);
        totalPriceText = findViewById(R.id.total_price_text);
        seatPriceText = findViewById(R.id.no_of_seats);
        checkoutButton = findViewById(R.id.checkout_button);
        if (totalPriceText == null || seatPriceText == null || checkoutButton == null) {
            Log.e("ComboSelectionActivity", "One or more views failed to initialize");
            return;
        }
    }

    private void setupComboList() {
        List<ComboItem> comboItems = getComboItems();
        comboAdapter = new ComboAdapter(comboItems);
        comboAdapter.setTotalPriceChangedListener(this::updateTotalPrice);
        comboRecyclerView.setAdapter(comboAdapter);
        comboRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void updateTotalPrice(double comboTotal) {
        if (totalPriceText == null || checkoutButton == null) {
            Log.e("ComboSelectionActivity", "Views are null in updateTotalPrice");
            return;
        }
        // Calculate total price
        double totalPrice = seatPrice + comboTotal;

        // Format and display total price
        String formattedPrice = PriceCalculator.formatPrice(totalPrice);
        totalPriceText.setText(String.format("Tổng tiền (đã bao gồm phụ thu): %s", formattedPrice));

        // Enable/disable checkout button based on total price
        checkoutButton.setEnabled(totalPrice > 0);
        checkoutButton.setBackgroundResource(
                totalPrice > 0 ? R.drawable.rounded_active_background : R.drawable.rounded_dark_background
        );
    }

    private void setupCheckoutButton() {
        /* checkoutButton.setOnClickListener(v -> {
            // Handle checkout logic here
        }); */
    }

    private void setupBackButton() {
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private List<ComboItem> getComboItems() {
        // Sample data - replace with actual data from your backend
        List<vn.edu.usth.mcma.frontend.Showtimes.Models.ComboItem> items = new ArrayList<>();
        items.add(new ComboItem("OL Combo1 Sweet32oz - Pepsi22oz", String.valueOf(R.drawable.combo_image_1), 80000));
        items.add(new ComboItem("OL Combo2 Sweet32oz - Pepsi22oz", String.valueOf(R.drawable.combo_image_1), 107000));
        items.add(new ComboItem("OL Combo Special Bap nam Ga (Sweet)", String.valueOf(R.drawable.combo_image_1), 135000));
        items.add(new ComboItem("OL Special Combo1 Khoai Lac (Sweet)", String.valueOf(R.drawable.combo_image_1), 135000));
        items.add(new ComboItem("OL Combo Special Bap nam XX loc xoay (Sweet)", String.valueOf(R.drawable.combo_image_1), 135000));
        items.add(new ComboItem("OL Special Combo2 Bap nam Ga Lac (Sweet)", String.valueOf(R.drawable.combo_image_1), 162900));
        items.add(new ComboItem("OL Special Combo2 Khoai Lac (Sweet)", String.valueOf(R.drawable.combo_image_1), 162900));
        items.add(new ComboItem("OL Special Combo2 Bap nam XX loc xoay (Sweet)", String.valueOf(R.drawable.combo_image_1), 162900));
        return items;
    }
}
