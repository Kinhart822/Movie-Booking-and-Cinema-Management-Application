package vn.edu.usth.mcma.frontend.Personal;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;

public class Cancel_Booking_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Cancel_Booking_Adapter adapter;
    private List<Cancel_Booking_Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_booking);

        recyclerView = findViewById(R.id.recyclerview_cancel_booking);

        items = new ArrayList<>();
        items.add(new Cancel_Booking_Item("Wolverine", "Action", R.drawable.movie4));
        items.add(new Cancel_Booking_Item("IronMan", "Drama", R.drawable.movie13));
        items.add(new Cancel_Booking_Item("Wicked", "Comedy", R.drawable.movie12));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Cancel_Booking_Adapter(this, items);
        recyclerView.setAdapter(adapter);

        ImageButton backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}
