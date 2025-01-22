package vn.edu.usth.mcma.frontend.component.ShowtimesOld.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import lombok.Getter;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.constant.SeatAvailability;
import vn.edu.usth.mcma.frontend.constant.SeatAvailables;
import vn.edu.usth.mcma.frontend.dto.response.SeatTypeResponse;
import vn.edu.usth.mcma.frontend.dto.response.Seat;
import vn.edu.usth.mcma.frontend.component.ShowtimesOld.UI.SeatSelectionActivity;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {
    private final Map<Integer, Map<Integer, Seat>> seatMatrix;
    private final Map<Integer, Map<Integer, List<Seat>>> rootSeatMatrix;
    private final Map<Integer, SeatTypeResponse> seatTypes;
    private final Context context;
    private final OnSeatSelectedListener listener;
    private int numberOfTicketCounts = 0;
    private int desiredNumberOfTickets;
    private int numberOfColumnsPerRow;
    private final List<Seat> selectedSeats;
    @Getter // this contains list of root seats
    private final List<Seat> selectedRootSeats;
    private final String TAG = SeatAdapter.class.getName();

    public interface OnSeatSelectedListener {
        void onSeatSelected(Seat seat);
    }
    public SeatAdapter(
            Map<Integer, Map<Integer, Seat>> seatMatrix,
            Map<Integer, Map<Integer, List<Seat>>> rootSeatMatrix,
            Map<Integer, SeatTypeResponse> seatTypes,
            Context context,
            OnSeatSelectedListener listener,
            int desiredNumberOfTickets) {
        this.seatMatrix = seatMatrix;
        this.rootSeatMatrix = rootSeatMatrix;
        this.seatTypes = seatTypes;
        this.context = context;
        this.listener = listener;
        this.desiredNumberOfTickets = desiredNumberOfTickets;
        this.selectedSeats = new ArrayList<>();
        this.selectedRootSeats  = new ArrayList<>();
        this.numberOfColumnsPerRow = Objects
                .requireNonNull(seatMatrix.get(1))
                .size();
    }

    //todo
    @Override
    public int getItemViewType(int position) {
        int totalCols = numberOfColumnsPerRow + 1; // Thêm cột cho chữ cái hàng
        int col = position % totalCols;
        // Loại item: chữ cái hàng (cột đầu tiên)
        return (col == 0) ? 0 : 1;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("SeatAdapter", seatMatrix.toString());
        if (viewType == 0) { // Chữ cái hàng
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_row_seat_letter, parent, false);
            return new SeatViewHolder(view, true);
        } else { // Ghế
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.seat_selection_item, parent, false);
            return new SeatViewHolder(view, false);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        int totalCols = numberOfColumnsPerRow + 1;
        int row = position / totalCols;
        int col = position % totalCols;
        if (col == 0) {
            char rowLetter = (char) ('A' + row);
            holder.rowLetterTextView.setText(String.valueOf(rowLetter));
            return;
        }
        Seat seat = Objects
                .requireNonNull(seatMatrix.get(row))
                .get(col - 1);
        assert seat != null;
        String name = seat.getName();
        int seatTypeId = seat.getTypeId();
        int availabilityId = seat.getAvailability();
        if (name != null) {
            holder.seatTextView.setText(name);
            holder.itemView.setBackground(ContextCompat
                    .getDrawable(context, SeatAvailability
                            .getById(availabilityId)
                            .getBackgroundId()));
            holder.itemView.setOnClickListener(null);
            if (availabilityId == SeatAvailability.Buyable.getId()) {
                holder.itemView.setBackground(ContextCompat
                        .getDrawable(context, SeatAvailables
                                .getById(seatTypeId)
                                .getBackgroundId()));
                holder.itemView.setOnClickListener(v -> toggleSeat(seat, holder));
            }
            if (selectedSeats.contains(seat)) {
                holder.itemView.setBackground(ContextCompat
                        .getDrawable(context, SeatAvailables.SELECTED.getBackgroundId()));
            }
        } else {
            holder.seatTextView.setText("");
            holder.itemView.setBackground(ContextCompat
                    .getDrawable(context, R.drawable.ic_seat_empty));
            holder.itemView.setOnClickListener(null);
        }
    }
    private void toggleSeat(Seat seat, SeatViewHolder holder) {
        int rootRow = seat.getRootRow();
        int rootCol = seat.getRootCol();
        List<Seat> rectangle = Objects
                .requireNonNull(Objects
                        .requireNonNull(rootSeatMatrix
                                .get(rootRow))
                .get(rootCol));
        Optional<Seat> rootSeatOpt = rectangle
                .parallelStream()
                .filter(s -> s.getRootRow() == rootRow && s.getRootCol() == rootCol)
                .findAny();
        Seat rootSeat = null;
        if (rootSeatOpt.isPresent()) {
            rootSeat = rootSeatOpt.get();
        }
        // HashSet for better performance
        if (new HashSet<>(selectedSeats).containsAll(rectangle)) {
            selectedSeats.removeAll(rectangle);
            selectedRootSeats.remove(rootSeat);
        } else {
            if (isNumberOfTicketsExceeded(seat)) {
                Toast.makeText(context, "Desired number of seats exceeded!", Toast.LENGTH_SHORT).show();
                return;
            }
            selectedSeats.addAll(rectangle);
            selectedRootSeats.add(rootSeat);
        }
        rectangle.forEach(s -> notifyItemChanged(s.getRow() * (numberOfColumnsPerRow + 1) + s.getCol() + 1));
        if (context instanceof SeatSelectionActivity) {
            ((SeatSelectionActivity) context).updateSelectedSeatsDisplay();
        }
        if (listener != null) {
            listener.onSeatSelected(seat);
        }
    }
    private boolean isNumberOfTicketsExceeded(Seat seat) {
        int numberOfAdditionalTicket = getNumberOfTicketCount(seat);
        int count = selectedRootSeats
                .stream()
                .mapToInt(this::getNumberOfTicketCount)
                .sum();
        int total = count + numberOfAdditionalTicket;
        Log.d(TAG, "isNumberOfTicketsExceeded: "+total);
        if (total > desiredNumberOfTickets) {
            return true;
        }
        desiredNumberOfTickets = total;
        return false;
    }
    private int getNumberOfTicketCount(Seat seat) {
        return (
                Objects.equals(seat.getTypeId(), SeatAvailables.LOVERS.getId()) ||
                        Objects.equals(seat.getTypeId(), SeatAvailables.BED.getId()))
                ? 2
                : 1;
    }
    // Rows * Columns
    @Override
    public int getItemCount() {
        return seatMatrix.size() * (Objects.requireNonNull(seatMatrix.get(1)).size() + 1);
    }
    public static class SeatViewHolder extends RecyclerView.ViewHolder {
        TextView seatTextView;
        TextView rowLetterTextView;
        SeatViewHolder(@NonNull View itemView, boolean isRowLetter) {
            super(itemView);
            if (isRowLetter) {
                rowLetterTextView = itemView.findViewById(R.id.rowSeatLetterTextView);
            }
            seatTextView = itemView.findViewById(R.id.seatTextView);//todo monospace font
        }
    }
}
