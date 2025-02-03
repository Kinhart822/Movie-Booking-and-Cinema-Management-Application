package vn.edu.usth.mcma.frontend.component.customview.filter;

import android.content.Context;

import androidx.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreButton extends CustomFilterButton {
    private Long genreId;
    public GenreButton(@NonNull Context context) {
        super(context);
    }
}
