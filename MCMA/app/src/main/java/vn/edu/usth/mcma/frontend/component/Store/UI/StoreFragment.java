package vn.edu.usth.mcma.frontend.component.Store.UI;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import vn.edu.usth.mcma.frontend.dto.Response.ListFoodAndDrinkToOrderingResponse;
import vn.edu.usth.mcma.frontend.dto.Response.ViewCinemaResponse;
import vn.edu.usth.mcma.frontend.network.apis.GetCinemaListAPI;
import vn.edu.usth.mcma.frontend.network.apis.ViewAllFoodsAndDrinksByCinemaAPI;
import vn.edu.usth.mcma.frontend.network.RetrofitService;
import vn.edu.usth.mcma.frontend.component.Store.Adapters.ComboAdapter;
import vn.edu.usth.mcma.frontend.component.Store.Adapters.TheaterAdapter;
import vn.edu.usth.mcma.frontend.component.Store.Models.ComboItem;
import vn.edu.usth.mcma.frontend.component.Store.Models.Theater;

public class StoreFragment extends Fragment implements TheaterAdapter.OnTheaterClickListener {
    private Theater selectedTheater;
    private RecyclerView comboRecyclerView;
    private FrameLayout noDataContainer;
    private Button buttonTheater;
    private TextView totalPriceText;
    private View comboMenuContainer;
    private DrawerLayout mDrawerLayout;
    private final List<Theater> theaters = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        initializeViews(view);

        updateTheaterButton("All Theaters");

        getTheaterList();

        buttonTheater.setOnClickListener(v -> showTheaterSelectionDialog());

        showNoDataView();

        return view;
    }

    private void initializeViews(View view) {
        noDataContainer = view.findViewById(R.id.no_data_container);
        buttonTheater = view.findViewById(R.id.theater_button);
        comboRecyclerView = view.findViewById(R.id.combo_recycler_view);
        Button checkoutButton = view.findViewById(R.id.checkout_button);
        comboMenuContainer = view.findViewById(R.id.combo_menu_container);
        ImageView noDataImage = view.findViewById(R.id.no_data_image);
        TextView noDataText = view.findViewById(R.id.no_data_text);
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
                updateTheaterButton(selectedTheater.getName());
                updateViewForTheater(selectedTheater);
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    private void updateTheaterButton(String name) {
        buttonTheater.setText(name != null ? name : "All Theaters");
    }

    private void updateViewForTheater(Theater theater) {
        if (theater != null) {
            fetchComboItems(Integer.parseInt(theater.getId()));
        }
    }

    private void fetchComboItems(int cinemaId) {
        RetrofitService retrofitService = new RetrofitService(requireContext());
        ViewAllFoodsAndDrinksByCinemaAPI apiService = retrofitService.getRetrofit().create(ViewAllFoodsAndDrinksByCinemaAPI.class);

        apiService.ViewFoodsAndDrinks(cinemaId).enqueue(new Callback<List<ListFoodAndDrinkToOrderingResponse>>() {
            @Override
            public void onResponse(Call<List<ListFoodAndDrinkToOrderingResponse>> call, Response<List<ListFoodAndDrinkToOrderingResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ListFoodAndDrinkToOrderingResponse> comboData = response.body();
                    List<ComboItem> comboItems = convertResponseToComboItems(comboData);
                    updateComboList(comboItems);
                } else {
                    Log.e("StoreFragment", "Failed to load combos: " + response.message());
                    Toast.makeText(requireActivity(), "No available combos", Toast.LENGTH_SHORT).show();
                    showNoDataView();
                }
            }

            @Override
            public void onFailure(Call<List<ListFoodAndDrinkToOrderingResponse>> call, Throwable t) {
                Log.e("StoreFragment", "API Call Failed: " + t.getMessage(), t);
                Toast.makeText(requireActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                showNoDataView();
            }
        });
    }

    private List<ComboItem> convertResponseToComboItems(List<ListFoodAndDrinkToOrderingResponse> comboData) {
        List<ComboItem> items = new ArrayList<>();
        for (ListFoodAndDrinkToOrderingResponse response : comboData) {
            for (int i = 0; i < response.getFoodNameList().size(); i++) {
                items.add(new ComboItem(
                        response.getFoodNameList().get(i),
                        response.getImageUrlFoodList().get(i),
                        response.getFoodPriceList().get(i)
                ));
            }
            for (int i = 0; i < response.getDrinkNameList().size(); i++) {
                items.add(new ComboItem(
                        response.getDrinkNameList().get(i),
                        response.getImageUrlDrinkList().get(i),
                        response.getDrinkPriceList().get(i)
                ));
            }
        }
        return items;
    }

    private void updateComboList(List<ComboItem> comboItems) {
        if (comboItems.isEmpty()) {
            showNoDataView();
        } else {
            hideNoDataView();
            ComboAdapter comboAdapter = new ComboAdapter(comboItems);
            comboRecyclerView.setAdapter(comboAdapter);
            comboRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        }
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
        updateTheaterButton(theater.getName());
        updateViewForTheater(theater);
    }

    private void getTheaterList() {
        RetrofitService retrofitService = new RetrofitService(requireContext());
        GetCinemaListAPI getCinemaListAPI = retrofitService.getRetrofit().create(GetCinemaListAPI.class);

        getCinemaListAPI.getCinemaList().enqueue(new Callback<ViewCinemaResponse>() {
            @Override
            public void onResponse(Call<ViewCinemaResponse> call, Response<ViewCinemaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ViewCinemaResponse cinemaResponse = response.body();
                    List<Long> cinemaIdList = cinemaResponse.getCinemaIdList();
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
                Toast.makeText(requireActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
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