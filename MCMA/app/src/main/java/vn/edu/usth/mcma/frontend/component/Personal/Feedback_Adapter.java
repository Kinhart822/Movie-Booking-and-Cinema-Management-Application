package vn.edu.usth.mcma.frontend.component.Personal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.dto.response.MovieRespondResponse;

public class Feedback_Adapter extends RecyclerView.Adapter<Feedback_ViewHolder> {

    Context context;
//    List<Feedback_Item> items;
    private final List<MovieRespondResponse> items;

    public Feedback_Adapter(Context context, List<MovieRespondResponse> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Feedback_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Feedback_ViewHolder(LayoutInflater.from(context).inflate(R.layout.feedback_frame,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Feedback_ViewHolder holder, int position) {
        MovieRespondResponse movieRespondResponse = items.get(position);
        holder.nameView.setText(movieRespondResponse.getMovieName());
        holder.ratingView.setText(String.valueOf(movieRespondResponse.getRatingStar()));
        holder.commentView.setText(movieRespondResponse.getContent());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
