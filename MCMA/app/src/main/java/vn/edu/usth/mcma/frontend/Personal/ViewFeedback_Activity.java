package vn.edu.usth.mcma.frontend.Personal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.MovieRespondResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.GetAllFeedBackAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;

public class ViewFeedback_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Feedback_Adapter adapter;
    //    private List<Feedback_Item> items;
    private FrameLayout noDataContainer;
    private List<MovieRespondResponse> movieRespondList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);

        recyclerView = findViewById(R.id.recyclerview_feedback);
        noDataContainer = findViewById(R.id.user_feedback_no_data_container);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Feedback_Adapter(this, movieRespondList);
        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new Feedback_Adapter(this, items));

        ImageButton backButton = findViewById(R.id.feedback_back_button);
        backButton.setOnClickListener(view -> {
            onBackPressed();
        });

        showNoDataView();
        fetchFeedBack();
    }

    void showNoDataView() {
        recyclerView.setVisibility(View.GONE);
        noDataContainer.setVisibility(View.VISIBLE);
    }

    void hideNoDataView() {
        recyclerView.setVisibility(View.VISIBLE);
        noDataContainer.setVisibility(View.GONE);
    }

    private void fetchFeedBack() {
        RetrofitService retrofitService = new RetrofitService(this);
        GetAllFeedBackAPI getAllFeedBackAPI = retrofitService.getRetrofit().create(GetAllFeedBackAPI.class);
        getAllFeedBackAPI.getAllUserFeedback().enqueue(new Callback<List<MovieRespondResponse>>() {
            @Override
            public void onResponse(Call<List<MovieRespondResponse>> call, Response<List<MovieRespondResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(ViewFeedback_Activity.this, "Here's your feedbacks", Toast.LENGTH_SHORT).show();
                    movieRespondList.clear();

                    if (response.body().isEmpty()) {
                        showNoDataView();
                    } else {
                        hideNoDataView();
                        movieRespondList.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    }
                } else {
//                    Toast.makeText(ViewFeedback_Activity.this, "Failed to load feedbacks", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MovieRespondResponse>> call, Throwable t) {
                showNoDataView();
//                Toast.makeText(ViewFeedback_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
