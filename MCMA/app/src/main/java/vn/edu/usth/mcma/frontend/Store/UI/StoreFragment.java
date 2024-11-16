package vn.edu.usth.mcma.frontend.Store.UI;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.MainActivity;
import vn.edu.usth.mcma.frontend.Store.Adapters.ComboAdapter;
import vn.edu.usth.mcma.frontend.Store.Adapters.TheaterAdapter;
import vn.edu.usth.mcma.frontend.Store.Models.ComboItem;
import vn.edu.usth.mcma.frontend.Store.Models.Theater;
import vn.edu.usth.mcma.frontend.Store.Utils.PriceCalculator;

public class StoreFragment extends Fragment implements TheaterAdapter.OnTheaterClickListener {
    private ImageView noDataImage;
    private TextView noDataText;
    private Theater selectedTheater;
    private RecyclerView comboRecyclerView;
    private FrameLayout noDataContainer;
    private Button buttonTheater;
    private TextView totalPriceText;
    private Button checkoutButton;
    private ComboAdapter comboAdapter;
    private View comboMenuContainer;
    private DrawerLayout mDrawerLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        initializeViews(view);

        ImageButton mImageView = view.findViewById(R.id.menu_button);

        mDrawerLayout = view.findViewById(R.id.store_fragment);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout != null && !mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        LinearLayout to_home_fragment = view.findViewById(R.id.home_side_navigation);
        to_home_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof MainActivity){
                    ((MainActivity) getActivity()).close_to_home_page();
                }
            }
        });

        LinearLayout to_showtimes_fragment = view.findViewById(R.id.showtimes_side_navigation);
        to_showtimes_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof MainActivity){
                    ((MainActivity) getActivity()).close_to_showtimes_page();
                }
            }
        });

        LinearLayout to_store_fragment = view.findViewById(R.id.store_side_navigation);
        to_store_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof MainActivity){
                    ((MainActivity) getActivity()).close_to_store_page();
                }
            }
        });

        LinearLayout to_personal_fragment = view.findViewById(R.id.personal_side_navigation);
        to_personal_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof MainActivity){
                    ((MainActivity) getActivity()).close_to_personal_page();
                }
            }
        });

        ImageButton notication_buttonn = view.findViewById(R.id.notification_button);
        notication_buttonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Notification.Notification_Activity.class );
                startActivity(i);
            }
        });

        // Set default theater (Tất cả các rạp)
        selectedTheater = getTheaterList().get(0);
        updateTheaterButton();

        buttonTheater.setOnClickListener(v -> showTheaterSelectionDialog());
        setupComboList();
        setupCheckoutButton();

        // Show default view for "Tất cả các rạp"
        updateViewForTheater(selectedTheater);

        return view;
    }

    @SuppressLint("WrongViewCast")
    private void initializeViews(View view) {
        noDataContainer = view.findViewById(R.id.no_data_container);
        buttonTheater = view.findViewById(R.id.theater_button);
        comboRecyclerView = view.findViewById(R.id.combo_recycler_view);
        totalPriceText = view.findViewById(R.id.total_price_text);
        checkoutButton = view.findViewById(R.id.checkout_button);
        comboMenuContainer = view.findViewById(R.id.combo_menu_container);
        noDataImage = view.findViewById(R.id.no_data_image);
        noDataText = view.findViewById(R.id.no_data_text);
    }

    private void showTheaterSelectionDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_theater_selection_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        TextView cancelBtn = dialogView.findViewById(R.id.cancel_button);
        Button confirmBtn = dialogView.findViewById(R.id.confirm_button);
        RecyclerView theaterDialogRecyclerView = dialogView.findViewById(R.id.theater_recycler_view);

        TheaterAdapter dialogAdapter = new TheaterAdapter(getTheaterList());
        dialogAdapter.setOnTheaterClickListener(theater -> {
            selectedTheater = theater;
            dialogAdapter.setCurrentSelection(theater);
        });

        // Set current selection in dialog
        dialogAdapter.setCurrentSelection(selectedTheater);

        theaterDialogRecyclerView.setAdapter(dialogAdapter);
        theaterDialogRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        AlertDialog dialog = builder.create();

        cancelBtn.setOnClickListener(v -> dialog.dismiss());
        confirmBtn.setOnClickListener(v -> {
            if (selectedTheater != null) {
                updateTheaterButton();
                updateViewForTheater(selectedTheater);
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    private void updateTheaterButton() {
        buttonTheater.setText(selectedTheater.getName());
    }

    private void updateViewForTheater(Theater theater) {
        if ("1".equals(theater.getId())) { // "Tất cả các rạp"
            showNoDataView();
        } else {
            hideNoDataView();
            setupComboList();
        }
    }

    private void setupComboList() {
        List<ComboItem> comboItems = getComboItems();
        comboAdapter = new ComboAdapter(comboItems);
        comboAdapter.setTotalPriceChangedListener(this::updateTotalPrice);
        comboRecyclerView.setAdapter(comboAdapter);
        comboRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void updateTotalPrice(double total) {
        PriceCalculator.PriceResult result = PriceCalculator.calculateTotalPrice(comboAdapter.getComboItems());
        String formattedPrice = PriceCalculator.formatPrice(result.getTotal());
        totalPriceText.setText(String.format("Tổng tiền (đã bao gồm phụ thu): %s", formattedPrice));
        checkoutButton.setEnabled(result.getTotal() > 0);
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

    private void showNoDataView() {
        comboMenuContainer.setVisibility(View.GONE);
        noDataContainer.setVisibility(View.VISIBLE);
    }

    private void hideNoDataView() {
        noDataContainer.setVisibility(View.GONE);
        comboMenuContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTheaterClick(Theater theater) {
        selectedTheater = theater;
        updateTheaterButton();
        updateViewForTheater(theater);
    }

    private List<Theater> getTheaterList() {
        List<Theater> theaters = new ArrayList<>();
        theaters.add(new Theater("1", "Tất cả các rạp"));
        theaters.add(new Theater("2", "BHD Star 3.2"));
        theaters.add(new Theater("3", "BHD Star Phạm Hùng"));
        theaters.add(new Theater("4", "BHD Star Quang Trung"));
        theaters.add(new Theater("5", "BHD Star Thảo Điền"));
        theaters.add(new Theater("6", "BHD Star Lê Văn Việt"));
        theaters.add(new Theater("7", "BHD Star Phạm Ngọc Thạch"));
        theaters.add(new Theater("8", "BHD Star Huế"));
        theaters.add(new Theater("9", "BHD Star Discovery"));
        theaters.add(new Theater("10", "BHD Star The Garden"));
        theaters.add(new Theater("11", "BHD Star Long Khánh"));
        theaters.add(new Theater("12", "BHD Star Phú Mỹ"));
        return theaters;
    }
}