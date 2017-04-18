package ng.com.tinweb.www.simone20.group;

import java.lang.ref.WeakReference;
import java.util.List;

import ng.com.tinweb.www.simone20.data.group.SimOneGroup;

import static ng.com.tinweb.www.simone20.data.group.SimOneGroup.DB_INSERT_ERROR;
import static ng.com.tinweb.www.simone20.data.group.SimOneGroup.GROUP_EXISTS_ERROR;

/**
 * Created by kamiye on 11/09/2016.
 */
class GroupPresenter implements GroupContract.Presenter {

    private WeakReference<GroupContract.View> groupView;
    private SimOneGroup simOneGroup;

    GroupPresenter(SimOneGroup simOneGroup, GroupContract.View groupView) {
        this.groupView = new WeakReference<>(groupView);
        this.simOneGroup = simOneGroup;
    }

    @Override
    public void loadGroups() {
        simOneGroup.getAll(new SimOneGroup.GetAllCallback() {
            @Override
            public void onSuccess(List<SimOneGroup> groups) {
                if (groupView.get() != null) {
                    groupView.get().onGroupsLoaded(groups);
                }
            }

            @Override
            public void onError(int errorCode) {
                String message = "An unknown error occurred";
                if (groupView.get() != null) {
                    groupView.get().onGroupsLoadingError(message);
                }
            }
        });
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

    static class AddGroupPresenter implements DialogFragmentContract.Presenter {

        private WeakReference<DialogFragmentContract.View> view;
        private SimOneGroup group;

        AddGroupPresenter(DialogFragmentContract.View view, SimOneGroup group) {
            this.view = new WeakReference<>(view);
            this.group = group;
        }

        @Override
        public void addGroup(String name, int interval, boolean isEdit) {
            if (view.get() != null) {
                // TODO validate group
                if (name.isEmpty() || interval < 1) {
                    view.get().onAddGroupError("Group name and interval should not be empty");
                    return;
                }
                SimOneGroup.ActionCallback callback = new SimOneGroup.ActionCallback() {
                    @Override
                    public void onSuccess() {
                        view.get().onAddGroupSuccess();
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
                        view.get().onAddGroupError(errorMessage);
                    }
                };
                String oldName = "";
                if (isEdit) {
                    oldName = group.getName();
                }
                group.setName(name);
                group.setInterval(interval);
                if (isEdit) group.update(oldName, callback);
                else group.save(callback);
            }
        }

    }
}
