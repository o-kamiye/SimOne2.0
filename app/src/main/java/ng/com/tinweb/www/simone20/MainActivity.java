package ng.com.tinweb.www.simone20;

import android.databinding.DataBindingUtil;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

import ng.com.tinweb.www.simone20.databinding.ActivityMainBinding;
import ng.com.tinweb.www.simone20.util.BottomNavManager;

public class MainActivity extends AppCompatActivity {


    private NavigationPagerAdapter navigationPagerAdapter;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(activityMainBinding.toolbar);

        setUpViewPager();
        setUpBottomNav();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpViewPager() {
        String[] bottomNavTabs = getBottomNavMenu();
        navigationPagerAdapter = new NavigationPagerAdapter(getSupportFragmentManager(), bottomNavTabs);

        // Set up the ViewPager with the sections adapter.
        activityMainBinding.container.setAdapter(navigationPagerAdapter);
    }

    private String[] getBottomNavMenu() {
        return getResources().getStringArray(R.array.bottom_nav_tabs);
    }

    private void setUpBottomNav() {
        //BottomNavManager bottomNavManager = new BottomNavManager(activityMainBinding, getBottomNavMenu());
        //BottomNavigationView bottomNavigation = bottomNavManager.getBottomNav();
        activityMainBinding.bottomNavigation.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {
                Toast.makeText(MainActivity.this, "Item " +index +" clicked", Toast.LENGTH_SHORT).show();
            }
        });
        int[] colourResources = getResources().getIntArray(R.array.bottomNavColours);
        int[] imageResources = new int[] {
                R.drawable.today_icon,
                R.drawable.reminder_icon,
                R.drawable.group_icon
        };
        activityMainBinding.bottomNavigation.setUpWithViewPager(activityMainBinding.container, colourResources, imageResources);
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

}
