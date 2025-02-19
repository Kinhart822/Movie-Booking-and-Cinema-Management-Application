package vn.edu.usth.mcma.frontend.component.ShowtimesOld.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.R;

public class SeatDetailsAdapter extends RecyclerView.Adapter<SeatDetailsAdapter.SeatDetailsViewHolder> {
    private List<SeatDetailItem> seatDetails;

    public static class SeatDetailItem {
        private String typeAndPosition;
        private String price;

        public SeatDetailItem(String typeAndPosition, String price) {
            this.typeAndPosition = typeAndPosition;
            this.price = price;
        }

        public String getTypeAndPosition() {
            return typeAndPosition;
        }

        public String getPrice() {
            return price;
        }
    }

    public SeatDetailsAdapter(List<SeatDetailItem> seatDetails) {
        this.seatDetails = seatDetails;
    }

    @NonNull
    @Override
    public SeatDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_seat_details, parent, false);
        return new SeatDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatDetailsViewHolder holder, int position) {
        SeatDetailItem item = seatDetails.get(position);
        holder.typeAndPositionTV.setText(item.getTypeAndPosition());
        holder.priceTV.setText(item.getPrice());
    }

    @Override
    public int getItemCount() {
        return seatDetails.size();
    }

    public static class SeatDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView typeAndPositionTV;
        TextView priceTV;

        public SeatDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            typeAndPositionTV = itemView.findViewById(R.id.numberTypeAndPosition);
            priceTV = itemView.findViewById(R.id.seatPricePerQuantity);
        }
    }
}
