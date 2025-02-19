package vn.edu.usth.mcma.frontend.component.customview.filter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import vn.edu.usth.mcma.R;

/*
 * tends to be used in linear layout
 */
public class CustomFilterButton extends AppCompatButton {
    public CustomFilterButton(@NonNull Context context) {
        super(context);
        init();
    }
    private void init() {
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearParams.setMargins(20, 0, 20, 0);
        this.setLayoutParams(linearParams);
        this.setTextSize(17);
        this.setPadding(50, 5, 50, 5);
        this.setMinWidth(0);
        this.setMinHeight(0);
        this.setAllCaps(false);
        // unselected state
        ShapeDrawable unselected = new ShapeDrawable(new RoundRectShape(new float[]{15f, 15f, 15f, 15f, 15f, 15f, 15f, 15f}, null, null));
        unselected.getPaint().setColor(Color.TRANSPARENT);
        unselected.getPaint().setStyle(Paint.Style.STROKE);
        unselected.getPaint().setStrokeWidth(2);
        unselected.getPaint().setColor(getResources().getColor(R.color.color_secondary));
        // selected state
        ShapeDrawable selected = new ShapeDrawable(new RoundRectShape(new float[]{15f, 15f, 15f, 15f, 15f, 15f, 15f, 15f}, null, null));
        selected.getPaint().setColor(getResources().getColor(R.color.color_primary));
        selected.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        selected.getPaint().setStrokeWidth(2);
        selected.getPaint().setColor(getResources().getColor(R.color.color_primary));

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{-android.R.attr.state_selected}, unselected);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selected);
        this.setBackground(stateListDrawable);
    }
}
