package vn.edu.usth.mcma.frontend.Showtimes.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Theater;
import vn.edu.usth.mcma.frontend.Showtimes.Models.TheaterType;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.TheaterDataProvider;
import vn.edu.usth.mcma.frontend.Showtimes.Adapters.TheaterAdapter;

public class LaunchtimeFragment extends Fragment implements TheaterAdapter.OnTheaterClickListener {
    private TheaterType currentType = TheaterType.REGULAR;
    private RecyclerView theaterRecyclerView;
    private TheaterAdapter theaterAdapter;
    private Button typeButton;
    private String currentCity = "TPHCM";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_launchtime, container, false);

        // Initialize views and adapters
        setupViews(v);
        setupTypeButton();
        setupCityButtons(v); // Pass 'v' to avoid calling getView() prematurely
        updateTheaterList();

        return v;
    }

    private void setupViews(View v) {
        theaterRecyclerView = v.findViewById(R.id.theater_recycler_view);
        theaterAdapter = new TheaterAdapter(this);
        theaterRecyclerView.setAdapter(theaterAdapter);
        theaterRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        typeButton = v.findViewById(R.id.type_button);
    }

    private void setupTypeButton() {
        if (typeButton != null) {
            typeButton.setText(currentType.getDisplayName());
            typeButton.setOnClickListener(v -> {
                currentType = (currentType == TheaterType.REGULAR) ?
                        TheaterType.FIRST_CLASS : TheaterType.REGULAR;
                typeButton.setText(currentType.getDisplayName());
                updateTheaterList();
            });
        }
    }

    private void setupCityButtons(View v) { // Use 'v' instead of getView()
        LinearLayout cityContainer = v.findViewById(R.id.city_container);
        if (cityContainer != null) {
            cityContainer.removeAllViews();
            List<String> cities = TheaterDataProvider.getCities();

            for (String city : cities) {
                Button cityButton = new Button(requireContext());
                cityButton.setText(city);
                cityButton.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                // Add margin between buttons
                ((LinearLayout.LayoutParams) cityButton.getLayoutParams()).setMargins(8, 0, 8, 0);

                cityButton.setOnClickListener(v1 -> {
                    currentCity = city;
                    updateTheaterList();
                    // Update button states
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
        List<Theater> theaters = TheaterDataProvider.getTheatersForCity(currentCity, currentType);
        if (theaterAdapter != null) {
            theaterAdapter.setTheaters(theaters);
            theaterAdapter.setTheaterType(currentType);
        }
    }

    @Override
    public void onTheaterClick(Theater theater) {
        Intent intent = new Intent(requireContext(), TheaterScheduleActivity.class);
        intent.putExtra("THEATER_NAME", theater.getName());
        intent.putExtra("THEATER_ADDRESS", theater.getAddress());
        intent.putExtra("THEATER_TYPE", currentType.name());
        startActivity(intent);
    }
}
