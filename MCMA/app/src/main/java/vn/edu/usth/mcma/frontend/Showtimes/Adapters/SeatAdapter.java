package vn.edu.usth.mcma.frontend.Showtimes.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat.AvailableSeatResponse;
import vn.edu.usth.mcma.frontend.Showtimes.Models.Seat;
import vn.edu.usth.mcma.frontend.Showtimes.Models.SeatType;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {
    private final List<AvailableSeatResponse> seatList;
    private final Context context;
    private final OnSeatSelectedListener listener;
    private final Set<AvailableSeatResponse> selectedSeats = new HashSet<>();
    private int maxSeats;

    public interface OnSeatSelectedListener {
//        void onSeatSelected(Seat seat);
        void onSeatSelected(AvailableSeatResponse seat);
    }
//    @Override
//    public int getItemViewType(int position) {
//        AvailableSeatResponse seat = seatList.get(position);
//        if ("Couple".equalsIgnoreCase(seat.getAvailableSeatsType())) {
//            return 1; // Ghế đôi
//        }
//        return 0; // Ghế đơn
//    }

    public SeatAdapter( List<AvailableSeatResponse> seatList, Context context, OnSeatSelectedListener listener, int maxSeats) {
        this.seatList = seatList;
        this.context = context;
        this.listener = listener;
        this.maxSeats = maxSeats;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.seat_selection_item, parent, false);
        return new SeatViewHolder(view);
//        View view;
//        if (viewType == 1) {
//            view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.seat_selection_item_couple, parent, false);
//        } else {
//            view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.seat_selection_item, parent, false);
//        }
//        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        AvailableSeatResponse seat = seatList.get(position);
        if (seat == null) {
            holder.seatTextView.setText("");
            holder.seatTextView.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_seat_null));
            return;
        }
        // Display the seat name
        holder.seatTextView.setText(seat.getAvailableSeat());

//        if ("Couple".equalsIgnoreCase(seat.getAvailableSeatsType())) {
//            holder.seatTxtView1.setText(seat.getAvailableSeat());
//            holder.seatTxtView2.setText(seatList.get(position + 1).getAvailableSeat());
//        } else {
//            holder.seatTextView.setText(seat.getAvailableSeat());
//        }
        // Update background based on seat type and availability
        updateSeatBackground(holder, seat);

        // Set click listener for seat selection
        holder.itemView.setOnClickListener(v -> {
            if (!"Unavailable".equalsIgnoreCase(seat.getAvailableSeatsType())) {
                toggleSeatSelection(seat, holder);
            }
        });

    }
    private void updateSeatBackground(SeatViewHolder holder, AvailableSeatResponse seat) {
        int backgroundResId;

        if (selectedSeats.contains(seat)) {
            backgroundResId = R.drawable.ic_seat_selecting;
        } else if ("Unavailable".equalsIgnoreCase(seat.getAvailableSeatsType())) {
            backgroundResId = R.drawable.ic_seat_unavailable;
        } else if ("Held".equalsIgnoreCase(seat.getAvailableSeatsType())) {
            backgroundResId = R.drawable.ic_seat_held;
        } else {
            switch (seat.getAvailableSeatsType()) {
                case "VIP":
                    backgroundResId = R.drawable.ic_seat_vip;
                    break;
                case "Couple":
                    backgroundResId = R.drawable.ic_seat_couple;
                    break;
                default:
                    backgroundResId = R.drawable.standard;
                    break;
            }

        }

        holder.seatTextView.setBackground(ContextCompat.getDrawable(context, backgroundResId));
    }

    private void toggleSeatSelection(AvailableSeatResponse seat, SeatViewHolder holder) {
        if (selectedSeats.contains(seat)) {
            selectedSeats.remove(seat);
        } else {
            if (selectedSeats.size() < maxSeats) {
                selectedSeats.add(seat);
            } else {
                Toast.makeText(context, "Maximum seats selected!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Notify listener and update seat appearance
        notifyItemChanged(seatList.indexOf(seat));
        if (listener != null) {
            listener.onSeatSelected(seat);
        }
    }

    public Set<AvailableSeatResponse> getSelectedSeats() {
        return selectedSeats;
    }

//    private boolean canSelectCoupleSeat() {
//        // Calculate max couple seats based on guest quantity
//        int maxCoupleSeatCount;
//        if (maxSeats == 1) {
//            // If only 1 guest, cannot select couple seats
//            return false;
//        } else if (maxSeats % 2 == 0) {
//            // If even number of guests, can select half as couple seats
//            maxCoupleSeatCount = maxSeats / 2;
//        } else {
//            // If odd number of guests, can select (n-1)/2 couple seats
//            maxCoupleSeatCount = (maxSeats - 1) / 2;
//        }
//
//        // Count currently selected couple seats
//        long currentCoupleSeatCount = selectedSeats.stream()
//                .filter(seat -> seat.getType() == SeatType.COUPLE)
//                .count();
//
//        return currentCoupleSeatCount < maxCoupleSeatCount;
//    }
//
//    private boolean canSelectSeat(Seat seat) {
//        // Current selected seats count
//        int currentSelectedCount = selectedSeats.size();
//
//        // Special handling for couple seats
//        if (seat.getType() == SeatType.COUPLE) {
//            // Check if guest quantity allows couple seats
//            if (maxSeats == 1) return false;
//
//            // Check if can select more couple seats
//            return canSelectCoupleSeat() &&
//                    (currentSelectedCount + 2 <= maxSeats);
//        }
//
//        // For standard and VIP seats
//        return currentSelectedCount + 1 <= maxSeats;
//    }

//    private void updateSeatBackground(SeatViewHolder holder, Seat seat) {
//        // Default background based on seat type
//        int backgroundResId = seat.getType().getDrawableResId();
//
//        // Check if seat is selected
//        if (selectedSeats.contains(seat)) {
//            backgroundResId = R.drawable.ic_seat_selecting;
//        }
//
//        holder.itemView.setBackgroundResource(backgroundResId);
//        holder.itemView.setEnabled(seat.isAvailable());
//    }
//
//    private void toggleSeatSelection(Seat seat, SeatViewHolder holder) {
//        if (selectedSeats.contains(seat)) {
//            selectedSeats.remove(seat);
//        } else {
//            // Check if seat can be selected
//            if (canSelectSeat(seat)) {
//                selectedSeats.add(seat);
//            } else {
//                // Optional: Show toast or message about seat selection limit
//                return;
//            }
//        }
//
//        // Always notify item changed and call listener
//        notifyItemChanged(getPosition(seat));
//        if (listener != null) {
//            listener.onSeatSelected(seat);
//        }
//    }

//    public Set<Seat> getSelectedSeats() {
//        return selectedSeats;
//    }

//    private int getPosition(Seat seat) {
//        for (int row = 0; row < seatLayout.size(); row++) {
//            int index = seatLayout.get(row).indexOf(seat);
//            if (index != -1) {
//                return row * seatLayout.get(0).size() + index;
//            }
//        }
//        return -1;
//    }
//
//    private String generateSeatLabel(int rowIndex, int seatIndexInRow) {
//        // For the last row (couple row), handle differently
//        if (rowIndex == seatLayout.size() - 1) {
//            return "H-" + (seatIndexInRow + 1);
//        }
//
//        // For other rows, use row letter and seat number
//        char rowLetter = (char) ('A' + rowIndex);
//        return rowLetter + String.valueOf(seatIndexInRow + 1);
//    }

    @Override
    public int getItemCount() {
        return seatList.size();
    }
//    @Override
//    public int getItemCount() {
//        return seatLayout.stream().mapToInt(List::size).sum();
//    }

    static class SeatViewHolder extends RecyclerView.ViewHolder {
        TextView seatTextView;
//        TextView seatTxtView1;
//        TextView seatTxtView2;
        SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            seatTextView = itemView.findViewById(R.id.seatTextView);
//            seatTxtView1 = itemView.findViewById(R.id.seatTxtView1);
//            seatTxtView2 = itemView.findViewById(R.id.seatTxtView2);
        }
    }
}