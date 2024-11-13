package vn.edu.usth.mcma.frontend.Home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private FilmAdapter adapter;
    private Button buttonnowshowing, buttoncomingsoon;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        buttoncomingsoon = v.findViewById(R.id.button_comingsoon);
        buttonnowshowing = v.findViewById(R.id.button_nowshowing);
        recyclerView = v.findViewById(R.id.recyclerviewhome);

        int images[] = {R.drawable.movie9 , R.drawable.movie3, R.drawable.movie4};
        v_flipper = v.findViewById(R.id.view_flipper);

        for (int image : images) {
            flipperImages(image);
        }

        buttonnowshowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFilmList("nowshowing");
            }
        });

        buttoncomingsoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFilmList("comingsoon");
            }
        });

        // Thiết lập LinearLayoutManager và Adapter cho RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        items = new ArrayList<>(); // Khởi tạo danh sách phim trống ban đầu
        adapter = new FilmAdapter(requireContext(), items, this);
        recyclerView.setAdapter(adapter);

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

        return v;
    }

    private void updateFilmList(String type) {
        items.clear(); // Xóa dữ liệu hiện có trong danh sách

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

        adapter.notifyDataSetChanged(); // Cập nhật RecyclerView với danh sách mới
    }

    public void flipperImages(int image) {
        ImageView imageView = new ImageView(requireContext());
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(5000);
        v_flipper.setAutoStart(true);

        v_flipper.setInAnimation(requireContext(), android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(requireContext(), android.R.anim.slide_out_right);
    }

    @Override
    public void onItemClick(int position) {
        FilmItem selectedFilm = items.get(position);
        Toast.makeText(requireContext(), "Selected Film: " + selectedFilm.getName(), Toast.LENGTH_SHORT).show();
    }
}
