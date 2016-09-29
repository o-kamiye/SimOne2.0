package ng.com.tinweb.www.simone20.reminder;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.data.contact.SimOneContact;
import ng.com.tinweb.www.simone20.databinding.FragmentAddReminderBinding;

/**
 * Created by kamiye on 28/09/2016.
 */

public class AddReminderDialogFragment extends DialogFragment {

    private static final String INPUT_BUNDLE = "input_fragment";

    private FragmentAddReminderBinding fragmentAddReminderBinding;
    private SimOneContact contact;

    public static AddReminderDialogFragment getInstance(SimOneContact contact) {
        AddReminderDialogFragment inputFragment = new AddReminderDialogFragment();

        Bundle bundle = new Bundle();

        bundle.putSerializable(INPUT_BUNDLE, contact);
        inputFragment.setArguments(bundle);
        return inputFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contact = (SimOneContact) getArguments().getSerializable(INPUT_BUNDLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentAddReminderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_reminder,
                container, false);

        String title = getString(R.string.add_reminder_fragment_title, contact.getName());
        getDialog().setTitle(title);

        TextView titleTextView = (TextView) getDialog().findViewById(android.R.id.title);
        setTitleDimension(titleTextView);

        return fragmentAddReminderBinding.getRoot();
    }

    private void setTitleDimension(TextView titleTextView) {
        titleTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        titleTextView.setTextSize(16);
        titleTextView.setPadding(40,40,0,5);
    }
}