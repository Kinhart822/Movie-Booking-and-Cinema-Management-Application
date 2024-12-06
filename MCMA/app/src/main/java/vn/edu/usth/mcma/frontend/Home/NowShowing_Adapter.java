package vn.edu.usth.mcma.frontend.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.NowShowingResponse;

public class NowShowing_Adapter extends RecyclerView.Adapter<NowShowing_ViewHolder> {
    private final FilmViewInterface filmViewInterface;
    private final Context context;
    private final List<NowShowingResponse> items;

    public NowShowing_Adapter(Context context, List<NowShowingResponse> items, FilmViewInterface filmViewInterface) {
        this.context = context;
        this.items = items;
        this.filmViewInterface = filmViewInterface;
    }

    @NonNull
    @Override
    public NowShowing_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NowShowing_ViewHolder(LayoutInflater.from(context).inflate(R.layout.now_showing_film_frame, parent, false), filmViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull NowShowing_ViewHolder holder, int position) {
        NowShowingResponse nowShowingResponse = items.get(position);
        List<String> genres = nowShowingResponse.getMovieGenreNameList();
        if (genres != null && !genres.isEmpty() ) {
            holder.typeView.setText(genres.get(0)); // Use a valid index, e.g., 0 or a relevant value
        } else {
            holder.typeView.setText(R.string.unknown_genre); // Fallback text
        }
        holder.nameView.setText(nowShowingResponse.getMovieName());
//        holder.typeView.setText(nowShowingResponse.getMovieGenreNameList().get(position));
        holder.timeView.setText(String.format("%d min", nowShowingResponse.getMovieLength()));
        holder.age_limitView.setText(nowShowingResponse.getPublishedDate());
        Glide.with(context)
                .load(nowShowingResponse.getImageUrl())
                .into(holder.filmView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
