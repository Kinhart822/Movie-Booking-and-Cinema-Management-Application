package vn.edu.usth.mcma.frontend.component.main;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.model.response.NotificationResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;
import vn.edu.usth.mcma.frontend.utils.mapper.NotificationMapper;

public class NotificationActivity extends AppCompatActivity {
    private static final String TAG = NotificationActivity.class.getName();

    private RecyclerView notificationRecyclerView;
    private List<NotificationResponse> notificationResponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ImageButton backButton = findViewById(R.id.button_back);
        notificationRecyclerView = findViewById(R.id.recycler_view_notification);

        backButton.setOnClickListener(view -> onBackPressed());
        findAllNotifications();
    }
    private void findAllNotifications() {
        ApiService
                .getAccountApi(this)
                .findAllNotifications()
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<NotificationResponse>> call, @NonNull Response<List<NotificationResponse>> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "findAllNotifications onResponse: code not 200 || body is null");
                            return;
                        }
                        notificationResponses = response.body();
                        postFindAllNotifications();
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<NotificationResponse>> call, @NonNull Throwable t) {
                        Log.e(TAG, "findAllNotifications onFailure: " + t);
                    }
                });
    }
    private void postFindAllNotifications() {
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationRecyclerView.setAdapter(new NotificationAdapter(NotificationMapper.fromResponseList(notificationResponses)));
    }
}
