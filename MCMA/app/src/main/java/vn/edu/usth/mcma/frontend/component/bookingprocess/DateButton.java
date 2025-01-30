package vn.edu.usth.mcma.frontend.component.bookingprocess;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import vn.edu.usth.mcma.R;

@Getter
@Setter
public class DateButton extends CustomButton {
    private LocalDate date;
    public DateButton(@NonNull Context context) {
        super(context);
    }
}
