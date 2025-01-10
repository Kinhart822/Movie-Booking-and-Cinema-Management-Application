package vn.edu.usth.mcma.frontend.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import lombok.Getter;

public class ImageDecoder {
    private final String TAG = ImageDecoder.class.getName();
    private String base64;
    @Getter
    private Bitmap result;
    public ImageDecoder(String base64) {
        this.base64 = base64;
        decode();
    }
    private void decode() {
        try {
            if (base64.contains(",")) {
                base64 = base64.split(",")[1];
            }
            byte[] byteArrayDecodedFromBase64 = Base64.decode(base64, Base64.DEFAULT);
            result = BitmapFactory.decodeByteArray(byteArrayDecodedFromBase64,0, byteArrayDecodedFromBase64.length);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "decode: error", e);
        }
    }
}
