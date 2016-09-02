package ng.com.tinweb.www.simone20.today;

import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.databinding.FragmentReminderBinding;
import ng.com.tinweb.www.simone20.util.LinearLayoutDecorator;

public class TodayFragment extends Fragment {


    private static TodayFragment todayFragment;
    private FragmentReminderBinding fragmentReminderBinding;

    public static TodayFragment newInstance() {
        if (todayFragment == null) {
            todayFragment = new TodayFragment();
        }
        return todayFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentReminderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reminder,
                container, false);
        setUpTodayFragment();
        return fragmentReminderBinding.getRoot();
    }

    private void setUpTodayFragment() {
        setFragmentTitle();
        int remindersCount = getRemindersCount();
        Resources res = getResources();
        fragmentReminderBinding.todayCallsTextView.setText(res.getQuantityString(R.plurals.no_of_calls_today,
                remindersCount, remindersCount));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragmentReminderBinding.todayCallsRecyclerView.setLayoutManager(layoutManager);
        fragmentReminderBinding.todayCallsRecyclerView.setAdapter(new TodayAdapter());
        fragmentReminderBinding.todayCallsRecyclerView.addItemDecoration(new LinearLayoutDecorator(getContext(), null));
    }

    private int getRemindersCount() {
        return 0;
    }

    private void setFragmentTitle() {
        getActivity().setTitle(R.string.today_fragment_title);
    }
}
