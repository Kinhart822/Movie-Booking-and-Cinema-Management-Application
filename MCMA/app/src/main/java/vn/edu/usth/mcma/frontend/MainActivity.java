package vn.edu.usth.mcma.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.Home.Fragment_changing;
import vn.edu.usth.mcma.frontend.component.auth.SignInFragment;
import vn.edu.usth.mcma.frontend.component.Notification.Notification_Activity;
import vn.edu.usth.mcma.frontend.network.AuthPrefsManager;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 mViewPager;
    private DrawerLayout mDrawerLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AuthPrefsManager authPrefsManager = new AuthPrefsManager(this);
        if (!authPrefsManager.isLoggedIn()) {
            navigateToSignInFragment();
            return;
        }

        // Khởi tạo DrawerLayout
        mDrawerLayout = findViewById(R.id.main_activity);

        // Menu button mở Drawer
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(view -> {
            if (!mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // Notification button
        ImageButton notificationButton = findViewById(R.id.notification_button);
        notificationButton.setOnClickListener(view -> {
            // Dùng Intent để chuyển đến Notification_Activity
            Intent intent = new Intent(MainActivity.this, Notification_Activity.class);
            startActivity(intent);
        });

        // Navigation Drawer click listeners
        setupDrawerNavigation();

        // Khởi tạo ViewPager và BottomNavigationView
        mViewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.home_bottom_navigation);

        Fragment_changing adapter = new Fragment_changing(getSupportFragmentManager(), getLifecycle());
        mViewPager.setAdapter(adapter);
        mViewPager.setUserInputEnabled(false);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.home_page).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.showtimes_page).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.store_page).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.feedback_page).setChecked(true);
                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.personal_page).setChecked(true);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home_page) {
                mViewPager.setCurrentItem(0, true);
                return true;
            }
            if (item.getItemId() == R.id.showtimes_page) {
                mViewPager.setCurrentItem(1, true);
                return true;
            }
            if (item.getItemId() == R.id.store_page) {
                mViewPager.setCurrentItem(2, true);
                return true;
            }
            if (item.getItemId() == R.id.feedback_page) {
                mViewPager.setCurrentItem(3, true);
                return true;
            }
            if (item.getItemId() == R.id.personal_page) {
                mViewPager.setCurrentItem(4, true);
                return true;
            }
            return false;
        });
    }

    private void setupDrawerNavigation() {
        LinearLayout toHomeFragment = findViewById(R.id.home_side_navigation);
        toHomeFragment.setOnClickListener(view -> {
            closeToHomePage();
            mDrawerLayout.closeDrawer(GravityCompat.START);
        });
        LinearLayout toShowtimesFragment = findViewById(R.id.showtimes_side_navigation);
        toShowtimesFragment.setOnClickListener(view -> {
            closeToShowtimesPage();
            mDrawerLayout.closeDrawer(GravityCompat.START);
        });
        LinearLayout toStoreFragment = findViewById(R.id.store_side_navigation);
        toStoreFragment.setOnClickListener(view -> {
            closeToStorePage();
            mDrawerLayout.closeDrawer(GravityCompat.START);
        });
        LinearLayout toPersonalFragment = findViewById(R.id.personal_side_navigation);
        toPersonalFragment.setOnClickListener(view -> {
            closeToPersonalPage();
            mDrawerLayout.closeDrawer(GravityCompat.START);
        });
        LinearLayout toFeedbackFragment = findViewById(R.id.feedback_side_navigation);
        toFeedbackFragment.setOnClickListener(view -> {
            closeToFeedbackPage();
            mDrawerLayout.closeDrawer(GravityCompat.START);
        });
    }

    private void navigateToSignInFragment() {
        Fragment signInFragment = new SignInFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, signInFragment);
        transaction.commit();
    }

    public void closeToHomePage() {
        mViewPager.setCurrentItem(0, true);
    }
    public void closeToShowtimesPage() {
        mViewPager.setCurrentItem(1, true);
    }
    public void closeToStorePage() {
        mViewPager.setCurrentItem(2, true);
    }
    public void closeToFeedbackPage() {
        mViewPager.setCurrentItem(3, true);
    }
    public void closeToPersonalPage() {
        mViewPager.setCurrentItem(4, true);
    }
}
