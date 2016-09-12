package ng.com.tinweb.www.simone20.group;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.databinding.FragmentGroupBinding;
import ng.com.tinweb.www.simone20.util.LinearLayoutDecorator;

/**
 * Created by kamiye on 11/09/2016.
 */
public class GroupFragment extends Fragment implements IGroupView,
        GroupActionsListener {

    private static GroupFragment groupFragment;
    private FragmentGroupBinding groupBinding;
    private IGroupPresenter groupPresenter;

    public static GroupFragment newInstance() {
        if (groupFragment == null) {
            groupFragment = new GroupFragment();
        }
        return groupFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialisePresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.groupPresenter = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        groupBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_group,
                container, false);
        setUpFragment();
        return groupBinding.getRoot();
    }

    @Override
    public void setGroupsCountTextView(int groupsCount) {
        groupBinding.totalGroupsTextView.setText(getResources()
                .getQuantityString(R.plurals.no_of_groups,
                        groupsCount, groupsCount));
    }

    @Override
    public void onEditAction(String groupId) {
        groupPresenter.editGroup(groupId);
    }

    @Override
    public void onDeleteAction(String groupId) {
        groupPresenter.deleteGroup(groupId);
    }

    private void initialisePresenter() {
        groupPresenter = new GroupPresenter(this);
    }

    private void setUpFragment() {
        groupPresenter.setTotalGroupsCount();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        groupBinding.groupsRecyclerView.setLayoutManager(layoutManager);
        groupBinding.groupsRecyclerView.addItemDecoration(new LinearLayoutDecorator(getContext(), null));
        groupBinding.groupsRecyclerView.setAdapter(new GroupAdapter(this));

        groupBinding.groupsFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "You don't need a presenter action for this", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
