package vn.edu.usth.mcma.frontend.component.showtimes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.UI.TheaterScheduleActivity;
import vn.edu.usth.mcma.frontend.dto.cinema.CinemaDetailShort;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models.Theater;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Adapters.TheaterAdapter;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class ShowtimesFragment extends Fragment implements TheaterAdapter.OnTheaterClickListener {
    private static final String TAG = ShowtimesFragment.class.getName();
    private TheaterAdapter theaterAdapter;
    private String cityName;
    private Long cityId;
    private View view;
    private Button selectedCityButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_showtimes, container, false);
        cityId = 1L;//todo
        cityName = "TPHCM";//todo
        RecyclerView theaterRecyclerView = view.findViewById(R.id.theater_recycler_view);
        theaterAdapter = new TheaterAdapter(this);
        theaterRecyclerView.setAdapter(theaterAdapter);
        theaterRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        findAllCinemaByCity();

        return view;
    }

    private void findAllCinemaByCity() {
        ApiService
                .getCinemaApi(requireContext())
                .findAllCinemaByCity(cityId).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<CinemaDetailShort>> call, @NonNull Response<List<CinemaDetailShort>> response) {
                        if (!isAdded() || !response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "findAllCinemaByCity onResponse: fragment not added to activity || code not 200 || body is null");
                            return;
                        }
                        theaterAdapter
                                .setTheaters(response
                                        .body()
                                        .stream()
                                        .map(c -> Theater.builder()
                                                .id(c.getId())
                                                .name(c.getName())
                                                .address(c.getAddress())
                                                .cityName(cityName)//todo
                                                .imageResId(R.drawable.date_button_selector) //todo
                                                .build())
                                        .collect(Collectors.toList()));
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<CinemaDetailShort>> call, @NonNull Throwable t) {
                        if (isAdded()) {
                            Toast.makeText(requireContext(), "Failed to load theater list. Please try again.", Toast.LENGTH_SHORT).show();//todo
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
