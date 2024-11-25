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

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.MainActivity;

public class FeedbackFragment extends Fragment {
    private EditText editFeedback;
    private Button buttonSubmit;
    private RatingBar ratingBar;
    private TextView ratingScale;
    private DrawerLayout mDrawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_feedback, container, false);

        ImageButton mImageView = v.findViewById(R.id.menu_button);

        mDrawerLayout = v.findViewById(R.id.feedback_fragment);

        ratingBar = v.findViewById(R.id.ratingBar);
        ratingScale = v.findViewById(R.id.tvRatingScale);
        editFeedback = v.findViewById(R.id.etComment);
        buttonSubmit = v.findViewById(R.id.btnSubmit);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout != null && !mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        LinearLayout to_home_fragment = v.findViewById(R.id.home_side_navigation);
        to_home_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof MainActivity){
                    ((MainActivity) getActivity()).close_to_home_page();
                }
            }
        });

        LinearLayout to_showtimes_fragment = v.findViewById(R.id.showtimes_side_navigation);
        to_showtimes_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof MainActivity){
                    ((MainActivity) getActivity()).close_to_showtimes_page();
                }
            }
        });

        LinearLayout to_store_fragment = v.findViewById(R.id.store_side_navigation);
        to_store_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof MainActivity){
                    ((MainActivity) getActivity()).close_to_store_page();
                }
            }
        });

        LinearLayout to_feedback_fragment = v.findViewById(R.id.feedback_side_navigation);
        to_feedback_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof MainActivity){
                    ((MainActivity) getActivity()).close_to_feedback_page();
                }
            }
        });

        LinearLayout to_personal_fragment = v.findViewById(R.id.personal_side_navigation);
        to_personal_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof MainActivity){
                    ((MainActivity) getActivity()).close_to_personal_page();
                }
            }
        });

        ImageButton notification_button = v.findViewById(R.id.notification_button);
        notification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Notification.Notification_Activity.class);
                startActivity(i);
            }
        });

        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            String ratingText;
            if (rating <= 1) {
                ratingText = "Terrible";
            } else if (rating <= 2) {
                ratingText = "Bad";
            } else if (rating <= 3) {
                ratingText = "Okay";
            } else if (rating <= 4) {
                ratingText = "Good";
            } else {
                ratingText = "Excellent!";
            }
            ratingScale.setText(ratingText);
        });

        buttonSubmit.setOnClickListener(v1 -> {
            String feedback = editFeedback.getText().toString().trim();
            float rating = ratingBar.getRating();

            if (feedback.isEmpty()) {
                Toast.makeText(getContext(), "Please leave a comment", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Thank you for rating: " + rating + " stars\nFeedback: " + feedback, Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
