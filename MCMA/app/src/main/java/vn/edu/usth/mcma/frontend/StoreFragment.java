package vn.edu.usth.mcma.frontend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import vn.edu.usth.mcma.R;


public class StoreFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_store, container, false);

        ImageButton mImageView = v.findViewById(R.id.menu_button);

        DrawerLayout mDrawerLayout = v.findViewById(R.id.store_fragment);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout != null && !mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        ImageButton searchbutton = v.findViewById(R.id.searching_button);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), Search_Activity.class );
                startActivity(i);
            }
        });

        return v;
    }
}