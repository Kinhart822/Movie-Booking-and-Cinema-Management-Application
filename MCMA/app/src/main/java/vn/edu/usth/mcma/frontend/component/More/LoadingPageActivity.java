package vn.edu.usth.mcma.frontend.component.More;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.main.MainActivity;

public class LoadingPageActivity extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            Intent intent = new Intent(LoadingPageActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 3);///todo 3000
    }
}