package ng.com.tinweb.www.simone20.group;

import ng.com.tinweb.www.simone20.data.group.SimOneGroup;

/**
 * GroupActionsListener - Interface for interacting with group adapter
 */
interface GroupActionsListener {

    /**
     * Method to call when edit icon is clicked in
     * groups fragment
     *
     * @param group {@link SimOneGroup} group to edit
     */
    void onEditAction(SimOneGroup group);

    /**
     * Method to call when delete icon is clicked in
     * groups fragment
     *
     * @param groupName Name of group to delete
     */
    void onDeleteAction(String groupName);
}
