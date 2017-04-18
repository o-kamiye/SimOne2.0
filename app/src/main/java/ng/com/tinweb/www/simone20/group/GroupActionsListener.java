package ng.com.tinweb.www.simone20.group;

import ng.com.tinweb.www.simone20.data.group.SimOneGroup;

/**
 * GroupActionsListener - Interface for interacting with group adapter
 */
interface GroupActionsListener {

    void onEditAction(SimOneGroup group);

    void onDeleteAction(String groupId);
}
