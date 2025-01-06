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
import java.util.Set;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat.AvailableSeatResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat.HeldSeatResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat.UnavailableSeatResponse;
import vn.edu.usth.mcma.frontend.Showtimes.UI.SeatSelectionActivity;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {
    private final Object[][] seatMatrix;
    private final Context context;
    private final OnSeatSelectedListener listener;
    private final Set<AvailableSeatResponse> selectedSeats = new HashSet<>();
    private final int maxSeats;

    public interface OnSeatSelectedListener {
        void onSeatSelected(AvailableSeatResponse seat);
    }

    public SeatAdapter(
            Object[][] seatMatrix,
            Context context,
            OnSeatSelectedListener listener,
            int maxSeats
    ) {
        this.seatMatrix = seatMatrix;
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
    }

//    @SuppressLint("SetTextI18n")
//    @Override
//    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
//
//        int totalCols = seatMatrix.length;
//        int  totalRows= seatMatrix[0].length;
//
//        int row = position / totalRows;
//        int col = position % totalCols;
//
//        Object seat = seatMatrix[row][col];
//
//        if (seat instanceof AvailableSeatResponse) {
//            AvailableSeatResponse availableSeat = (AvailableSeatResponse) seat;
//            holder.seatTextView.setText(availableSeat.getAvailableSeat());
//            updateSeatBackground(holder, availableSeat);
//
//            holder.itemView.setOnClickListener(v -> {
//                if (!"Unavailable".equalsIgnoreCase(availableSeat.getSeatStatus()) && !"Held".equalsIgnoreCase(availableSeat.getSeatStatus())) {
//                    toggleSeatSelection(availableSeat, holder, row, col);
//                }
//            });
//        } else if (seat instanceof UnavailableSeatResponse) {
//            UnavailableSeatResponse unavailableSeat = (UnavailableSeatResponse) seat;
//            holder.seatTextView.setText(unavailableSeat.getUnAvailableSeat());
//            updateSeatBackground(holder, unavailableSeat);
//        } else if (seat instanceof HeldSeatResponse) {
//            HeldSeatResponse heldSeat = (HeldSeatResponse) seat;
//            holder.seatTextView.setText(heldSeat.getHeldSeat());
//            updateSeatBackground(holder, heldSeat);
//        } else {
//            holder.itemView.setBackground(null);
//            holder.itemView.setOnClickListener(null);
//            holder.seatTextView.setText("");
//            return;
//        }
//    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        int totalRows = seatMatrix.length; // Rows represent the horizontal layout
        int totalCols = seatMatrix[0].length; // Columns represent the vertical layout

        int row = position / totalCols; // Correcting the row calculation
        int col = position % totalCols; // Correcting the column calculation

        Object seat = seatMatrix[row][col];

        if (seat instanceof AvailableSeatResponse) {
            AvailableSeatResponse availableSeat = (AvailableSeatResponse) seat;
            holder.seatTextView.setText(availableSeat.getAvailableSeat());
            updateSeatBackground(holder, availableSeat);

            holder.itemView.setOnClickListener(v -> {
                if (!"Unavailable".equalsIgnoreCase(availableSeat.getSeatStatus()) && !"Held".equalsIgnoreCase(availableSeat.getSeatStatus())) {
                    toggleSeatSelection(availableSeat, holder, row, col);
                }
            });
        } else if (seat instanceof UnavailableSeatResponse) {
            UnavailableSeatResponse unavailableSeat = (UnavailableSeatResponse) seat;
            holder.seatTextView.setText(unavailableSeat.getUnAvailableSeat());
            updateSeatBackground(holder, unavailableSeat);
        } else if (seat instanceof HeldSeatResponse) {
            HeldSeatResponse heldSeat = (HeldSeatResponse) seat;
            holder.seatTextView.setText(heldSeat.getHeldSeat());
            updateSeatBackground(holder, heldSeat);
        } else {
            holder.itemView.setBackground(null);
            holder.itemView.setOnClickListener(null);
            holder.seatTextView.setText("");
        }
    }

    private void toggleSeatSelection(AvailableSeatResponse seat, SeatViewHolder holder, int row, int col) {
        // Kiểm tra nếu ghế là loại "Couple"
        if ("couple".equalsIgnoreCase(seat.getAvailableSeatsType())) {
            // Tìm ghế đôi liền kề
            int[] adjacentPosition = findAdjacentCoupleSeat(row, col);

            if (adjacentPosition != null) {
                int adjacentRow = adjacentPosition[0];
                int adjacentCol = adjacentPosition[1];
                AvailableSeatResponse adjacentSeat = (AvailableSeatResponse) seatMatrix[adjacentRow][adjacentCol];

                // Chọn hoặc bỏ chọn cả hai ghế
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

                // Cập nhật giao diện của cả hai ghế
                notifyItemChanged(row * seatMatrix[0].length + col);
                notifyItemChanged(adjacentRow * seatMatrix[0].length + adjacentCol);
            }
        } else {
            // Xử lý chọn ghế thường
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

            // Cập nhật giao diện ghế
            notifyItemChanged(row * seatMatrix[0].length + col);
        }

        // Cập nhật danh sách ghế đã chọn trong Activity
        if (context instanceof SeatSelectionActivity) {
            ((SeatSelectionActivity) context).updateSelectedSeatsDisplay();
        }

        // Gọi listener nếu có
        if (listener != null) {
            listener.onSeatSelected(seat);
        }
    }


    private int[] findAdjacentCoupleSeat(int row, int col) {
        // Tìm ghế liền kề bên phải
        if (col + 1 < 10) {
            Object adjacentSeat = seatMatrix[row][col + 1];
            if (adjacentSeat instanceof AvailableSeatResponse &&
                    "Couple".equalsIgnoreCase(((AvailableSeatResponse) adjacentSeat).getAvailableSeatsType())) {
                return new int[]{row, col + 1};
            }
        }

        // Tìm ghế liền kề bên trái
        if (col - 1 >= 0) {
            Object adjacentSeat = seatMatrix[row][col - 1];
            if (adjacentSeat instanceof AvailableSeatResponse &&
                    "Couple".equalsIgnoreCase(((AvailableSeatResponse) adjacentSeat).getAvailableSeatsType())) {
                return new int[]{row, col - 1};
            }
        }

        return null;
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

    public Set<AvailableSeatResponse> getSelectedSeats() {
        return selectedSeats;
    }


    @Override
    public int getItemCount() {
//        return 100;
//        return seatMatrix[0].length * seatMatrix.length;
        return seatMatrix.length * seatMatrix[0].length; // Rows * Columns

    }

    static class SeatViewHolder extends RecyclerView.ViewHolder {
        TextView seatTextView;

        SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            seatTextView = itemView.findViewById(R.id.seatTextView);

        }
    }
}
