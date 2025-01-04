package vn.edu.usth.mcma.frontend.Feedback;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.GetAllBookingAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;
import vn.edu.usth.mcma.frontend.MainActivity;

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

//        // Thêm dữ liệu mẫu vào danh sách
//        items.add(new RatingMovie_Item("Wolverine", "Action", R.drawable.movie4));
//        items.add(new RatingMovie_Item("IronMan", "Drama", R.drawable.movie13));
//        items.add(new RatingMovie_Item("Wicked", "Comedy", R.drawable.movie12));

        adapter = new RatingMovie_Adapter(requireContext(), items);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

//        // Thiết lập adapter với callback showFeedbackDialog
//        RatingMovie_Adapter adapter = new RatingMovie_Adapter(requireContext(), items, this::showFeedbackDialog);
//        recyclerView.setAdapter(adapter);
        recyclerView.setAdapter(adapter);

        fetchBookingList();

//        ImageButton notification_button = v.findViewById(R.id.notification_button);
//        notification_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Notification.Notification_Activity.class);
//                startActivity(i);
//            }
//        });

        return v;
    }

    private void showFeedbackDialog(String movieName, String movieType, int movieImage) {
        // Inflate dialog layout
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_feedback, null);
        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create();

        // Bind các view từ layout dialog_feedback.xml
        Button btnViewFeedback = dialogView.findViewById(R.id.btn_viewmovie_feedback);
        Button btnCreateFeedback = dialogView.findViewById(R.id.btn_create_feedback);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel_feedback);

        // Xử lý sự kiện cho các nút
        btnViewFeedback.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), View_Movie_Feedback_Activity.class);
            startActivity(intent);
            dialog.dismiss();
        });

        btnCreateFeedback.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), RatingMovie_Activity.class);
            intent.putExtra("movie_name", movieName);
            intent.putExtra("movie_type", movieType);
            intent.putExtra("movie_image", movieImage);
            startActivity(intent);
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // Hiển thị dialog
        dialog.show();
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
