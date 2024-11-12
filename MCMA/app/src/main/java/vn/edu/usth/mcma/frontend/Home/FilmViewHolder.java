package vn.edu.usth.mcma.frontend.Home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.mcma.R;

public class FilmViewHolder extends RecyclerView.ViewHolder{

    ImageView filmView;
    TextView nameView;

    public FilmViewHolder(@NonNull View itemView) {
        super(itemView);
        filmView = itemView.findViewById(R.id.film_image);
        nameView = itemView.findViewById(R.id.file_name);
    }
}
