package vn.edu.usth.mcma.frontend.component.ShowtimesOld.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import vn.edu.usth.mcma.R;

public class TicketDetailsAdapter extends RecyclerView.Adapter<TicketDetailsAdapter.TicketDetailsViewHolder> {
    private List<TicketDetailsItem> ticketDetailsItems;

    public static class TicketDetailsItem {
        private int quantity;
        private String ticketType;
        private double totalPrice;

        public TicketDetailsItem(int quantity, String ticketType, double totalPrice) {
            this.quantity = quantity;
            this.ticketType = ticketType;
            this.totalPrice = totalPrice;
        }

        public int getQuantity() { return quantity; }
        public String getTicketType() { return ticketType; }
        public double getTotalPrice() { return totalPrice; }
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

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull TicketDetailsViewHolder holder, int position) {
        TicketDetailsItem item = ticketDetailsItems.get(position);
        holder.quantityAndTypeTicket.setText(String.format("%d x %s",
                item.getQuantity(), item.getTicketType()));
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        holder.ticketPricePerQuantity.setText(currencyFormat.format(item.getTotalPrice()));
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