package vn.edu.usth.mcma.frontend.Home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class HomeFragment extends Fragment implements FilmViewInterface {

    private ViewFlipper v_flipper;
    private List<FilmItem> items;
    private List<FilmItem> filteredItems = new ArrayList<>(); // Initialize filteredItems
    private FilmAdapter adapter;
    private Button buttonnowshowing, buttoncomingsoon;
    private RecyclerView recyclerView;
    private SearchView searchView;

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

        // Run flipperImages method on the UI thread to ensure it’s fully loaded
        v_flipper.post(() -> {
            for (int image : images) {
                flipperImages(image);
            }
        });

        buttonnowshowing.setOnClickListener(v1 -> updateFilmList("nowshowing"));

        buttoncomingsoon.setOnClickListener(v1 -> updateFilmList("comingsoon"));

        // Set up LinearLayoutManager and Adapter for RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        items = new ArrayList<>(); // Initialize the empty film list
        adapter = new FilmAdapter(requireContext(), filteredItems, this); // Set adapter to use filteredItems
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

        return v;
    }

    private void updateFilmList(String type) {
        items.clear(); // Clear the current list

        if (type.equals("nowshowing")) {
            items.add(new FilmItem("Grace Morgan", R.drawable.movie1));
            items.add(new FilmItem("Isabella Lewis", R.drawable.movie3));
            items.add(new FilmItem("Evelyn", R.drawable.movie4));
            items.add(new FilmItem("Jack", R.drawable.movie5));
            items.add(new FilmItem("Tino", R.drawable.movie7));
        } else if (type.equals("comingsoon")) {
            items.add(new FilmItem("Olivia Adams", R.drawable.movie12));
            items.add(new FilmItem("Liam Johnson", R.drawable.movie6));
            items.add(new FilmItem("Noah Brown", R.drawable.movie8));
        }

        filteredItems.clear();
        filteredItems.addAll(items);

        adapter.notifyDataSetChanged(); // Update RecyclerView with the new list
    }

    // Chỗ này cho ảnh tự động chạy ở banner
    public void flipperImages(int image) {
        if (getContext() == null) return; // Check if context is available
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(5000);
        v_flipper.setAutoStart(true);

        v_flipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }

    private void filterList(String text) {
        filteredItems.clear();
        for (FilmItem item : items) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredItems.add(item);
            }
        }

        if (filteredItems.isEmpty()) {
            Toast.makeText(getContext(), "No results found", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged(); // Update adapter to show filtered list
    }

    @Override
    public void onItemClick(int position) {
        if (position < filteredItems.size()) {
            FilmItem selectedFilm = filteredItems.get(position);
            Toast.makeText(getContext(), "Selected Film: " + selectedFilm.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
