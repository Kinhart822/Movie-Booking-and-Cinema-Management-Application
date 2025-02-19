package vn.edu.usth.mcma.frontend.utils.diff;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;
import java.util.Objects;

import vn.edu.usth.mcma.frontend.dto.movie.MovieDetailShort;

public class MovieDiffCallback extends DiffUtil.Callback {
    private final List<MovieDetailShort> oldList;
    private final List<MovieDetailShort> newList;

    public MovieDiffCallback(List<MovieDetailShort> oldList, List<MovieDetailShort> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldList.get(oldItemPosition).getId(), newList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
