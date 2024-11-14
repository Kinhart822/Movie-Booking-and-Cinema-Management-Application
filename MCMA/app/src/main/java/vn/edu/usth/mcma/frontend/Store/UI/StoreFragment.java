package vn.edu.usth.mcma.frontend.Store.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;

public class StoreFragment extends Fragment implements TheaterAdapter.OnTheaterClickListener {
    /*private Button theaterButton; */
    private ImageView noDataImage;
    private TextView noDataText;
    private Theater selectedTheater;
    private RecyclerView theaterRecyclerView;
    private FrameLayout noDataContainer;
    private TheaterAdapter adapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        initializeViews(view);
        /* setupTheaterButton(); */
        setupRecyclerView();
        return view;
    }

    @SuppressLint("WrongViewCast")
    private void initializeViews(View view) {
        /*
        theaterButton = view.findViewById(R.id.theater_button);
        noDataImage = view.findViewById(R.id.no_data_image);
        noDataText = view.findViewById(R.id.no_data_text); */

        theaterRecyclerView = view.findViewById(R.id.theater_recycler_view);
        noDataContainer = view.findViewById(R.id.no_data_container);
    }

    /*
    private void setupTheaterButton() {
        theaterButton.setOnClickListener(v -> showTheaterSelectionDialog());
    }

    private void showTheaterSelectionDialog() {
        TheaterSelectionDialog dialog = TheaterSelectionDialog.newInstance();
        dialog.setTheaterSelectedListener(theater -> {
            if (theater != null) {
                selectedTheater = theater;
                theaterButton.setText(theater.getName());
                startComboMenuActivity(theater);
            }
        });
        dialog.show(getChildFragmentManager(), "theater_selection");
    }
    */

    private void setupRecyclerView() {
        adapter = new TheaterAdapter(getTheaterList());
        adapter.setOnTheaterClickListener(this);
        theaterRecyclerView.setAdapter(adapter);
        theaterRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    @Override
    public void onTheaterClick(Theater theater) {
        // Update UI based on selection
        adapter.setCurrentSelection(theater);

        if ("1".equals(theater.getId())) { // "Tất cả các rạp"
            showNoDataView();
        } else {
            hideNoDataView();
            startComboMenuActivity(theater);
        }
    }

    private void showNoDataView() {
        theaterRecyclerView.setVisibility(View.GONE);
        noDataContainer.setVisibility(View.VISIBLE);
    }

    private void hideNoDataView() {
        noDataContainer.setVisibility(View.GONE);
        theaterRecyclerView.setVisibility(View.VISIBLE);
    }


    private void startComboMenuActivity(Theater theater) {
        Intent intent = new Intent(requireActivity(), ComboMenuActivity.class);
        intent.putExtra("theater_id", theater.getId());
        intent.putExtra("theater_name", theater.getName());
        startActivity(intent);
    }

    private List<Theater> getTheaterList() {
        List<Theater> theaters = new ArrayList<>();
        theaters.add(new Theater("1", "Tất cả các rạp"));
        theaters.add(new Theater("2", "BHD Star 3.2"));
        theaters.add(new Theater("3", "BHD Star Phạm Hùng"));
        theaters.add(new Theater("4", "BHD Star Quang Trung"));
        theaters.add(new Theater("5", "BHD Star Thảo Điền"));
        theaters.add(new Theater("6", "BHD Star Lê Văn Việt"));
        theaters.add(new Theater("7", "BHD Star Phạm Ngọc Thạch"));
        theaters.add(new Theater("8", "BHD Star Huế"));
        theaters.add(new Theater("9", "BHD Star Discovery"));
        theaters.add(new Theater("10", "BHD Star The Garden"));
        theaters.add(new Theater("11", "BHD Star Long Khánh"));
        theaters.add(new Theater("12", "BHD Star Phú Mỹ"));
        return theaters;
    }
}
