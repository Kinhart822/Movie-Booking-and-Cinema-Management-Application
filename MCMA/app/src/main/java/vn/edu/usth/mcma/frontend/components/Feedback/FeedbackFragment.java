package vn.edu.usth.mcma.frontend.components.Feedback;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.Response.BookingResponse;
import vn.edu.usth.mcma.frontend.network.apis.GetAllBookingAPI;
import vn.edu.usth.mcma.frontend.network.RetrofitService;

public class FeedbackFragment extends Fragment {
    private RecyclerView recyclerView;
    //    private List<RatingMovie_Item> items;
    private RatingMovie_Adapter adapter;
    private List<BookingResponse> items;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feedback, container, false);

        recyclerView = v.findViewById(R.id.recyclerview_feedbackmovie);
        items = new ArrayList<>();
        fetchBookingList();
        adapter = new RatingMovie_Adapter(requireContext(), items);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        return v;
    }
    private void fetchBookingList() {
        RetrofitService retrofitService = new RetrofitService(requireContext());
        GetAllBookingAPI getAllBookingAPI = retrofitService.getRetrofit().create(GetAllBookingAPI.class);
        getAllBookingAPI.getBookingHistory().enqueue(new Callback<List<BookingResponse>>() {
            @Override
            public void onResponse(Call<List<BookingResponse>> call, Response<List<BookingResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    Toast.makeText(requireActivity(), "Here's your feedbacks", Toast.LENGTH_SHORT).show();
                    items.clear();
                    items.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(requireActivity(), "You haven't made any feedbacks", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BookingResponse>> call, Throwable t) {
                Toast.makeText(requireActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
