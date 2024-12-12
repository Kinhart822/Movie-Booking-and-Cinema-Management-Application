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
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Movie;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Seat;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.ComboAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.TheaterAdapter;
import vn.edu.usth.mcma.frontend.Showtimes.Models.ComboItem;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Theater;
import vn.edu.usth.mcma.frontend.Showtimes.Models.TicketItem;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.PriceCalculator;

public class ComboSelectionActivity extends AppCompatActivity {

    public static final String EXTRA_SEAT_PRICE = "extra_seat_price";
    public static final String EXTRA_SEAT_COUNT = "extra_seat_count";
    public static final String EXTRA_SELECTED_SEATS = "extra_selected_seats";
    public static final String EXTRA_THEATER = "extra_theater";
    public static final String EXTRA_MOVIE = "extra_movie";

    private int seatCount = 0;
    private int seatPriceTotal = 0;
    private RecyclerView comboRecyclerView;
    private Button checkoutButton;
    private ComboAdapter comboAdapter;
    private TextView seatCountText;
    private TextView seatPriceText;
    private TextView comboPriceText;
    private TextView totalPriceText;
    private TextView theaterNameTV;
    private TextView movieNameTV;
    private TextView releaseDateTV;
    private TextView screenRoomTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_selection);
        initializeViews();
        handleIntentExtras();
        setupBackButton();
        setupComboList();
        setupCheckoutButton();
    }

    private void handleIntentExtras() {
        try {
            // Parse seat price and count
            seatPriceTotal = getIntent().getIntExtra(EXTRA_SEAT_PRICE, 0);
            seatCount = getIntent().getIntExtra(EXTRA_SEAT_COUNT, 0);

            // Theater name handling
            Theater selectedTheater = (Theater) getIntent().getSerializableExtra(EXTRA_THEATER);
            if (selectedTheater != null && theaterNameTV != null) {
                theaterNameTV.setText(selectedTheater.getName());
            }

            // Movie name handling
            Movie selectedMovie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
            if (selectedMovie != null && movieNameTV != null) {
                movieNameTV.setText(selectedMovie.getTitle());
            }

            // Release date handling (always today's date)
            if (releaseDateTV != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd'th' MMM, yyyy", Locale.getDefault());
                Date today = new Date();
                String formattedDate = formatDateWithOrdinal(today);
                releaseDateTV.setText(formattedDate);
            }

            // Screen number handling
            String selectedScreenRoom = getIntent().getStringExtra("SELECTED_SCREEN_ROOM");
            if (screenRoomTV != null) {
                screenRoomTV.setText(selectedScreenRoom != null ? selectedScreenRoom : "Screen 1");
            }

            // Find TextView references with null checks
            TextView seatCountText = findViewById(R.id.no_of_seats);
            TextView seatPriceText = findViewById(R.id.seat_price_total);

            if (seatCountText != null) {
                seatCountText.setText(String.format("%d ghế", seatCount));
            } else {
                Log.e("ComboSelectionActivity", "seatCountText is null");
            }

            if (seatPriceText != null) {
                seatPriceText.setText(PriceCalculator.formatPrice(seatPriceTotal));
            } else {
                Log.e("ComboSelectionActivity", "seatPriceText is null");
            }

            // Initialize total price

        } catch (Exception e) {
            Log.e("ComboSelectionActivity", "Error in handleIntentExtras", e);
            // Optionally show a toast or handle the error appropriately
            Toast.makeText(this, "Error loading seat information", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper method to format date with ordinal suffix
    private String formatDateWithOrdinal(Date date) {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.getDefault());

        String month = monthFormat.format(date);
        String year = yearFormat.format(date);
        int day = Integer.parseInt(dayFormat.format(date));

        String dayWithSuffix = getDayWithOrdinal(day);

        return String.format("%s %s, %s", dayWithSuffix, month, year);
    }

    // Helper method to get day with ordinal suffix
    private String getDayWithOrdinal(int day) {
        if (day >= 11 && day <= 13) {
            return day + "th";
        }
        switch (day % 10) {
            case 1:
                return day + "st";
            case 2:
                return day + "nd";
            case 3:
                return day + "rd";
            default:
                return day + "th";
        }
    }

    @SuppressLint("WrongViewCast")
    private void initializeViews() {
        comboRecyclerView = findViewById(R.id.combo_recycler_view);
        checkoutButton = findViewById(R.id.checkout_button);
        seatCountText = findViewById(R.id.no_of_seats);
        seatPriceText = findViewById(R.id.seat_price_total);
        comboPriceText = findViewById(R.id.combo_price_total);
        totalPriceText = findViewById(R.id.total_price_0);
        theaterNameTV = findViewById(R.id.theater_name);
        movieNameTV = findViewById(R.id.movie_name);
        releaseDateTV = findViewById(R.id.movie_release_date);
        screenRoomTV = findViewById(R.id.screen_number);
        if (checkoutButton == null) {
            Log.e("ComboSelectionActivity", "checkoutButton is null");
        }
    }

    private void setupComboList() {
        List<ComboItem> comboItems = getComboItems();
        comboAdapter = new ComboAdapter(comboItems);

        // Ensure listener is set before calling updateTotalPrice
        comboAdapter.setTotalPriceChangedListener(items -> {
            if (totalPriceText != null) {
                updateTotalPrice();
            }
        });

        comboRecyclerView.setAdapter(comboAdapter);
        comboRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initial total price update
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        try {
            // Ensure comboAdapter is not null
            if (comboAdapter == null) {
                Log.e("ComboSelectionActivity", "ComboAdapter is null");
                return;
            }

            // Calculate combo price
            List<ComboItem> selectedComboItems = comboAdapter.getSelectedComboItems();
            double comboPriceTotal = selectedComboItems.stream()
                    .mapToDouble(ComboItem::getTotalPrice)
                    .sum();

            // Calculate total price
            double totalPrice = seatPriceTotal + comboPriceTotal;

            // Update combo price
            if (comboPriceText != null) {
                comboPriceText.setText(PriceCalculator.formatPrice(comboPriceTotal));
            }

            // Update total price
            if (totalPriceText != null) {
                totalPriceText.setText(PriceCalculator.formatPrice(totalPrice));
            }

            // Enable/disable checkout button
            if (checkoutButton != null) {
                checkoutButton.setEnabled(totalPrice > 0);
                checkoutButton.setBackgroundResource(
                        totalPrice > 0 ? R.drawable.rounded_active_background : R.drawable.rounded_dark_background
                );
            }
        } catch (Exception e) {
            Log.e("ComboSelectionActivity", "Error in updateTotalPrice", e);
        }
    }

    private void setupCheckoutButton() {
        Button checkoutButton = findViewById(R.id.checkout_button);
        checkoutButton.setOnClickListener(v -> {
            List<TicketItem> ticketItems = getIntent().getParcelableArrayListExtra("TICKET_ITEMS");
            // Retrieve selected seats from previous activity
            List<Seat> selectedSeats = getIntent().getParcelableArrayListExtra(EXTRA_SELECTED_SEATS);

            // Get selected combo items
            List<ComboItem> selectedComboItems = comboAdapter.getSelectedComboItems();

            // Calculate total price
            double totalPrice = PriceCalculator.calculateTotalPrice(seatPriceTotal, selectedComboItems);

            // Create intent to PaymentBookingActivity
            Intent intent = new Intent(this, PaymentBookingActivity.class);
            intent.putParcelableArrayListExtra("TICKET_ITEMS", new ArrayList<>(ticketItems));
            intent.putExtra("SELECTED_MOVIE", getIntent().getSerializableExtra("SELECTED_MOVIE"));
            intent.putExtra("SELECTED_THEATER", getIntent().getSerializableExtra("SELECTED_THEATER"));
            intent.putExtra("SELECTED_SHOWTIME", getIntent().getStringExtra("SELECTED_SHOWTIME"));
            intent.putParcelableArrayListExtra("SELECTED_SEATS", new ArrayList<>(selectedSeats));
            intent.putParcelableArrayListExtra("SELECTED_COMBO_ITEMS", new ArrayList<>(selectedComboItems));
            intent.putExtra("TOTAL_PRICE", totalPrice);

            startActivity(intent);
        });
    }

    private void setupBackButton() {
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private List<ComboItem> getComboItems() {
        // Sample data - replace with actual data from your backend
        List<ComboItem> items = new ArrayList<>();
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
