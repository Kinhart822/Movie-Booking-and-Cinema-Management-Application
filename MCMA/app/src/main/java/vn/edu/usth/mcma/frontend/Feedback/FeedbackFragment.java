package vn.edu.usth.mcma.frontend.Feedback;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.MainActivity;


public class FeedbackFragment extends Fragment {
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private RatingMovie_Adapter adapter;
    private List<RatingMovie_Item> items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_feedback, container, false);

        recyclerView = v.findViewById(R.id.recyclerview_feedbackmovie);
        items = new ArrayList<>();

        items.add(new RatingMovie_Item("Wolverine", "Action", R.drawable.movie4));
        items.add(new RatingMovie_Item("IronMan", "Drama", R.drawable.movie13));
        items.add(new RatingMovie_Item("Wicked", "Comedy", R.drawable.movie12));

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new RatingMovie_Adapter(requireContext(), items));

        return v;
    }
}
