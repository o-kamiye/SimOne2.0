package ng.com.tinweb.www.simone20.data;

import android.database.Cursor;

/**
 * Created by kamiye on 08/09/2016.
 */
public interface DataStore {

    boolean save(String contactId, String contactName, int interval);
    boolean update(String contactId, String contactName, int interval);
    void get(String contactId, ActionCallback callback);
    boolean delete(String contactId);

    interface ActionCallback {
        void onGetSuccess(String contactId, String contactname, int interval);
        void onGetError();
    }

}
