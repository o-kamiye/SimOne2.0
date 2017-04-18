package ng.com.tinweb.www.simone20.group;

import java.util.List;

import ng.com.tinweb.www.simone20.data.group.SimOneGroup;

/**
 * Created by kamiye on 15/04/2017.
 */

public class GroupContract {

    interface View {

        void onGroupsLoaded(List<SimOneGroup> groups);

        void onGroupsLoadingError(String message);
    }

    interface Presenter {

        void loadGroups();

        void deleteGroup(String groupId);
    }
}
