package vn.edu.usth.mcma.frontend.Home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;

public class HomeFragment extends Fragment {

    private ViewFlipper v_flipper;
    private List<FilmItem> items;
    private FilmAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        int images[] = {R.drawable.movie6, R.drawable.movie3, R.drawable.movie4};

        v_flipper = v.findViewById(R.id.view_flipper);

        for (int image: images) {
            flipperImages(image);
        }

        RecyclerView recyclerView = v.findViewById(R.id.recyclerviewhome);

        items = new ArrayList<>();
        items.add(new FilmItem("Grace Morgan", R.drawable.movie1));
        items.add(new FilmItem("Isabella Lewis", R.drawable.movie3));
        items.add(new FilmItem("Evelyn", R.drawable.movie4));
        items.add(new FilmItem("Jack", R.drawable.movie5));
        items.add(new FilmItem("Tino", R.drawable.movie7));

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new FilmAdapter(requireContext(), items));

        DrawerLayout mDrawerLayout = v.findViewById(R.id.home_fragment);

        ImageButton mImageButton = v.findViewById(R.id.menu_button);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout != null && !mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        // Call flipperImages function to add images to the ViewFlipper
        for (int image : images) {
            flipperImages(image);
        }

        return v;
    }

    public void flipperImages(int image) {

        ImageView imageView = new ImageView(requireContext());
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(5000);
        v_flipper.setAutoStart(true);

        // Animation
        v_flipper.setInAnimation(requireContext(), android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(requireContext(), android.R.anim.slide_out_right);
    }
}
