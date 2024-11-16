package vn.edu.usth.mcma.frontend.Showtimes.Adapters;

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
import vn.edu.usth.mcma.frontend.Showtimes.Models.ShowtimeTheater;
import vn.edu.usth.mcma.frontend.Showtimes.Models.TheaterType;

public class ShowtimeTheaterAdapter extends RecyclerView.Adapter<ShowtimeTheaterAdapter.TheaterViewHolder> {
    private List<ShowtimeTheater> showtimeTheaters;
    private TheaterType currentType;
    private OnTheaterClickListener listener;

    public ShowtimeTheaterAdapter(OnTheaterClickListener listener) {
        this.showtimeTheaters = new ArrayList<>();
        this.listener = listener;
        this.currentType = TheaterType.REGULAR;
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
        ShowtimeTheater showtimeTheater = showtimeTheaters.get(position);
        holder.bind(showtimeTheater);
    }

    @Override
    public int getItemCount() {
        return showtimeTheaters.size();
    }

    public void setTheaters(List<ShowtimeTheater> showtimeTheaters) {
        this.showtimeTheaters = showtimeTheaters;
        notifyDataSetChanged();
    }

    public void setTheaterType(TheaterType type) {
        this.currentType = type;
        notifyDataSetChanged();
    }

    class TheaterViewHolder extends RecyclerView.ViewHolder {
        private ImageView theaterImage;
        private TextView theaterName;
        private TextView theaterAddress;

        TheaterViewHolder(@NonNull View itemView) {
            super(itemView);
            theaterImage = itemView.findViewById(R.id.theater_image);
            theaterName = itemView.findViewById(R.id.theater_name);
            theaterAddress = itemView.findViewById(R.id.theater_address);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onTheaterClick(showtimeTheaters.get(position));
                }
            });
        }

        void bind(ShowtimeTheater showtimeTheater) {
            theaterName.setText(showtimeTheater.getName());
            theaterAddress.setText(showtimeTheater.getAddress());
            theaterImage.setImageResource(showtimeTheater.getImageResId());
        }
    }

    public interface OnTheaterClickListener {
        void onTheaterClick(ShowtimeTheater showtimeTheater);
    }
}
