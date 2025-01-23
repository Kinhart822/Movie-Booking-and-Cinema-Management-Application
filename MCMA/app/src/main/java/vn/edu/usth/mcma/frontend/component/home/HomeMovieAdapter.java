package vn.edu.usth.mcma.frontend.component.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.usth.mcma.frontend.component.HomeOld.ComingSoonFragment;
import vn.edu.usth.mcma.frontend.component.HomeOld.NowShowingFragment;

public class HomeMovieAdapter extends FragmentStateAdapter {

    public HomeMovieAdapter(Fragment fragment) {
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
