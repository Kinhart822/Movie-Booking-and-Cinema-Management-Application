package vn.edu.usth.mcma.frontend.component.ShowtimesOld.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models.Theater;

public class TheaterAdapter extends RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder> {
    private List<Theater> theaters;
    private final OnTheaterClickListener listener;

    public TheaterAdapter(OnTheaterClickListener listener) {
        this.theaters = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public TheaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.theater_item, parent, false);
        return new TheaterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheaterViewHolder holder, int position) {
        Theater theater = theaters.get(position);
        holder.bind(theater);
    }

    @Override
    public int getItemCount() {
        return theaters.size();
    }

    public void setTheaters(List<Theater> theaters) {
        this.theaters = theaters;
        notifyDataSetChanged();
    }

    public class TheaterViewHolder extends RecyclerView.ViewHolder {
        private final ImageView theaterImage;
        private final TextView theaterName;
        private final TextView theaterAddress;

        TheaterViewHolder(@NonNull View itemView) {
            super(itemView);
            theaterImage = itemView.findViewById(R.id.theater_image);
            theaterName = itemView.findViewById(R.id.theater_name);
            theaterAddress = itemView.findViewById(R.id.theater_address);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onTheaterClick(theaters.get(position));
                }
            });
        }

        void bind(Theater theater) {
            theaterName.setText(theater.getName());
            theaterAddress.setText(theater.getAddress());
            theaterImage.setImageResource(theater.getImageResId());
        }
    }

    public interface OnTheaterClickListener {
        void onTheaterClick(Theater theater);
    }
}
