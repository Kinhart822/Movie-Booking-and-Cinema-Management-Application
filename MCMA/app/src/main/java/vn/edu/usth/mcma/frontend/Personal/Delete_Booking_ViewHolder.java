package vn.edu.usth.mcma.frontend.Personal;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import vn.edu.usth.mcma.R;

public class Delete_Booking_ViewHolder extends RecyclerView.ViewHolder {
    TextView nameView, typeView;
    ImageView imageView;

    public Delete_Booking_ViewHolder(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.theater_name);
        typeView = itemView.findViewById(R.id.theater_type);
        imageView = itemView.findViewById(R.id.theater_image);

    }
}
