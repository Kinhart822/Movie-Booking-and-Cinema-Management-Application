package vn.edu.usth.mcma.frontend.component.Notification;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.response.NotificationResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class Notification_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private FrameLayout noDataContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.recyclerviewnotification);
        noDataContainer = findViewById(R.id.noti_no_data_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotificationAdapter(this);
        recyclerView.setAdapter(adapter);


        ImageButton backButton = findViewById(R.id.button_back);

        backButton.setOnClickListener(view -> onBackPressed());

        showNoDataView();
        fetchNotification();
    }
    void showNoDataView() {
        recyclerView.setVisibility(View.GONE);
        noDataContainer.setVisibility(View.VISIBLE);
    }
    void hideNoDataView() {
        recyclerView.setVisibility(View.VISIBLE);
        noDataContainer.setVisibility(View.GONE);
    }


    private void fetchNotification() {
        ApiService
                .getAccountApi(this)
                .getNotifications()
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<NotificationResponse> call, @NonNull Response<NotificationResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            NotificationResponse notificationResponse = response.body();
                            if (notificationResponse.getMessage() == null || notificationResponse.getMessage().isEmpty() ||
                                    notificationResponse.getDateCreated() == null || notificationResponse.getDateCreated().isEmpty()) {
                                showNoDataView();
                            } else {
                                hideNoDataView();
                                adapter.updateData(notificationResponse.getMessage(), notificationResponse.getDateCreated());
                            }
                        } else {
                            showNoDataView();
//                            Toast.makeText(Notification_Activity.this, "Failed to load notifications", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<NotificationResponse> call, @NonNull Throwable t) {
                        showNoDataView();
//                        Toast.makeText(Notification_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
