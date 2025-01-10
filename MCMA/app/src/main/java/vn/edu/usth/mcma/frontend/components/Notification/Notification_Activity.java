package vn.edu.usth.mcma.frontend.components.Notification;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.Response.NotificationResponse;
import vn.edu.usth.mcma.frontend.network.apis.GetNotifications;
import vn.edu.usth.mcma.frontend.network.RetrofitService;

public class Notification_Activity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.recyclerviewnotification);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotificationAdapter(this);
        recyclerView.setAdapter(adapter);


        ImageButton backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(view -> {
            onBackPressed();
        });

        fetchNotification();
    }

    private void fetchNotification() {
        RetrofitService retrofitService = new RetrofitService(this);
        GetNotifications getNotifications = retrofitService.getRetrofit().create(GetNotifications.class);
        getNotifications.getNotifications().enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NotificationResponse notificationResponse = response.body();
                    adapter.updateData(notificationResponse.getMessage(), notificationResponse.getDateCreated());
                } else {
                    Toast.makeText(Notification_Activity.this, "Failed to load notifications", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                Toast.makeText(Notification_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
