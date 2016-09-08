package ng.com.tinweb.www.simone20.today;

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
import ng.com.tinweb.www.simone20.SimOneApplication;
import ng.com.tinweb.www.simone20.databinding.FragmentReminderBinding;
import ng.com.tinweb.www.simone20.databinding.FragmentTodayBinding;
import ng.com.tinweb.www.simone20.util.LinearLayoutDecorator;

public class TodayFragment extends Fragment implements ITodayView,
        CallActionListener {


    private static TodayFragment todayFragment;
    private FragmentTodayBinding fragmentReminderBinding;
    private ITodayPresenter todayPresenter;

    public static TodayFragment newInstance() {
        if (todayFragment == null) {
            todayFragment = new TodayFragment();
        }
        return todayFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.todayPresenter = new TodayPresenter(this);
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
        fragmentReminderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_today,
                container, false);
        setUpTodayFragment();
        return fragmentReminderBinding.getRoot();
    }

    @Override
    public void setTotalReminders(int remindersCount) {
        fragmentReminderBinding.todayCallsTextView.setText(getResources()
                .getQuantityString(R.plurals.no_of_calls_today,
                remindersCount, remindersCount));
    }

    @Override
    public void callContact(String contactName) {
        Toast.makeText(SimOneApplication.getContext(), "I am going to call " + contactName, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onCallClick(String contactName) {
        todayPresenter.callContact(contactName);
    }

    private void setUpTodayFragment() {
        setFragmentTitle();

        // TODO use presenter here to interact with the model to getSingle today's calls
        todayPresenter.setReminderCount();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragmentReminderBinding.todayCallsRecyclerView.setLayoutManager(layoutManager);
        fragmentReminderBinding.todayCallsRecyclerView.setAdapter(new TodayAdapter(this));
        fragmentReminderBinding.todayCallsRecyclerView.addItemDecoration(new LinearLayoutDecorator(getContext(), null));
    }

    private void setFragmentTitle() {
        getActivity().setTitle(R.string.today_fragment_title);
    }

}
