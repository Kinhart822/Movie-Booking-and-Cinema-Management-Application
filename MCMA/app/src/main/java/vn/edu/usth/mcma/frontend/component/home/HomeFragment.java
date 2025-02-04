package vn.edu.usth.mcma.frontend.component.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
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
import vn.edu.usth.mcma.frontend.dto.home.Advertisement;
import vn.edu.usth.mcma.frontend.utils.ImageDecoder;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class HomeFragment extends Fragment {
    private static final String TAG = HomeFragment.class.getName();
    private ViewFlipper viewFlipper;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        NestedScrollView scrollView = view.findViewById(R.id.main);
        viewFlipper = view.findViewById(R.id.view_flipper);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager2 = view.findViewById(R.id.view_pager_2);

        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY >= 350) {
                viewFlipper.setAlpha((float) -scrollY / 100 + 4.5f);
                if (viewFlipper.isFlipping()) {
                    viewFlipper.stopFlipping();
                }
                return;
            }
            if (viewFlipper.getAlpha() != 1) {
                viewFlipper.setAlpha(1);
                if (!viewFlipper.isFlipping()) {
                    viewFlipper.startFlipping();
                }
            }
        });
        prepareViewFlipper();
        findAllAdvertisement();
        prepareTabLayoutAndViewPager2();

        return view;
    }
    private void prepareViewFlipper() {
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(requireContext(), android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(requireContext(), android.R.anim.slide_out_right);
    }
    private void findAllAdvertisement() {
        ApiService
                .getMovieApi(requireContext())
                .findAllAdvertisement()
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Advertisement>> call, @NonNull Response<List<Advertisement>> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "findAllAdvertisement onResponse: code not 200 || body is null");
                            return;
                        }
                        List<Advertisement> ads = response.body();
                        if (viewFlipper != null && viewFlipper.getChildCount() == 0) {
                            ads.forEach(m -> addFlipperChild(m));
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<Advertisement>> call, @NonNull Throwable t) {
                        Log.e(TAG, "findAllAdvertisement onFailure: ", t);
                    }
                });
    }
    @SuppressLint("DefaultLocale")
    private void addFlipperChild(Advertisement ad) {
        if (viewFlipper == null) return;
        ImageView banner = new ImageView(requireContext());
        banner.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (ad.getBanner() != null) {
            Glide
                    .with(requireContext())
                    .load(ImageDecoder.decode(ad.getBanner()))
                    .placeholder(R.drawable.placeholder1920x1080)
                    .error(R.drawable.placeholder1920x1080)
                    .into(banner);
        }
        viewFlipper.addView(banner);
    }
    private void prepareTabLayoutAndViewPager2() {
        viewPager2.setAdapter(new HomeAdapter(this));
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
