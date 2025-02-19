package vn.edu.usth.mcma.frontend.component.Personal;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
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

public class Revoke_Cancel_Booking_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Revoke_Cancel_Booking_Adapter adapter;
    private List<Revoke_Cancel_Booking_Item> items;
    private FrameLayout noDataContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revoke_cancel_booking);

        recyclerView = findViewById(R.id.recyclerview_revoke_cancel_booking);
        noDataContainer = findViewById(R.id.revoke_cancel_booking_no_data_container);

        items = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Revoke_Cancel_Booking_Adapter(this, items);
        recyclerView.setAdapter(adapter);
        showNoDataView();
        fetchCanceledBookings();

        ImageButton backButton = findViewById(R.id.button_back);

        backButton.setOnClickListener(view -> onBackPressed());
    }

    void showNoDataView() {
        recyclerView.setVisibility(View.GONE);
        noDataContainer.setVisibility(View.VISIBLE);
    }
    void hideNoDataView() {
        recyclerView.setVisibility(View.VISIBLE);
        noDataContainer.setVisibility(View.GONE);
    }
    private void fetchCanceledBookings() {
        ApiService
                .getBookingApi(this)
                .getAllBookingsCanceled().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<BookingResponse>> call, @NonNull Response<List<BookingResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Clear current items
                            items.clear();
                            // Map BookingResponse to Revoke_Cancel_Booking_Item
                            if (response.body().isEmpty()) {
                                showNoDataView();
                            } else {
                                for (BookingResponse booking : response.body()) {
                                    items.add(new Revoke_Cancel_Booking_Item(
                                            booking.getBookingId(),
                                            booking.getBookingNo(),
                                            booking.getMovieName(),
                                            "Booking No: " + booking.getBookingNo(),
                                            booking.getImageUrlMovie(),
                                            booking.getStartDateTime()
                                    ));
                                }
                                // Notify adapter about data changes
                                hideNoDataView();
                                adapter.notifyDataSetChanged();
                            }
                        } else {
//                    Toast.makeText(Revoke_Cancel_Booking_Activity.this, "Failed to fetch bookings", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BookingResponse>> call, @NonNull Throwable t) {
                        showNoDataView();
//                Toast.makeText(Revoke_Cancel_Booking_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
