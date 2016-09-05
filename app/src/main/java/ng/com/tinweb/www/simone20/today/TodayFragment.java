package ng.com.tinweb.www.simone20.today;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.databinding.FragmentReminderBinding;
import ng.com.tinweb.www.simone20.util.LinearLayoutDecorator;

public class TodayFragment extends Fragment implements ITodayView {


    private static TodayFragment todayFragment;
    private FragmentReminderBinding fragmentReminderBinding;
    private ITodayPresenter todayPresenter;
    private CallActionListener callActionListener;

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
        this.callActionListener = new CallActionListener() {
            @Override
            public void onCallClick(String contactName) {
                todayPresenter.callContact(contactName);
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.todayPresenter = null;
        this.callActionListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentReminderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reminder,
                container, false);
        setUpTodayFragment();
        // TODO use the presenter to handle all UI callbacks and actions such as clicking on the call icon
        return fragmentReminderBinding.getRoot();
    }

    @Override
    public void setSummaryDisplay(int remindersCount) {
        fragmentReminderBinding.todayCallsTextView.setText(getResources()
                .getQuantityString(R.plurals.no_of_calls_today,
                remindersCount, remindersCount));
    }

    private void setUpTodayFragment() {
        setFragmentTitle();

        // TODO use presenter here to interact with the model to get today's calls
        todayPresenter.setReminderCount();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragmentReminderBinding.todayCallsRecyclerView.setLayoutManager(layoutManager);
        fragmentReminderBinding.todayCallsRecyclerView.setAdapter(new TodayAdapter(callActionListener));
        fragmentReminderBinding.todayCallsRecyclerView.addItemDecoration(new LinearLayoutDecorator(getContext(), null));
    }

    private void setFragmentTitle() {
        getActivity().setTitle(R.string.today_fragment_title);
    }

    public interface CallActionListener {
        void onCallClick(String contactName);
    }

}
