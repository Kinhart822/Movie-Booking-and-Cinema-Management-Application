package vn.edu.usth.mcma.frontend.components.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import vn.edu.usth.mcma.frontend.dto.Response.BookingProcess.Genre;
import vn.edu.usth.mcma.frontend.dto.Response.ComingSoonResponse;
import vn.edu.usth.mcma.frontend.dto.Response.Performer;
import vn.edu.usth.mcma.frontend.network.apis.ComingSoonMovieAPI;
import vn.edu.usth.mcma.frontend.network.RetrofitService;
import vn.edu.usth.mcma.frontend.constants.IntentKey;

public class ComingSoonFragment extends Fragment {
    private List<ComingSoonResponse> comingSoonResponseList = new ArrayList<>();
    private ComingSoon_Adapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_coming_soon, container, false);

        recyclerView = v.findViewById(R.id.recyclerview_coming_soon);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        adapter = new ComingSoon_Adapter(requireContext(), comingSoonResponseList, new FilmViewInterface() {
            @Override
            public void onFilmSelected(int position) {
                openMovieDetailsActivity(position);
            }

            @Override
            public void onBookingClicked(int position) {
            }
        });
        recyclerView.setAdapter(adapter);

        fetchComingSoonMovies();
        return v;
    }

    private void fetchComingSoonMovies() {
        RetrofitService retrofitService = new RetrofitService(requireContext());
        ComingSoonMovieAPI comingSoonMovieAPI = retrofitService.getRetrofit().create(ComingSoonMovieAPI.class);

        comingSoonMovieAPI.getAvailableComingSoonMovies().enqueue(new Callback<List<ComingSoonResponse>>() {
            @Override
            public void onResponse(Call<List<ComingSoonResponse>> call, Response<List<ComingSoonResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateMovieList(response.body());
                } else {
                    Log.e("ComingSoonFragment", "Error: " + response.message());
                    Toast.makeText(requireActivity(), "No movies to show", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ComingSoonResponse>> call, Throwable t) {
                Log.e("ComingSoonFragment", "API Call Failed: " + t.getMessage(), t);
                Toast.makeText(requireActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateMovieList(List<ComingSoonResponse> newMovies) {
        comingSoonResponseList.clear();
        comingSoonResponseList.addAll(newMovies);
        adapter.notifyDataSetChanged();
    }

    private void openMovieDetailsActivity(int position) {
        ComingSoonResponse selectedFilm = comingSoonResponseList.get(position);
        Log.d("ComingSoonFragment", "Launching OnlyDetailsActivity with film: " + selectedFilm.getName());

        Intent intent = new Intent(requireContext(), OnlyDetailsActivity.class);
        intent.putExtra(IntentKey.MOVIE_NAME.name(), selectedFilm.getName());
        intent.putExtra(IntentKey.MOVIE_GENRES.name(), new ArrayList<>(selectedFilm.getGenres().stream().map(Genre::getName).collect(Collectors.toList())));
        intent.putExtra(IntentKey.MOVIE_LENGTH.name(), selectedFilm.getLength());
        intent.putExtra(IntentKey.MOVIE_DESCRIPTION.name(), selectedFilm.getDescription());
        intent.putExtra(IntentKey.PUBLISHED_DATE.name(), selectedFilm.getPublishDate());
        intent.putExtra(IntentKey.IMAGE_URL.name(), selectedFilm.getImageUrl());
        intent.putExtra(IntentKey.BACKGROUND_IMAGE_URL.name(), selectedFilm.getBackgroundImageUrl());
        intent.putExtra(IntentKey.TRAILER.name(), selectedFilm.getTrailerUrl());
        intent.putExtra(IntentKey.MOVIE_RATING.name(), selectedFilm.getRating().getName());
        intent.putExtra(IntentKey.MOVIE_PERFORMER_NAME.name(), new ArrayList<>(selectedFilm.getPerformers().stream().map(Performer::getName).collect(Collectors.toList())));
        intent.putStringArrayListExtra(IntentKey.MOVIE_PERFORMER_TYPE.name(), new ArrayList<>(selectedFilm.getPerformers().stream().map(Performer::getType).collect(Collectors.toList())));

        startActivity(intent);
    }
}
