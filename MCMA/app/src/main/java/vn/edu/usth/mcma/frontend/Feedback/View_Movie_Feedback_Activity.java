package vn.edu.usth.mcma.frontend.Feedback;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;

public class View_Movie_Feedback_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private View_Movie_Feedback_Adapter adapter;
    private List<View_Movie_Feedback_Item> feedbackItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie_feedback);

        recyclerView = findViewById(R.id.recyclerview_view_movie_feedback);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        feedbackItems = getSampleFeedbacks();
        adapter = new View_Movie_Feedback_Adapter(feedbackItems);
        recyclerView.setAdapter(adapter);

        ImageButton backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private List<View_Movie_Feedback_Item> getSampleFeedbacks() {
        List<View_Movie_Feedback_Item> feedbacks = new ArrayList<>();
        feedbacks.add(new View_Movie_Feedback_Item(4.5f, "Loved the action scenes!"));
        feedbacks.add(new View_Movie_Feedback_Item(3.0f, "Could be better."));
        feedbacks.add(new View_Movie_Feedback_Item(5.0f, "One of the best movies ever!"));
        feedbacks.add(new View_Movie_Feedback_Item(2.5f, "Storyline was weak."));
        return feedbacks;
    }

}
