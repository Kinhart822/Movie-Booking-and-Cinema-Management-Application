package vn.edu.usth.mcma.frontend.component.HomeOld;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.usth.mcma.frontend.component.Feedback.FeedbackFragment;
import vn.edu.usth.mcma.frontend.component.Personal.PersonalFragment;
import vn.edu.usth.mcma.frontend.component.home.HomeFragment;
import vn.edu.usth.mcma.frontend.component.showtimes.ShowtimesFragment;
import vn.edu.usth.mcma.frontend.component.Store.UI.StoreFragment;

public class PrimaryPageAdapter extends FragmentStateAdapter {
    public PrimaryPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return switch (position) {
            case 1 -> new ShowtimesFragment();
            case 2 -> new StoreFragment();
            case 3 -> new FeedbackFragment();
            case 4 -> new PersonalFragment();
            default -> new HomeFragment();
        };
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
