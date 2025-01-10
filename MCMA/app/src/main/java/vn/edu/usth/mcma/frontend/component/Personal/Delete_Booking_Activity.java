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
import vn.edu.usth.mcma.frontend.dto.Response.BookingResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class Delete_Booking_Activity extends AppCompatActivity {
    private Delete_Booking_Adapter adapter;
    private List<Delete_Booking_Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_booking);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_delete_booking);

        items = new ArrayList<>();
//        items.add(new Delete_Booking_Item("Wolverine", "Action", R.drawable.movie4));
//        items.add(new Delete_Booking_Item("IronMan", "Drama", R.drawable.movie13));
//        items.add(new Delete_Booking_Item("Wicked", "Comedy", R.drawable.movie12));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Delete_Booking_Adapter(this, items);
        recyclerView.setAdapter(adapter);
        fetchAllBookingsByUser();

        ImageButton backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(view -> onBackPressed());
    }

    private void fetchAllBookingsByUser() {
        ApiService
                .getBookingApi(this)
                .getAllBookingsByUser().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<BookingResponse>> call, @NonNull Response<List<BookingResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Clear current items
                            items.clear();
                            // Map BookingResponse to Cancel_Booking_Item
                            for (BookingResponse booking : response.body()) {
                                items.add(new Delete_Booking_Item(
                                        booking.getBookingId(),
                                        booking.getBookingNo(),
                                        booking.getMovieName(),
                                        "Booking No: " + booking.getBookingNo(),
                                        booking.getImageUrlMovie(),
                                        booking.getStartDateTime()
                                ));
                            }
                            // Notify adapter about data changes
                            adapter.notifyDataSetChanged();
                        } else {
                    Toast.makeText(Delete_Booking_Activity.this, "Failed to fetch bookings", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BookingResponse>> call, @NonNull Throwable t) {
//                Toast.makeText(Cancel_Booking_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
