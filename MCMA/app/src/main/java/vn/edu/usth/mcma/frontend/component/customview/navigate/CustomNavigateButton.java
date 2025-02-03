package vn.edu.usth.mcma.frontend.component.customview.navigate;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import vn.edu.usth.mcma.R;

/*
 * tends to be used in linear layout
 */
public class CustomNavigateButton extends AppCompatButton {
    public CustomNavigateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public CustomNavigateButton(@NonNull Context context) {
        super(context);
        init();
    }
    private void init() {
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        this.setLayoutParams(linearParams);
        this.setTextSize(17);
        this.setTextColor(Color.WHITE);
        this.setAllCaps(false);
        this.setPadding(50, 5, 50, 5);
        this.setMinWidth(0);
        this.setMinHeight(0);
        ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(new float[]{15f, 15f, 15f, 15f, 15f, 15f, 15f, 15f}, null, null));
        drawable.getPaint().setStyle(Paint.Style.FILL);
        drawable.getPaint().setColor(getResources().getColor(R.color.color_primary));

        // disabled state
        ShapeDrawable disabled = new ShapeDrawable(new RoundRectShape(new float[]{15f, 15f, 15f, 15f, 15f, 15f, 15f, 15f}, null, null));
        disabled.getPaint().setColor(R.color.disabled);
        // enabled state
        ShapeDrawable enabled = new ShapeDrawable(new RoundRectShape(new float[]{15f, 15f, 15f, 15f, 15f, 15f, 15f, 15f}, null, null));
        enabled.getPaint().setColor(getResources().getColor(R.color.color_primary));

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, disabled);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, enabled);
        this.setBackground(stateListDrawable);
    }
}
