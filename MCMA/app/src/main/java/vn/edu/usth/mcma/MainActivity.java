package vn.edu.usth.mcma;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

//    private ViewPager2 mviewPager;
//
//    private BottomNavigationView bottomNavigationView;
////
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_main);
//
////        SharedPreferences sharedPreferences = getSharedPreferences("Islogin", MODE_PRIVATE);
////        boolean isLoggedIn = sharedPreferences.getBoolean("IsLoggedIn", false);
////
////        if (!isLoggedIn) {
////            navigateToLoginFragment();
////            return;
////        }
//
//        mviewPager = findViewById(R.id.view_pager);
//        bottomNavigationView = findViewById(R.id.home_bottom_navigation);
//
//        Fragment_home_changing adapter = new Fragment_home_changing(getSupportFragmentManager(), getLifecycle());
//        mviewPager.setAdapter(adapter);
//        mviewPager.setUserInputEnabled(false);
//
//        mviewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position)
//                {
//                    case 0:
//                        bottomNavigationView.getMenu().findItem(R.id.home_page).setChecked(true);
//                        break;
//                    case 1:
//                        bottomNavigationView.getMenu().findItem(R.id.showtimes_page).setChecked(true);
//                        break;
//                    case 2:
//                        bottomNavigationView.getMenu().findItem(R.id.store_page).setChecked(true);
//                        break;
//
//                    case 3:
//                        bottomNavigationView.getMenu().findItem(R.id.personal_page).setChecked(true);
//                        break;
//
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                super.onPageScrollStateChanged(state);
//            }
//        });
//
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                if (item.getItemId() == R.id.home_page) {
//                    mviewPager.setCurrentItem(0, true); // Switch to the first fragment
//                    return true;
//                }
//                if (item.getItemId() == R.id.showtimes_page) {
//                    mviewPager.setCurrentItem(1, true); // Switch to the first fragment
//                    return true;
//                }
//                if (item.getItemId() == R.id.store_page) {
//                    mviewPager.setCurrentItem(2, true); // Switch to the first fragment
//                    return true;
//                }
//                if (item.getItemId() == R.id.personal_page) {
//                    mviewPager.setCurrentItem(3, true); // Switch to the first fragment
//                    return true;
//                }
//                return false;
//
//            }
//        });


    }