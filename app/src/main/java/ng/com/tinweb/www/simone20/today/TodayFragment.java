package ng.com.tinweb.www.simone20.today;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.SimOne;
import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;
import ng.com.tinweb.www.simone20.databinding.FragmentTodayBinding;
import ng.com.tinweb.www.simone20.util.LinearLayoutDecorator;

public class TodayFragment extends Fragment implements ITodayView,
        CallActionListener {


    private FragmentTodayBinding fragmentTodayBinding;
    private ITodayPresenter todayPresenter;

    @Inject
    SimOneReminder simOneReminder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimOne.get(getActivity().getApplication())
                .getAppComponent()
                .inject(this);

        setHasOptionsMenu(true);
        this.todayPresenter = new TodayPresenter(simOneReminder,
                this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.todayPresenter = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentTodayBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_today,
                container, false);
        setUpTodayFragment();
        return fragmentTodayBinding.getRoot();
    }

    @Override
    public void onRemindersLoaded(List<SimOneReminder> simOneReminders) {
        // Set total simOneReminders for the day
        fragmentTodayBinding.todayCallsTextView.setText(getResources()
                .getQuantityString(R.plurals.no_of_calls_today,
                        simOneReminders.size(), simOneReminders.size()));
        // TODO use simOneReminders here to make content a bit more dynamic
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragmentTodayBinding.todayCallsRecyclerView.setLayoutManager(layoutManager);
        fragmentTodayBinding.todayCallsRecyclerView.setAdapter(new TodayAdapter(this));
        fragmentTodayBinding.todayCallsRecyclerView.addItemDecoration(new LinearLayoutDecorator(getContext(), null));
    }

    @Override
    public void callContact(String contactName) {
        Toast.makeText(SimOne.getContext(), "I am going to call " + contactName, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onReminderLoadingError(String message) {
        // TODO implement error message showing here. Don't forget
    }

    @Override
    public void onCallClick(String contactName) {
        todayPresenter.callContact(contactName);
    }

    private void setUpTodayFragment() {
        todayPresenter.loadReminders();
    }

}
