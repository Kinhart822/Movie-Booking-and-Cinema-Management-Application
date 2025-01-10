package vn.edu.usth.mcma.frontend.component.Home;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.mcma.R;

public class NowShowing_ViewHolder extends RecyclerView.ViewHolder {
    TextView nameView, typeView, timeView, age_limitView;
    ImageView filmView;
    Button bookingButton;

    public NowShowing_ViewHolder(View itemView, final FilmViewInterface filmViewInterface) {
        super(itemView);
        filmView = itemView.findViewById(R.id.film_image);
        nameView = itemView.findViewById(R.id.film_name);
        timeView = itemView.findViewById(R.id.film_time);
        age_limitView = itemView.findViewById(R.id.age_limit);
        typeView = itemView.findViewById(R.id.category_name);
        bookingButton = itemView.findViewById(R.id.btn_booking_film);

        // Sự kiện click cho toàn bộ itemView
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filmViewInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        filmViewInterface.onFilmSelected(position);
                    }
                }
            }
        });

        // Sự kiện click cho ImageView
        filmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filmViewInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        filmViewInterface.onFilmSelected(position);
                    }
                }
            }
        });
    }
}
