package vn.edu.usth.mcma.frontend.Personal;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.mcma.R;

public class BookingHistory_ViewHolder extends RecyclerView.ViewHolder {
    TextView timeView;
    TextView siteView;
    TextView item_nameView;
    TextView quantityView;

    public BookingHistory_ViewHolder(@NonNull View itemView) {
        super(itemView);
        timeView = itemView.findViewById(R.id.booking_time);
        siteView = itemView.findViewById(R.id.booking_site);
        item_nameView = itemView.findViewById(R.id.booking_item);
        quantityView = itemView.findViewById(R.id.booking_quantity);
    }
}
