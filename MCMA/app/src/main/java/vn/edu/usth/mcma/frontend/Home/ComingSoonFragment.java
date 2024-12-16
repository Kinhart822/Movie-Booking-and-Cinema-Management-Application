package vn.edu.usth.mcma.frontend.Home;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Enum.PerformerType;
import vn.edu.usth.mcma.frontend.Showtimes.Models.MovieDetails;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.MovieDataProvider;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.ComingSoonResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.ComingSoonMovieAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;

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
        Log.d("ComingSoonFragment", "Launching OnlyDetailsActivity with film: " + selectedFilm.getMovieName());

        Intent intent = new Intent(requireContext(), OnlyDetailsActivity.class);
        intent.putExtra("MOVIE_NAME", selectedFilm.getMovieName());
        intent.putExtra("MOVIE_GENRES", new ArrayList<>(selectedFilm.getMovieGenreNameList()));
        intent.putExtra("MOVIE_LENGTH", selectedFilm.getMovieLength());
        intent.putExtra("MOVIE_DESCRIPTION", selectedFilm.getDescription());
        intent.putExtra("PUBLISHED_DATE", selectedFilm.getPublishedDate());
        intent.putExtra("IMAGE_URL", selectedFilm.getImageUrl());
        intent.putExtra("BACKGROUND_IMAGE_URL", selectedFilm.getBackgroundImageUrl());
        intent.putExtra("TRAILER", selectedFilm.getTrailer());
        intent.putExtra("MOVIE_RATING", new ArrayList<>(selectedFilm.getMovieRatingDetailNameList()));
        intent.putExtra("MOVIE_PERFORMER_NAME", new ArrayList<>(selectedFilm.getMoviePerformerNameList()));

        List<String> performerTypeStrings = new ArrayList<>();
        for (PerformerType performerType : selectedFilm.getMoviePerformerType()) {
            performerTypeStrings.add(performerType.toString());
        }
        intent.putStringArrayListExtra("MOVIE_PERFORMER_TYPE", new ArrayList<>(performerTypeStrings));

        startActivity(intent);
    }
}
