package vn.edu.usth.mcma.frontend.component.bookingprocess;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;

import androidx.annotation.NonNull;

import vn.edu.usth.mcma.R;

public class TimeButton extends CustomButton{
    public TimeButton(@NonNull Context context) {
        super(context);
        init();
    }
    private void init() {
        StateListDrawable  stateListDrawable = (StateListDrawable) this.getBackground();
        ShapeDrawable unselected = (ShapeDrawable) stateListDrawable.getStateDrawable(0);
        if (unselected == null) {
            return;
        }
        unselected.getPaint().setColor(getResources().getColor(R.color.color_secondary));
        unselected.getPaint().setStyle(Paint.Style.STROKE);
        unselected.getPaint().setStrokeWidth(2);
        unselected.getPaint().setColor(getResources().getColor(R.color.color_secondary));
    }
}
