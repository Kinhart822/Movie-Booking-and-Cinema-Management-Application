package vn.edu.usth.mcma.frontend.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.MainActivity;
import vn.edu.usth.mcma.frontend.Notification.Notification_Activity;

public class HomeFragment extends Fragment implements FilmViewInterface {

    private ViewFlipper v_flipper;
    private List<FilmItem> items;
    private List<FilmItem> filteredItems = new ArrayList<>();
    private FilmAdapter adapter;
    private Button buttonnowshowing, buttoncomingsoon;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private DrawerLayout mDrawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        searchView = v.findViewById(R.id.searchView);
        searchView.clearFocus();

        buttoncomingsoon = v.findViewById(R.id.button_comingsoon);
        buttonnowshowing = v.findViewById(R.id.button_nowshowing);

        recyclerView = v.findViewById(R.id.recyclerviewhome);

        int images[] = {R.drawable.movie9, R.drawable.movie3, R.drawable.movie4};
        v_flipper = v.findViewById(R.id.view_flipper);

        if (v_flipper != null && v_flipper.getChildCount() == 0) {
            for (int image : images) {
                flipperImages(image);
            }
        }

        buttonnowshowing.setOnClickListener(v1 -> updateFilmList("nowshowing"));
        buttoncomingsoon.setOnClickListener(v1 -> updateFilmList("comingsoon"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        items = new ArrayList<>();
        adapter = new FilmAdapter(requireContext(), filteredItems, this);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        DrawerLayout mDrawerLayout = v.findViewById(R.id.home_fragment);

        ImageButton mImageButton = v.findViewById(R.id.menu_button);
        mImageButton.setOnClickListener(view -> {
            if (mDrawerLayout != null && !mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        ImageButton notication_buttonn = v.findViewById(R.id.notification_button);
        notication_buttonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Notification.Notification_Activity.class );
                startActivity(i);
            }
        });

        LinearLayout to_home_page = v.findViewById(R.id.home_side_navigation);
        to_home_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.mcma.frontend.MainActivity.class);
                startActivity(i);;
            }
        });

        LinearLayout to_showtimes_page = v.findViewById(R.id.showtimes_side_navigation);
        to_showtimes_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof MainActivity){
                    ((MainActivity) getActivity()).close_to_showtimes_page();
                }
            }
        });


        // Hiện list film Now Showing mặc định khi khởi chạy
        updateFilmList("nowshowing");

        return v;
    }

    private void updateFilmList(String type) {
        items.clear();

        if (type.equals("nowshowing")) {
            items.add(new FilmItem("Grace Morgan","(Horror)" ,R.drawable.movie1));
            items.add(new FilmItem("Isabella Lewis","(Comedy)", R.drawable.movie3));
            items.add(new FilmItem("Evelyn", "(Sci-Fi)" ,R.drawable.movie4));
            items.add(new FilmItem("Jack", "Horror" ,R.drawable.movie5));
            items.add(new FilmItem("Tino", "Horror" ,R.drawable.movie7));
        } else if (type.equals("comingsoon")) {
            items.add(new FilmItem("Olivia Adams","Horror", R.drawable.movie12));
            items.add(new FilmItem("Liam Johnson","Horror", R.drawable.movie6));
            items.add(new FilmItem("Noah Brown","Horror", R.drawable.movie8));
        }

        filteredItems.clear();
        filteredItems.addAll(items);

        adapter.notifyDataSetChanged();
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

    private void filterList(String text) {
        filteredItems.clear();
        for (FilmItem item : items) {
            if (item.getName().toLowerCase().contains(text.toLowerCase()) ||
                    item.getCategory().toLowerCase().contains(text.toLowerCase())) {
                filteredItems.add(item);
            }
        }

        if (filteredItems.isEmpty()) {
            Toast.makeText(getContext(), "No results found", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        if (position < filteredItems.size()) {
            FilmItem selectedFilm = filteredItems.get(position);
            Toast.makeText(getContext(), "Selected Film: " + selectedFilm.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
