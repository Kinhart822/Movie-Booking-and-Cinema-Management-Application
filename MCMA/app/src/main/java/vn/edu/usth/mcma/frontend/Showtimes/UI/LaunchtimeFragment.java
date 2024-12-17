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
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.ViewCityResponse;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Theater;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.TheaterDataProvider;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.TheaterAdapter;

public class LaunchtimeFragment extends Fragment implements TheaterAdapter.OnTheaterClickListener {
    private RecyclerView theaterRecyclerView;
    private TheaterAdapter theaterAdapter;
    private String currentCity = "TPHCM";
    private View rootView;
    private Button selectedCityButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_launchtime, container, false);
        rootView = inflater.inflate(R.layout.fragment_launchtime, container, false);

        // Initialize views and adapters
//        setupViews(v);
//        setupCityButtons(v); // Pass 'v' to avoid calling getView() prematurely
        setupViews(rootView);
        loadCitiesFromAPI();
        updateTheaterList();

//        return v;
        return rootView;
    }

    private void setupViews(View v) {
        theaterRecyclerView = v.findViewById(R.id.theater_recycler_view);
        theaterAdapter = new TheaterAdapter(this);
        theaterRecyclerView.setAdapter(theaterAdapter);
        theaterRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void loadCitiesFromAPI() {
        TheaterDataProvider.getCities(new Callback<ViewCityResponse>() {
            @Override
            public void onResponse(Call<ViewCityResponse> call, Response<ViewCityResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> cities = response.body().getCityNameList();

                    if (!cities.isEmpty()) {
                        setupCityButtons(cities);
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

    private void setupCityButtons(List<String> cities) {
        LinearLayout cityContainer = rootView.findViewById(R.id.city_container);
        if (cityContainer != null) {
            cityContainer.removeAllViews();

            for (String city : cities) {
                Button cityButton = new Button(requireContext());
                cityButton.setText(city);
                cityButton.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                // Thêm margin giữa các nút
                ((LinearLayout.LayoutParams) cityButton.getLayoutParams()).setMargins(8, 0, 8, 0);

                cityButton.setOnClickListener(v1 -> {
                    currentCity = city;
                    updateTheaterList();
                    for (int i = 0; i < cityContainer.getChildCount(); i++) {
                        View child = cityContainer.getChildAt(i);
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
        List<Theater> theaters = TheaterDataProvider.getTheatersForCity(currentCity);
        if (theaterAdapter != null) {
            theaterAdapter.setTheaters(theaters);
        }
    }

    @Override
    public void onTheaterClick(Theater theater) {
        Intent intent = new Intent(requireContext(), TheaterScheduleActivity.class);
        intent.putExtra("THEATER_NAME", theater.getName());
        intent.putExtra("THEATER_ADDRESS", theater.getAddress());
        startActivity(intent);
    }
}
