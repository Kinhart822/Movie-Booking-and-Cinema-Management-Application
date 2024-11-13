package vn.edu.usth.mcma.frontend.Store.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.usth.mcma.R;


import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class StoreFragment extends Fragment {
    private Button theaterButton;
    private ImageView noDataImage;
    private TextView noDataText;
    private Theater selectedTheater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        initializeViews(view);
        setupTheaterButton();
        return view;
    }

    private void setupTheaterButton() {
        theaterButton.setOnClickListener(v -> {
            TheaterSelectionDialog dialog = new TheaterSelectionDialog();
            dialog.setTheaterSelectedListener(theater -> {
                selectedTheater = theater;
                theaterButton.setText(theater.getName());
                startComboMenuActivity(theater);
            });
            dialog.show(getParentFragmentManager(), "theater_selection");
        });
    }

    private void startComboMenuActivity(Theater theater) {
        Intent intent = new Intent(getActivity(), ComboMenuActivity.class);
        intent.putExtra("theater_id", theater.getId());
        intent.putExtra("theater_name", theater.getName());
        startActivity(intent);
    }

}