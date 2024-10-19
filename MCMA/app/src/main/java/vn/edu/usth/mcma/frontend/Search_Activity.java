package vn.edu.usth.mcma.frontend;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContentProviderCompat;
import androidx.core.graphics.Insets;
import android.view.View;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.widget.ImageButton;

import vn.edu.usth.mcma.R;

public class Search_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ImageButton backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(view -> {
            onBackPressed();
        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}