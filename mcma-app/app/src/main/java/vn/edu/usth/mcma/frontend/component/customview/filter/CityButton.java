package vn.edu.usth.mcma.frontend.component.customview.filter;

import android.content.Context;

import androidx.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityButton extends CustomFilterButton {
    private Long cityId;
    public CityButton(@NonNull Context context) {
        super(context);
    }
}
