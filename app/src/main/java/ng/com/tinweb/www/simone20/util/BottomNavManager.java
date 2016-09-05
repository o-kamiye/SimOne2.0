package ng.com.tinweb.www.simone20.util;

import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.databinding.ActivityMainBinding;

/**
 * Created by kamiye on 03/09/2016.
 */
public class BottomNavManager {

    private String[] navMenu;
    private ActivityMainBinding binding;

    public BottomNavManager(ActivityMainBinding binding, String[] navMenu) {
        this.navMenu = navMenu;
        this.binding = binding;
        setUpNavigationMenu();
    }

    public BottomNavigationView getBottomNav() {
        return binding.bottomNavigation;
    }

    private void setUpNavigationMenu () {

        BottomNavigationItem bottomNavigationItem = new BottomNavigationItem("Today",
                ContextCompat.getColor(binding.getRoot().getContext(),
                        R.color.colorPrimary), R.drawable.today_icon);
        BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem("Reminders",
                ContextCompat.getColor(binding.getRoot().getContext(), R.color.colorPrimary),
                R.drawable.reminder_icon);
        BottomNavigationItem bottomNavigationItem2 = new BottomNavigationItem("Groups",
                ContextCompat.getColor(binding.getRoot().getContext(), R.color.colorPrimary),
                R.drawable.group_icon);

        binding.bottomNavigation.addTab(bottomNavigationItem);
        binding.bottomNavigation.addTab(bottomNavigationItem1);
        binding.bottomNavigation.addTab(bottomNavigationItem2);
    }
}
