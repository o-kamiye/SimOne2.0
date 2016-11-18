package ng.com.tinweb.www.simone20.reminder;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.data.contact.SimOneContact;
import ng.com.tinweb.www.simone20.data.reminder.Reminder;
import ng.com.tinweb.www.simone20.databinding.FragmentAddReminderBinding;
import ng.com.tinweb.www.simone20.helper.Injection;

/**
 * Created by kamiye on 28/09/2016.
 */

public class SetReminderDialogFragment extends DialogFragment
        implements IReminderView.IReminderFragmentView, View.OnClickListener,
        RadioGroup.OnCheckedChangeListener {

    private static final String INPUT_BUNDLE = "input_fragment";

    private IReminderPresenter.IReminderFragmentPresenter fragmentPresenter;
    private FragmentAddReminderBinding fragmentAddReminderBinding;
    private SimOneContact contact;
    private boolean isEditMode;

    public static SetReminderDialogFragment getInstance(SimOneContact contact) {
        SetReminderDialogFragment inputFragment = new SetReminderDialogFragment();

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
        if (isEditMode) {
            populateFormFields();
        }
        fragmentAddReminderBinding.reminderSelectionRadioGroup.setOnCheckedChangeListener(this);
        fragmentAddReminderBinding.cancelButton.setOnClickListener(this);
        fragmentAddReminderBinding.saveButton.setOnClickListener(this);
        return fragmentAddReminderBinding.getRoot();
    }

    @Override
    public void onSetReminderSuccess() {
        dismiss();
        String successMessage = (isEditMode) ? "Reminder updated successfully" :
                getString(R.string.add_reminder_success_toast,
                contact.getName());
        Toast.makeText(getContext(), successMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSetReminderError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        fragmentAddReminderBinding.inputErrorTextView.setText(message);
        fragmentAddReminderBinding.inputErrorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == fragmentAddReminderBinding.cancelButton.getId()) {
            dismiss();
        }
        if (view.getId() == fragmentAddReminderBinding.saveButton.getId()) {
            int checkedId = fragmentAddReminderBinding.reminderSelectionRadioGroup.getCheckedRadioButtonId();
            if (checkedId == fragmentAddReminderBinding.intervalRadioButton.getId()) {
                String intervalInput = fragmentAddReminderBinding.intervalEditText.getText().toString();
                if (intervalInput.equals("")) {
                    fragmentAddReminderBinding.inputErrorTextView.setVisibility(View.VISIBLE);
                }
                else {
                    int interval = Integer.parseInt(intervalInput);
                    fragmentPresenter.setReminder(null, interval, isEditMode);
                }
            }
            // TODO add implementation for group option when group story has been defined
//            else if (checkedId == fragmentAddReminderBinding.groupRadioButton.getId()) {
//                String groupName = (String) fragmentAddReminderBinding.groupListSpinner.getSelectedItem();
//                fragmentPresenter.setReminder(groupName, 0);
//            }
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

    public void setEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
    }

    private void initialisePresenter() {
        int id = (isEditMode) ? ((Reminder) contact).getReminderContactId() : contact.getId();
        String name = (isEditMode) ? ((Reminder) contact).getContactName() : contact.getName();

        this.fragmentPresenter = new ReminderPresenter.SetReminderPresenter(this,
                Injection.getReminder(id, name));
    }

    private void populateFormFields() {
        Reminder reminder = (Reminder) contact;
        if (reminder.getContactGroup() == null) {
            fragmentAddReminderBinding.intervalRadioButton.setChecked(true);
            fragmentAddReminderBinding.intervalEditText.setText(String.valueOf(reminder.getInterval()));
        }
        else {
            fragmentAddReminderBinding.groupRadioButton.setChecked(true);
            // TODO implement group setting here
        }
        fragmentAddReminderBinding.saveButton.setText("Update");

    }

    private void setTitleDimension() {
        TextView titleTextView = (TextView) getDialog().findViewById(android.R.id.title);
        String title = (isEditMode) ? getString(R.string.update_reminder_fragment_title,
                ((Reminder) contact).getContactName()) :
                getString(R.string.add_reminder_fragment_title, contact.getName());
        titleTextView.setText(title);
        titleTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        titleTextView.setTextSize(16);
        titleTextView.setPadding(40,40,0,40);
    }
}