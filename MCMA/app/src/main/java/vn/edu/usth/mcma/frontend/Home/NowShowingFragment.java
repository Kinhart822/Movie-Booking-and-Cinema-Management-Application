package vn.edu.usth.mcma.frontend.Home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Showtimes.Models.MovieDetails;
import vn.edu.usth.mcma.frontend.Showtimes.UI.MovieBookingActivity;
import vn.edu.usth.mcma.frontend.Showtimes.UI.MovieDetailsActivity;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.MovieDataProvider;

public class NowShowingFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_now_showing, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.recyclerview_now_showing);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Get the list of movie details
        List<MovieDetails> movieDetailsList = MovieDataProvider.getAllMovieDetails();

        // Create NowShowing_Item list from MovieDetails
        List<NowShowing_Item> items = new ArrayList<>();
        for (MovieDetails movie : movieDetailsList) {
            items.add(new NowShowing_Item(
                    movie.getTitle(),
                    movie.getGenres().get(0), // Take first genre
                    movie.getDuration() + " minutes",
                    movie.getClassification(),
                    movie.getBannerImageResId()
            ));
        }
        NowShowing_Adapter adapter = new NowShowing_Adapter(requireContext(), items, new FilmViewInterface() {
            @Override
            public void onFilmSelected(int position) {
                NowShowing_Item selectedFilm = items.get(position);
                Intent intent = new Intent(requireContext(), OnlyDetailsActivity.class);
                intent.putExtra("MOVIE_TITLE", selectedFilm.getName());
                startActivity(intent);
            }

            @Override
            public void onBookingClicked(int position) {
                NowShowing_Item selectedFilm = items.get(position);
                Intent intent = new Intent(requireContext(), MovieBookingActivity.class);
                intent.putExtra("MOVIE_TITLE", selectedFilm.getName());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        return v;
    }
}


