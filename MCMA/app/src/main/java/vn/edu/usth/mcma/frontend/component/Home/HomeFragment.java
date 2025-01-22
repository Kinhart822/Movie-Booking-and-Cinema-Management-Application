package vn.edu.usth.mcma.frontend.component.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import vn.edu.usth.mcma.frontend.dto.response.HighRatingMovieResponse;
import vn.edu.usth.mcma.frontend.helper.ImageDecoder;
import vn.edu.usth.mcma.frontend.component.Search.Search_Activity;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class HomeFragment extends Fragment {
    private final String TAG = HomeFragment.class.getName();
    private ViewFlipper viewFlipper;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout = view.findViewById(R.id.type_tablayout);
        viewPager2 = view.findViewById(R.id.type_viewPager2);

        // Setup TabLayout and ViewPager2
        setupViewPagerAndTabs();
        LinearLayout to_search_activity = view.findViewById(R.id.search_bar);
        to_search_activity.setOnClickListener(v -> {
            Intent i = new Intent(requireContext(), Search_Activity.class );
            startActivity(i);
        });

        viewFlipper = view.findViewById(R.id.view_flipper);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(requireContext(), android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(requireContext(), android.R.anim.slide_out_right);

        fetchHighRatingMovies();
        return view;
    }

    private void fetchHighRatingMovies() {
        ApiService
                .getMovieApi(requireContext())
                .getHighRatingMovies()
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<HighRatingMovieResponse>> call, @NonNull Response<List<HighRatingMovieResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<HighRatingMovieResponse> movies = response.body();
                            if (viewFlipper != null && viewFlipper.getChildCount() == 0) {
                                movies.forEach(m -> addFlipperChild(m));
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
    private void addFlipperChild(HighRatingMovieResponse movie) {
        if (viewFlipper == null) return;
        ImageView poster = new ImageView(requireContext());
        poster.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                8));
        TextView name = new TextView(requireContext());
        name.setText(movie.getName());
        name.setGravity(Gravity.CENTER);
        name.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1));
        TextView avgVote = new TextView(requireContext());
        avgVote.setText(String.format("%.1f ⭐", movie.getAvgVote()));
        avgVote.setGravity(Gravity.CENTER);
        avgVote.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1));
        LinearLayout description = new LinearLayout(requireContext());
        description.setOrientation(LinearLayout.VERTICAL);
        description.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1));
        description.addView(name);
        description.addView(avgVote);
        description.setGravity(Gravity.CENTER);
        LinearLayout child = new LinearLayout(requireContext());
        child.setOrientation(LinearLayout.VERTICAL);
        child.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        child.addView(poster);
        child.addView(description);

        Glide.with(requireContext())
                .load(ImageDecoder.decode(movie.getImageBase64()))
                .placeholder(R.drawable.placeholder1080x1920)
                .into(poster);
        viewFlipper.addView(child);
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
