package ng.com.tinweb.www.simone20.group;

/**
 * Created by kamiye on 11/09/2016.
 */
interface IGroupPresenter {
    void loadGroups();

    void editGroup(String groupId);

    void deleteGroup(String groupId);

    interface IAddGroupPresenter {
        void addGroup(String name, int interval);
    }
}
