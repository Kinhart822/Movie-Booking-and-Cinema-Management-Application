package vn.edu.usth.mcma.frontend.Showtimes.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Showtimes.Models.TicketItem;
import vn.edu.usth.mcma.frontend.Showtimes.Utils.PriceCalculator;

public class TicketDetailsAdapter extends RecyclerView.Adapter<TicketDetailsAdapter.TicketDetailsViewHolder> {
    private List<TicketDetailsItem> ticketDetailsItems;

    public static class TicketDetailsItem {
        private int quantity;
        private String ticketType;
        private int totalPrice;

        public TicketDetailsItem(int quantity, String ticketType, int totalPrice) {
            this.quantity = quantity;
            this.ticketType = ticketType;
            this.totalPrice = totalPrice;
        }

        public int getQuantity() { return quantity; }
        public String getTicketType() { return ticketType; }
        public int getTotalPrice() { return totalPrice; }
    }

    public TicketDetailsAdapter(List<TicketDetailsItem> ticketDetailsItems) {
        this.ticketDetailsItems = ticketDetailsItems;
    }

    @NonNull
    @Override
    public TicketDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ticket_details, parent, false);
        return new TicketDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketDetailsViewHolder holder, int position) {
        TicketDetailsItem item = ticketDetailsItems.get(position);
        holder.quantityAndTypeTicket.setText(String.format("%d x %s",
                item.getQuantity(), item.getTicketType()));
        holder.ticketPricePerQuantity.setText(PriceCalculator.formatPrice(item.getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return ticketDetailsItems.size();
    }

    public static class TicketDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView quantityAndTypeTicket;
        TextView ticketPricePerQuantity;

        public TicketDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            quantityAndTypeTicket = itemView.findViewById(R.id.quantityAndTypeTicket);
            ticketPricePerQuantity = itemView.findViewById(R.id.ticketPricePerQuantity);
        }
    }
}