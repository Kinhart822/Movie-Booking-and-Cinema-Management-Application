package vn.edu.usth.mcma.frontend.Home;

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

public class NowShowingFragment extends Fragment {
    private List<NowShowingResponse> nowShowingResponseList;
    private NowShowing_Adapter adapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_now_showing, container, false);

        // Initialize the list
        nowShowingResponseList = new ArrayList<>();
        RecyclerView recyclerView = v.findViewById(R.id.recyclerview_now_showing);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NowShowing_Adapter(requireContext(), nowShowingResponseList, position -> {
            NowShowingResponse selectedFilm = nowShowingResponseList.get(position);
            Toast.makeText(requireContext(), "Selected Film: " + selectedFilm.getMovieName(), Toast.LENGTH_SHORT).show();
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
//                    Toast.makeText(requireActivity(), "Here's your feedbacks", Toast.LENGTH_SHORT).show();
                    nowShowingResponseList.clear();
                    nowShowingResponseList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(requireActivity(), "Failed to display now showing movies", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NowShowingResponse>> call, Throwable t) {
                Toast.makeText(requireActivity(), "Failed to display now showing movies", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
