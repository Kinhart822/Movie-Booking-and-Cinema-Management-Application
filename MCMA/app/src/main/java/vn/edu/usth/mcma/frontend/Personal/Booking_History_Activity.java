package vn.edu.usth.mcma.frontend.Personal;

import android.os.Bundle;
import android.widget.ImageButton;

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

public class Booking_History_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookingHistory_Adapter adapter;
    private List<BookingHistory_Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        recyclerView = findViewById(R.id.recyclerviewbooking_history);
        items = new ArrayList<>();

        items.add(new BookingHistory_Item("18h20","A", "Coca", "5"));
        items.add(new BookingHistory_Item("18h20","A", "Coca", "5"));
        items.add(new BookingHistory_Item("18h20","A", "Coca", "5"));
        items.add(new BookingHistory_Item("18h20","A", "Coca", "5"));
        items.add(new BookingHistory_Item("18h20","A", "Coca", "5"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BookingHistory_Adapter(this, items));

        ImageButton backButton = findViewById(R.id.booking_history_back_button);
        backButton.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}