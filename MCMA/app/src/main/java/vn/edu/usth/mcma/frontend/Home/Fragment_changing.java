package vn.edu.usth.mcma.frontend.Home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

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
                return new vn.edu.usth.mcma.frontend.LaunchtimeFragment();
            case 2:
                return new vn.edu.usth.mcma.frontend.Store.UI.StoreFragment();
            case 3:
                return new vn.edu.usth.mcma.frontend.PersonalFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {

        return 4;
    }
}
