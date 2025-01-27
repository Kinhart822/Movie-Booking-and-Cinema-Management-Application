package vn.edu.usth.mcma.frontend.component.common;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreButton extends AppCompatButton {
    private Long genreId;
    private boolean isSelected = false;
    public GenreButton(@NonNull Context context) {
        super(context);
    }
}
