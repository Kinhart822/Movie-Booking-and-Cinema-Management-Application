package vn.edu.usth.mcma.frontend.component.bookingprocess;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreButton extends AppCompatButton {
    private Long genreId;
    public GenreButton(@NonNull Context context) {
        super(context);
    }
}
