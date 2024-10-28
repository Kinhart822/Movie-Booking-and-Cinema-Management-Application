package vn.edu.usth.mcma.frontend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import vn.edu.usth.mcma.R;

public class LaunchtimeFragment extends Fragment {

    private LinearLayout theaterList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_launchtime, container, false);

        // Handle navigation drawer menu button
        ImageButton mImageView = v.findViewById(R.id.menu_button);
        DrawerLayout mDrawerLayout = v.findViewById(R.id.launchtime_fragment);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout != null && !mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        // Handle search button click
        ImageButton searchbutton = v.findViewById(R.id.searching_button);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), Search_Activity.class);
                startActivity(i);
            }
        });

        // Reference to the theater list layout
        theaterList = v.findViewById(R.id.theater_list_layout);

        // City buttons
        Button btnHCM = v.findViewById(R.id.btn_hcm);
        Button btnHanoi = v.findViewById(R.id.btn_hanoi);
        Button btnHue = v.findViewById(R.id.btn_hue);

        // Set listeners to handle city button clicks
        btnHCM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTheaterList("hcm");
            }
        });

        btnHanoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTheaterList("hanoi");
            }
        });

        btnHue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTheaterList("hue");
            }
        });

        return v;
    }

    // Method to update the theater list based on the selected city
    private void updateTheaterList(String city) {
        theaterList.removeAllViews();

        // Add theater items dynamically based on the city
        if (city.equals("hcm")) {
            // Populate the theater list with TPHCM theaters
            addTheaterItem("BHD Star 3.2", "Lầu 5, Siêu Thị Vincom 3/2, Quận 10, TPHCM");
            addTheaterItem("CGV Aeon Tân Phú", "30 Bờ Bao Tân Thắng, Sơn Kỳ, Tân Phú, TPHCM");
            // Add more TPHCM theaters here
        } else if (city.equals("hanoi")) {
            // Populate the theater list with Hà Nội theaters
            addTheaterItem("CGV Vincom Bà Triệu", "191 Bà Triệu, Hai Bà Trưng, Hà Nội");
            addTheaterItem("Lotte Cinema Đống Đa", "229 Tây Sơn, Đống Đa, Hà Nội");
            // Add more Hà Nội theaters here
        } else if (city.equals("hue")) {
            // Populate the theater list with Huế theaters
            addTheaterItem("Cinestar Huế", "25 Hai Bà Trưng, Huế");
            // Add more Huế theaters here
        }
    }

    // Helper method to dynamically add theater items with click listener
    private void addTheaterItem(String theaterName, String theaterAddress) {
        View theaterItemView = getLayoutInflater().inflate(R.layout.theater_item, null);

        // Find and set theater name and address
        TextView theaterNameView = theaterItemView.findViewById(R.id.theater_name);
        TextView theaterAddressView = theaterItemView.findViewById(R.id.theater_address);

        theaterNameView.setText(theaterName);
        theaterAddressView.setText(theaterAddress);

        // Set click listener to navigate to TheaterDetailActivity
        theaterItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), TheaterScheduleActivity.class);
                intent.putExtra("THEATER_NAME", theaterName); // Pass theater name or any other identifier
                startActivity(intent);
            }
        });

        // Add the view to the theater list layout
        theaterList.addView(theaterItemView);
    }
}

