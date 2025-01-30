package vn.edu.usth.mcma.frontend.component.bookingprocess;

import android.content.Context;

import androidx.annotation.NonNull;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateButton extends CustomButton {
    private LocalDate date;
    public DateButton(@NonNull Context context) {
        super(context);
    }
}
