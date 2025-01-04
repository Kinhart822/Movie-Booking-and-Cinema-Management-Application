package vn.edu.usth.mcma.frontend.Personal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.mcma.R;

import android.app.AlertDialog;

import java.util.List;

public class Delete_Booking_Adapter extends RecyclerView.Adapter<Delete_Booking_ViewHolder> {

    Context context;
    List<Delete_Booking_Item> items;

    public Delete_Booking_Adapter(Context context, List<Delete_Booking_Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Delete_Booking_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Delete_Booking_ViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_feedback_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Delete_Booking_ViewHolder holder, int position) {
        Delete_Booking_Item item = items.get(position);

        holder.nameView.setText(item.getMovie_name());
        holder.typeView.setText(item.getMovie_type());
        holder.imageView.setImageResource(item.getMovie_image());

        holder.itemView.setOnClickListener(v -> {
            showCancelBookingDialog();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void showCancelBookingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_delete_booking, null);

        builder.setView(dialogView);

        Button btn_yes = dialogView.findViewById(R.id.btn_delete_yes);
        Button btn_no = dialogView.findViewById(R.id.btn_delete_no);

        AlertDialog dialog = builder.create();

        btn_yes.setOnClickListener(view -> {
            dialog.dismiss();
        });

        btn_no.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }
}
