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
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.Response.BookingProcess.Genre;
import vn.edu.usth.mcma.frontend.dto.Response.NowShowingResponse;
import vn.edu.usth.mcma.frontend.dto.Response.Performer;
import vn.edu.usth.mcma.frontend.dto.Response.Review;
import vn.edu.usth.mcma.frontend.component.Showtimes.UI.MovieBookingActivity;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class NowShowingFragment extends Fragment {
    private final List<NowShowingResponse> nowShowingResponseList = new ArrayList<>();
    private NowShowing_Adapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_now_showing, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.recyclerview_now_showing);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        adapter = new NowShowing_Adapter(requireContext(), nowShowingResponseList, new FilmViewInterface() {
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

        // Fetch movies from API
        fetchNowShowingMovies();

        return v;
    }

    private void fetchNowShowingMovies() {
        ApiService
                .getMovieApi(requireContext())
                .getAvailableNowShowingMovies()
                .enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<NowShowingResponse>> call, @NonNull Response<List<NowShowingResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("NowShowingFragment", "Movies received: " + response.body().size());
                    updateMovieList(response.body());
                } else {
                    Log.e("NowShowingFragment", "Error: " + response.message());
                    Toast.makeText(requireActivity(), "No movies to show", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<NowShowingResponse>> call, @NonNull Throwable t) {
                Log.e("NowShowingFragment", "API Call Failed: " + t.getMessage(), t);
                Toast.makeText(requireActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateMovieList(List<NowShowingResponse> newMovies) {
        nowShowingResponseList.clear();
        nowShowingResponseList.addAll(newMovies);
        adapter.notifyDataSetChanged();
    }

    private void openMovieDetailsActivity(int position) {
        NowShowingResponse selectedFilm = nowShowingResponseList.get(position);
        Log.d("NowShowingFragment", "Launching OnlyDetailsActivity with film: " + selectedFilm.getName());
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

        intent.putExtra(IntentKey.MOVIE_COMMENT.name(), new ArrayList<>(selectedFilm.getReviews().stream().map(Review::getUserComment).collect(Collectors.toList())));
        intent.putExtra(IntentKey.AVERAGE_STAR.name(), selectedFilm.getReviews().stream().mapToInt(Review::getUserVote).average().orElse(0.0));

        startActivity(intent);
    }

    private void openMovieBookingActivity(int position) {

        NowShowingResponse selectedFilm = nowShowingResponseList.get(position);
        Intent intent = new Intent(requireContext(), MovieBookingActivity.class);
        intent.putExtra(IntentKey.MOVIE_TITLE.name(), selectedFilm.getName());
        intent.putExtra(IntentKey.MOVIE_ID.name(), selectedFilm.getId());
        startActivity(intent);
    }
}

