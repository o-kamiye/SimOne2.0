package ng.com.tinweb.www.simone20.group;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
 * GroupFragment - Fragment class to show groups tab
 */
public class GroupFragment extends Fragment implements GroupContract.View,
        GroupActionsListener {

    private static final String ADD_GROUP_FRAGMENT_TAG = "add_new_group";

    private FragmentGroupBinding fragmentBinding;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_group,
                container, false);
        setUpRecyclerView();
        loadGroups();
        return fragmentBinding.getRoot();
    }

    @Override
    public void onGroupsLoaded(List<SimOneGroup> groups) {

        fragmentBinding.totalGroupsTextView.setText(getResources()
                .getQuantityString(R.plurals.no_of_groups,
                        groups.size(), groups.size()));

        fragmentBinding.groupsRecyclerView.setAdapter(new GroupAdapter(groups, this));

        fragmentBinding.groupsFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGroupDialogFragment(null);
            }
        });
    }

    @Override
    public void onGroupsLoadingError(String message) {
        // TODO show group loading error here
    }

    @Override
    public void onEditAction(SimOneGroup group) {
        showGroupDialogFragment(group);
    }

    @Override
    public void onDeleteAction(String groupId) {
        groupPresenter.deleteGroup(groupId);
    }

    /**
     * Get groups
     */
    public void loadGroups() {
        groupPresenter.loadGroups();
    }

    /**
     * Show input dialog to save and edit group
     *
     * @param group {@code null} if creating group and edit if otherwise
     */
    private void showGroupDialogFragment(@Nullable SimOneGroup group) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(ADD_GROUP_FRAGMENT_TAG);
        if (prev != null) {
            fragmentTransaction.remove(prev);
        }
        GroupDialogFragment groupDialogFragment = GroupDialogFragment.getInstance(group);
        fragmentTransaction.add(groupDialogFragment, ADD_GROUP_FRAGMENT_TAG).commitNow();
    }

    /**
     * Setup recycler view
     */
    private void setUpRecyclerView() {
        Context context = fragmentBinding.getRoot().getContext();
        fragmentBinding.groupsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        fragmentBinding.groupsRecyclerView.addItemDecoration(new LinearLayoutDecorator(context,
                null));
    }
}
