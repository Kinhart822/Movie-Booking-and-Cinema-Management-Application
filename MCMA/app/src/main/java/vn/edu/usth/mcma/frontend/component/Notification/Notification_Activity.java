package vn.edu.usth.mcma.frontend.component.Notification;

import android.os.Bundle;
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
import vn.edu.usth.mcma.frontend.dto.Response.NotificationResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class Notification_Activity extends AppCompatActivity {

    private NotificationAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        RecyclerView recyclerView = findViewById(R.id.recyclerviewnotification);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotificationAdapter(this);
        recyclerView.setAdapter(adapter);


        ImageButton backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(view -> onBackPressed());

        fetchNotification();
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
                            adapter.updateData(notificationResponse.getMessage(), notificationResponse.getDateCreated());
                        } else {
                            Toast.makeText(Notification_Activity.this, "Failed to load notifications", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<NotificationResponse> call, @NonNull Throwable t) {
                        Toast.makeText(Notification_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
