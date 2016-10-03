package ng.com.tinweb.www.simone20.reminder;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.data.contact.SimOneContact;
import ng.com.tinweb.www.simone20.databinding.FragmentAddReminderBinding;
import ng.com.tinweb.www.simone20.helper.Injection;

import static ng.com.tinweb.www.simone20.R.id.reminderSelectionRadioGroup;

/**
 * Created by kamiye on 28/09/2016.
 */

public class AddReminderDialogFragment extends DialogFragment
        implements IReminderView.IReminderFragmentView, View.OnClickListener,
        RadioGroup.OnCheckedChangeListener {

    private static final String INPUT_BUNDLE = "input_fragment";

    private IReminderPresenter.IReminderFragmentPresenter fragmentPresenter;
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
        initialisePresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.fragmentPresenter = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentAddReminderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_reminder,
                container, false);
        setTitleDimension();
        fragmentAddReminderBinding.reminderSelectionRadioGroup.setOnCheckedChangeListener(this);
        fragmentAddReminderBinding.cancelButton.setOnClickListener(this);
        fragmentAddReminderBinding.saveButton.setOnClickListener(this);
        return fragmentAddReminderBinding.getRoot();
    }

    @Override
    public void onAddReminderSuccess() {

    }

    @Override
    public void onAddReminderError(String message) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == fragmentAddReminderBinding.cancelButton.getId()) {
            dismiss();
        }
        if (view.getId() == fragmentAddReminderBinding.saveButton.getId()) {
            int checkedId = fragmentAddReminderBinding.reminderSelectionRadioGroup.getCheckedRadioButtonId();
            if (checkedId == fragmentAddReminderBinding.intervalRadioButton.getId()) {
                int interval = Integer.parseInt(
                        fragmentAddReminderBinding.intervalEditText.getText().toString());
                fragmentPresenter.addReminder(null, interval);
            }
            else if (checkedId == fragmentAddReminderBinding.groupRadioButton.getId()) {
                String groupName = (String) fragmentAddReminderBinding.groupListSpinner.getSelectedItem();
                fragmentPresenter.addReminder(groupName, 0);
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == fragmentAddReminderBinding.intervalRadioButton.getId()) {
            fragmentAddReminderBinding.intervalEditText.setVisibility(View.VISIBLE);
            fragmentAddReminderBinding.groupListSpinner.setVisibility(View.GONE);
        }
        if (i == fragmentAddReminderBinding.groupRadioButton.getId()) {
            fragmentAddReminderBinding.intervalEditText.setVisibility(View.GONE);
            fragmentAddReminderBinding.groupListSpinner.setVisibility(View.VISIBLE);
        }
    }

    private void initialisePresenter() {
        this.fragmentPresenter = new ReminderPresenter.AddReminderPresenter(this,
                Injection.getReminder(contact.getId(), contact.getName()));
    }

    private void setTitleDimension() {
        TextView titleTextView = (TextView) getDialog().findViewById(android.R.id.title);
        String title = getString(R.string.add_reminder_fragment_title, contact.getName());
        titleTextView.setText(title);
        titleTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        titleTextView.setTextSize(16);
        titleTextView.setPadding(40,40,0,40);
    }
}