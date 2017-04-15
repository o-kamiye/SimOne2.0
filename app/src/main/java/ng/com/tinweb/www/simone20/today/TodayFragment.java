package ng.com.tinweb.www.simone20.today;

import android.content.Context;
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

public class TodayFragment extends Fragment implements TodayContract.View,
        CallActionListener {


    private FragmentTodayBinding fragmentBinding;

    @Inject
    TodayPresenter todayPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimOne.get(getActivity().getApplication())
                .getAppComponent()
                .subComponent(new TodayModule(this))
                .inject(this);

        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_today,
                container, false);
        setUpTodayFragment();
        setupRecyclerView();
        return fragmentBinding.getRoot();
    }

    @Override
    public void onRemindersLoaded(List<SimOneReminder> reminders) {
        // Set total simOneReminders for the day
        fragmentBinding.todayCallsTextView.setText(getResources()
                .getQuantityString(R.plurals.no_of_calls_today,
                        reminders.size(), reminders.size()));
        fragmentBinding.todayCallsRecyclerView.setAdapter(new TodayAdapter(reminders, this));
    }

    @Override
    public void onReminderLoadingError(String message) {
        // TODO implement error message showing here. Don't forget
    }

    @Override
    public void onCallClick(String contactName, String phoneNumber) {
        callContact(contactName, phoneNumber);
    }

    private void setUpTodayFragment() {
        todayPresenter.loadReminders();
    }

    private void setupRecyclerView() {

        Context context = fragmentBinding.getRoot().getContext();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);

        LinearLayoutDecorator layoutDecorator = new LinearLayoutDecorator(context, null);

        fragmentBinding.todayCallsRecyclerView.setLayoutManager(layoutManager);

        fragmentBinding.todayCallsRecyclerView.addItemDecoration(layoutDecorator);
    }

    private void callContact(String contactName, String phoneNumber) {
        Toast.makeText(getContext(), "Name: " + contactName + ", Phone: " + phoneNumber, Toast.LENGTH_LONG)
                .show();
    }

}
