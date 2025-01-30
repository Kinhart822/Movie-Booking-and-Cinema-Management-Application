package vn.edu.usth.mcma.frontend.component.bookingprocess;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import lombok.Getter;
import lombok.Setter;
import vn.edu.usth.mcma.R;

@Getter
@Setter
public class GenreButton extends CustomButton {
    private Long genreId;
    public GenreButton(@NonNull Context context) {
        super(context);
    }
}
