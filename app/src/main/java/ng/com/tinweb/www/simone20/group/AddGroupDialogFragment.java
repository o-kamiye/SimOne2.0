package ng.com.tinweb.www.simone20.group;

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
import ng.com.tinweb.www.simone20.databinding.FragmentAddGroupBinding;

/**
 * Created by kamiye on 09/10/2016.
 */

public class AddGroupDialogFragment extends DialogFragment implements View.OnClickListener {

    private FragmentAddGroupBinding fragmentAddGroupBinding;

    public static AddGroupDialogFragment getInstance() {
        return new AddGroupDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (view.getId() == fragmentAddGroupBinding.cancelButton.getId()) {
            dismiss();
        }
        if (view.getId() == fragmentAddGroupBinding.saveButton.getId()) {
            // TODO implement saving new group logic here
        }
    }

    private void setTitleDimension() {
        TextView titleTextView = (TextView) getDialog().findViewById(android.R.id.title);
        String title = getString(R.string.new_group_title);
        titleTextView.setText(title);
        titleTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        titleTextView.setTextSize(16);
        titleTextView.setPadding(40,40,0,40);
    }

}
