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
class NavigationPagerAdapter extends FragmentPagerAdapter {

    private String[] navMenu;
    private TodayFragment todayFragment;
    private ReminderFragment reminderFragment;
    private GroupFragment groupFragment;

    NavigationPagerAdapter(FragmentManager fragmentManager, String[] navMenu) {
        super(fragmentManager);
        this.navMenu = navMenu;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        switch (position) {
            case 0:
                if (todayFragment == null) {
                    todayFragment = new TodayFragment();
                }
                return todayFragment;
            case 1:
                if (reminderFragment == null) {
                    reminderFragment = new ReminderFragment();
                }
                return reminderFragment;
            case 2:
                if (groupFragment == null) {
                    groupFragment = new GroupFragment();
                }
                return groupFragment;
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

    void refreshPage(int position) {
        switch (position) {
            case 1:
                reminderFragment.loadReminders();
        }
    }
}
