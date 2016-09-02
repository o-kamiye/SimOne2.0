package ng.com.tinweb.www.simone20;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ng.com.tinweb.www.simone20.today.TodayFragment;

/**
 * Created by kamiye on 02/09/2016.
 */
public class NavigationPagerAdapter extends FragmentPagerAdapter {

    private String[] navMenu;

    public NavigationPagerAdapter(FragmentManager fragmentManager, String[] navMenu) {
        super(fragmentManager);
        this.navMenu = navMenu;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        switch (position) {
            case 0:
                return TodayFragment.newInstance();
            case 1:
            case 2:
                return MainActivity.PlaceholderFragment.newInstance(position + 1);
        }
        return null;
    }

    @Override
    public int getCount() {
        return navMenu.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return navMenu[position];
    }
}
