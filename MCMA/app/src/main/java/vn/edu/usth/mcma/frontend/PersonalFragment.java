package vn.edu.usth.mcma.frontend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import vn.edu.usth.mcma.R;

public class PersonalFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_personal, container, false);

        // Find the ImageButton by its ID (assuming you have an ImageButton in your layout)
        ImageButton closeButton = v.findViewById(R.id.close_button);

        // Set an OnClickListener for the button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof MainActivity){
                    ((MainActivity) getActivity()).close_to_home_page();
                }
            }
        });

        return v;
    }
}
