package ng.com.tinweb.www.simone20.group;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.SimOne;
import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;
import ng.com.tinweb.www.simone20.databinding.FragmentGroupContactsBinding;
import ng.com.tinweb.www.simone20.util.LinearLayoutDecorator;

/**
 * GroupRemindersFragment - Fragment class to show group contact list
 */
public class GroupRemindersFragment extends DialogFragment implements
        GroupRemindersContract.View, GroupRemindersActionListener {

    private static final String GROUP_NAME = "group_name";

    private FragmentGroupContactsBinding fragmentBinding;
    private FragmentInteractionListener interactionListener;
    private GroupRemindersContract.Presenter presenter;

    private String groupName;

    @Inject
    SimOneReminder reminder;

    public static GroupRemindersFragment newInstance(String groupName) {
        GroupRemindersFragment fragment = new GroupRemindersFragment();
        Bundle args = new Bundle();
        args.putString(GROUP_NAME, groupName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimOne.get(getActivity().getApplicationContext())
                .getAppComponent()
                .inject(this);

        if (getArguments() != null) {
            groupName = getArguments().getString(GROUP_NAME);
        }

        presenter = new GroupRemindersPresenter(this, reminder);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_group_contacts,
                container, false);

        setTitleDimension();
        setupRecyclerView();
        fetchGroupReminders();
        return fragmentBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            interactionListener = (FragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    @Override
    public void onRemindersLoaded(List<SimOneReminder> reminders) {
        fragmentBinding.membersRecyclerView.setAdapter(new GroupRemindersAdapter(reminders, this));
    }

    @Override
    public void onRemindersLoadingError(int errorCode) {
        // TODO: 21/04/2017 show user an error message here
    }

    @Override
    public void onClickDelete(SimOneReminder reminder) {
        interactionListener.onDeleteGroupReminder();
    }

    private void setTitleDimension() {
        TextView titleTextView = (TextView) getDialog().findViewById(android.R.id.title);
//        getDialog().setTitle(title);
        titleTextView.setText(groupName);
        titleTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        titleTextView.setTextSize(16);
        titleTextView.setPadding(40,40,0,5);
    }

    private void setupRecyclerView() {
        Context context = fragmentBinding.getRoot().getContext();
        fragmentBinding.membersRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        fragmentBinding.membersRecyclerView.addItemDecoration(new LinearLayoutDecorator(context,
                null));
    }

    private void fetchGroupReminders() {
        presenter.loadGroupReminders(groupName);
    }

    public interface FragmentInteractionListener {
        void onDeleteGroupReminder();
    }
}
