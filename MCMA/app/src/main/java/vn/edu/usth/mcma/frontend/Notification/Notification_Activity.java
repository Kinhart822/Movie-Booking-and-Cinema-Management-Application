package vn.edu.usth.mcma.frontend.Notification;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;

public class Notification_Activity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<NotificationItem> items;
    private List<NotificationItem> filteredItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.recyclerviewnotification);

        items = new ArrayList<>();

        items.add(new NotificationItem("Hoang Anh just became a young actress to appear in Titanic as cameo", "18:20"));
        items.add(new NotificationItem("Netflix", "15:20"));
        items.add(new NotificationItem("Viet Anh has received MCMA award", "14:20"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NotificationAdapter(this, items));

        ImageButton backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
