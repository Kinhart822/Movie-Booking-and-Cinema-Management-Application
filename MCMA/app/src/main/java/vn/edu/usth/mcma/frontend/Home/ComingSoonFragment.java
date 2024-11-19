package vn.edu.usth.mcma.frontend.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;

public class ComingSoonFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_coming_soon, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.recyclerview_coming_soon);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Tạo danh sách phim Coming Soon
        List<FilmItem> items = new ArrayList<FilmItem>();
        items.add(new FilmItem("Olivia Adams", "Horror", R.drawable.movie12));
        items.add(new FilmItem("Liam Johnson", "Action", R.drawable.movie6));
        items.add(new FilmItem("Noah Brown", "Horror", R.drawable.movie8));

        FilmAdapter adapter = new FilmAdapter(requireContext(), items, position -> {
            FilmItem selectedFilm = items.get(position);
            Toast.makeText(requireContext(), "Selected Film: " + selectedFilm.getName(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);

        return v;
    }
}
