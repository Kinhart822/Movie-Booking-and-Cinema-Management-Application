package vn.edu.usth.mcma.frontend.Feedback;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingResponse;
import vn.edu.usth.mcma.frontend.constants.IntentKey;

public class RatingMovie_Adapter extends RecyclerView.Adapter<RatingMovie_ViewHolder> {
//    private final Context context;
//    private final List<RatingMovie_Item> items;
//    private final OnItemClickListener onItemClickListener;

    Context context;
    private List<BookingResponse> items;

//    public interface OnItemClickListener {
//        void onItemClick(String movieName, String movieType, int movieImage);
//    }

    public interface OnItemClickListener {
        void onItemClick(String movieName, String movieType, String movieImage);
    }

    public RatingMovie_Adapter(Context context, List<BookingResponse> items) {
        this.context = context;
        this.items = items;
    }

//    public RatingMovie_Adapter(Context context, List<BookingResponse> items, OnItemClickListener onItemClickListener) {
//        this.context = context;
//        this.items = items;
//        this.onItemClickListener = onItemClickListener;
//    }

//    public RatingMovie_Adapter(Context context, List<RatingMovie_Item> items, OnItemClickListener onItemClickListener) {
//        this.context = context;
//        this.items = items;
//        this.onItemClickListener = onItemClickListener;
//    }

    @NonNull
    @Override
    public RatingMovie_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RatingMovie_ViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_feedback_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RatingMovie_ViewHolder holder, int position) {
//        RatingMovie_Item item = items.get(position);
        BookingResponse bookingResponse = items.get(position);

//        // Gán dữ liệu cho các view
//        holder.nameView.setText(item.getMovie_name());
//        holder.typeView.setText(item.getMovie_type());
//        holder.imageView.setImageResource(item.getMovie_image());

        // Load image using Glide
        Glide.with(context)
                .load(bookingResponse.getImageUrlMovie())
                .into(holder.imageView);
        holder.nameView.setText(bookingResponse.getMovieName());
        holder.typeView.setText(bookingResponse.getBookingNo());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RatingMovie_Activity.class);
            intent.putExtra(IntentKey.movieId.name(), bookingResponse.getMovieId()); // Pass movieId
            intent.putExtra(IntentKey.movie_name.name(), bookingResponse.getMovieName());
            intent.putExtra(IntentKey.movie_type.name(), bookingResponse.getBookingNo());
            intent.putExtra(IntentKey.movie_image.name(), bookingResponse.getImageUrlMovie());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
