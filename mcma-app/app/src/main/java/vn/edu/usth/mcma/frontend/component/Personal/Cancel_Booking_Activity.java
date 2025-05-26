package vn.edu.usth.mcma.frontend.component.Personal;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

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

public class Cancel_Booking_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Cancel_Booking_Adapter adapter;
    private List<Cancel_Booking_Item> items;
    private FrameLayout noDataContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_booking);

        recyclerView = findViewById(R.id.recyclerview_cancel_booking);
        noDataContainer = findViewById(R.id.cancel_booking_no_data_container);

        items = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Cancel_Booking_Adapter(this, items);
        recyclerView.setAdapter(adapter);
        showNoDataView();
        fetchCompletedBookings();

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


    private void fetchCompletedBookings() {
        ApiService
                .getBookingApi(this)
                .getAllCompletedBookingsByUser().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<BookingResponse>> call, @NonNull Response<List<BookingResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            items.clear();
                            if (response.body().isEmpty()) {
                                showNoDataView();
                            } else {
                                for (BookingResponse booking : response.body()) {
                                    items.add(new Cancel_Booking_Item(
                                            booking.getBookingId(),
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
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BookingResponse>> call, @NonNull Throwable t) {
                        showNoDataView();
//                Toast.makeText(Cancel_Booking_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
