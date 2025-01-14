package vn.edu.usth.mcma.frontend.component.Personal;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.response.BookingResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class Booking_History_Activity extends AppCompatActivity {

    private BookingHistory_Adapter adapter;
//    private List<BookingHistory_Item> items;
    private List<BookingResponse> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        RecyclerView recyclerView = findViewById(R.id.recyclerviewbooking_history);
        items = new ArrayList<>();

        adapter = new BookingHistory_Adapter(this, items);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ImageButton backButton = findViewById(R.id.booking_history_back_button);
        backButton.setOnClickListener(view -> onBackPressed());
        fetchBookingHistory();

//        items.add(new BookingHistory_Item("5","Spider Man - Across The Spider Verse part 2", "07/11", "08/11", "Pending"));
//        items.add(new BookingHistory_Item("5","Spider Man - Across The Spider Verse part 2", "07/11", "08/11", "Pending"));
//        items.add(new BookingHistory_Item("5","Spider Man - Across The Spider Verse part 2", "07/11", "08/11", "Pending"));
//        items.add(new BookingHistory_Item("5","Spider Man - Across The Spider Verse part 2", "07/11", "08/11", "Pending"));
//        items.add(new BookingHistory_Item("5","Spider Man - Across The Spider Verse part 2", "07/11", "08/11", "Pending"));
    }

    private void fetchBookingHistory() {
        ApiService
                .getAccountApi(this)
                .getBookingHistory().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<BookingResponse>> call, @NonNull Response<List<BookingResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            items.clear();
                            items.addAll(response.body());
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(Booking_History_Activity.this, "Failed to load bookings", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BookingResponse>> call, @NonNull Throwable t) {
                        Toast.makeText(Booking_History_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}