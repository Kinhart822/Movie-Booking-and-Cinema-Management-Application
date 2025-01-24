package vn.edu.usth.mcma.frontend.component.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.HomeOld.MovieDetailActivity;
import vn.edu.usth.mcma.frontend.dto.movie.MovieDetailShort;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.UI.MovieBookingActivity;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class NowShowingFragment extends Fragment {
    private static final String TAG = NowShowingFragment.class.getName();
    private static final int inf = 100;
    private ViewPager2 nowShowingViewPager2;
    private List<MovieDetailShort> items;
    private NowShowingAdapter adapter;
    TextView nameTextView, lengthTextView, ratingTextView;
    Button bookTicketsButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_now_showing, container, false);
        nowShowingViewPager2 = v.findViewById(R.id.view_pager_2_now_showing);
        items = new ArrayList<>();
        adapter = new NowShowingAdapter(requireContext(), inf, items, this::navigateToMovieDetailActivity);
        nameTextView = v.findViewById(R.id.text_view_name);
        lengthTextView = v.findViewById(R.id.text_view_length);
        ratingTextView = v.findViewById(R.id.text_view_rating);
        bookTicketsButton = v.findViewById(R.id.button_book_tickets);

        nowShowingViewPager2.setAdapter(adapter);
        nowShowingViewPager2.setOffscreenPageLimit(2);
        nowShowingViewPager2.setPageTransformer((page, position) -> {
            float scaleFactor = Math.max(0.85f, 1 - Math.abs(position));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setTranslationX(-position * page.getWidth() * 1f);
        });
        nowShowingViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (items.isEmpty()) {
                    return;
                }
                MovieDetailShort movie = items.get(position % items.size());
                nameTextView.setText(movie.getName());
                lengthTextView.setText(String.format("%d min", movie.getLength()));
                ratingTextView.setText(movie.getRating());
                bookTicketsButton.setOnClickListener(v -> openMovieBookingActivity(movie));
            }
        });

        findAllNowShowing();
        return v;
    }
    private void findAllNowShowing() {
        ApiService
                .getMovieApi(requireContext())
                .findAllNowShowing()
                .enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<MovieDetailShort>> call, @NonNull Response<List<MovieDetailShort>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.e(TAG, "findAllNowShowing onResponse: code not 200 || body is null");
                    return;
                }
                postFindAllNowShowing(response.body());
            }
            @Override
            public void onFailure(@NonNull Call<List<MovieDetailShort>> call, @NonNull Throwable t) {
                Log.e(TAG, "findAllNowShowing onFailure: " + t);
            }
        });
    }
    private void postFindAllNowShowing(List<MovieDetailShort> items) {
        this.items.clear();
        this.items.addAll(items);
        adapter.notifyDataSetChanged();
        nowShowingViewPager2.setCurrentItem(inf / 2, false);
    }
    private void navigateToMovieDetailActivity(int position) {
        MovieDetailShort selectedFilm = items.get(position % items.size());
        Log.d("NowShowingFragment", "Launching MovieDetailsActivity with film: " + selectedFilm.getName());
        Intent intent = new Intent(requireContext(), MovieDetailActivity.class);
        intent.putExtra(IntentKey.MOVIE_ID.name(), selectedFilm.getId());
        startActivity(intent);
    }
    private void openMovieBookingActivity(MovieDetailShort movie) {
        Intent intent = new Intent(requireContext(), MovieBookingActivity.class);
        intent.putExtra(IntentKey.MOVIE_TITLE.name(), movie.getName());
        intent.putExtra(IntentKey.MOVIE_ID.name(), movie.getId());
        startActivity(intent);
    }
}

