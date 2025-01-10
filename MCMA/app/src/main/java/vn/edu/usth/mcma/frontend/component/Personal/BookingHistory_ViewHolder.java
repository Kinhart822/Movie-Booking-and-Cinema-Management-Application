package vn.edu.usth.mcma.frontend.component.Personal;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.mcma.R;

public class BookingHistory_ViewHolder extends RecyclerView.ViewHolder {
    TextView numView;
    TextView movieView;
    TextView start_dateView;
    TextView end_dateView;
    TextView statusView;

    public BookingHistory_ViewHolder(@NonNull View itemView) {
        super(itemView);
        numView = itemView.findViewById(R.id.booking_number_input);
        movieView = itemView.findViewById(R.id.booking_movie_input);
        start_dateView = itemView.findViewById(R.id.booking_startdate_input);
        end_dateView = itemView.findViewById(R.id.booking_enddate_input);
        statusView = itemView.findViewById(R.id.booking_status_input);
    }
}
