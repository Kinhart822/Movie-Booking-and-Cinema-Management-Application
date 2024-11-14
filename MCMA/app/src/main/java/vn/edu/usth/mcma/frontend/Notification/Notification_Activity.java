package vn.edu.usth.mcma.frontend.Notification;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

        RecyclerView recyclerView = findViewById(R.id.recyclerviewnotification);

        List<NotificationItem> items = new ArrayList<NotificationItem>();

        items.add(new NotificationItem("VTV24 was live", R.drawable.user));
        items.add(new NotificationItem("Netflix posted a new video",  R.drawable.user));
        items.add(new NotificationItem("Vu Duc Duy invite you to like him", R.drawable.user));

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