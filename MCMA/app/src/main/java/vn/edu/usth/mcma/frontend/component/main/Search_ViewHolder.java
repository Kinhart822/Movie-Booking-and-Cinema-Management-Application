package vn.edu.usth.mcma.frontend.component.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.mcma.R;

public class Search_ViewHolder extends RecyclerView.ViewHolder {
    TextView nameView, typeView, timeView, age_limitView;
    ImageView filmView;

    public Search_ViewHolder(View itemView, final SearchViewInterface searchViewInterface) {
        super(itemView);
        filmView = itemView.findViewById(R.id.film_image);
        nameView = itemView.findViewById(R.id.film_name);
        timeView = itemView.findViewById(R.id.film_time);
        age_limitView = itemView.findViewById(R.id.age_limit);
        typeView = itemView.findViewById(R.id.category_name);

        itemView.setOnClickListener(view -> {
            if (searchViewInterface != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    searchViewInterface.onItemClick(position);
                }
            }
        });
        filmView.setOnClickListener(view -> {
            if (searchViewInterface != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    searchViewInterface.onItemClick(position);
                }
            }
        });
    }
}
