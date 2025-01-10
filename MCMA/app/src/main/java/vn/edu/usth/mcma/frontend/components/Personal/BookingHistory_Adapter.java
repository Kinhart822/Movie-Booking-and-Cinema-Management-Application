package vn.edu.usth.mcma.frontend.components.Personal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.Response.BookingResponse;

public class BookingHistory_Adapter extends RecyclerView.Adapter<BookingHistory_ViewHolder> {

    Context context;
//    List<BookingHistory_Item> items;
    private final List<BookingResponse> items;

    public BookingHistory_Adapter(Context context, List<BookingResponse> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public BookingHistory_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingHistory_ViewHolder(LayoutInflater.from(context).inflate(R.layout.booking_history_frame,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookingHistory_ViewHolder holder, int position) {
        BookingResponse bookingResponse = items.get(position);
        holder.numView.setText(bookingResponse.getBookingNo());
        holder.movieView.setText(bookingResponse.getMovieName());
        holder.start_dateView.setText(bookingResponse.getStartDateTime());
        holder.end_dateView.setText(bookingResponse.getEndDateTime());
        holder.statusView.setText(bookingResponse.getStatus());
//        holder.numView.setText(items.get(position).getBooking_num());
//        holder.movieView.setText(items.get(position).getBooking_movie());
//        holder.start_dateView.setText(items.get(position).getBooking_startdate());
//        holder.end_dateView.setText(items.get(position).getBooking_enddate());
//        holder.statusView.setText(items.get(position).getBooking_status());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
