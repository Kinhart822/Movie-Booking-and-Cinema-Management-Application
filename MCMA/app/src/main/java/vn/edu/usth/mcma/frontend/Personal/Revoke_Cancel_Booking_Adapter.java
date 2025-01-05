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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs.BookingAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;

import android.app.AlertDialog;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class Revoke_Cancel_Booking_Adapter extends RecyclerView.Adapter<Revoke_Cancel_Booking_ViewHolder> {

    Context context;
    List<Revoke_Cancel_Booking_Item> items;

    public Revoke_Cancel_Booking_Adapter(Context context, List<Revoke_Cancel_Booking_Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Revoke_Cancel_Booking_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Revoke_Cancel_Booking_ViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_feedback_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Revoke_Cancel_Booking_ViewHolder holder, int position) {
        Revoke_Cancel_Booking_Item item = items.get(position);

        holder.nameView.setText(item.getMovie_name());
        holder.typeView.setText(item.getMovie_type());

        Glide.with(context)
                .load(item.getMovieImageUrl())
                .placeholder(R.drawable.usthlogo)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            showRevokeCancelBookingDialog(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Dialog
    private void showRevokeCancelBookingDialog(Revoke_Cancel_Booking_Item item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_revoke_cancel_booking, null);

        builder.setView(dialogView);

        Button btn_yes = dialogView.findViewById(R.id.btn_revoke_yes);
        Button btn_no = dialogView.findViewById(R.id.btn_revoke_no);

        AlertDialog dialog = builder.create();

        btn_yes.setOnClickListener(view -> {
            revokeCanceledBooking(item.getBookingId(), item);
            dialog.dismiss();
        });

        btn_no.setOnClickListener(view -> {
            dialog.dismiss();
        });

        // Show the dialog
        dialog.show();
    }

    private void revokeCanceledBooking(int bookingId, Revoke_Cancel_Booking_Item item) {
        RetrofitService retrofitService = new RetrofitService(context);
        BookingAPI bookingAPI = retrofitService.getRetrofit().create(BookingAPI.class);
        bookingAPI.revokeCancelBooking(bookingId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Booking reinstated successfully!", Toast.LENGTH_SHORT).show();
                    removeItemFromList(bookingId);
                    showRevokeCancellationSuccessDialog(item);
                } else {
//                    Toast.makeText(context, "Failed to reinstate booking", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeItemFromList(int bookingId) {
        int position = -1;

        // Find the item position by bookingId
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getBookingId() == bookingId) {
                position = i;
                break;
            }
        }

        if (position != -1) {
            // Remove the item and notify adapter
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    private void showRevokeCancellationSuccessDialog(Revoke_Cancel_Booking_Item item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Booking Reinstated");
        builder.setMessage("Your booking for " + item.getMovie_name() + " has been successfully reinstated. Booking Number: " + item.getBookingId());

        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

