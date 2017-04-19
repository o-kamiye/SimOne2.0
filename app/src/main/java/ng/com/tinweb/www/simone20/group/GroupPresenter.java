package ng.com.tinweb.www.simone20.group;

import java.lang.ref.WeakReference;
import java.util.List;

import ng.com.tinweb.www.simone20.data.group.SimOneGroup;

import static ng.com.tinweb.www.simone20.data.group.SimOneGroup.DB_INSERT_ERROR;
import static ng.com.tinweb.www.simone20.data.group.SimOneGroup.GROUP_EXISTS_ERROR;

/**
 * GroupPresenter - Presenter to interact with group model
 */
class GroupPresenter implements GroupContract.Presenter {

    private WeakReference<GroupContract.View> view;
    private SimOneGroup group;

    GroupPresenter(SimOneGroup group, GroupContract.View view) {
        this.view = new WeakReference<>(view);
        this.group = group;
    }

    @Override
    public void loadGroups() {
        group.getAll(new SimOneGroup.GetAllCallback() {
            @Override
            public void onSuccess(List<SimOneGroup> groups) {
                if (view.get() != null) {
                    view.get().onGroupsLoaded(groups);
                }
            }

            @Override
            public void onError(int errorCode) {
                String message = "An unknown error occurred";
                if (view.get() != null) {
                    view.get().onGroupsLoadingError(message);
                }
            }
        });
    }

    @Override
    public void deleteGroup(String groupName) {
        group.delete(groupName, new SimOneGroup.ActionCallback() {
            @Override
            public void onSuccess() {
                if (view.get() != null) view.get().onDeleteSuccess();
            }

            @Override
            public void onError(int errorCode) {
                if (view.get() != null) view.get().onDeleteError(errorCode);
            }
        });
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
                if (isEdit) {
                    String oldName = group.getOldName();
                    if (oldName == null) group.setOldname(group.getName());
                }
                group.setName(name);
                group.setInterval(interval);
                if (isEdit) group.update(callback);
                else group.save(callback);
            }
        }

    }
}
