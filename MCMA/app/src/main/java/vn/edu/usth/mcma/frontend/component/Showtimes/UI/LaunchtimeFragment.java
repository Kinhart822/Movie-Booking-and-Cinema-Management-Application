package vn.edu.usth.mcma.frontend.component.Showtimes.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.response.ViewCinemaResponse;
import vn.edu.usth.mcma.frontend.component.Showtimes.Models.Theater;
import vn.edu.usth.mcma.frontend.component.Showtimes.Utils.TheaterDataProvider;
import vn.edu.usth.mcma.frontend.component.Showtimes.Adapters.TheaterAdapter;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class LaunchtimeFragment extends Fragment implements TheaterAdapter.OnTheaterClickListener {
    private TheaterAdapter theaterAdapter;
    private String currentCity = "TPHCM";
    private int currentCityId = 1;
    private View rootView;
    private Button selectedCityButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_launchtime, container, false);

        // Initialize views and adapters
        setupViews(rootView);
        loadCitiesFromAPI();
        updateTheaterList();

        return rootView;
    }

    private void setupViews(View v) {
        RecyclerView theaterRecyclerView = v.findViewById(R.id.theater_recycler_view);
        theaterAdapter = new TheaterAdapter(this);
        theaterRecyclerView.setAdapter(theaterAdapter);
        theaterRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void loadCitiesFromAPI() {
        TheaterDataProvider.getCities();
    }

    private void setupCityButtons(List<Integer> cityIds, List<String> cityNames) {
        LinearLayout cityContainer = rootView.findViewById(R.id.city_container);
        if (cityContainer != null) {
            cityContainer.removeAllViews();

            for (int i = 0; i < cityNames.size(); i++) {
                String cityName = cityNames.get(i);
                int cityId = cityIds.get(i);

                Button cityButton = new Button(requireContext());
                cityButton.setText(cityName);
                cityButton.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                // Thêm margin giữa các nút
                ((LinearLayout.LayoutParams) cityButton.getLayoutParams()).setMargins(8, 0, 8, 0);

                cityButton.setOnClickListener(v1 -> {
                    currentCity = cityName;
                    currentCityId = cityId;
                    updateTheaterList();

                    for (int j = 0; j < cityContainer.getChildCount(); j++) {
                        View child = cityContainer.getChildAt(j);
                        if (child instanceof Button) {
                            child.setSelected(child == cityButton);
                        }
                    }
                });

                cityContainer.addView(cityButton);
            }
        }
    }

    private void updateTheaterList() {
        ApiService
                .getCinemaApi(requireContext())
                .getCinemaListByCity(currentCityId).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<ViewCinemaResponse> call, @NonNull Response<ViewCinemaResponse> response) {
                        if (isAdded() && response.isSuccessful() && response.body() != null) {
                            // Process the response
                            List<Theater> theaters = new ArrayList<>();
                            List<Long> cinemaIds = response.body().getCinemaIdList();
                            List<String> cinemaNames = response.body().getCinemaNameList();
                            List<String> cinemaAddressNames = response.body().getCinemaAddressList();

                            for (int i = 0; i < cinemaNames.size(); i++) {
                                Long id = cinemaIds.get(i);
                                String name = cinemaNames.get(i);
                                String address = i < cinemaAddressNames.size() ? cinemaAddressNames.get(i) : "Address not available";

                                theaters.add(new Theater(id, name != null && !name.isEmpty() ? name : "Unnamed Cinema",
                                        address, currentCity, R.drawable.theater_image1));
                            }

                            if (theaterAdapter != null) {
                                theaterAdapter.setTheaters(theaters);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ViewCinemaResponse> call, @NonNull Throwable t) {
                        if (isAdded()) {
                            Toast.makeText(requireContext(), "Failed to load theater list. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onTheaterClick(Theater theater) {
        Intent intent = new Intent(requireContext(), TheaterScheduleActivity.class);
        intent.putExtra(IntentKey.CINEMA_ID.name(), theater.getId());
        intent.putExtra(IntentKey.THEATER_NAME.name(), theater.getName());
        intent.putExtra(IntentKey.THEATER_ADDRESS.name(), theater.getAddress());
        startActivity(intent);
    }
}
