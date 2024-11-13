package vn.edu.usth.mcma.frontend.Store.UI;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.usth.mcma.R;

import android.widget.Button;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TheaterSelectionDialog extends DialogFragment {
    private RecyclerView theaterRecyclerView;
    private Button confirmButton;
    private Button cancelButton;
    private TheaterAdapter adapter;
    private OnTheaterSelectedListener listener;
    private Theater currentSelection;

    public interface OnTheaterSelectedListener {
        void onTheaterSelected(Theater theater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theater_selection_dialog, container, false);
        initializeViews(view);
        setupRecyclerView();
        setupButtons();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    private void initializeViews(View view) {
        theaterRecyclerView = view.findViewById(R.id.theater_recycler_view);
        confirmButton = view.findViewById(R.id.confirm_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        // Set up the title
        TextView titleText = view.findViewById(R.id.dialog_title);
        titleText.setText("Chọn rạp để nhận hàng");
    }

    private void setupRecyclerView() {
        adapter = new TheaterAdapter(getTheaterList());
        adapter.setOnTheaterClickListener(theater -> {
            // Deselect previous selection
            if (currentSelection != null) {
                currentSelection.setSelected(false);
            }
            // Update new selection
            theater.setSelected(true);
            currentSelection = theater;
            adapter.notifyDataSetChanged();
            confirmButton.setEnabled(true);
        });

        theaterRecyclerView.setAdapter(adapter);
        theaterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupButtons() {
        confirmButton.setEnabled(false);
        confirmButton.setText("Xác nhận");
        confirmButton.setOnClickListener(v -> {
            if (currentSelection != null && listener != null) {
                listener.onTheaterSelected(currentSelection);
            }
            dismiss();
        });

        cancelButton.setText("Hủy");
        cancelButton.setOnClickListener(v -> dismiss());
    }

    private List<Theater> getTheaterList() {
        List<Theater> theaters = new ArrayList<>();
        theaters.add(new Theater("1", "Tất cả các rạp"));
        theaters.add(new Theater("2", "BHD Star 3.2"));
        theaters.add(new Theater("3", "BHD Star Phạm Hùng"));
        theaters.add(new Theater("4", "BHD Star Quang Trung"));
        theaters.add(new Theater("5", "BHD Star Thảo Điền"));
        theaters.add(new Theater("6", "BHD Star Lê Văn Việt"));
        theaters.add(new Theater("7", "BHD Star Phạm Ngọc Thạch"));
        theaters.add(new Theater("8", "BHD Star Huế"));
        theaters.add(new Theater("9", "BHD Star Discovery"));
        theaters.add(new Theater("10", "BHD Star The Garden"));
        theaters.add(new Theater("11", "BHD Star Long Khánh"));
        theaters.add(new Theater("12", "BHD Star Phú Mỹ"));
        return theaters;
    }

    public void setTheaterSelectedListener(OnTheaterSelectedListener listener) {
        this.listener = listener;
    }

    private static class TheaterAdapter extends RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder> {
        private final List<Theater> theaters;
        private OnTheaterClickListener listener;

        interface OnTheaterClickListener {
            void onTheaterClick(Theater theater);
        }

        TheaterAdapter(List<Theater> theaters) {
            this.theaters = theaters;
        }

        void setOnTheaterClickListener(OnTheaterClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public TheaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.store_item_theater, parent, false);
            return new TheaterViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TheaterViewHolder holder, int position) {
            Theater theater = theaters.get(position);
            holder.bind(theater);
            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onTheaterClick(theater);
                }
            });
        }

        @Override
        public int getItemCount() {
            return theaters.size();
        }

        static class TheaterViewHolder extends RecyclerView.ViewHolder {
            private final TextView theaterName;
            private final View checkMark;

            TheaterViewHolder(@NonNull View itemView) {
                super(itemView);
                theaterName = itemView.findViewById(R.id.theater_name);
                checkMark = itemView.findViewById(R.id.selected_check);
            }

            void bind(Theater theater) {
                theaterName.setText(theater.getName());
                checkMark.setVisibility(theater.isSelected() ? View.VISIBLE : View.INVISIBLE);
            }
        }
    }
}