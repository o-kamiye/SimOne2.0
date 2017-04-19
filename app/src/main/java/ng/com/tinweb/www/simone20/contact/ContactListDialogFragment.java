package ng.com.tinweb.www.simone20.contact;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import ng.com.tinweb.www.simone20.data.contact.SimOneContact;
import ng.com.tinweb.www.simone20.databinding.FragmentContactListBinding;
import ng.com.tinweb.www.simone20.util.LinearLayoutDecorator;

/**
 * ContactListDialogFragment - Dialog fragment to manage contact list
 * from search option
 */

public class ContactListDialogFragment extends DialogFragment
        implements DialogFragmentContract.View,
        ContactActionListener {

    private static final String BUNDLE_KEY = "query";

    private FragmentContactListBinding fragmentBinding;
    private FragmentInteractionListener interactionListener;
    private DialogFragmentContract.Presenter presenter;
    private String searchQuery;

    @Inject
    SimOneContact contact;

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
        presenter.fetchContacts(searchQuery);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            interactionListener = (FragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context +
                    " must implement ContactListDialogFragment.FragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_list,
                container, false);

        setTitleDimension();
        setupRecyclerView();
        presenter.fetchContacts(searchQuery);
        return fragmentBinding.getRoot();
    }

    @Override
    public void loadContacts(List<SimOneContact> contacts) {
        fragmentBinding.contactListRecyclerView.setAdapter(new ContactListAdapter(contacts, this));
    }

    @Override
    public void onClickAdd(SimOneContact contact) {
        interactionListener.showReminderDialogFragment(contact);
    }

    private void setTitleDimension() {
        TextView titleTextView = (TextView) getDialog().findViewById(android.R.id.title);
        String title = getString(R.string.contact_search_title, searchQuery);
//        getDialog().setTitle(title);
        titleTextView.setText(title);
        titleTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        titleTextView.setTextSize(16);
        titleTextView.setPadding(40,40,0,5);
    }

    private void initialisePresenter() {
        this.presenter = new ContactPresenter(this, contact);
    }

    private void setupRecyclerView() {
        Context context = fragmentBinding.getRoot().getContext();
        fragmentBinding.contactListRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        fragmentBinding.contactListRecyclerView.addItemDecoration(new LinearLayoutDecorator(context,
                null));
    }

    public interface FragmentInteractionListener {
        void showReminderDialogFragment(SimOneContact contact);
    }
}
