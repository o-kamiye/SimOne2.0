package ng.com.tinweb.www.simone20.group;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    private FragmentGroupBinding fragmentBinding;
    private FragmentInteractionListener interactionListener;

    @Inject
    GroupPresenter presenter;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            interactionListener = (FragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context +
                    " must implement GroupFragment.FragmentInteractionListener");
        }
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
                interactionListener.showGroupDialogFragment(null);
            }
        });
    }

    @Override
    public void onGroupsLoadingError(String message) {
        // TODO show group loading error here
    }

    @Override
    public void onDeleteSuccess() {
        presenter.loadGroups();
        Toast.makeText(getContext(), "Group deleted successfully", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeleteError(int errorCode) {
        new AlertDialog.Builder(getContext())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setMessage("Error deleting group. Please try again.")
                .create()
                .show();
    }

    @Override
    public void onEditAction(SimOneGroup group) {
        interactionListener.showGroupDialogFragment(group);
    }

    @Override
    public void onDeleteAction(final String groupName) {
        String message = getString(R.string.delete_confirmation, groupName);
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.deleteGroup(groupName);
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onSelectInfo(String name) {
        interactionListener.showGroupMembersFragment(name);
    }

    /**
     * Get groups
     */
    public void loadGroups() {
        presenter.loadGroups();
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

    public interface FragmentInteractionListener {
        void showGroupDialogFragment(@Nullable SimOneGroup group);
        void showGroupMembersFragment(@NonNull String groupName);
    }
}
