package vn.edu.usth.mcma.frontend.component.HomeOld;

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
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.GenreResponse;
import vn.edu.usth.mcma.frontend.dto.response.ComingSoonResponse;
import vn.edu.usth.mcma.frontend.dto.response.PerformerResponse;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class ComingSoonFragment extends Fragment {
    private final List<ComingSoonResponse> comingSoonResponseList = new ArrayList<>();
    private ComingSoon_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_coming_soon, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.recyclerview_coming_soon);
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
        ApiService
                .getMovieApi(requireContext())
                .getAvailableComingSoonMovies().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ComingSoonResponse>> call, @NonNull Response<List<ComingSoonResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            updateMovieList(response.body());
                        } else {
                            Log.e("ComingSoonFragment", "Error: " + response.message());
                            Toast.makeText(requireActivity(), "No movies to show", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ComingSoonResponse>> call, @NonNull Throwable t) {
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
        Log.d("ComingSoonFragment", "Launching MovieDetailsActivity with film: " + selectedFilm.getName());

        Intent intent = new Intent(requireContext(), MovieDetailActivity.class);
        intent.putExtra(IntentKey.MOVIE_NAME.name(), selectedFilm.getName());
        intent.putExtra(IntentKey.MOVIE_GENRES.name(), new ArrayList<>(selectedFilm.getGenreResponses().stream().map(GenreResponse::getName).collect(Collectors.toList())));
        intent.putExtra(IntentKey.MOVIE_LENGTH.name(), selectedFilm.getLength());
        intent.putExtra(IntentKey.MOVIE_DESCRIPTION.name(), selectedFilm.getDescription());
        intent.putExtra(IntentKey.PUBLISHED_DATE.name(), selectedFilm.getPublishDate());
        intent.putExtra(IntentKey.IMAGE_URL.name(), selectedFilm.getImageUrl());
        intent.putExtra(IntentKey.BACKGROUND_IMAGE_URL.name(), selectedFilm.getBackgroundImageUrl());
        intent.putExtra(IntentKey.TRAILER.name(), selectedFilm.getTrailerUrl());
        intent.putExtra(IntentKey.MOVIE_RATING.name(), selectedFilm.getRatingResponse().getName());
        intent.putExtra(IntentKey.MOVIE_PERFORMER_NAME.name(), new ArrayList<>(selectedFilm.getPerformerResponses().stream().map(PerformerResponse::getName).collect(Collectors.toList())));
        intent.putStringArrayListExtra(IntentKey.MOVIE_PERFORMER_TYPE.name(), new ArrayList<>(selectedFilm.getPerformerResponses().stream().map(performerResponse -> performerResponse.getTypeId().toString()).collect(Collectors.toList())));

        startActivity(intent);
    }
}
