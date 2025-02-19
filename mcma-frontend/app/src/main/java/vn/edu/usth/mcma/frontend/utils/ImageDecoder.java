package vn.edu.usth.mcma.frontend.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

public class ImageDecoder {
    private static final String TAG = ImageDecoder.class.getName();
    public static Bitmap decode(String base64) {
        try {
            if (base64.contains(",")) {
                base64 = base64.split(",")[1];
            }
            byte[] byteArrayDecodedFromBase64 = Base64.decode(base64, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(byteArrayDecodedFromBase64,0, byteArrayDecodedFromBase64.length);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "decode: error", e);
        }
        return null;
    }
}
