package ng.com.tinweb.www.simone20.group;

import java.util.List;

import ng.com.tinweb.www.simone20.data.group.SimOneGroup;

/**
 * GroupContract - Contract class to manage
 * group package MVP interfaces
 */

class GroupContract {

    interface View {

        /**
         * Notify view if groups was fetched successfully
         *
         * @param groups {@link SimOneGroup} list
         */
        void onGroupsLoaded(List<SimOneGroup> groups);

        /**
         * Notify view if there was an error fetching groups
         *
         * @param message {@link String} error message
         */
        void onGroupsLoadingError(String message);

        /**
         * Notify view of successful group delete action
         */
        void onDeleteSuccess();

        /**
         * Notify group of failed group delete action
         *
         * @param errorCode {@code int} error code
         */
        void onDeleteError(int errorCode);
    }

    interface Presenter {

        /**
         * Load groups
         */
        void loadGroups();

        /**
         * Delete a group
         *
         * @param groupName {@link String} group name
         */
        void deleteGroup(String groupName);
    }
}
