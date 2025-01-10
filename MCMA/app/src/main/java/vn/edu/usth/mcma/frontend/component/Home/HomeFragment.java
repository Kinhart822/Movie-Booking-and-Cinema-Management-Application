package vn.edu.usth.mcma.frontend.component.Home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import vn.edu.usth.mcma.frontend.dto.Response.HighRatingMovieResponse;
import vn.edu.usth.mcma.frontend.helper.ImageDecoder;
import vn.edu.usth.mcma.frontend.network.apis.GetHighRatingMovieAPI;
import vn.edu.usth.mcma.frontend.network.RetrofitService;
import vn.edu.usth.mcma.frontend.component.Search.Search_Activity;

public class HomeFragment extends Fragment {

    private ViewFlipper viewFlipper;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout = v.findViewById(R.id.type_tablayout);
        viewPager2 = v.findViewById(R.id.type_viewPager2);

        // Setup TabLayout and ViewPager2
        setupViewPagerAndTabs();
        LinearLayout to_search_activity = v.findViewById(R.id.search_bar);
        to_search_activity.setOnClickListener(view -> {
            Intent i = new Intent(requireContext(), Search_Activity.class );
            startActivity(i);
        });

        viewFlipper = v.findViewById(R.id.view_flipper);

        fetchHighRatingMovies();
        return v;
    }

    private void fetchHighRatingMovies() {
        RetrofitService retrofitService = new RetrofitService(requireContext());
        GetHighRatingMovieAPI apiClient = retrofitService.getRetrofit().create(GetHighRatingMovieAPI.class);
        apiClient.getHighRatingMovies().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<HighRatingMovieResponse>> call, @NonNull Response<List<HighRatingMovieResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<HighRatingMovieResponse> movies = response.body();
                    if (viewFlipper != null && viewFlipper.getChildCount() == 0) {
                        for (HighRatingMovieResponse movie : movies) {
                            String imageBase64 = movie.getImageBase64(); // Fetch the image URL
                            if (imageBase64 != null && !imageBase64.isEmpty()) {
                                ImageDecoder imageDecoder = new ImageDecoder(imageBase64);
                                flipperImages(imageDecoder.getResult());
                            }
                        }
                        viewFlipper.setVisibility(View.VISIBLE); // Ensure visibility
                    }
                } else {
                    Log.e("HighRatingMovies", "Failed to fetch data: " + response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<HighRatingMovieResponse>> call, @NonNull Throwable t) {
                Log.e("HighRatingMovies", "API call failed: " + t.getMessage());
            }
        });
    }
    private void flipperImages(Bitmap bitmap) {
        Context context = getContext();
        if (viewFlipper == null || context == null) return;
        ImageView imageView = new ImageView(context);
        Glide.with(context)
                .load(bitmap)
                .placeholder(R.drawable.placeholder1080x1920)
                .into(imageView);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(context, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(context, android.R.anim.slide_out_right);
    }

    private void setupViewPagerAndTabs() {
        FilmPagerAdapter adapter = new FilmPagerAdapter(this);
        viewPager2.setAdapter(adapter);
        viewPager2.setUserInputEnabled(false);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
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
