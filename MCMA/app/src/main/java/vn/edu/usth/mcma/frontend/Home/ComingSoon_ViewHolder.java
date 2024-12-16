package vn.edu.usth.mcma.frontend.Home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.mcma.R;

public class ComingSoon_ViewHolder extends RecyclerView.ViewHolder {
    TextView nameView, typeView, timeView, age_limitView;
    ImageView filmView;

    public ComingSoon_ViewHolder(View itemView, final FilmViewInterface filmViewInterface) {
        super(itemView);
        filmView = itemView.findViewById(R.id.film_image);
        nameView = itemView.findViewById(R.id.film_name);
        timeView = itemView.findViewById(R.id.film_time);
        age_limitView = itemView.findViewById(R.id.age_limit);
        typeView = itemView.findViewById(R.id.category_name);

        // Add click listener to filmView as well
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
