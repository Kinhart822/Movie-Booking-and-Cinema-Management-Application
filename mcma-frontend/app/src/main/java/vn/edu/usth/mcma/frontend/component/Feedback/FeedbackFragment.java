package vn.edu.usth.mcma.frontend.component.Feedback;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
import vn.edu.usth.mcma.frontend.dto.response.BookingResponse;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class FeedbackFragment extends Fragment {
    private RecyclerView recyclerView;
    //    private List<RatingMovie_Item> items;
    private RatingMovie_Adapter adapter;
    private FrameLayout noDataContainer;
    private List<BookingResponse> items;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feedback, container, false);

        recyclerView = v.findViewById(R.id.recyclerview_feedbackmovie);
        noDataContainer = v.findViewById(R.id.feedback_no_data_container);
        items = new ArrayList<>();
        showNoDataView();
        fetchBookingList();
        adapter = new RatingMovie_Adapter(requireContext(), items);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        return v;
    }
    void showNoDataView() {
        recyclerView.setVisibility(View.GONE);
        noDataContainer.setVisibility(View.VISIBLE);
    }
    void hideNoDataView() {
        recyclerView.setVisibility(View.VISIBLE);
        noDataContainer.setVisibility(View.GONE);
    }

    private void fetchBookingList() {
        ApiService
                .getAccountApi(requireContext())
                .getBookingHistory().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<BookingResponse>> call, @NonNull Response<List<BookingResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
//                    Toast.makeText(requireActivity(), "Here's your feedbacks", Toast.LENGTH_SHORT).show();
                            items.clear();
                            if (response.body().isEmpty()) {
                                showNoDataView();
                            } else {
                                items.addAll(response.body());
                                hideNoDataView();
                                adapter.notifyDataSetChanged();
                            }
                        } else {
//                            Toast.makeText(requireActivity(), "You haven't made any feedbacks", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<BookingResponse>> call, @NonNull Throwable t) {
//                        Toast.makeText(requireActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        showNoDataView();
                    }
                });
    }
}
