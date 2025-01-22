package vn.edu.usth.mcma.frontend.component.ShowtimesOld.UI;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import vn.edu.usth.mcma.R;

public class QuantityTicketDialog extends Dialog {
    private ImageButton btnClose;
    private Button btnContinue;
    private EditText getGuestQuantity;
    private OnDialogActionListener listener;

    public interface OnDialogActionListener {
        void onContinueClicked(int guestQuantity);
        void onCloseClicked();
    }

    public QuantityTicketDialog(@NonNull Context context, OnDialogActionListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_quantity_ticket_layout);
        setupDialog();
        initializeViews();
    }

    private void setupDialog() {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setDimAmount(0.5f);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
        setCanceledOnTouchOutside(false);
    }

    private void initializeViews() {
        btnClose = findViewById(R.id.btnClose);
        btnContinue = findViewById(R.id.btnContinue);
        getGuestQuantity = findViewById(R.id.getGuestQuantity);

        btnClose.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCloseClicked();
            }
            dismiss();
        });

        btnContinue.setOnClickListener(v -> {
            try {
                int guestQuantity = Integer.parseInt(getGuestQuantity.getText().toString());

                if (guestQuantity <= 0) {
                    Toast.makeText(getContext(), "Please enter quantity >= 1", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (listener != null) {
                    listener.onContinueClicked(guestQuantity);
                }
                dismiss();
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Please enter the integer form", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
