package ng.com.tinweb.www.simone20;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import ng.com.tinweb.www.simone20.contact.ContactListDialogFragment;
import ng.com.tinweb.www.simone20.data.contact.SimOneContact;
import ng.com.tinweb.www.simone20.data.group.SimOneGroup;
import ng.com.tinweb.www.simone20.databinding.ActivityMainBinding;
import ng.com.tinweb.www.simone20.group.GroupDialogFragment;
import ng.com.tinweb.www.simone20.group.GroupFragment;
import ng.com.tinweb.www.simone20.reminder.ReminderDialogFragment;

public class MainActivity extends AppCompatActivity implements
        ViewPager.OnPageChangeListener,
        ReminderDialogFragment.InteractionListener,
        GroupDialogFragment.FragmentInteractionListener,
        ContactListDialogFragment.FragmentInteractionListener,
        GroupFragment.FragmentInteractionListener {

    public static final String CONTACT_LIST_FRAGMENT_TAG = "search_result";

    private static final String ADD_REMINDER_FRAGMENT_TAG = "add_reminder";
    private static final String EDIT_REMINDER_FRAGMENT_TAG = "edit_reminder";
    private static final String ADD_GROUP_FRAGMENT_TAG = "add_new_group";

    private ActivityMainBinding activityBinding;
    private NavigationPagerAdapter pagerAdapter;
    private FragmentManager fragmentManager;
    private List<String> pageTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(activityBinding.toolbar);

        fragmentManager = getSupportFragmentManager();

        setUpViewPager();
        setUpBottomNav();
        handleSearchIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleSearchIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // TODO implement settings here
            Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        activityBinding.bottomNavigation.selectTab(position);
        pageTitles = Arrays.asList(getResources()
                .getStringArray(R.array.pageTitles));
        setTitle(pageTitles.get(position));
        pagerAdapter.refreshPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onReminderSet() {

        if (pageTitles != null) {

            pagerAdapter.refreshPage(pageTitles
                    .indexOf(getString(R.string.reminder_fragment_title)));
        }
    }

    @Override
    public void onGroupSet() {
        if (pageTitles != null) {
            pagerAdapter.refreshPage(pageTitles
                    .indexOf(getString(R.string.group_fragment_title)));
        }
    }

    @Override
    public void showReminderDialogFragment(SimOneContact contact, boolean isEditMode) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(isEditMode ? EDIT_REMINDER_FRAGMENT_TAG :
                ADD_REMINDER_FRAGMENT_TAG);
        if (prev != null) {
            fragmentTransaction.remove(prev);
        }
        fragmentTransaction.addToBackStack(null);
        ReminderDialogFragment dialogFragment = ReminderDialogFragment.getInstance(contact);
        dialogFragment.setEditMode(isEditMode);

        dialogFragment.show(fragmentTransaction, isEditMode ? EDIT_REMINDER_FRAGMENT_TAG :
                ADD_REMINDER_FRAGMENT_TAG);
        //addReminderFragment.show(fragmentTransaction, EDIT_REMINDER_FRAGMENT_TAG);
    }

    @Override
    public void showGroupDialogFragment(@Nullable SimOneGroup group) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(ADD_GROUP_FRAGMENT_TAG);
        if (prev != null) {
            fragmentTransaction.remove(prev);
        }
        GroupDialogFragment groupDialogFragment = GroupDialogFragment.getInstance(group);
        fragmentTransaction.add(groupDialogFragment, ADD_GROUP_FRAGMENT_TAG).commitNow();
    }

    private void setUpViewPager() {
        pagerAdapter =
                new NavigationPagerAdapter(getSupportFragmentManager(),
                        Arrays.asList(getResources().getStringArray(R.array.bottomNavMenu)));

        activityBinding.viewPager.setAdapter(pagerAdapter);
        activityBinding.viewPager.addOnPageChangeListener(this);
    }

    private void setUpBottomNav() {
        int[] colourResources = getResources().getIntArray(R.array.bottomNavColours);
        int[] imageResources = new int[]{
                R.drawable.today_icon,
                R.drawable.reminder_icon,
                R.drawable.group_icon
        };
        activityBinding.bottomNavigation.setUpWithViewPager(activityBinding.viewPager,
                colourResources, imageResources);
    }

    private void handleSearchIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String searchQuery = intent.getStringExtra(SearchManager.QUERY);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment prev = fragmentManager.findFragmentByTag(CONTACT_LIST_FRAGMENT_TAG);
            if (prev != null) {
                fragmentTransaction.remove(prev);
            }
            fragmentTransaction.addToBackStack(null);
            ContactListDialogFragment dialogFragment =
                    ContactListDialogFragment.getInstance(searchQuery);
            dialogFragment.show(fragmentTransaction, CONTACT_LIST_FRAGMENT_TAG);
        }
    }
}
