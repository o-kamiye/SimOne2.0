package ng.com.tinweb.www.simone20.group;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.SimOne;
import ng.com.tinweb.www.simone20.data.group.SimOneGroup;
import ng.com.tinweb.www.simone20.databinding.FragmentGroupBinding;
import ng.com.tinweb.www.simone20.util.LinearLayoutDecorator;

/**
 * Created by kamiye on 11/09/2016.
 */
public class GroupFragment extends Fragment implements GroupContract.View,
        GroupActionsListener {

    private static final String ADD_GROUP_FRAGMENT_TAG = "add_new_group";

    private FragmentGroupBinding groupBinding;

    @Inject
    GroupPresenter groupPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimOne.get(getActivity().getApplication())
                .getAppComponent()
                .subComponent(new GroupModule(this))
                .inject(this);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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
    public void onGroupsLoaded(List<SimOneGroup> groups) {
        // TODO use groups here to fetch dynamic data
        groupBinding.totalGroupsTextView.setText(getResources()
                .getQuantityString(R.plurals.no_of_groups,
                        groups.size(), groups.size()));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        groupBinding.groupsRecyclerView.setLayoutManager(layoutManager);
        groupBinding.groupsRecyclerView.addItemDecoration(new LinearLayoutDecorator(getContext(), null));
        groupBinding.groupsRecyclerView.setAdapter(new GroupAdapter(this));

        groupBinding.groupsFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(ADD_GROUP_FRAGMENT_TAG);
                if (prev != null) {
                    fragmentTransaction.remove(prev);
                }
                GroupDialogFragment groupDialogFragment = GroupDialogFragment.getInstance();
                fragmentTransaction.add(groupDialogFragment, ADD_GROUP_FRAGMENT_TAG).commitNow();
            }
        });
    }

    @Override
    public void onGroupsLoadingError(String message) {
        // TODO show group loading error here
    }

    @Override
    public void onEditAction(String groupId) {
        groupPresenter.editGroup(groupId);
    }

    @Override
    public void onDeleteAction(String groupId) {
        groupPresenter.deleteGroup(groupId);
    }

    private void setUpFragment() {
        groupPresenter.loadGroups();
    }
}
