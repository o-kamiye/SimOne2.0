package ng.com.tinweb.www.simone20.reminder;

import android.app.SearchManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.databinding.FragmentReminderBinding;
import ng.com.tinweb.www.simone20.util.LinearLayoutDecorator;

/**
 * Created by kamiye on 08/09/2016.
 */
public class ReminderFragment extends Fragment implements IReminderView,
        ReminderActionsListener {

    private IReminderPresenter reminderPresenter;
    private FragmentReminderBinding fragmentBinding;
    private Menu activityMenu;
    private SearchView searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initialisePresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.reminderPresenter = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        activityMenu = menu;
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reminder,
                container, false);

        setUpFragment();
        return fragmentBinding.getRoot();
    }

    @Override
    public void setWeeklyReminders(int total) {
        fragmentBinding.weeklyRemindersTextView.setText(getResources()
                .getQuantityString(R.plurals.no_of_calls_this_week,
                        total, total));
    }

    @Override
    public void showEditReminderPopUp() {
        Toast.makeText(getContext(), "Edit reminder pop will appear here", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDeleteSuccessInfo() {
        // TODO implement delete successful message here
    }

    @Override
    public void showDeleteErrorInfo() {
        // TODO implement delete error message here
    }

    @Override
    public void onEditAction(String contactId) {
        reminderPresenter.editReminder(contactId);
    }

    @Override
    public void onDeleteAction(String contactId) {
        reminderPresenter.deleteReminder(contactId);
    }

    private void initialisePresenter() {
        this.reminderPresenter = new ReminderPresenter(this);
    }

    private void setUpFragment() {
        reminderPresenter.setWeeklyReminderCount();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragmentBinding.weeklyRemindersRecyclerView.setLayoutManager(linearLayoutManager);
        fragmentBinding.weeklyRemindersRecyclerView.setAdapter(new ReminderAdapter(this));
        fragmentBinding.weeklyRemindersRecyclerView.addItemDecoration(new LinearLayoutDecorator(getContext(), null));

        fragmentBinding.remindersFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });
    }
}
