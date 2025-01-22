package vn.edu.usth.mcma.frontend.component.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import vn.edu.usth.mcma.frontend.dto.movie.MovieDetailShort;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.UI.MovieBookingActivity;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class NowShowingFragment extends Fragment {
    private final List<MovieDetailShort> nowShowingList = new ArrayList<>();
    private NowShowing_Adapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_now_showing, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.recyclerview_now_showing);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        adapter = new NowShowing_Adapter(requireContext(), nowShowingList, new FilmViewInterface() {
            @Override
            public void onFilmSelected(int position) {
                openMovieDetailsActivity(position);
            }

            @Override
            public void onBookingClicked(int position) {
                openMovieBookingActivity(position);

            }
        });
        recyclerView.setAdapter(adapter);

        findAllNowShowing();

        return v;
    }

    private void findAllNowShowing() {
        ApiService
                .getMovieApi(requireContext())
                .findAllNowShowing()
                .enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<MovieDetailShort>> call, @NonNull Response<List<MovieDetailShort>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("NowShowingFragment", "Movies received: " + response.body().size());
                    updateNowShowingRecyclerView(response.body());
                } else {
                    Log.e("NowShowingFragment", "Error: " + response.message());
                    Toast.makeText(requireActivity(), "No movies to show", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MovieDetailShort>> call, @NonNull Throwable t) {
                Log.e("NowShowingFragment", "API Call Failed: " + t.getMessage(), t);
                Toast.makeText(requireActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateNowShowingRecyclerView(List<MovieDetailShort> movies) {
        nowShowingList.clear();
        nowShowingList.addAll(movies);
        adapter.notifyDataSetChanged();
    }

    private void openMovieDetailsActivity(int position) {
        MovieDetailShort selectedFilm = nowShowingList.get(position);
        Log.d("NowShowingFragment", "Launching MovieDetailsActivity with film: " + selectedFilm.getName());
        Intent intent = new Intent(requireContext(), MovieDetailActivity.class);
        intent.putExtra(IntentKey.MOVIE_ID.name(), selectedFilm.getId());
        startActivity(intent);
    }

    private void openMovieBookingActivity(int position) {

        MovieDetailShort selectedFilm = nowShowingList.get(position);
        Intent intent = new Intent(requireContext(), MovieBookingActivity.class);
        intent.putExtra(IntentKey.MOVIE_TITLE.name(), selectedFilm.getName());
        intent.putExtra(IntentKey.MOVIE_ID.name(), selectedFilm.getId());
        startActivity(intent);
    }
}

