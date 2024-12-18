package vn.edu.usth.mcma.frontend.Showtimes.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.ViewCinemaResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.ViewCityResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.GetCinemaListAPI;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Theater;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.TheaterDataProvider;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.TheaterAdapter;

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
        TheaterDataProvider.getCities(new Callback<ViewCityResponse>() {
            @Override
            public void onResponse(Call<ViewCityResponse> call, Response<ViewCityResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Integer> cityIds = response.body().getCityIds();
                    List<String> cityNames = response.body().getCityNameList();

                    if (!cityNames.isEmpty()) {
                        setupCityButtons(cityIds, cityNames);
                    } else {
                        Toast.makeText(requireContext(), "No cities available", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ViewCityResponse> call, Throwable t) {
                Log.e("LoadCitiesError", "Error fetching city list", t);
                Toast.makeText(requireContext(), "Failed to load cities. Please try again later.", Toast.LENGTH_SHORT).show();
            }
        });
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
        GetCinemaListAPI apiService = new Retrofit.Builder()
                .baseUrl("http://192.168.1.103:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GetCinemaListAPI.class);

        Call<ViewCinemaResponse> call = apiService.getCinemaListByCity(currentCityId);
        call.enqueue(new Callback<ViewCinemaResponse>() {
            @Override
            public void onResponse(Call<ViewCinemaResponse> call, Response<ViewCinemaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Integer> cinemaIds = response.body().getCinemaIdList();
                    List<String> cinemaNames = response.body().getCinemaNameList();
                    List<String> cinemaAddressNames = response.body().getCinemaAddressList();
                    List<Theater> theaters = new ArrayList<>();

                    for (int i = 0; i < cinemaNames.size(); i++) {
                        int id = cinemaIds.get(i);
                        String name = cinemaNames.get(i);
                        String address = i < cinemaAddressNames.size() ? cinemaAddressNames.get(i) : "Address not available";

                        if (name == null || name.isEmpty()) {
                            theaters.add(new Theater(id, "Unnamed Cinema", "Address not available", currentCity, R.drawable.theater_image1));
                        } else {
                            theaters.add(new Theater(id, name, address, currentCity, R.drawable.theater_image1));
                        }
                    }

                    if (theaterAdapter != null) {
                        theaterAdapter.setTheaters(theaters);
                    }
                }
            }

            @Override
            public void onFailure(Call<ViewCinemaResponse> call, Throwable t) {
                Log.e("TheaterListError", "Error fetching theater list", t);
                Toast.makeText(requireContext(), "Failed to load theater list. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onTheaterClick(Theater theater) {
        Intent intent = new Intent(requireContext(), TheaterScheduleActivity.class);
        intent.putExtra("CINEMA_ID", theater.getId());
        intent.putExtra("THEATER_NAME", theater.getName());
        intent.putExtra("THEATER_ADDRESS", theater.getAddress());
        startActivity(intent);
    }
}
