package ng.com.tinweb.www.simone20.group;

import java.util.List;

import ng.com.tinweb.www.simone20.data.group.SimOneGroup;

/**
 * Created by kamiye on 11/09/2016.
 */
interface IGroupView {

    void onGroupsLoaded(List<SimOneGroup> groups);
    void onGroupsLoadingError(String message);

    interface IGroupFragmentView {
        void onAddGroupSuccess();
        void onAddGroupError(String message);
    }
}
