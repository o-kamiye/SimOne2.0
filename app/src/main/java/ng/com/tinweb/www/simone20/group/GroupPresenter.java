package ng.com.tinweb.www.simone20.group;

import java.lang.ref.WeakReference;

import ng.com.tinweb.www.simone20.data.group.SimOneGroup;

import static ng.com.tinweb.www.simone20.data.group.SimOneGroup.DB_INSERT_ERROR;
import static ng.com.tinweb.www.simone20.data.group.SimOneGroup.GROUP_EXISTS_ERROR;

/**
 * Created by kamiye on 11/09/2016.
 */
class GroupPresenter implements IGroupPresenter {

    private WeakReference<IGroupView> groupView;

    public GroupPresenter(IGroupView groupView) {
        this.groupView = new WeakReference<>(groupView);
    }

    @Override
    public void setTotalGroupsCount() {
        if (groupView.get() != null) {
            int groupsCount = 2;
            groupView.get().setGroupsCountTextView(groupsCount);
        }
    }

    @Override
    public void editGroup(String groupId) {
        if (groupView.get() != null) {
            // TODO get the group from the groupId
            // TODO get the view to show the pop up dialog with the group details
            // TODO figure out the implementation of how this will work
        }
    }

    @Override
    public void deleteGroup(String groupId) {
        if (groupView.get() != null) {
            // TODO get the group from the groupId
            // TODO call the remove method on the group object
            // TODO if the removal was successful, then call the view's remove successful callback method
            // TODO if the removal was unsuccessful, then call the view's remove unsuccessful callback method
        }
    }

    static class AddGroupPresenter implements IGroupPresenter.IAddGroupPresenter{

        private WeakReference<IGroupView.IGroupFragmentView> fragmentView;

        AddGroupPresenter(IGroupView.IGroupFragmentView fragmentView) {
            this.fragmentView = new WeakReference<>(fragmentView);
        }

        @Override
        public void addGroup(String name, int interval) {
            if (fragmentView.get() != null) {
                // TODO validate group
                SimOneGroup group = new SimOneGroup(name, interval);
                group.create(new SimOneGroup.ActionCallback() {
                    @Override
                    public void onSuccess() {
                        fragmentView.get().onAddGroupSuccess();
                    }

                    @Override
                    public void onError(int errorCode) {
                        String errorMessage = "Oops! We didn't anticipate this one, please try again ;)";
                        switch (errorCode) {
                            case DB_INSERT_ERROR:
                                errorMessage = "Hmmmm, something went wrong. Try again";
                                break;
                            case GROUP_EXISTS_ERROR:
                                errorMessage = "You have a group with the same name already";
                                break;
                        }
                        fragmentView.get().onAddGroupError(errorMessage);
                    }
                });
            }
        }

    }
}
