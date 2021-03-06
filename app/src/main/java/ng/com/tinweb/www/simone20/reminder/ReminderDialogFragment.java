package ng.com.tinweb.www.simone20.reminder;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.SimOne;
import ng.com.tinweb.www.simone20.data.contact.SimOneContact;
import ng.com.tinweb.www.simone20.data.group.SimOneGroup;
import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;
import ng.com.tinweb.www.simone20.databinding.FragmentAddReminderBinding;

/**
 * Created by kamiye on 28/09/2016.
 */

public class ReminderDialogFragment extends DialogFragment
        implements DialogFragmentContract.View, View.OnClickListener,
        RadioGroup.OnCheckedChangeListener {

    private static final String INPUT_BUNDLE = "input_fragment";

    private DialogFragmentContract.Presenter presenter;
    private FragmentAddReminderBinding fragmentAddReminderBinding;
    private SimOneContact contact;
    private Map<String, Integer> groupsMap;
    private boolean isEditMode;
    private ArrayAdapter<String> groupListAdapter;
    private InteractionListener interactionListener;

    @Inject
    SimOneReminder reminder;

    @Inject
    SimOneGroup group;

    public static ReminderDialogFragment getInstance(SimOneContact contact) {
        ReminderDialogFragment inputFragment = new ReminderDialogFragment();

        Bundle bundle = new Bundle();

        bundle.putSerializable(INPUT_BUNDLE, contact);
        inputFragment.setArguments(bundle);
        return inputFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimOne.get(getActivity().getApplication())
                .getAppComponent()
                .inject(this);

        contact = (SimOneContact) getArguments().getSerializable(INPUT_BUNDLE);
        initialisePresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InteractionListener) {
            interactionListener = (InteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString() +
                    " must implement ReminderDialogFragment.InteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentAddReminderBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_add_reminder,
                container, false);
        setTitleDimension();
        populateGroupListSpinner();
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
        interactionListener.onReminderSet();
        dismiss();
        String successMessage = (isEditMode) ? getString(R.string.update_reminder_success) :
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
    public void onGroupNamesLoaded(Map<String, Integer> groupsMap) {
        this.groupsMap = groupsMap;
        List<String> groupNames = new ArrayList<>(groupsMap.keySet());
        Collections.sort(groupNames);
        groupListAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, groupNames);
        fragmentAddReminderBinding.groupListSpinner.setAdapter(groupListAdapter);
    }

    @Override
    public void onGroupNamesLoadingError(String message) {
        // TODO implement nice error message display here
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == fragmentAddReminderBinding.cancelButton.getId()) {
            dismiss();
        }
        if (view.getId() == fragmentAddReminderBinding.saveButton.getId()) {
            int checkedId = fragmentAddReminderBinding.reminderSelectionRadioGroup
                    .getCheckedRadioButtonId();
            if (checkedId == fragmentAddReminderBinding.intervalRadioButton.getId()) {
                String intervalInput = fragmentAddReminderBinding.intervalEditText.
                        getText().toString();
                if (intervalInput.equals("")) {
                    fragmentAddReminderBinding.inputErrorTextView.setVisibility(View.VISIBLE);
                }
                else {
                    int interval = Integer.parseInt(intervalInput);
                    presenter.setReminder(null, interval, isEditMode);
                }
            }
            else if (checkedId == fragmentAddReminderBinding.groupRadioButton.getId()) {
                String groupName = (String) fragmentAddReminderBinding.groupListSpinner
                        .getSelectedItem();
                if (groupName == null)
                    fragmentAddReminderBinding.inputErrorTextView.setVisibility(View.VISIBLE);
                else
                    presenter.setReminder(groupName, groupsMap.get(groupName), isEditMode);
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

    public void setEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
    }

    private void initialisePresenter() {
        int id = (isEditMode) ? ((SimOneReminder) contact).getReminderContactId() : contact.getId();
        String name = (isEditMode) ? ((SimOneReminder) contact).getContactName() : contact.getName();

        reminder.setContactId(id);
        reminder.setContactName(name);

        this.presenter = new ReminderPresenter.SetReminderPresenter(this,
                reminder, group);
    }

    private void populateGroupListSpinner() {
        presenter.loadGroupNames();
    }

    private void populateFormFields() {
        SimOneReminder reminder = (SimOneReminder) contact;
        if (reminder.getContactGroup() == null || reminder.getContactGroup().equals("")) {
            fragmentAddReminderBinding.intervalRadioButton.setChecked(true);
            fragmentAddReminderBinding.intervalEditText
                    .setText(String.valueOf(reminder.getInterval()));
        }
        else {
            fragmentAddReminderBinding.groupRadioButton.setChecked(true);
            fragmentAddReminderBinding.intervalEditText.setVisibility(View.GONE);
            fragmentAddReminderBinding.groupListSpinner.setVisibility(View.VISIBLE);
            fragmentAddReminderBinding.groupListSpinner.setSelection(
                    groupListAdapter.getPosition(reminder.getContactGroup())
            );
        }
        fragmentAddReminderBinding.saveButton.setText(R.string.txt_update);

    }

    private void setTitleDimension() {
        TextView titleTextView = (TextView) getDialog().findViewById(android.R.id.title);
        String title = (isEditMode) ? getString(R.string.update_reminder_fragment_title,
                ((SimOneReminder) contact).getContactName()) :
                getString(R.string.add_reminder_fragment_title, contact.getName());
        titleTextView.setText(title);
        titleTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        titleTextView.setTextSize(16);
        titleTextView.setPadding(40,40,0,40);
    }

    public interface InteractionListener {

        void onReminderSet();

    }
}