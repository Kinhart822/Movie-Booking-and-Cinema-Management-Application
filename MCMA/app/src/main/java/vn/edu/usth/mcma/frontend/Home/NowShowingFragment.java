package vn.edu.usth.mcma.frontend.Home;

import android.content.Intent;
import android.os.Bundle;
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
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.NowShowingResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.NowShowingMovieAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;
import vn.edu.usth.mcma.frontend.Showtimes.Models.MovieDetails;
import vn.edu.usth.mcma.frontend.Showtimes.UI.MovieBookingActivity;
import vn.edu.usth.mcma.frontend.Showtimes.UI.MovieDetailsActivity;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.MovieDataProvider;

public class NowShowingFragment extends Fragment {
    private List<NowShowing_Item> nowShowingResponseList;
    private NowShowing_Adapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_now_showing, container, false);

        // Initialize the list
        nowShowingResponseList = new ArrayList<>();
        RecyclerView recyclerView = v.findViewById(R.id.recyclerview_now_showing);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NowShowing_Adapter(requireContext(), nowShowingResponseList, new FilmViewInterface() {
            @Override
            public void onFilmSelected(int position) {
                NowShowing_Item  selectedFilm = nowShowingResponseList.get(position);
                Intent intent = new Intent(requireContext(), OnlyDetailsActivity.class);
                intent.putExtra("MOVIE_TITLE", selectedFilm.getMovieName());
                startActivity(intent);
            }

            @Override
            public void onBookingClicked(int position) {
                NowShowing_Item  selectedFilm = nowShowingResponseList.get(position);
                Intent intent = new Intent(requireContext(), MovieBookingActivity.class);
                intent.putExtra("MOVIE_TITLE", selectedFilm.getMovieName());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

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
                    List<NowShowing_Item> items = new ArrayList<>();
                    for (NowShowingResponse movie : response.body()) {
                        // Map API response to UI model
                        items.add(new NowShowing_Item(
                                movie.getMovieId(),
                                movie.getMovieName(),
                                movie.getMovieLength(),
                                movie.getPublishedDate(),
                                movie.getImageUrl(),
                                movie.getMovieGenreNameList(),
                                movie.getMovieRatingDetailNameList()
                        ));
                    }

                    // Update adapter data
                    adapter.updateData(items);
                } else {
                    Toast.makeText(requireActivity(), "No movies to show", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NowShowingResponse>> call, Throwable t) {
                Toast.makeText(requireActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}


