package vn.edu.usth.mcma.frontend.component.Home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FilmPagerAdapter extends FragmentStateAdapter {

    public FilmPagerAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return switch (position) {
            case 1 -> new ComingSoonFragment();
            default -> new NowShowingFragment();
        };
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
