package vn.edu.usth.mcma.frontend.Personal;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;

public class Movie_Feedback_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Movie_Feedback_Adapter adapter;
    private List<Movie_Feedback_Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_feedback);

        recyclerView = findViewById(R.id.recyclerview_movie_list_feedback);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        items = new ArrayList<>();
        items.add(new Movie_Feedback_Item("Wolverine", "Action", R.drawable.movie4));
        items.add(new Movie_Feedback_Item("IronMan", "Drama", R.drawable.movie13));
        items.add(new Movie_Feedback_Item("Wicked", "Comedy", R.drawable.movie12));

        adapter = new Movie_Feedback_Adapter(this, items);
        recyclerView.setAdapter(adapter);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> onBackPressed());
    }
}
