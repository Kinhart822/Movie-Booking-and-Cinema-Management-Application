package vn.edu.usth.mcma.frontend.Home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Showtimes.UI.MovieBookingActivity;

public class NowShowingFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_now_showing, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.recyclerview_now_showing);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        List<NowShowing_Item> items = new ArrayList<>();
        items.add(new NowShowing_Item("Grace Morgan", "Horror", "85 minutes", "P", R.drawable.movie1));
        items.add(new NowShowing_Item("Isabella Lewis", "Comedy", "100 minutes", "P", R.drawable.movie3));
        items.add(new NowShowing_Item("Evelyn", "Sci-Fi", "94 minutes", "T13", R.drawable.movie4));
        items.add(new NowShowing_Item("Jack", "Action", "114 minutes", "P", R.drawable.movie5));
        items.add(new NowShowing_Item("Tino", "Horror", "125 minutes", "P", R.drawable.movie7));

        NowShowing_Adapter adapter = new NowShowing_Adapter(requireContext(), items, new FilmViewInterface() {
            @Override
            public void onFilmSelected(int position) {
                NowShowing_Item selectedFilm = items.get(position);
                Toast.makeText(requireContext(), "Selected Film: " + selectedFilm.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBookingClicked(int position) {
                NowShowing_Item selectedFilm = items.get(position);
                Intent intent = new Intent(requireContext(), MovieBookingActivity.class);
                intent.putExtra("MOVIE_TITLE", selectedFilm.getName());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        return v;
    }
}


