package ng.com.tinweb.www.simone20.data.group;

/**
 * Created by kamiye on 20/09/2016.
 */

interface DataStore {

    void save(String name, int interval, SimOneGroup.ActionCallback callback);
    void update(String oldName, String name, int interval, SimOneGroup.ActionCallback callback);
    void getSingle(String name, SimOneGroup.ActionCallback callback);
    void getMultiple(SimOneGroup.GetAllCallback callback);
    boolean delete();
    int getInterval(String groupName);

}
