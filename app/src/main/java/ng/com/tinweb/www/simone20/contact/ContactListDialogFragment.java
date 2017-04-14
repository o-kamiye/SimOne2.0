package ng.com.tinweb.www.simone20.contact;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import ng.com.tinweb.www.simone20.data.contact.SimOneContact;
import ng.com.tinweb.www.simone20.databinding.FragmentContactListBinding;
import ng.com.tinweb.www.simone20.reminder.ReminderDialogFragment;
import ng.com.tinweb.www.simone20.util.LinearLayoutDecorator;

/**
 * Created by kamiye on 28/09/2016.
 */

public class ContactListDialogFragment extends DialogFragment
        implements IContactView, ContactActionListener {

    private static final String BUNDLE_KEY = "query";
    private static final String ADD_REMINDER_FRAGMENT_TAG = "add_reminder";

    private FragmentContactListBinding fragmentBinding;
    private IContactPresenter contactPresenter;
    private String searchQuery;

    @Inject
    SimOneContact simOneContact;

    public static ContactListDialogFragment getInstance(String searchQuery) {
        ContactListDialogFragment dialogFragment = new ContactListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY, searchQuery);
        dialogFragment.setArguments(bundle);

        return dialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimOne.get(getActivity().getApplication())
                .getAppComponent()
                .inject(this);

        searchQuery = getArguments().getString(BUNDLE_KEY);

        initialisePresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        contactPresenter.fetchContacts(searchQuery);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_list,
                container, false);

        TextView titleTextView = (TextView) getDialog().findViewById(android.R.id.title);
        setTitleDimension(titleTextView);

        contactPresenter.fetchContacts(searchQuery);
        return fragmentBinding.getRoot();
    }

    @Override
    public void loadContacts(List<SimOneContact> contacts) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragmentBinding.contactListRecyclerView.setLayoutManager(linearLayoutManager);
        fragmentBinding.contactListRecyclerView.addItemDecoration(new LinearLayoutDecorator(getContext(), null));
        fragmentBinding.contactListRecyclerView.setAdapter(new ContactListAdapter(contacts, this));
    }

    @Override
    public void onClickAdd(SimOneContact contact) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(ADD_REMINDER_FRAGMENT_TAG);
        if (prev != null) {
            fragmentTransaction.remove(prev);
        }
        fragmentTransaction.addToBackStack(null);
        ReminderDialogFragment addReminderFragment = ReminderDialogFragment.getInstance(contact);
        addReminderFragment.show(fragmentTransaction, ADD_REMINDER_FRAGMENT_TAG);
    }

    private void setTitleDimension(TextView titleTextView) {
        String title = getString(R.string.contact_search_title, searchQuery);
//        getDialog().setTitle(title);
        titleTextView.setText(title);
        titleTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        titleTextView.setTextSize(16);
        titleTextView.setPadding(40,40,0,5);
    }

    private void initialisePresenter() {
        this.contactPresenter = new Presenter(this, simOneContact);
    }
}
