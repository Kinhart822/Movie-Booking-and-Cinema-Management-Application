package vn.edu.usth.mcma.frontend.Home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.mcma.R;

public class FilmViewHolder extends RecyclerView.ViewHolder {
    TextView nameView;
    ImageView filmView;

    public FilmViewHolder(View itemView, final FilmViewInterface filmViewInterface) {
        super(itemView);
        filmView = itemView.findViewById(R.id.film_image);
        nameView = itemView.findViewById(R.id.film_name);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filmViewInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        filmViewInterface.onItemClick(position);
                    }
                }
            }
        });
    }
}
