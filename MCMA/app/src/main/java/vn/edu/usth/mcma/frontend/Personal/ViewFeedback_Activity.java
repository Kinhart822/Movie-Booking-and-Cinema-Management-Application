package vn.edu.usth.mcma.frontend.Personal;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;

public class ViewFeedback_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Feedback_Adapter adapter;
    private List<Feedback_Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);

        recyclerView =  findViewById(R.id.recyclerview_feedback);
        items = new ArrayList<>();

        items.add(new Feedback_Item("Insidious", "4 stars", "Good"));
        items.add(new Feedback_Item("Insidious", "4 stars", "Good"));
        items.add(new Feedback_Item("Insidious", "4 stars", "Good"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Feedback_Adapter(this, items));

        ImageButton backButton = findViewById(R.id.feedback_back_button);
        backButton.setOnClickListener(view -> {
            onBackPressed();
        });
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
