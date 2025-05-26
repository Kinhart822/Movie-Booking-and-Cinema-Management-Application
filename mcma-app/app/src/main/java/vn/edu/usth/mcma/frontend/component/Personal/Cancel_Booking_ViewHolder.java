package vn.edu.usth.mcma.frontend.component.Personal;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.mcma.R;

public class Cancel_Booking_ViewHolder extends RecyclerView.ViewHolder {
    TextView nameView, typeView;
    ImageView imageView;

    public Cancel_Booking_ViewHolder(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.theater_name);
        typeView = itemView.findViewById(R.id.theater_type);
        imageView = itemView.findViewById(R.id.theater_image);

    }
}
