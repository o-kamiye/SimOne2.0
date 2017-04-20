package ng.com.tinweb.www.simone20.data.group;

/**
 * DataStore - Interface that group database must implement
 */

interface DataStore {

    void save(String name, int interval, SimOneGroup.ActionCallback callback);
    void update(String oldName, String name, int interval, SimOneGroup.ActionCallback callback);
    void getSingle(String name, SimOneGroup.ActionCallback callback);
    void getMultiple(SimOneGroup.GetAllCallback callback);
    void delete(String name, SimOneGroup.ActionCallback callback);
    int getInterval(String groupName);

}
