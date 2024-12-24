package vn.edu.usth.mcma.frontend.Home;

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
import vn.edu.usth.mcma.frontend.ConnectAPI.Enum.PerformerType;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.NowShowingResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.NowShowingMovieAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;
import vn.edu.usth.mcma.frontend.Showtimes.UI.MovieBookingActivity;

public class NowShowingFragment extends Fragment {
    private List<NowShowingResponse> nowShowingResponseList = new ArrayList<>();
    private NowShowing_Adapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_now_showing, container, false);

        recyclerView = v.findViewById(R.id.recyclerview_now_showing);
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
        RetrofitService retrofitService = new RetrofitService(requireContext());
        NowShowingMovieAPI nowShowingMovieAPI = retrofitService.getRetrofit().create(NowShowingMovieAPI.class);

        nowShowingMovieAPI.getAvailableNowShowingMovies().enqueue(new Callback<List<NowShowingResponse>>() {
            @Override
            public void onResponse(Call<List<NowShowingResponse>> call, Response<List<NowShowingResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Log.d("NowShowingFragment", "Movies received: " + response.body().size());
                    updateMovieList(response.body());
                } else {
                    Log.e("NowShowingFragment", "Error: " + response.message());
                    Toast.makeText(requireActivity(), "No movies to show", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NowShowingResponse>> call, Throwable t) {
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
        Log.d("NowShowingFragment", "Launching OnlyDetailsActivity with film: " + selectedFilm.getMovieName());
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

        intent.putExtra("MOVIE_COMMENT", new ArrayList<>(selectedFilm.getComments()));
        intent.putExtra("AVERAGE_STAR", selectedFilm.getAverageRating());

        startActivity(intent);
    }

    private void openMovieBookingActivity(int position) {

        NowShowingResponse selectedFilm = nowShowingResponseList.get(position);
        Intent intent = new Intent(requireContext(), MovieBookingActivity.class);
        intent.putExtra("MOVIE_TITLE", selectedFilm.getMovieName());
        intent.putExtra("MOVIE_ID", selectedFilm.getMovieId());
        startActivity(intent);
    }
}

