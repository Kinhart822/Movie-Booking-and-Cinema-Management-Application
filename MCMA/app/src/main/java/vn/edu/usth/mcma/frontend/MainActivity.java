package vn.edu.usth.mcma.frontend;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.Login.LoginFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 mviewPager;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Mng muốn đăng nhập vào trang chủ lại nhiều lần thì thay name ở dòng 31 và thay giống name ở dòng 42 trong Login Fragment nhé
        SharedPreferences sharedPreferences = getSharedPreferences("123", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            // If not logged in
            navigateToLoginFragment();
            return;
        }

        mviewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.home_bottom_navigation);

        Fragment_changing adapter = new Fragment_changing(getSupportFragmentManager(), getLifecycle());
        mviewPager.setAdapter(adapter);
        mviewPager.setUserInputEnabled(false);

        mviewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
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
                        bottomNavigationView.getMenu().findItem(R.id.personal_page).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home_page) {
                    mviewPager.setCurrentItem(0, true);
                    return true;
                }
                if (item.getItemId() == R.id.showtimes_page) {
                    mviewPager.setCurrentItem(1, true);
                    return true;
                }
                if (item.getItemId() == R.id.store_page) {
                    mviewPager.setCurrentItem(2, true);
                    return true;
                }
                if (item.getItemId() == R.id.personal_page) {
                    mviewPager.setCurrentItem(3, true);
                    return true;
                }
                return false;

            }
        });


    }

    private void navigateToLoginFragment() {
        // Chuyển hướng đến LoginFragment
        Fragment loginFragment = new LoginFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, loginFragment);
        transaction.commit();
    }

    // close icon to home page in personal
    public void close_to_home_page(){
        mviewPager.setCurrentItem(0,true);
    }

    public void close_to_showtimes_page() {
        mviewPager.setCurrentItem(1, true);
    }

    public void close_to_store_page() {
        mviewPager.setCurrentItem(2, true);
    }
    public void close_to_personal_page() {
        mviewPager.setCurrentItem(3, true);
    }
}