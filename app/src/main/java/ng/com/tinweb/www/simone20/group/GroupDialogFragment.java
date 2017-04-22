package ng.com.tinweb.www.simone20.group;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.SimOne;
import ng.com.tinweb.www.simone20.data.group.SimOneGroup;
import ng.com.tinweb.www.simone20.databinding.FragmentAddGroupBinding;

/**
 * GroupDialogFragment - Dialog fragment for creating and editing group
 */

public class GroupDialogFragment extends DialogFragment
        implements View.OnClickListener, DialogFragmentContract.View {

    private static final String INPUT_BUNDLE = "group";

    private FragmentAddGroupBinding fragmentBinding;
    private DialogFragmentContract.Presenter presenter;
    private FragmentInteractionListener interactionListener;
    private SimOneGroup simOneGroup;
    private boolean isEdit;

    private TextView titleTextView;

    @Inject
    SimOneGroup group;

    public static GroupDialogFragment getInstance(@Nullable SimOneGroup group) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(INPUT_BUNDLE, group);
        GroupDialogFragment fragment = new GroupDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimOne.get(getActivity().getApplication())
                .getAppComponent()
                .inject(this);

        simOneGroup = (SimOneGroup) getArguments().getSerializable(INPUT_BUNDLE);

        initialisePresenter();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            interactionListener = (FragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context +
                    " must implement GroupDialogFragment.FragmentInteractionListener");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_group,
                container, false);
        setTitleDimension();
        if (simOneGroup != null) {
            fillGroupDetails();
            isEdit = true;
            fragmentBinding.saveButton.setText(getString(R.string.txt_update));
        }
        fragmentBinding.cancelButton.setOnClickListener(this);
        fragmentBinding.saveButton.setOnClickListener(this);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onClick(View view) {
        fragmentBinding.inputErrorTextView.setVisibility(View.GONE);
        if (view.getId() == fragmentBinding.cancelButton.getId()) {
            dismiss();
        }
        if (view.getId() == fragmentBinding.saveButton.getId()) {
            // TODO implement saving new group logic here
            String groupName = fragmentBinding.groupNameEditText.getText().toString();
            String intervalString = fragmentBinding.groupIntervalEditText.getText().toString();
            int groupInterval = intervalString.equals("") ? 0 : Integer.valueOf(intervalString);
            presenter.addGroup(groupName, groupInterval, isEdit);
        }
    }

    @Override
    public void onAddGroupSuccess() {
        String message = (isEdit) ? "Group updated successfully" : "New group added successfully";
        interactionListener.onGroupSet();
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        dismiss();
    }

    @Override
    public void onAddGroupError(String message) {
        fragmentBinding.inputErrorTextView.setText(message);
        fragmentBinding.inputErrorTextView.setVisibility(View.VISIBLE);
    }

    private void setTitleDimension() {
        titleTextView = (TextView) getDialog().findViewById(android.R.id.title);
        String title = getString(R.string.new_group_title);
        titleTextView.setText(title);
        titleTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        titleTextView.setTextSize(16);
        titleTextView.setPadding(40,40,0,40);
    }

    private void initialisePresenter() {
        presenter = new GroupPresenter.AddGroupPresenter(this,
                simOneGroup == null ? group : simOneGroup);
    }

    private void fillGroupDetails() {
        String title = "Edit " + simOneGroup.getName();
        titleTextView.setText(title);
        fragmentBinding.groupNameEditText.setText(simOneGroup.getName());
        fragmentBinding.groupIntervalEditText.setText(String.valueOf(simOneGroup.getInterval()));
    }

    public interface FragmentInteractionListener {
        void onGroupSet();
    }
}
