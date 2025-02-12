package vn.edu.usth.mcma.frontend.component.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.component.auth.SignInFragment;
import vn.edu.usth.mcma.frontend.component.Notification.Notification_Activity;
import vn.edu.usth.mcma.frontend.network.AuthPrefsManager;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ImageButton menuButton, searchButton, notificationButton;
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.main);
        menuButton = findViewById(R.id.button_menu);
        searchButton = findViewById(R.id.button_search);
        notificationButton = findViewById(R.id.button_notification);
        viewPager2 = findViewById(R.id.view_pager_2);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        //todo
        AuthPrefsManager authPrefsManager = new AuthPrefsManager(this);
        if (!authPrefsManager.isLoggedIn()) {
            navigateToSignInFragment();
            return;
        }

        prepareTopBar();
        setupDrawerNavigation();
        prepareViewPage2AndBottomNavigationView();
    }
    private void prepareTopBar() {
        menuButton.setOnClickListener(v -> {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        searchButton.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, SearchMovieActivity.class);
            startActivity(i);
        });
        notificationButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Notification_Activity.class);
            startActivity(intent);
        });
    }
    private void setupDrawerNavigation() {
        LinearLayout toHomeFragment = findViewById(R.id.home_side_navigation);
        toHomeFragment.setOnClickListener(view -> {
            viewPager2.setCurrentItem(0, false);
            drawerLayout.closeDrawer(GravityCompat.START);
        });
        LinearLayout toShowtimesFragment = findViewById(R.id.showtimes_side_navigation);
        toShowtimesFragment.setOnClickListener(view -> {
            viewPager2.setCurrentItem(1, false);
            drawerLayout.closeDrawer(GravityCompat.START);
        });
        LinearLayout toStoreFragment = findViewById(R.id.store_side_navigation);
        toStoreFragment.setOnClickListener(view -> {
            viewPager2.setCurrentItem(2, false);
            drawerLayout.closeDrawer(GravityCompat.START);
        });
        LinearLayout toFeedbackFragment = findViewById(R.id.feedback_side_navigation);
        toFeedbackFragment.setOnClickListener(view -> {
            viewPager2.setCurrentItem(3, false);
            drawerLayout.closeDrawer(GravityCompat.START);
        });
        LinearLayout toPersonalFragment = findViewById(R.id.account_side_navigation);
        toPersonalFragment.setOnClickListener(view -> {
            viewPager2.setCurrentItem(4, false);
            drawerLayout.closeDrawer(GravityCompat.START);
        });
    }
    private void prepareViewPage2AndBottomNavigationView() {
        viewPager2.setAdapter(new MainAdapter(getSupportFragmentManager(), getLifecycle()));
        viewPager2.setUserInputEnabled(false);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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
                viewPager2.setCurrentItem(0, false);
                return true;
            }
            if (item.getItemId() == R.id.showtimes_page) {
                viewPager2.setCurrentItem(1, false);
                return true;
            }
            if (item.getItemId() == R.id.store_page) {
                viewPager2.setCurrentItem(2, false);
                return true;
            }
            if (item.getItemId() == R.id.feedback_page) {
                viewPager2.setCurrentItem(3, false);
                return true;
            }
            if (item.getItemId() == R.id.personal_page) {
                viewPager2.setCurrentItem(4, false);
                return true;
            }
            return false;
        });
    }
    private void navigateToSignInFragment() {
        Fragment signInFragment = new SignInFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, signInFragment);
        transaction.commit();
    }
}
