package vn.edu.usth.mcma.frontend.component.customview.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

public class ScreenView extends View {
    private Paint paint;
    private Path path;

    public ScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(0xFFFFFFFF);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        path = new Path();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // Define the curved path for the screen
        path.reset();
        path.moveTo(0, height * 1.0f); // Start from left
        path.quadTo(width / 2f, height * 0.0f, width, height * 1.0f); // Curve to right

        canvas.drawPath(path, paint);
    }
}
