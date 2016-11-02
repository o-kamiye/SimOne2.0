package ng.com.tinweb.www.simone20.group;

import android.databinding.DataBindingUtil;
import android.databinding.tool.util.StringUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.databinding.FragmentAddGroupBinding;

/**
 * Created by kamiye on 09/10/2016.
 */

public class AddGroupDialogFragment extends DialogFragment
        implements View.OnClickListener, IGroupView.IGroupFragmentView {

    private FragmentAddGroupBinding fragmentAddGroupBinding;
    private IGroupPresenter.IAddGroupPresenter fragmentPresenter;

    public static AddGroupDialogFragment getInstance() {
        return new AddGroupDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialisePresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentAddGroupBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_group,
                container, false);
        setTitleDimension();
        fragmentAddGroupBinding.cancelButton.setOnClickListener(this);
        fragmentAddGroupBinding.saveButton.setOnClickListener(this);
        return fragmentAddGroupBinding.getRoot();
    }

    @Override
    public void onClick(View view) {
        fragmentAddGroupBinding.inputErrorTextView.setVisibility(View.GONE);
        if (view.getId() == fragmentAddGroupBinding.cancelButton.getId()) {
            dismiss();
        }
        if (view.getId() == fragmentAddGroupBinding.saveButton.getId()) {
            // TODO implement saving new group logic here
            String groupName = fragmentAddGroupBinding.groupNameEditText.getText().toString();
            String intervalString = fragmentAddGroupBinding.groupIntervalEditText.getText().toString();
            int groupInterval = intervalString.equals("") ? 0 : Integer.valueOf(intervalString);
            fragmentPresenter.addGroup(groupName, groupInterval);
        }
    }

    @Override
    public void onAddGroupSuccess() {
        dismiss();
        Toast.makeText(getContext(), "New group added successfully",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAddGroupError(String message) {
        fragmentAddGroupBinding.inputErrorTextView.setText(message);
        fragmentAddGroupBinding.inputErrorTextView.setVisibility(View.VISIBLE);
    }

    private void setTitleDimension() {
        TextView titleTextView = (TextView) getDialog().findViewById(android.R.id.title);
        String title = getString(R.string.new_group_title);
        titleTextView.setText(title);
        titleTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        titleTextView.setTextSize(16);
        titleTextView.setPadding(40,40,0,40);
    }

    private void initialisePresenter() {
        fragmentPresenter = new GroupPresenter.AddGroupPresenter(this);
    }
}
