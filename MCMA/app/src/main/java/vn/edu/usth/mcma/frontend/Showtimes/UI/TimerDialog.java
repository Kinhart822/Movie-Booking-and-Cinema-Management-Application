package vn.edu.usth.mcma.frontend.Showtimes.UI;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import vn.edu.usth.mcma.R;

public class TimerDialog extends Dialog {
    private ImageButton btnClose;
    private Button btnUnderstand;
    private TextView tvMessage;
    private OnDialogActionListener listener;

    public interface OnDialogActionListener {
        void onUnderstandClicked();
        void onCloseClicked();
    }

    public TimerDialog(@NonNull Context context, OnDialogActionListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_timer_layout);

        // Make dialog background semi-transparent
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setDimAmount(0.5f);

        // Set dialog width to match parent with margins
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);

        // Initialize views
        btnClose = findViewById(R.id.btnClose);
        btnUnderstand = findViewById(R.id.btnUnderstand);
        tvMessage = findViewById(R.id.tvMessage);

        // Set click listeners
        btnClose.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCloseClicked();
            }
            dismiss();
        });

        btnUnderstand.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUnderstandClicked();
            }
            dismiss();
        });

        // Prevent dialog from being dismissed when clicking outside
        setCanceledOnTouchOutside(false);
    }
}
