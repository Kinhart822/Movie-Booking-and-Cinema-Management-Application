package vn.edu.usth.mcma.frontend.component.Personal;

import android.os.Bundle;
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
import vn.edu.usth.mcma.frontend.dto.Response.MovieRespondResponse;
import vn.edu.usth.mcma.frontend.network.apis.GetAllFeedBackAPI;
import vn.edu.usth.mcma.frontend.network.RetrofitService;

public class ViewFeedback_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Feedback_Adapter adapter;
//    private List<Feedback_Item> items;
    private List<MovieRespondResponse> movieRespondList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);

        recyclerView =  findViewById(R.id.recyclerview_feedback);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Feedback_Adapter(this,movieRespondList);
        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new Feedback_Adapter(this, items));

        ImageButton backButton = findViewById(R.id.feedback_back_button);
        backButton.setOnClickListener(view -> {
            onBackPressed();
        });

        fetchFeedBack();
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
                    movieRespondList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ViewFeedback_Activity.this, "Failed to load feedbacks", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MovieRespondResponse>> call, Throwable t) {
                Toast.makeText(ViewFeedback_Activity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
