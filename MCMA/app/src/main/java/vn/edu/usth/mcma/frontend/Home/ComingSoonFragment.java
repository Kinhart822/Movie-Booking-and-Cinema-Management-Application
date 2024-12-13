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

        List<ComingSoon_Item> items = new ArrayList<ComingSoon_Item>();
        items.add(new ComingSoon_Item("Olivia Adams", "Horror", "135 minutes","T16",R.drawable.movie12));
        items.add(new ComingSoon_Item("Liam Johnson", "Action","120 minutes","T18" ,R.drawable.movie6));
        items.add(new ComingSoon_Item("Noah Brown", "Horror","90 minutes","P" ,R.drawable.movie8));

        ComingSoon_Adapter adapter = new ComingSoon_Adapter(requireContext(), items, new FilmViewInterface() {
            @Override
            public void onFilmSelected(int position) {
                ComingSoon_Item selectedFilm = items.get(position);
                Toast.makeText(requireContext(), "Selected Film: " + selectedFilm.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBookingClicked(int position) {

            }
        });
        recyclerView.setAdapter(adapter);

        return v;
    }
}
