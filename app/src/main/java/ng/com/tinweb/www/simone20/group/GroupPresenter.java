package ng.com.tinweb.www.simone20.group;

import java.lang.ref.WeakReference;

/**
 * Created by kamiye on 11/09/2016.
 */
public class GroupPresenter implements IGroupPresenter {

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
}
