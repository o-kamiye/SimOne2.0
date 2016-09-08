package ng.com.tinweb.www.simone20.reminder;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
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

    private static ReminderFragment reminderFragment;
    private IReminderPresenter reminderPresenter;
    private FragmentReminderBinding fragmentBinding;

    public static ReminderFragment newInstance() {
        if (reminderFragment == null) {
            reminderFragment = new ReminderFragment();
        }
        return reminderFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialisePresenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.reminderPresenter = null;
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
    public void onEditAction(String contactId) {
        Toast.makeText(getContext(), "Editing item at "+contactId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteAction(String contactId) {
        Toast.makeText(getContext(), "Deleting item at "+contactId, Toast.LENGTH_SHORT).show();
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
    }
}
