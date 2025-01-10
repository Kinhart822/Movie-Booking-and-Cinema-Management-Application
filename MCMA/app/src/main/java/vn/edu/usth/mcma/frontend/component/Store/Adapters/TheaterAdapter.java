package vn.edu.usth.mcma.frontend.component.Store.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.Store.Models.Theater;

public class TheaterAdapter extends RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder> {
    private final List<Theater> theaters;
    private OnTheaterClickListener listener;
    private Theater currentSelection;

    public interface OnTheaterClickListener {
        void onTheaterClick(Theater theater);
    }

    public TheaterAdapter(List<Theater> theaters) {
        this.theaters = theaters;
    }

    public void setOnTheaterClickListener(OnTheaterClickListener listener) {
        this.listener = listener;
    }

    public Theater getCurrentSelection() {
        return currentSelection;
    }

    public void setCurrentSelection(Theater theater) {
        // Deselect previous selection
        if (currentSelection != null) {
            currentSelection.setSelected(false);
        }
        // Update new selection
        if (theater != null) {
            theater.setSelected(true);
            currentSelection = theater;
        }
        notifyDataSetChanged();
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
                setCurrentSelection(theater);
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