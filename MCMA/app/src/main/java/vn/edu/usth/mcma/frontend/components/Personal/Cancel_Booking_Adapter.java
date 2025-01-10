package vn.edu.usth.mcma.frontend.components.Personal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.network.apis.BookingProcessAPIs.BookingAPI;
import vn.edu.usth.mcma.frontend.network.RetrofitService;

import android.app.AlertDialog;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Cancel_Booking_Adapter extends RecyclerView.Adapter<Cancel_Booking_ViewHolder> {

    Context context;
    List<Cancel_Booking_Item> items;

    public Cancel_Booking_Adapter(Context context, List<Cancel_Booking_Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Cancel_Booking_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Cancel_Booking_ViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_feedback_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Cancel_Booking_ViewHolder holder, int position) {
        Cancel_Booking_Item item = items.get(position);

        holder.nameView.setText(item.getMovie_name());
        holder.typeView.setText(item.getMovie_type());
//        holder.imageView.setImageResource(item.getMovie_image());
        Glide.with(context)
                .load(item.getMovieImageUrl())
                .placeholder(R.drawable.usthlogo)
                .into(holder.imageView);
//        holder.itemView.setOnClickListener(v -> {
//            showCancelBookingDialog();
//        });
        holder.itemView.setOnClickListener(v -> {
            showCancelBookingDialog(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //Dialog
//    private void showCancelBookingDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View dialogView = inflater.inflate(R.layout.dialog_cancel_booking_movie, null);
//
//        builder.setView(dialogView);
//
//        Button btn_yes = dialogView.findViewById(R.id.btn_cancel_yes);
//        Button btn_no = dialogView.findViewById(R.id.btn_cancel_no);
//
//        AlertDialog dialog = builder.create();
//
//        btn_yes.setOnClickListener(view -> {
//            dialog.dismiss();
//        });
//
//        btn_no.setOnClickListener(view -> {
//            dialog.dismiss();
//        });
//
//        dialog.show();
//    }

    private void showCancelBookingDialog(Cancel_Booking_Item item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_cancel_booking_movie, null);

        builder.setView(dialogView);

        Button btn_yes = dialogView.findViewById(R.id.btn_cancel_yes);
        Button btn_no = dialogView.findViewById(R.id.btn_cancel_no);

        AlertDialog dialog = builder.create();

        btn_yes.setOnClickListener(view -> {
            // Call cancelBooking API
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - hh:mm a", Locale.getDefault());
                Date movieStartDateTime = sdf.parse(item.getStartDateTime());
                long currentTime = System.currentTimeMillis();

                if (movieStartDateTime != null && movieStartDateTime.getTime() - currentTime >= 60 * 60 * 1000) {
                    cancelBooking(item.getBookingId());
                } else {
                    Toast.makeText(context, "You can only cancel bookings 60 minutes before the movie starts.", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                Toast.makeText(context, "Invalid movie start time format.", Toast.LENGTH_SHORT).show();
            }


            dialog.dismiss();
        });

        btn_no.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    // Method to call API
    private void cancelBooking(int bookingId) {
        RetrofitService retrofitService = new RetrofitService(context);
        BookingAPI bookingAPI = retrofitService.getRetrofit().create(BookingAPI.class);
        bookingAPI.cancelBooking(bookingId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Booking cancelled successfully!", Toast.LENGTH_SHORT).show();
                    removeItemFromList(bookingId);
                    showCancellationSuccessDialog();
                } else {
                    Toast.makeText(context, "Failed to cancel booking", Toast.LENGTH_SHORT).show();
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

    private void showCancellationSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Booking Cancelled");
        builder.setMessage("The booking has been cancelled. You have approximately 10 minutes to decide if you want to proceed with this cancellation. . After 10 minutes, the booking will be permanently deleted!");

        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss(); // Close the dialog when "OK" is clicked
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
