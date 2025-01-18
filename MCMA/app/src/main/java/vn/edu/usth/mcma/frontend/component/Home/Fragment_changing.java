package vn.edu.usth.mcma.frontend.component.Home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.usth.mcma.frontend.component.Feedback.FeedbackFragment;
import vn.edu.usth.mcma.frontend.component.Personal.PersonalFragment;
import vn.edu.usth.mcma.frontend.component.Showtimes.UI.LaunchtimeFragment;
import vn.edu.usth.mcma.frontend.component.Store.UI.StoreFragment;

// todo: rename to primary page adapter
public class Fragment_changing extends FragmentStateAdapter {
    public Fragment_changing(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new LaunchtimeFragment();
            case 2:
                return new StoreFragment();
            case 3:
                return new FeedbackFragment();
            case 4:
                return new PersonalFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
