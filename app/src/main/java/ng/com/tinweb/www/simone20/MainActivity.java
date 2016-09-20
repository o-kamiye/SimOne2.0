package ng.com.tinweb.www.simone20;

import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ng.com.tinweb.www.simone20.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        activityMainBinding.bottomNavigation.selectTab(position);
        String[] pageTitles = SimOne.getPageTitles();
        setTitle(pageTitles[position]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    private void setUpViewPager() {
        navigationPagerAdapter = new NavigationPagerAdapter(getSupportFragmentManager());

        activityMainBinding.container.setAdapter(navigationPagerAdapter);
        activityMainBinding.container.addOnPageChangeListener(this);
    }

    private void setUpBottomNav() {
        int[] colourResources = getResources().getIntArray(R.array.bottomNavColours);
        int[] imageResources = new int[] {
                R.drawable.today_icon,
                R.drawable.reminder_icon,
                R.drawable.group_icon
        };
        activityMainBinding.bottomNavigation.setUpWithViewPager(activityMainBinding.container,
                colourResources, imageResources);
    }

}
