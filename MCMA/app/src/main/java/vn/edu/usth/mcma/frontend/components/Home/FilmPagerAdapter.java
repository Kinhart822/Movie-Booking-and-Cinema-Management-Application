package vn.edu.usth.mcma.frontend.components.Home;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FilmPagerAdapter extends FragmentStateAdapter {

    public FilmPagerAdapter(Fragment fragment) {
        super(fragment);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new NowShowingFragment();
            case 1:
                return new ComingSoonFragment();
            default:
                return new NowShowingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
