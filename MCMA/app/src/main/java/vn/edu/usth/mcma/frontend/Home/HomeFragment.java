package vn.edu.usth.mcma.frontend.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.HighRatingMovieResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.GetHighRatingMovieAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;

public class HomeFragment extends Fragment {

    private ViewFlipper v_flipper;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        tabLayout = v.findViewById(R.id.type_tablayout);
        viewPager = v.findViewById(R.id.type_viewPager2);

        // Setup TabLayout and ViewPager2
        setupViewPagerAndTabs();

        LinearLayout to_search_activity = v.findViewById(R.id.search_bar);
        to_search_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Search.Search_Activity.class );
                startActivity(i);
            }
        });

        v_flipper = v.findViewById(R.id.view_flipper);

        fetchHighRatingMovies();
        return v;
    }

    private void fetchHighRatingMovies() {
        RetrofitService retrofitService = new RetrofitService(requireContext());
        GetHighRatingMovieAPI getHighRatingMovieAPI = retrofitService.getRetrofit().create(GetHighRatingMovieAPI.class);
        getHighRatingMovieAPI.getHighRatingMovies().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<HighRatingMovieResponse>> call, @NonNull Response<List<HighRatingMovieResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<HighRatingMovieResponse> movies = response.body();

                    if (v_flipper != null && v_flipper.getChildCount() == 0) {
                        for (HighRatingMovieResponse movie : movies) {
                            String imageUrl = movie.getImageUrl(); // Fetch the image URL
                            Log.d("ImageURL", "URL: " + imageUrl); // Debug log
                            if (imageUrl != null && !imageUrl.isEmpty()) {
                                flipperImages(imageUrl);
                            }
                        }
                        v_flipper.setVisibility(View.VISIBLE); // Ensure visibility
                    }
                } else {
                    Log.e("HighRatingMovies", "Failed to fetch data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<HighRatingMovieResponse>> call, Throwable t) {
                Log.e("HighRatingMovies", "API call failed: " + t.getMessage());
            }
        });
    }
    private void flipperImages(String image) {
        if (v_flipper == null || getContext() == null) return;

        ImageView imageView = new ImageView(getContext());
        Glide.with(getContext())
                .load(image)
                .into(imageView);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(3000);
        v_flipper.setAutoStart(true);
        v_flipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }

    private void setupViewPagerAndTabs() {
        FilmPagerAdapter adapter = new FilmPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Now Showing");
                    break;
                case 1:
                    tab.setText("Coming Soon");
                    break;
            }
        }).attach();
    }
}
