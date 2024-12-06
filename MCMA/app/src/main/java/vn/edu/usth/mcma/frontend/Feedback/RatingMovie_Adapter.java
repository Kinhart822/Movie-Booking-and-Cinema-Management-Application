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
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.MovieRespondResponse;

public class RatingMovie_Adapter extends RecyclerView.Adapter<RatingMovie_ViewHolder> {

    Context context;
    private List<BookingResponse> items;


    public RatingMovie_Adapter(Context context, List<BookingResponse> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RatingMovie_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RatingMovie_ViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_feedback_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RatingMovie_ViewHolder holder, int position) {
        BookingResponse bookingResponse = items.get(position);

        // Load image using Glide
        Glide.with(context)
                .load(bookingResponse.getImageUrlMovie())
                .into(holder.imageView);
        holder.nameView.setText(bookingResponse.getMovieName());
        holder.typeView.setText(bookingResponse.getBookingNo());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RatingMovie_Activity.class);
            intent.putExtra("movieId", bookingResponse.getMovieId()); // Pass movieId
            intent.putExtra("movie_name", bookingResponse.getMovieName());
            intent.putExtra("movie_type", bookingResponse.getBookingNo());
            intent.putExtra("movie_image", bookingResponse.getImageUrlMovie());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
