package vn.edu.usth.mcma.frontend.component.bookingprocess;

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
import vn.edu.usth.mcma.frontend.dto.response.Seat;
import vn.edu.usth.mcma.frontend.helper.SeatMapHelper;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.ViewHolder> {
    private final Map<Integer, Map<Integer, Seat>> seatMatrix;
    private final Map<Integer, Map<Integer, List<Seat>>> rootSeatMatrix;
    private final Context context;
    private final ISeatItemView iSeatItemView;
    private int numberOfTicketCounts = 0;
    @Deprecated
    private int desiredNumberOfTickets;
    @Getter
    private final int maxSeatPerRow;
    private final List<Seat> selectedSeats;
    @Getter // this contains list of root seats
    private final List<Seat> selectedRootSeats;
//    private final String TAG = SeatAdapter.class.getName();


    public SeatAdapter(
            Context context,
            List<Seat> seats,
            ISeatItemView iSeatItemView,
            int desiredNumberOfTickets) {
        SeatMapHelper seatMapHelper = new SeatMapHelper(seats);
        this.seatMatrix = seatMapHelper.getSeatMatrix();
        this.rootSeatMatrix = seatMapHelper.getRootSeatMatrix();
        this.maxSeatPerRow = seatMapHelper.getMaxSeatPerRow();
        this.context = context;
        this.iSeatItemView = iSeatItemView;
        this.desiredNumberOfTickets = desiredNumberOfTickets;
        this.selectedSeats = new ArrayList<>();
        this.selectedRootSeats  = new ArrayList<>();
    }

    /*
     * recycler view is divided into two parts: the first column (type 0) and the rest (type 1)
     * - the first column presents individual letters (e.g. A, B, ...) suggesting index of the rows
     * - the rest is the actual seat map
     */
    @Override
    public int getItemViewType(int position) {
        int col = position % (maxSeatPerRow + 1);
        return (col == 0) ? 0 : 1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        int holderSide = context.getResources().getDisplayMetrics().widthPixels / (maxSeatPerRow + 1);
        if (viewType == 0) {
            View itemView = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_seat_index, parent, false);
            holder = new ViewHolder(itemView);
            holder.seatIndexTextView = itemView.findViewById(R.id.text_view_seat_index);
            holder.seatIndexTextView.setTextSize((float) holderSide * 0.2f);
        } else if (viewType == 1) {
            holder = new ViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_seat, parent, false));
        }
        assert holder != null;
        holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(holderSide, holderSide));
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int row = position / (maxSeatPerRow + 1);
        int col = position % (maxSeatPerRow + 1);
        if (col == 0) {
            char rowLetter = (char) ('A' + row);
            holder.seatIndexTextView.setText(String.valueOf(rowLetter));
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
//            holder.seatTextView.setText(name);
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
                holder.itemView.setOnClickListener(v -> toggleSeat(seat));
            }
            if (selectedSeats.contains(seat)) {
                holder.itemView.setBackground(ContextCompat
                        .getDrawable(context, SeatAvailables.SELECTED.getBackgroundId()));
            }
        } else {
//            holder.seatTextView.setText("");
            holder.itemView.setBackground(ContextCompat
                    .getDrawable(context, R.drawable.ic_seat_empty));
            holder.itemView.setOnClickListener(null);
        }
    }
    private void toggleSeat(Seat seat) {
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
        rectangle.forEach(s -> notifyItemChanged(s.getRow() * (maxSeatPerRow + 1) + s.getCol() + 1));
        if (context instanceof SeatSelectionActivity) {
            ((SeatSelectionActivity) context).onSeatClickListener();
        }
        if (iSeatItemView != null) {
            iSeatItemView.onSeatClickListener(seat);
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
    @Override
    public int getItemCount() {
        return seatMatrix.size() * (maxSeatPerRow + 1);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView seatTextView;
        View seatView;
        TextView seatIndexTextView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
//            seatTextView = itemView.findViewById(R.id.seatTextView);//todo monospace font
        }
    }
}
