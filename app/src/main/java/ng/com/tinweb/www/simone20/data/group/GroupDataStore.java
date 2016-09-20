package ng.com.tinweb.www.simone20.data.group;

/**
 * Created by kamiye on 20/09/2016.
 */

interface GroupDataStore {

    boolean save();
    boolean update();
    void getSingle();
    void getMultiple();
    boolean delete();

    interface ActionCallback {

    }
}
