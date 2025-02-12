package vn.edu.usth.mcma.frontend.component.customview.item;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import lombok.Getter;
import lombok.Setter;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.model.item.PaymentMethodItem;
import vn.edu.usth.mcma.frontend.utils.ImageDecoder;

public class PaymentMethodLayout extends LinearLayout {
    @Setter
    private PaymentMethodItem item;
    @Getter
    private RadioButton radioButton;
    public PaymentMethodLayout(Context context) {
        super(context);
        init();
    }
    private void init() {
        this.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        this.setOrientation(LinearLayout.HORIZONTAL);
        int padding = dpToPx(8);
        this.setPadding(padding, padding, padding, padding);

        //icon
        ImageView imageView = new ImageView(this.getContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(50), dpToPx(50)));
        if (item.getImageBase64() != null) {
            Glide
                    .with(this.getContext())
                    .load(ImageDecoder.decode(item.getImageBase64()))
                    .placeholder(R.drawable.placeholder1080x1920)
                    .error(R.drawable.placeholder1080x1920)
                    .into(imageView);
        }

        //description
        TextView textView = new TextView(this.getContext());
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 15f);
        textParams.setMargins(dpToPx(10), 0, dpToPx(10), 0);
        textView.setLayoutParams(textParams);
        textView.setText(R.string.hint_payment_method_description);
        textView.setTextSize(16f);

        //radio button
        radioButton = new RadioButton(this.getContext());
        radioButton.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(50), dpToPx(50)));

        //add
        this.addView(imageView);
        this.addView(textView);
        this.addView(radioButton);
    }
    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }
}
