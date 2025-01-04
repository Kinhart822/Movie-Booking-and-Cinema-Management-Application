package vn.edu.usth.mcma.frontend.Feedback;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;

public class FeedbackFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<RatingMovie_Item> items;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feedback, container, false);

        recyclerView = v.findViewById(R.id.recyclerview_feedbackmovie);
        items = new ArrayList<>();

        // Thêm dữ liệu mẫu vào danh sách
        items.add(new RatingMovie_Item("Wolverine", "Action", R.drawable.movie4));
        items.add(new RatingMovie_Item("IronMan", "Drama", R.drawable.movie13));
        items.add(new RatingMovie_Item("Wicked", "Comedy", R.drawable.movie12));

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Thiết lập adapter với callback showFeedbackDialog
        RatingMovie_Adapter adapter = new RatingMovie_Adapter(requireContext(), items, this::showFeedbackDialog);
        recyclerView.setAdapter(adapter);

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
}
