package vn.edu.usth.mcma.frontend.Store.UI;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.ViewCinemaResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.GetCinemaListAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.NowShowingMovieAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;
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
    private final List<Theater> theaters = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        initializeViews(view);

        getTheaterList();
//        updateTheaterButton();

        buttonTheater.setOnClickListener(v -> showTheaterSelectionDialog());
//        setupComboList();
//
//        updateViewForTheater(selectedTheater);

        return view;
    }

    private void initializeViews(View view) {
        noDataContainer = view.findViewById(R.id.no_data_container);
        buttonTheater = view.findViewById(R.id.theater_button);
        comboRecyclerView = view.findViewById(R.id.combo_recycler_view);
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

        TheaterAdapter dialogAdapter = new TheaterAdapter(theaters);
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
                Toast.makeText(requireActivity(), "Theaters show", Toast.LENGTH_SHORT).show();
//                updateTheaterButton();
//                updateViewForTheater(selectedTheater);
            }
            dialog.dismiss();
        });

        dialog.show();
    }

//    private void updateTheaterButton() {
//        buttonTheater.setText(selectedTheater.getName());
//    }

//    private void updateViewForTheater(Theater theater) {
//        if ("1".equals(theater.getId())) {
//            showNoDataView();
//        } else {
//            hideNoDataView();
//            setupComboList();
//        }
//    }

//    private void setupComboList() {
//        List<ComboItem> comboItems = getComboItems();
//        comboAdapter = new ComboAdapter(comboItems);
//        comboRecyclerView.setAdapter(comboAdapter);
//        comboRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
//    }
//
//    private List<ComboItem> getComboItems() {
//        List<ComboItem> items = new ArrayList<>();
//        items.add(new ComboItem("OL Combo1 Sweet32oz - Pepsi22oz", String.valueOf(R.drawable.combo_image_1), 80000));
//        items.add(new ComboItem("OL Combo2 Sweet32oz - Pepsi22oz", String.valueOf(R.drawable.combo_image_1), 107000));
//        items.add(new ComboItem("OL Combo Special Bap nam Ga (Sweet)", String.valueOf(R.drawable.combo_image_1), 135000));
//        items.add(new ComboItem("OL Special Combo1 Khoai Lac (Sweet)", String.valueOf(R.drawable.combo_image_1), 135000));
//        items.add(new ComboItem("OL Combo Special Bap nam XX loc xoay (Sweet)", String.valueOf(R.drawable.combo_image_1), 135000));
//        items.add(new ComboItem("OL Special Combo2 Bap nam Ga Lac (Sweet)", String.valueOf(R.drawable.combo_image_1), 162900));
//        items.add(new ComboItem("OL Special Combo2 Khoai Lac (Sweet)", String.valueOf(R.drawable.combo_image_1), 162900));
//        items.add(new ComboItem("OL Special Combo2 Bap nam XX loc xoay (Sweet)", String.valueOf(R.drawable.combo_image_1), 162900));
//        return items;
//    }
//
//    private void showNoDataView() {
//        comboMenuContainer.setVisibility(View.GONE);
//        noDataContainer.setVisibility(View.VISIBLE);
//    }
//
//    private void hideNoDataView() {
//        noDataContainer.setVisibility(View.GONE);
//        comboMenuContainer.setVisibility(View.VISIBLE);
//    }

    @Override
    public void onTheaterClick(Theater theater) {
        selectedTheater = theater;
//        updateTheaterButton();
//        updateViewForTheater(theater);
    }

    private void getTheaterList() {
        RetrofitService retrofitService = new RetrofitService(requireContext());
        GetCinemaListAPI getCinemaListAPI = retrofitService.getRetrofit().create(GetCinemaListAPI.class);

        getCinemaListAPI.getCinemaList().enqueue(new Callback<ViewCinemaResponse>() {
            @Override
            public void onResponse(Call<ViewCinemaResponse> call, Response<ViewCinemaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ViewCinemaResponse cinemaResponse = response.body();
                    List<Integer> cinemaIdList = cinemaResponse.getCinemaIdList();
                    List<String> cinemaNameList = cinemaResponse.getCinemaNameList();

                    if (cinemaIdList == null || cinemaNameList == null) {
                        Log.e("StoreFragment", "Cinema lists are null");
                        Toast.makeText(requireActivity(), "No theaters to show", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    theaters.clear();
                    for (int i = 0; i < cinemaIdList.size(); i++) {
                        theaters.add(new Theater(
                                String.valueOf(cinemaIdList.get(i)),
                                cinemaNameList.get(i)
                        ));
                    }
                    updateTheaterList(theaters);
                } else {
                    Log.e("StoreFragment", "Error: " + response.message());
                    Toast.makeText(requireActivity(), "No theaters to show", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<ViewCinemaResponse> call, Throwable t) {
                Log.e("StoreFragment", "API Call Failed: " + t.getMessage(), t);
                Toast.makeText(requireActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();            }
        });
    }

    private void updateTheaterList(List<Theater> theaterList) {
        if (getActivity() != null) {
            TheaterAdapter adapter = new TheaterAdapter(theaterList);
            comboRecyclerView.setAdapter(adapter);
            adapter.setOnTheaterClickListener(this);
        }
    }

}