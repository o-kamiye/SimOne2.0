package ng.com.tinweb.www.simone20;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ng.com.tinweb.www.simone20.group.GroupFragment;
import ng.com.tinweb.www.simone20.reminder.ReminderFragment;
import ng.com.tinweb.www.simone20.today.TodayFragment;

/**
 * Created by kamiye on 02/09/2016.
 */
public class NavigationPagerAdapter extends FragmentPagerAdapter {

    private String[] navMenu = SimOneApplication.getNavMenu();

    public NavigationPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        switch (position) {
            case 0:
                return TodayFragment.newInstance();
            case 1:
                return ReminderFragment.newInstance();
            case 2:
                return GroupFragment.newInstance();
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
