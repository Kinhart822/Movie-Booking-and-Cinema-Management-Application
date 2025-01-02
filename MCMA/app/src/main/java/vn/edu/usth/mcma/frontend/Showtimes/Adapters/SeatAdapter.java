package vn.edu.usth.mcma.frontend.Showtimes.Adapters;

import android.annotation.SuppressLint;
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
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat.HeldSeatResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat.UnavailableSeatResponse;
import vn.edu.usth.mcma.frontend.Showtimes.UI.SeatSelectionActivity;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {
    private final List<Object> combinedSeatList;
    private final Context context;
    private final OnSeatSelectedListener listener;
    private final Set<AvailableSeatResponse> selectedSeats = new HashSet<>();
    private final int maxSeats;

    public interface OnSeatSelectedListener {
        void onSeatSelected(AvailableSeatResponse seat);
    }

    public SeatAdapter(
            List<Object> combinedSeatList,
            Context context,
            OnSeatSelectedListener listener,
            int maxSeats
    ) {
        this.context = context;
        this.listener = listener;
        this.maxSeats = maxSeats;
        this.combinedSeatList = combinedSeatList;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.seat_selection_item, parent, false);
        return new SeatViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        Object seat = combinedSeatList.get(position);

        if (seat instanceof AvailableSeatResponse) {
            AvailableSeatResponse availableSeat = (AvailableSeatResponse) seat;
            holder.seatTextView.setText(availableSeat.getAvailableSeat());
            updateSeatBackground(holder, availableSeat);

            holder.itemView.setOnClickListener(v -> {
                if (!"Unavailable".equalsIgnoreCase(availableSeat.getSeatStatus()) && !"Held".equalsIgnoreCase(availableSeat.getSeatStatus())) {
                    toggleSeatSelection(availableSeat, holder, position);
                }
            });
        } else if (seat instanceof UnavailableSeatResponse) {
            UnavailableSeatResponse unavailableSeat = (UnavailableSeatResponse) seat;
            holder.seatTextView.setText(unavailableSeat.getUnAvailableSeat());
            updateSeatBackground(holder, unavailableSeat);

            holder.itemView.setOnClickListener(v -> {
                // Do nothing for unavailable seats
            });
        } else if (seat instanceof HeldSeatResponse) {
            HeldSeatResponse heldSeat = (HeldSeatResponse) seat;
            holder.seatTextView.setText(heldSeat.getHeldSeat());
            updateSeatBackground(holder, heldSeat);

            holder.itemView.setOnClickListener(v -> {
                // Do nothing for held seats
            });
        }
    }

    private void updateSeatBackground(SeatViewHolder holder, AvailableSeatResponse seat) {
        int backgroundResId;

        if (selectedSeats.contains(seat)) {
            backgroundResId = R.drawable.ic_seat_selecting;
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


    private void updateSeatBackground(SeatViewHolder holder, UnavailableSeatResponse seat) {
        int backgroundResId = R.drawable.ic_seat_unavailable;

        holder.seatTextView.setBackground(ContextCompat.getDrawable(context, backgroundResId));
    }

    private void updateSeatBackground(SeatViewHolder holder, HeldSeatResponse seat) {
        int backgroundResId = R.drawable.ic_seat_stand;

        holder.seatTextView.setBackground(ContextCompat.getDrawable(context, backgroundResId));
    }
    private void toggleSeatSelection(AvailableSeatResponse seat, SeatViewHolder holder, int position) {
        // Check if the selected seat is a "Couple" seat
        if ("couple".equalsIgnoreCase(seat.getAvailableSeatsType())) {
            // Try to find adjacent couple seat
            int adjacentPosition = findAdjacentCoupleSeat(position);

            if (adjacentPosition != -1) {
                // Select both the clicked seat and the adjacent seat
                AvailableSeatResponse adjacentSeat = (AvailableSeatResponse) combinedSeatList.get(adjacentPosition);
                if (selectedSeats.contains(seat) || selectedSeats.contains(adjacentSeat)) {
                    selectedSeats.remove(seat);
                    selectedSeats.remove(adjacentSeat);
                } else {
                    if (selectedSeats.size() + 2 <= maxSeats) {
                        selectedSeats.add(seat);
                        selectedSeats.add(adjacentSeat);
                    } else {
                        Toast.makeText(context, "Maximum seats selected!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // Notify that both seats have been selected or deselected
                notifyItemChanged(position);
                notifyItemChanged(adjacentPosition);
            }
        } else {
            // Normal seat selection for non-couple seats
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
            // Notify the listener and update appearance
            notifyItemChanged(position);
            if (listener != null) {
                listener.onSeatSelected(seat);
            }
        }

        // Update selected seats display in the activity
        if (context instanceof SeatSelectionActivity) {
            ((SeatSelectionActivity) context).updateSelectedSeatsDisplay();
        }
    }


    private int findAdjacentCoupleSeat(int position) {
        // Get current seat and its row/column
        AvailableSeatResponse currentSeat = (AvailableSeatResponse) combinedSeatList.get(position);
        int row = currentSeat.getSeatRow();
        int col = currentSeat.getSeatColumn();

        // Look for adjacent couple seat (either to the right or to the left)
        if (col + 1 < 10) { // assuming 10 columns per row
            Object adjacentSeat = combinedSeatList.get(position + 1); // seat to the right
            if (adjacentSeat instanceof AvailableSeatResponse &&
                    "Couple".equalsIgnoreCase(((AvailableSeatResponse) adjacentSeat).getAvailableSeatsType()) &&
                    ((AvailableSeatResponse) adjacentSeat).getSeatRow() == row) {
                return position + 1;
            }
        }

        if (col - 1 >= 0) { // seat to the left
            Object adjacentSeat = combinedSeatList.get(position - 1);
            if (adjacentSeat instanceof AvailableSeatResponse &&
                    "Couple".equalsIgnoreCase(((AvailableSeatResponse) adjacentSeat).getAvailableSeatsType()) &&
                    ((AvailableSeatResponse) adjacentSeat).getSeatRow() == row) {
                return position - 1;
            }
        }

        // Return -1 if no adjacent couple seat found
        return -1;
    }

    public Set<AvailableSeatResponse> getSelectedSeats() {
        return selectedSeats;
    }

    @Override
    public int getItemCount() {
        return combinedSeatList.size();
    }

    static class SeatViewHolder extends RecyclerView.ViewHolder {
        TextView seatTextView;

        SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            seatTextView = itemView.findViewById(R.id.seatTextView);

        }
    }
}