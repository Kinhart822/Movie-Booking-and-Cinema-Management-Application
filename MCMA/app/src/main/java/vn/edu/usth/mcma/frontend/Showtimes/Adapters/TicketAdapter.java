package vn.edu.usth.mcma.frontend.Showtimes.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Showtimes.Models.TicketItem;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {
    private List<TicketItem> ticketItems;
    private int guestQuantity;
    private OnTotalTicketsChangedListener listener;

    public interface OnTotalTicketsChangedListener {
        void onTotalTicketsChanged(List<TicketItem> ticketItems);
    }

    public TicketAdapter(List<TicketItem> ticketItems, int guestQuantity) {
        this.ticketItems = ticketItems;
        this.guestQuantity = guestQuantity;
    }

    public void setTotalTicketsChangedListener(OnTotalTicketsChangedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_item_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        TicketItem item = ticketItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return ticketItems != null ? ticketItems.size() : 0;
    }

    public List<TicketItem> getSelectedTicketItems() {
        return ticketItems;
    }

    private void updateQuantity(int position, int delta) {
        TicketItem item = ticketItems.get(position);
        int totalCurrentQuantity = ticketItems.stream()
                .mapToInt(TicketItem::getQuantity)
                .sum();

        int newTotalQuantity = totalCurrentQuantity + delta;

        if (newTotalQuantity <= guestQuantity) {
            int newQuantity = Math.max(0, item.getQuantity() + delta);
            item.setQuantity(newQuantity);
            notifyItemChanged(position);

            if (listener != null) {
                listener.onTotalTicketsChanged(ticketItems);
            }
        } else {
            // Optional: show a toast or notification
        }
    }
    public void updateItems(List<TicketItem> newItems) {
        this.ticketItems.clear();
        this.ticketItems.addAll(newItems);
        notifyDataSetChanged();
    }


    class TicketViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private TextView quantityText;
        private ImageView plusButton;
        private ImageView minusButton;
        private ImageView ticketImage;
        private TextView ticketPrice;

        TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.ticket_name);
            quantityText = itemView.findViewById(R.id.ticket_quantity);
            plusButton = itemView.findViewById(R.id.plus_button);
            minusButton = itemView.findViewById(R.id.minus_button);
            ticketImage = itemView.findViewById(R.id.ticket_image);
            ticketPrice = itemView.findViewById(R.id.ticket_price);

            plusButton.setOnClickListener(v -> updateQuantity(getAdapterPosition(), 1));
            minusButton.setOnClickListener(v -> updateQuantity(getAdapterPosition(), -1));
        }

        void bind(TicketItem item) {
            nameText.setText(item.getType().getName());
            quantityText.setText(String.valueOf(item.getQuantity()));
            ticketImage.setImageResource(item.getType().getImageResourceId());

            // Định dạng số tiền với dấu phẩy và thêm "đ"
//            int price = item.getType().getPrice();
//            String formattedPrice = formatPrice(price);
            ticketPrice.setText(String.valueOf(item.getPrice()));
        }

        // Phương thức định dạng số tiền
//        private String formatPrice(int price) {
//            NumberFormat format = NumberFormat.getInstance(new Locale("vi", "VN"));
//            return format.format(price) + "đ";
//        }
    }
}
