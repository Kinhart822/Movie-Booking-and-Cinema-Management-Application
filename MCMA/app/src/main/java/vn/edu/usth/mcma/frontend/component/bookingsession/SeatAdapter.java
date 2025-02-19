package vn.edu.usth.mcma.frontend.component.bookingsession;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import lombok.Getter;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.constant.SeatAvailability;
import vn.edu.usth.mcma.frontend.constant.SeatAvailables;
import vn.edu.usth.mcma.frontend.model.response.SeatTypeItem;
import vn.edu.usth.mcma.frontend.model.response.SeatTypeResponse;
import vn.edu.usth.mcma.frontend.model.item.SeatItem;
import vn.edu.usth.mcma.frontend.utils.helper.SeatHelper;
import vn.edu.usth.mcma.frontend.utils.helper.SeatMatrixHelper;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.ViewHolder> {
    private final Context context;
    @Getter
    private final Map<String, SeatTypeItem> seatTypes;
    private final Map<Integer, Map<Integer, SeatItem>> seatMatrix;
    private final Map<Integer, Map<Integer, List<SeatItem>>> rootSeatMatrix;
    private final ISeatItemView iSeatItemView;
    @Getter
    private final int maxSeatPerRow;
    @Getter
    private final List<SeatItem> selectedSeats;
    @Getter
    private final List<SeatItem> selectedRootSeats;
    @Getter
    private final String nearestRow;
    @Getter
    private final String farthestRow;


    public SeatAdapter(
            Context context,
            Map<String, SeatTypeItem> seatTypes,
            List<SeatItem> seatItems,
            ISeatItemView iSeatItemView) {
        this.context = context;
        this.seatTypes = seatTypes;
        SeatMatrixHelper seatMatrixHelper = new SeatMatrixHelper(seatItems);
        this.seatMatrix = seatMatrixHelper.getSeatMatrix();
        this.rootSeatMatrix = seatMatrixHelper.getRootSeatMatrix();
        this.maxSeatPerRow = seatMatrixHelper.getMaxSeatPerRow();
        this.nearestRow = seatMatrixHelper.getNearestRow();
        this.farthestRow = seatMatrixHelper.getFarthestRow();
        this.iSeatItemView = iSeatItemView;
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
            for (SeatItem seat : Objects.requireNonNull(seatMatrix.get(row)).values()) {
                if (seat.getName() != null) {
                    holder.seatIndexTextView.setText(seat.getName().substring(0, 1));
                    return;
                }
            }
            holder.seatIndexTextView.setText(null);
            return;
        }
        SeatItem seat = Objects
                .requireNonNull(seatMatrix.get(row))
                .get(col - 1);
        assert seat != null;
        String name = seat.getName();
        String seatTypeId = seat.getTypeId();
        String availability = seat.getAvailability();
        if (name != null) {
            setSeatBackground(holder.itemView, SeatAvailability.getById(availability).getBackgroundId());
            holder.itemView.setOnClickListener(null);
            if (availability.equals(SeatAvailability.BUYABLE.name())) {
                setSeatBackground(holder.itemView, SeatAvailables.getById(seatTypeId).getBackgroundId());
                holder.itemView.setOnClickListener(v -> toggleSeat(seat));
            }
            if (selectedSeats.contains(seat)) {
                setSeatBackground(holder.itemView, SeatAvailables.SELECTED.getBackgroundId());
            }
        } else {
            setSeatBackground(holder.itemView, R.drawable.ic_seat_empty);
            holder.itemView.setOnClickListener(null);
        }

    }
    private void setSeatBackground(View view, int backgroundId) {
        view.setBackground(ContextCompat.getDrawable(context, backgroundId));
    }
    private void toggleSeat(SeatItem seat) {
        int rootRow = seat.getRootRow();
        int rootCol = seat.getRootCol();
        List<SeatItem> rectangle = Objects
                .requireNonNull(Objects
                        .requireNonNull(rootSeatMatrix
                                .get(rootRow))
                .get(rootCol));
        Optional<SeatItem> rootSeatOpt = rectangle
                .stream()
                .filter(s -> s.getRootRow() == rootRow && s.getRootCol() == rootCol)
                .findFirst();//if use find any -> rootSeat may not be in selectedRootSeats
        SeatItem rootSeat = null;
        if (rootSeatOpt.isPresent()) {
            rootSeat = rootSeatOpt.get();
        }
        // HashSet for better performance
        if (new HashSet<>(selectedSeats).containsAll(rectangle)) {
            selectedSeats.removeAll(rectangle);
            selectedRootSeats.remove(rootSeat);
        } else {
            selectedSeats.addAll(rectangle);
            selectedRootSeats.add(rootSeat);
        }
        rectangle.forEach(s -> notifyItemChanged(s.getRow() * (maxSeatPerRow + 1) + s.getCol() + 1));
        iSeatItemView.onSeatClickListener(seat);
    }
    @Override
    public int getItemCount() {
        return seatMatrix.size() * (maxSeatPerRow + 1);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView seatIndexTextView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            //todo consider monospace font
        }
    }
    public int getTotalAudienceCount() {
        return this.selectedRootSeats.stream()
                .mapToInt(SeatHelper::getNumberOfAudiencePerSeat)
                .sum();
    }
    public double getTotalSeatPrice() {
        return this.selectedRootSeats.stream()
                .mapToDouble(s -> Objects.requireNonNull(seatTypes
                                .get(s.getTypeId()))
                        .getUnitPrice())
                .sum();
    }
}
