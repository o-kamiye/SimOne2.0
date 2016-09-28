package ng.com.tinweb.www.simone20.contact;

import android.content.DialogInterface;
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

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.data.contact.SimOneContact;
import ng.com.tinweb.www.simone20.databinding.FragmentContactListBinding;
import ng.com.tinweb.www.simone20.util.LinearLayoutDecorator;

/**
 * Created by kamiye on 28/09/2016.
 */

public class ContactListDialogFragment extends DialogFragment {

    private static final String BUNDLE_KEY = "query";

    private FragmentContactListBinding fragmentBinding;
    private String searchQuery;

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
        searchQuery = getArguments().getString(BUNDLE_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_list,
                container, false);

        String title = getString(R.string.contact_search_title, searchQuery);
        getDialog().setTitle(title);

        TextView titleTextView = (TextView) getDialog().findViewById(android.R.id.title);
        setTitleDimension(titleTextView);

        setupContactList();
        return fragmentBinding.getRoot();
    }

    private void setupContactList() {
        List<SimOneContact> contacts = new SimOneContact().getList(searchQuery);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragmentBinding.contactListRecyclerView.setLayoutManager(linearLayoutManager);
        fragmentBinding.contactListRecyclerView.addItemDecoration(new LinearLayoutDecorator(getContext(), null));
        fragmentBinding.contactListRecyclerView.setAdapter(new ContactListAdapter(contacts));
    }

    private void setTitleDimension(TextView titleTextView) {
        titleTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        titleTextView.setTextSize(16);
        titleTextView.setPadding(40,40,0,5);
    }

}
