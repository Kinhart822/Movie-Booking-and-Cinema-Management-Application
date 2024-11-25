package vn.edu.usth.mcma.frontend.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.MainActivity;

public class HomeFragment extends Fragment implements FilmViewInterface {

    private ViewFlipper v_flipper;
    private List<ComingSoon_Item> nowShowingFilms;
    private List<ComingSoon_Item> comingSoonFilms;
    private List<ComingSoon_Item> filteredFilms = new ArrayList<>();
    private ComingSoon_Adapter adapter;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private DrawerLayout mDrawerLayout;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton mImageButton = v.findViewById(R.id.menu_button);
        mDrawerLayout = v.findViewById(R.id.home_fragment);
        tabLayout = v.findViewById(R.id.type_tablayout);
        viewPager = v.findViewById(R.id.type_viewPager2);

        mImageButton.setOnClickListener(view -> {
            if (mDrawerLayout != null && !mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        LinearLayout to_home_fragment = v.findViewById(R.id.home_side_navigation);
        to_home_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof MainActivity){
                    ((MainActivity) getActivity()).close_to_home_page();
                }
            }
        });

        LinearLayout to_showtimes_fragment = v.findViewById(R.id.showtimes_side_navigation);
        to_showtimes_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof MainActivity){
                    ((MainActivity) getActivity()).close_to_showtimes_page();
                }
            }
        });

        LinearLayout to_store_fragment = v.findViewById(R.id.store_side_navigation);
        to_store_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof MainActivity){
                    ((MainActivity) getActivity()).close_to_store_page();
                }
            }
        });

        LinearLayout to_personal_fragment = v.findViewById(R.id.personal_side_navigation);
        to_personal_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof MainActivity){
                    ((MainActivity) getActivity()).close_to_personal_page();
                }
            }
        });

        LinearLayout to_feedback_fragment = v.findViewById(R.id.feedback_side_navigation);
        to_feedback_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof MainActivity){
                    ((MainActivity) getActivity()).close_to_feedback_page();
                }
            }
        });

        int images[] = {R.drawable.movie9, R.drawable.movie3, R.drawable.movie4};
        v_flipper = v.findViewById(R.id.view_flipper);

        if (v_flipper != null && v_flipper.getChildCount() == 0) {
            for (int image : images) {
                flipperImages(image);
            }
        }

        // Setup TabLayout and ViewPager2
        setupViewPagerAndTabs();

        ImageButton notication_buttonn = v.findViewById(R.id.notification_button);
        notication_buttonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Notification.Notification_Activity.class );
                startActivity(i);
            }
        });

        LinearLayout to_search_activity = v.findViewById(R.id.search_bar);
        to_search_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Search.Search_Activity.class );
                startActivity(i);
            }
        });

        return v;
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

    private void flipperImages(int image) {
        if (getContext() == null || v_flipper == null)
            return;

        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(3000);
        v_flipper.setAutoStart(true);

        v_flipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }

    @Override
    public void onItemClick(int position) {
        if (position < filteredFilms.size()) {
            ComingSoon_Item selectedFilm = filteredFilms.get(position);
            Toast.makeText(getContext(), "Selected Film: " + selectedFilm.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
