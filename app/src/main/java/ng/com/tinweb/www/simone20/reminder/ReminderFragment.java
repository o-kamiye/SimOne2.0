package ng.com.tinweb.www.simone20.reminder;

import android.app.SearchManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.SimOne;
import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;
import ng.com.tinweb.www.simone20.databinding.FragmentReminderBinding;
import ng.com.tinweb.www.simone20.util.LinearLayoutDecorator;

/**
 * Created by kamiye on 08/09/2016.
 */
public class ReminderFragment extends Fragment implements IReminderView,
        ReminderActionsListener {

    private static final String EDIT_REMINDER_FRAGMENT_TAG = "edit_reminder";

    private IReminderPresenter reminderPresenter;
    private FragmentReminderBinding fragmentBinding;
    private SearchView searchView;

    @Inject
    SimOneReminder simOneReminder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimOne.get(getActivity().getApplication())
                .getAppComponent()
                .inject(this);

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

        reminderPresenter.loadReminders();
        return fragmentBinding.getRoot();
    }

    @Override
    public void onRemindersLoaded(List<SimOneReminder> simOneReminders) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragmentBinding.weeklyRemindersRecyclerView.setLayoutManager(linearLayoutManager);
        fragmentBinding.weeklyRemindersRecyclerView.setAdapter(new ReminderAdapter(simOneReminders, this));
        fragmentBinding.weeklyRemindersRecyclerView.addItemDecoration(new LinearLayoutDecorator(getContext(), null));

        fragmentBinding.remindersFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });
    }

    @Override
    public void onReminderLoadingError() {
        Log.e("REMINDER_ERROR", "Error loading reminders");
    }

    @Override
    public void setWeekReminderTextView(int total) {
        fragmentBinding.weeklyRemindersTextView.setText(getResources()
                .getQuantityString(R.plurals.no_of_calls_this_week,
                        total, total));
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
    public void onEditAction(SimOneReminder simOneReminder) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(EDIT_REMINDER_FRAGMENT_TAG);
        if (prev != null) {
            fragmentTransaction.remove(prev);
        }
        SetReminderDialogFragment addReminderFragment = SetReminderDialogFragment.getInstance(simOneReminder);
        addReminderFragment.setEditMode(true);
        fragmentTransaction.add(addReminderFragment, EDIT_REMINDER_FRAGMENT_TAG).commitNow();
        //addReminderFragment.show(fragmentTransaction, EDIT_REMINDER_FRAGMENT_TAG);
    }

    @Override
    public void onDeleteAction(SimOneReminder simOneReminder) {
        //reminderPresenter.deleteReminder(contactId);
    }

    private void initialisePresenter() {
        this.reminderPresenter = new ReminderPresenter(simOneReminder,
                this);
    }

}
