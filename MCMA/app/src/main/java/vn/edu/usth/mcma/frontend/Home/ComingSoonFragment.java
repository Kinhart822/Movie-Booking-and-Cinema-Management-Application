package vn.edu.usth.mcma.frontend.Home;

import android.content.Intent;
import android.os.Bundle;
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
import vn.edu.usth.mcma.frontend.Showtimes.Models.MovieDetails;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.MovieDataProvider;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.ComingSoonResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.ComingSoonMovieAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;

public class ComingSoonFragment extends Fragment {
    private List<ComingSoonResponse> comingSoonResponseList;
    private ComingSoon_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_coming_soon, container, false);

        comingSoonResponseList = new ArrayList<>();
        RecyclerView recyclerView = v.findViewById(R.id.recyclerview_coming_soon);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setHasFixedSize(true);

        adapter = new ComingSoon_Adapter(requireContext(), comingSoonResponseList, new FilmViewInterface() {
            @Override
            public void onFilmSelected(int position) {
                ComingSoonResponse selectedFilm = comingSoonResponseList.get(position);
                Intent intent = new Intent(requireContext(), OnlyDetailsActivity.class);
                intent.putExtra("MOVIE_TITLE", selectedFilm.getMovieName());
                startActivity(intent);
            }

            @Override
            public void onBookingClicked(int position) {
                // Implement nếu cần
            }
        });


        recyclerView.setAdapter(adapter);

        fetchComingSoonMovie();
        return v;
    }

    private void fetchComingSoonMovie() {
        RetrofitService retrofitService = new RetrofitService(requireContext());
        ComingSoonMovieAPI comingSoonMovieAPI = retrofitService.getRetrofit().create(ComingSoonMovieAPI.class);
        comingSoonMovieAPI.getAvailableComingSoonMovies().enqueue(new Callback<List<ComingSoonResponse>>() {
            @Override
            public void onResponse(Call<List<ComingSoonResponse>> call, Response<List<ComingSoonResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    Toast.makeText(requireActivity(), "Here's your feedbacks", Toast.LENGTH_SHORT).show();
                    comingSoonResponseList.clear();
                    comingSoonResponseList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(requireActivity(), "Failed to display coming soon movies", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ComingSoonResponse>> call, Throwable t) {
                Toast.makeText(requireActivity(), "Failed to display coming soon movies", Toast.LENGTH_SHORT).show();
            }
        });
    }
//    List<ComingSoon_Item> items = new ArrayList<ComingSoon_Item>();
//        items.add(new ComingSoon_Item("Olivia Adams", "Horror", "135 minutes","T16",R.drawable.movie12));
//        items.add(new ComingSoon_Item("Liam Johnson", "Action","120 minutes","T18" ,R.drawable.movie6));
//        items.add(new ComingSoon_Item("Noah Brown", "Horror","90 minutes","P" ,R.drawable.movie8));
}
